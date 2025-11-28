package com.example.aviation_solutions;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteFeedbackActivity extends AppCompatActivity {

    private EditText etUserEmail, etFeedbackMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feedback);

        // Initialize EditText fields
        etUserEmail = findViewById(R.id.etUserEmail);
        etFeedbackMessage = findViewById(R.id.etFeedbackMessage);
    }

    // Button click handler to write feedback data to Firebase
    public void writeFeedbackToFirebase(View view) {
        // Get user input
        String userEmail = etUserEmail.getText().toString().trim();
        String feedbackMessage = etFeedbackMessage.getText().toString().trim();

        // Get the current time for the feedback (you can use a timestamp)
        String dateTime = System.currentTimeMillis() + "";  // Convert current time to string

        // Check if fields are not empty
        if (!userEmail.isEmpty() && !feedbackMessage.isEmpty()) {

            // Get Firebase Realtime Database reference
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("feedbacks");

            // Generate a unique ID for each feedback
            String feedbackId = database.push().getKey();  // Automatically generates a unique key

            if (feedbackId != null) {
                // Create a Feedback object
                Feedback feedback = new Feedback(dateTime, userEmail, feedbackMessage);

                // Write feedback data to Firebase Realtime Database
                database.child(feedbackId).setValue(feedback)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(WriteFeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(WriteFeedbackActivity.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

}
