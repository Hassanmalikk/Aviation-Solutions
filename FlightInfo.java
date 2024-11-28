package com.example.aviationsolutions;

import com.google.gson.annotations.SerializedName;

public class FlightInfo {
    @SerializedName("iata")
    private String iata;

    @SerializedName("number")
    private String number;

    public String getIata() {
        return iata;
    }

    public String getNumber() {
        return number;
    }
}

