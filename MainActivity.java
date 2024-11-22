package com.example.myapplication4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button userButton;
    private Button agentButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);

        // Check if the user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            // If not logged in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish MainActivity to prevent user from going back to it
        }

        // Initialize buttons
        userButton = findViewById(R.id.userButton);
        agentButton = findViewById(R.id.agentButton);

        // On user button click, launch the UserActivity
        userButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        });

        // On agent button click, launch the AgentActivity
        agentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgentActivity.class);
            startActivity(intent);
        });
    }
}
