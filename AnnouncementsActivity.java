package com.example.aviation_solutions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity {

    private EditText subjectEditText;
    private EditText bodyEditText;
    private RecyclerView usersRecyclerView;
    private Button sendEmailButton;

    private List<User> usersList = new ArrayList<>();
    private List<String> selectedUsersEmails = new ArrayList<>();
    private UserAdapter2 userAdapter;

    private DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    private DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("announcements");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        // Initialize UI components
        subjectEditText = findViewById(R.id.subjectEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        sendEmailButton = findViewById(R.id.sendEmailButton);

        // Setup RecyclerView
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter2(usersList, new UserAdapter2.UserSelectionListener() {
            @Override
            public void onUserSelected(String email) {
                selectedUsersEmails.add(email);
            }

            @Override
            public void onUserDeselected(String email) {
                selectedUsersEmails.remove(email);
            }
        });
        usersRecyclerView.setAdapter(userAdapter);

        // Fetch users from Firebase
        fetchUsersFromFirebase();

        // Set onClickListener for the send email button
        sendEmailButton.setOnClickListener(v -> sendAnnouncementEmails());
    }

    private void fetchUsersFromFirebase() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        usersList.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void sendAnnouncementEmails() {
        // Get the subject and body from the EditText fields
        String subject = subjectEditText.getText().toString().trim();
        String body = bodyEditText.getText().toString().trim();

        // Send the email to each selected user
        for (String email : selectedUsersEmails) {
            try {
                // Attempt to send the email
                EmailHelper.sendEmail(email, subject, body);
            } catch (Exception e) {
                // Catch any exceptions that are thrown by the sendEmail method
                e.printStackTrace();  // Handle exception properly
            }
        }
         Toast.makeText(this, "Emails are being sent.", Toast.LENGTH_SHORT).show();

        // Save the announcement in Firebase Database
        String timestamp = String.valueOf(System.currentTimeMillis());
        Announcement announcement = new Announcement(subject, body, timestamp);
        announcementsRef.child(timestamp).setValue(announcement);

    }
}

