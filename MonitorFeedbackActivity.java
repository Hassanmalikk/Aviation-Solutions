package com.example.aviation_solutions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonitorFeedbackActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private ArrayList<Feedback> feedbackList;
    private FeedbackAdapter adapter;  // Custom adapter to bind data to the ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_feedback);

        // Initialize Firebase Realtime Database
        dbRef = FirebaseDatabase.getInstance().getReference("Feedback");

        // Initialize ListView and Feedback list
        ListView feedbackListView = findViewById(R.id.feedbackListView);
        feedbackList = new ArrayList<>();
        adapter = new FeedbackAdapter(this, feedbackList);
        feedbackListView.setAdapter(adapter);

        // Initialize Button and set onClickListener to fetch feedback
        Button btnFetchFeedback = findViewById(R.id.btnFetchFeedback);
        btnFetchFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch feedback data when the button is clicked
                fetchFeedbackData();
            }
        });
    }

    private void fetchFeedbackData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedbackList.clear();  // Clear existing data before adding new feedback

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Feedback feedback = snapshot.getValue(Feedback.class);
                    feedbackList.add(feedback);
                }
                adapter.notifyDataSetChanged();  // Notify adapter to update the ListView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MonitorFeedbackActivity", "Error getting data: ", databaseError.toException());
                Toast.makeText(MonitorFeedbackActivity.this, "Failed to load feedback.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
