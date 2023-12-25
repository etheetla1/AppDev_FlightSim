package model;

import java.util.ArrayList;

public interface AdminActions {

    void addFlight(Flight flight);

    boolean updateFlight(Flight flight);

    boolean deleteFlight(String flightID);

    Flight searchFlight(String fID);

    ArrayList<Flight> getFlights();

    ArrayList<Reservation> getReservations();

}
