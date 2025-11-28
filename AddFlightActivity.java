package com.example.aviationsolutions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFlightActivity extends AppCompatActivity {

    private EditText flightNumberEditText, airlineEditText, originEditText, destinationEditText, departureDateEditText, departureTimeEditText;
    private Button saveButton;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        flightNumberEditText = findViewById(R.id.flightNumberEditText);
        airlineEditText = findViewById(R.id.airlineEditText);
        originEditText = findViewById(R.id.originEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        departureDateEditText = findViewById(R.id.departureDateEditText);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        saveButton = findViewById(R.id.saveButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("AdminFlights");

        saveButton.setOnClickListener(v -> saveFlight());
    }

    private void saveFlight() {
        String flightNumber = flightNumberEditText.getText().toString().trim();
        String airline = airlineEditText.getText().toString().trim();
        String origin = originEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        String departureDate = departureDateEditText.getText().toString().trim();
        String departureTime = departureTimeEditText.getText().toString().trim();

        if (flightNumber.isEmpty() || airline.isEmpty() || origin.isEmpty() || destination.isEmpty() || departureDate.isEmpty() || departureTime.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }


        databaseReference.orderByChild("flightNumber").equalTo(flightNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Toast.makeText(AddFlightActivity.this, "Flight with this number already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            //if not repeating
                            DatabaseReference newFlightRef = databaseReference.push();
                            AdminFlight flight = new AdminFlight(flightNumber, airline, origin, destination, departureDate, departureTime);
                            flight.setFlightId(newFlightRef.getKey());

                            newFlightRef.setValue(flight)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(AddFlightActivity.this, "Flight added successfully", Toast.LENGTH_SHORT).show();

                                    })
                                    .addOnFailureListener(e -> Toast.makeText(AddFlightActivity.this, "Failed to add flight", Toast.LENGTH_SHORT).show());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddFlightActivity.this, "Error checking flight number: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

