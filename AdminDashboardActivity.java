package com.example.aviation_solutions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity{

        Button btnViewNumberOfUsers,  btnProvideAnnouncments, btnMonitorFeedback;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_dashboard);

            // Initialize buttons
            btnViewNumberOfUsers = findViewById(R.id.btnViewNumberOfUsers);
            btnProvideAnnouncments = findViewById(R.id.btnProvideAnnouncments);
            btnMonitorFeedback = findViewById(R.id.btnMonitorFeedback);

            // Set listeners for each button
            btnProvideAnnouncments.setOnClickListener(view -> {
                // Navigate to Analytics Dashboard
                Intent intent = new Intent(AdminDashboardActivity.this, AnnouncementsActivity.class);
                startActivity(intent);
            });
            btnMonitorFeedback.setOnClickListener(view -> {
                // Navigate to Analytics Dashboard
                Intent intent = new Intent(AdminDashboardActivity.this, MonitorFeedbackActivity.class);
                startActivity(intent);
            });
            btnViewNumberOfUsers.setOnClickListener(view -> {
                // Navigate to Analytics Dashboard
                Intent intent = new Intent(AdminDashboardActivity.this, NumberOfUsersActivity.class);
                startActivity(intent);
            });


        }
    }


