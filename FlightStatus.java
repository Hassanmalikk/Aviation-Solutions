package com.example.aviationsolutions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightStatus extends AppCompatActivity {

    private LinearLayout flightContainer;
    private FlightApiHelper flightApiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flight_status);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.flight_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        flightContainer = findViewById(R.id.flight_container);


        flightApiHelper = new FlightApiHelper();


        fetchFlightData();
    }

    private void fetchFlightData() {

        String apiKey = "878f33c470b3c3e122a9b0096ef972fe";
        flightApiHelper.getFlights(apiKey, 10, new FlightApiHelper.FlightCallback() {
            @Override
            public void onSuccess(FlightResponse flightResponse) {

                flightContainer.removeAllViews();


                for (FlightData flightData : flightResponse.getData()) {
                    View flightCard = LayoutInflater.from(FlightStatus.this)
                            .inflate(R.layout.flight_card, flightContainer, false);


                    TextView flightNumber = flightCard.findViewById(R.id.flightnumber);
                    TextView departureAirport = flightCard.findViewById(R.id.departure_airport);
                    TextView arrivalAirport = flightCard.findViewById(R.id.arrival_airport);
                    TextView flightStatus = flightCard.findViewById(R.id.flight_status);
                    TextView flightTime = flightCard.findViewById(R.id.flight_time);

                    flightNumber.setText("Flight: " + flightData.getFlight().getNumber());
                    departureAirport.setText("Departure: " + flightData.getDeparture().getIata());
                    arrivalAirport.setText("Arrival: " + flightData.getArrival().getIata());
                    flightStatus.setText("Status: " + flightData.getFlight_status());
                    flightTime.setText("Scheduled: " + flightData.getDeparture().getScheduled());


                    flightContainer.addView(flightCard);
                }
            }

            @Override
            public void onFailure(String errorMessage) {

                TextView errorText = new TextView(FlightStatus.this);
                errorText.setText("Error: " + errorMessage);
                flightContainer.addView(errorText);
            }
        });
    }
}
