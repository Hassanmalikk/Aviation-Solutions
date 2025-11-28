package com.example.aviationsolutions;

public class AddFlightHelper {
    public String flightNumber;
    public String airline;
    public String origin;
    public String destination;
    public String departureDate;
    public String departureTime;

    public AddFlightHelper(String flightNumber, String airline, String origin, String destination, String departureDate, String departureTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
    }
}

