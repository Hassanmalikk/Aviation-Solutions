package com.example.aviationsolutions;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FlightResponse {
    private List<FlightData> data;


    public List<FlightData> getData() {
        return data;
    }
}


