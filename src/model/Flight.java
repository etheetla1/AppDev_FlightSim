
package model;

public class Flight {
    private String flightId;
    private String fromCity;
    private String toCity;
    private String date;
    private String time;
    private int capacity;
    private int bookedPassengers;
    private int recordId;

    public Flight(String flightId, String fromCity, String toCity, String date, String time, int capacity,
            int bookedPassengers) {
        this.flightId = flightId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.bookedPassengers = bookedPassengers;
    }
    public Flight(String flightId, String fromCity, String toCity, String date, String time, int capacity,
            int bookedPassengers, int recordId) {
        this.flightId = flightId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.bookedPassengers = bookedPassengers;
        this.recordId = recordId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBookedPassengers() {
        return bookedPassengers;
    }

    public void setBookedPassengers(int bookedPassengers) {
        this.bookedPassengers = bookedPassengers;
    }
    public int getrecordId() {
        return recordId;
    }

    public void setrecordId(int recordId) {
        this.recordId = recordId;
    }
}
