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
            String sql = "INSERT INTO Flight (flightId, fromCity, toCity, date, time, capacity, bookedPassengers) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for placeholders in the SQL query
                preparedStatement.setString(1, flight.getFlightId());
                preparedStatement.setString(2, flight.getFromCity());
                preparedStatement.setString(3, flight.getToCity());
                preparedStatement.setString(4, flight.getDate());
                preparedStatement.setString(5, flight.getTime());
                preparedStatement.setInt(6, flight.getCapacity());
                preparedStatement.setInt(7, flight.getBookedPassengers());

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
            String sql = "UPDATE Flight SET fromCity = ?, toCity = ?, date = ?, time = ?, capacity = ?, bookedPassengers = ? WHERE flightId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for the placeholders in the SQL query
                preparedStatement.setString(1, updatedFlight.getFromCity());
                preparedStatement.setString(2, updatedFlight.getToCity());
                preparedStatement.setString(3, updatedFlight.getDate());
                preparedStatement.setString(4, updatedFlight.getTime());
                preparedStatement.setInt(5, updatedFlight.getCapacity());
                preparedStatement.setInt(6, updatedFlight.getBookedPassengers());
                preparedStatement.setString(7, updatedFlight.getFlightId());

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

    public boolean deleteFlight(String flightId) {
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

    @Override
    public Flight searchFlight(String fID) {
        return CustomerDatabase.searchFlight(fID);
    }

    @Override
    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT * FROM Flight";
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
                            resultSet.getInt("bookedPassengers"));
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    @Override
    public ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String sql = "SELECT * FROM Reservation";
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String reservationNumber = resultSet.getString("reservationNumber");
                    String flightId = resultSet.getString("flightId");
                    String username = resultSet.getString("customerId");
                    String seatNumber = resultSet.getString("seatNumber");

                    // Create a Reservation object and add it to the list
                    Reservation reservation = new Reservation(reservationNumber, flightId, username, seatNumber);
                    Flight flight = searchFlight(flightId);
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
