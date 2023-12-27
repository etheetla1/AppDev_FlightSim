
package model;

import java.util.ArrayList;


public interface CustomerActions {
    boolean signUp(Customer customer);
    void bookFlight(String flightId, String seatNumber, String customerUsername);
    Flight searchFlight(String fID);
    Customer signIn(String username, String password);
    ArrayList<String> getBookedSeats(String flightID);
    ArrayList<Reservation> reservations(String username);
    boolean cancelReservation(String rese);
     String retrievePassword(String username, String securityQuestion);    
}