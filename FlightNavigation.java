package com.example.aviationsolutions;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.collection.BuildConfig;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlightNavigation extends AppCompatActivity {

    private static final String BASE_URL = "https://opensky-network.org/api/";
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_navigation);


        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


        mapView = findViewById(R.id.map);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(5.0);
        mapView.getController().setCenter(new GeoPoint(51.1657, 10.4515));  // Center map on Germany (example)


        fetchFlightData();
    }

    private void fetchFlightData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenSkyApiService apiService = retrofit.create(OpenSkyApiService.class);
        Call<OpenSkyResponse> call = apiService.getAllFlights(36.0, -123.0, 37.0, -122.0); // Example bounding box
        call.enqueue(new Callback<OpenSkyResponse>() {
            @Override
            public void onResponse(Call<OpenSkyResponse> call, Response<OpenSkyResponse> response) {
                if (response.isSuccessful()) {
                    OpenSkyResponse openSkyResponse = response.body();
                    if (openSkyResponse != null && openSkyResponse.getStates() != null) {
                        for (FlightState flightState : openSkyResponse.getStates()) {
                            double latitude = flightState.getLatitude();
                            double longitude = flightState.getLongitude();
                            if (latitude != 0 && longitude != 0) {
                                // Add marker for each flight
                                addFlightMarker(latitude, longitude, flightState.getCallsign());
                            }
                        }
                    }
                } else {
                    Log.e("FlightData", "Failed to get response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<OpenSkyResponse> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }

    private void addFlightMarker(double latitude, double longitude, String callsign) {
        Marker flightMarker = new Marker(mapView);
        flightMarker.setPosition(new GeoPoint(latitude, longitude));
        flightMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        flightMarker.setTitle(callsign);
        mapView.getOverlays().add(flightMarker);
        mapView.invalidate();
    }
}
