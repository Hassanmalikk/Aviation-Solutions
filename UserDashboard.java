package com.example.aviationsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserDashboard extends AppCompatActivity {

    Button enterFlightButton, flightnavigation_Button;
    Button viewFlightStatus_Button, TripHistory_Button, setReminder_Button;
    EditText search_bar;
    TextView EnterRedirect, welcomeText; // Added welcomeText for displaying username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);

        // Initialize UI elements
        enterFlightButton = findViewById(R.id.enterFlight_Button);
        viewFlightStatus_Button = findViewById(R.id.viewFlightStatus_Button);
        TripHistory_Button = findViewById(R.id.TripHistory_Button);
        setReminder_Button = findViewById(R.id.setReminder_Button);
        flightnavigation_Button = findViewById(R.id.flightnavigation_Button);
        welcomeText = findViewById(R.id.welcomeText); // Assuming you have a TextView to show the welcome message

        // Retrieve username from Intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeText.setText("Welcome, " + username);
        }

        // Set up button click listeners
        enterFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, EnterFlight.class);
                startActivity(intent);
            }
        });

        viewFlightStatus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, FlightStatus.class);
                startActivity(intent);
            }
        });

        TripHistory_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, TripHistory.class);
                intent.putExtra("username", username); // Pass username to TripHistory
                startActivity(intent);
            }
        });

        flightnavigation_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, FlightNavigation.class);
                startActivity(intent);
            }
        });

        setReminder_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboard.this, SetReminder.class);
                startActivity(intent);
            }
        });

        // Apply window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
