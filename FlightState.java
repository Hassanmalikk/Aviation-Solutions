package com.example.aviationsolutions;

import com.google.gson.annotations.SerializedName;

public class FlightState {

    @SerializedName("icao24")
    private String icao24;

    @SerializedName("callsign")
    private String callsign;

    @SerializedName("origin_country")
    private String originCountry;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("altitude")
    private double altitude;



    public String getIcao24() {
        return icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }
}

