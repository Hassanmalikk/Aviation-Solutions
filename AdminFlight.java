package com.example.aviationsolutions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminFlight {
    private String flightId;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureDate;
    private String departureTime;

    public AdminFlight() {}

    public AdminFlight(String flightNumber, String airline, String origin, String destination, String departureDate, String departureTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }


    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }


    public String getFlightStatus() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date flightDate = sdf.parse(departureDate + " " + departureTime);
            Date currentDate = new Date();

            if (flightDate != null && currentDate.before(flightDate)) {
                return "Scheduled";
            } else {
                return "Departed";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
