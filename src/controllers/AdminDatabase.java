package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.AdminActions;
import model.Flight;
import model.Reservation;

public class AdminDatabase implements AdminActions {

    CustomerDatabase CustomerDatabase = new CustomerDatabase();

    @Override
    public void addFlight(Flight flight) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "INSERT INTO FlightData (flightId, fromCity, toCity, date, time, capacity, bookedPassengers, recordId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for placeholders in the SQL query
                preparedStatement.setString(1, flight.getFlightId());
                preparedStatement.setString(2, flight.getFromCity());
                preparedStatement.setString(3, flight.getToCity());
                preparedStatement.setString(4, flight.getDate());
                preparedStatement.setString(5, flight.getTime());
                preparedStatement.setInt(6, flight.getCapacity());
                preparedStatement.setInt(7, flight.getBookedPassengers());
                preparedStatement.setInt(8, flight.getrecordId());

                preparedStatement.executeUpdate();
                System.out.println("Flight data inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean updateFlight(Flight updatedFlight) {

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            //String sql = "UPDATE Flight SET fromCity = ?, toCity = ?, date = ?, time = ?, capacity = ?, bookedPassengers = ? WHERE flightId = ?";
            String sql = "UPDATE FlightData SET fromCity = ?, toCity = ?, date = ?, time = ?, capacity = ?, bookedPassengers = ? WHERE flightId = ? AND recordId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for the placeholders in the SQL query
                preparedStatement.setString(1, updatedFlight.getFromCity());
                preparedStatement.setString(2, updatedFlight.getToCity());
                preparedStatement.setString(3, updatedFlight.getDate());
                preparedStatement.setString(4, updatedFlight.getTime());
                preparedStatement.setInt(5, updatedFlight.getCapacity());
                preparedStatement.setInt(6, updatedFlight.getBookedPassengers());
                preparedStatement.setString(7, updatedFlight.getFlightId());
                preparedStatement.setInt(8, updatedFlight.getrecordId());

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the flight was updated successfully
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override

    /*public boolean deleteFlight(String flightId) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "DELETE FROM Flight WHERE flightId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the flight was deleted successfully
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
*/
public boolean deleteFlight(String flightId) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "DELETE FROM FlightData WHERE flightId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the flight was deleted successfully
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public boolean deleteDestination(int recordId) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "DELETE FROM FlightData WHERE recordId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setInt(1, recordId);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the flight was deleted successfully
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getnewrecordId() {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT max(recordId) FROM flight_sim.flightdata;";
            int value = 0;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Execute the query
                ResultSet results = preparedStatement.executeQuery();
                //value = results.getInt(1);
               while (results.next()) {
                   // value = results.toString();
                   value = results.getInt(1);
                }
                return value + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Flight searchFlight(String fID) {
        return CustomerDatabase.searchFlight(fID);
    }
    public Flight searchFlight(String fID, int rID) {
        return CustomerDatabase.searchFlight(fID, rID);
    }

    @Override
    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
           //String sql = "SELECT * FROM Flight";
           //String sql = "SELECT FT.flightId, fromCity, toCity, date, time, capacity, FD.bookedPassengers FROM flight_sim.flightdestinations FD right OUTER JOIN flight_sim.flighttest FT ON FD.flightId = FT.flightId";
           String sql = "SELECT * FROM FlightData";
           try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the results
                while (resultSet.next()) {
                    Flight flight = new Flight(
                            resultSet.getString("flightId"),
                            resultSet.getString("fromCity"),
                            resultSet.getString("toCity"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("bookedPassengers"),
                            resultSet.getInt("recordId"));
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

  /*  @Override
    public ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
           String sql = "SELECT * FROM Reservation";
          //String sql = "SELECT * FROM Reservationtest";
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String reservationNumber = resultSet.getString("reservationNumber");
                    String flightId = resultSet.getString("flightId");
                    String username = resultSet.getString("customerId");
                    String seatNumber = resultSet.getString("seatNumber");
                  //  String recordId = resultSet.getString("recordId");

                    // Create a Reservation object and add it to the list
                    Reservation reservation = new Reservation(reservationNumber, flightId, username, seatNumber);
                    Flight flight = searchFlight(flightId);
                    reservation.setDate(flight.getDate());
                    reservation.setTime(flight.getTime());
                    reservation.setFrom(flight.getFromCity());
                    reservation.setTo(flight.getToCity());
                 //   reservation.setrecordId(recordId);
                    System.out.println(reservation.toString());
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;

    }
    */
@Override
public ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
          //  String sql = "SELECT * FROM Reservation";
          String sql = "SELECT * FROM Reservationtest";
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String reservationNumber = resultSet.getString("reservationNumber");
                    String flightId = resultSet.getString("flightId");
                    String username = resultSet.getString("customerId");
                    String seatNumber = resultSet.getString("seatNumber");
                    int recordId = resultSet.getInt("recordId");

                    // Create a Reservation object and add it to the list
                    Reservation reservation = new Reservation(reservationNumber, flightId, username, seatNumber, recordId);
                    Flight flight = searchFlight(flightId, recordId);
                    reservation.setDate(flight.getDate());
                    reservation.setTime(flight.getTime());
                    reservation.setFrom(flight.getFromCity());
                    reservation.setTo(flight.getToCity());
                    System.out.println(reservation.toString());
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;

    }

}
