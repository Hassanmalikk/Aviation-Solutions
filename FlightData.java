package com.example.aviationsolutions;

import com.google.gson.annotations.SerializedName;

public class FlightData {
    private Flight flight;
    private Departure departure;
    private Arrival arrival;
    private String flight_status;


    public Flight getFlight() {
        return flight;
    }

    public Departure getDeparture() {
        return departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public String getFlight_status() {
        return flight_status;
    }
}


