package com.example.aviationsolutions;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity implements AdminDashboardInterface {

    private Button addFlightButton;
    private Button viewFlightsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        addFlightButton = findViewById(R.id.addFlightButton);
       viewFlightsButton = findViewById(R.id.viewFlightsButton);

        addFlightButton.setOnClickListener(v -> onAddFlightClicked());
        viewFlightsButton.setOnClickListener(v -> onViewFlightsClicked());



    }

    @Override
    public void onAddFlightClicked() {
        Intent intent = new Intent(AdminDashboard.this, AddFlightActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewFlightsClicked() {
        Intent intent = new Intent(AdminDashboard.this, ViewFlightsActivity.class);
        startActivity(intent);
    }
}
