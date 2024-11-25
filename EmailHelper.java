package com.example.aviation_solutions;

import android.util.Base64;  // Use android.util.Base64 for Base64 encoding on lower API levels

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import java.security.GeneralSecurityException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class EmailHelper extends AppCompatActivity {

    private static final String APPLICATION_NAME = "Mailer";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    // Use Gmail SEND scope to send emails
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/gmail.send");

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = EmailHelper.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }

    public static void sendEmail(String toEmailAddress, String subject, String bodyText) {
        try {
            // Build a new authorized API client service
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Create the email content
            MimeMessage email = createEmail(toEmailAddress, "me", subject, bodyText);
            Message message = createMessageWithEmail(email);

            // Send the email
            service.users().messages().send("me", message).execute();
            System.out.println("Email sent to " + toEmailAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a MimeMessage using the provided parameters
    private static MimeMessage createEmail(String to, String from, String subject, String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new javax.mail.internet.InternetAddress(from));
        email.addRecipient(RecipientType.TO, new javax.mail.internet.InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    // Create a Message object from the MimeMessage and encode it in base64 using android.util.Base64
    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);

        // Use android.util.Base64 for encoding
        byte[] encodedEmail = Base64.encode(bytes.toByteArray(), Base64.URL_SAFE);

        Message message = new Message();
        message.setRaw(new String(encodedEmail));
        return message;
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Example of sending an email
        String toEmail = "recipient@example.com";
        String subject = "Announcement Subject";
        String bodyText = "This is the body of the announcement email.";

        sendEmail(toEmail, subject, bodyText);
    }
}
