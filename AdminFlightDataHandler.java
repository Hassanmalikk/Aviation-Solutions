package com.example.aviationsolutions;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminFlightDataHandler {

    private final DatabaseReference flightsReference;

    public AdminFlightDataHandler() {
        this.flightsReference = FirebaseDatabase.getInstance().getReference("AdminFlights");
    }

    public void loadFlights(AdminFlightInterface.FlightDataCallback callback) {
        flightsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<AdminFlight> flights = new ArrayList<>();
                for (DataSnapshot flightSnapshot : dataSnapshot.getChildren()) {
                    AdminFlight flight = flightSnapshot.getValue(AdminFlight.class);
                    if (flight != null) {
                        flights.add(flight);
                    }
                }

                callback.onSuccess(flights);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(new Exception(databaseError.getMessage()));
            }
        });
    }
}
