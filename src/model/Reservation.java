package model;

public class Reservation {

    private String reservationNumber;
    private String flightId;
    private String customerUsername;
    private String seatNumber;
    private String from;
    private String to;
    private String date;
    private String time;
    private int recordId;

    public Reservation(String reservationNumber, String flightId, String customerId, String seatNumber) {
        this.reservationNumber = reservationNumber;
        this.flightId = flightId;
        this.customerUsername = customerId;
        this.seatNumber = seatNumber;
    } 
 public Reservation(String reservationNumber, String flightId, String customerId, String seatNumber, int recordId) {
        this.reservationNumber = reservationNumber;
        this.flightId = flightId;
        this.customerUsername = customerId;
        this.seatNumber = seatNumber;
        this.recordId = recordId;
    }
    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getCustomerId() {
        return customerUsername;
    }

    public void setCustomerId(String customerId) {
        this.customerUsername = customerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public int getrecordId() {
        return recordId;
    }

    public void setrecordId(int recordId) {
        this.recordId = recordId;
    }
    @Override
    public String toString() {
        return "Reservation{" + "reservationNumber=" + reservationNumber + ", flightId=" + flightId
                + ", customerUsername=" + customerUsername + ", seatNumber=" + seatNumber + ", from=" + from + ", to="
                + to + ", date=" + date + ", time=" + time + '}';
    }

}
