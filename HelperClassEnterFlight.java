package com.example.aviationsolutions;

public class HelperClassEnterFlight {
    String name, username,  dept_date, dept_time, origin, destination, flightNumber;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
public String getDept_date(){
        return dept_date;

}

public void setDept_date(String dept_date){
        this.dept_date = dept_date;
}
    public String getDept_time() {
        return dept_time;
    }

    public void setDept_time(String dept_time) {
        this.dept_time = dept_time;
    }


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }


    public HelperClassEnterFlight(String name, String username, String dept_time, String dept_date, String origin, String destination, String flightNumber) {
        this.name = name;
        this.username = username;
        this.dept_time = dept_time;
        this.dept_date = dept_date; // Corrected this line
        this.origin = origin;
        this.destination = destination;
        this.flightNumber = flightNumber;
    }



    public HelperClassEnterFlight() {
    }
}
