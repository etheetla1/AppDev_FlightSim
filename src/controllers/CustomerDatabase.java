package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import model.Customer;
import model.CustomerActions;
import model.Flight;
import model.Reservation;

public class CustomerDatabase implements CustomerActions {

    static CustomerDatabase customerDatabase;
    public static CustomerDatabase getInstance() {
        if(customerDatabase == null) {
            customerDatabase = new CustomerDatabase();
        }
        return customerDatabase;
    }

    @Override
    public void bookFlight(String flightId, String seatNumber, String customerUsername) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Check if the seat is available
            // If available, reserve the seat
            String sql = "INSERT INTO Reservation (reservationNumber,flightId, customerId, seatNumber) VALUES (?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, generateRandomReservationNumber());
                preparedStatement.setString(2, flightId);
                preparedStatement.setString(3, customerUsername);
                preparedStatement.setString(4, seatNumber);

                int rowsAffected = preparedStatement.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
 /* */   public Flight searchFlight(String flightId) {
        Flight foundFlight = null;

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT * FROM Flight WHERE flightId = ?";
           //String sql = "SELECT FT.flightId, fromCity, toCity, date, time, capacity, FD.bookedPassengers FROM flight_sim.flightdestinations FD right OUTER JOIN flight_sim.flighttest FT ON FD.flightId = FT.flightId WHERE FD.flightId = ?";
           try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                // Process the results
                if (resultSet.next()) {
                    foundFlight = new Flight(
                            resultSet.getString("flightId"),
                            resultSet.getString("fromCity"),
                            resultSet.getString("toCity"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("bookedPassengers"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundFlight;
    }

    public Flight searchFlight(String flightId, int rID) {
        Flight foundFlight = null;

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT * FROM FlightData WHERE flightId = ? AND recordId = ?";
           //String sql = "SELECT FT.flightId, fromCity, toCity, date, time, capacity, FD.bookedPassengers FROM flight_sim.flightdestinations FD right OUTER JOIN flight_sim.flighttest FT ON FD.flightId = FT.flightId WHERE FD.flightId = ?";
           try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);
                  preparedStatement.setInt(2, rID);
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                // Process the results
                if (resultSet.next()) {
                    foundFlight = new Flight(
                            resultSet.getString("flightId"),
                            resultSet.getString("fromCity"),
                            resultSet.getString("toCity"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("bookedPassengers"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundFlight;
    } 
/*    @Override
    public Flight searchFlight(String flightId, String fromCity) {
        Flight foundFlight = null;

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT * FROM Flight WHERE flightId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the result
                if (resultSet.next()) {
                    foundFlight = new Flight(
                            resultSet.getString("flightId"),
                            resultSet.getString("fromCity"),
                            resultSet.getString("toCity"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("bookedPassengers"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundFlight;
    }
*/
    @Override
    public boolean signUp(Customer customer) {
        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "INSERT INTO Customer (firstName, lastName, address, zip, state, email, ssn, securityQuestion, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for the placeholders in the SQL query
                preparedStatement.setString(1, customer.getFirstName());
                preparedStatement.setString(2, customer.getLastName());
                preparedStatement.setString(3, customer.getAddress());
                preparedStatement.setString(4, customer.getZip());
                preparedStatement.setString(5, customer.getState());
                preparedStatement.setString(6, customer.getEmail());
                preparedStatement.setString(7, customer.getSsn());
                preparedStatement.setString(8, customer.getSecurityQuestion());
                preparedStatement.setString(9, customer.getUsername());
                preparedStatement.setString(10, customer.getPassword());

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the customer was registered successfully
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer signIn(String username, String password) {
        try (Connection connection = MysqlDB.getConnection()) {

            // Prepare the SQL query
            String sql = "SELECT * FROM Customer WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set values for the placeholders in the SQL query
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if the customer exists (if there is a matching record)
                if (resultSet.next()) {
                    // If the customer exists, create and return a Customer object
                    return new Customer(
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("address"),
                            resultSet.getString("zip"),
                            resultSet.getString("state"),
                            resultSet.getString("email"),
                            resultSet.getString("ssn"),
                            resultSet.getString("securityQuestion"),
                            resultSet.getString("username"),
                            resultSet.getString("password"));
                } else {
                    // Return null if the customer does not exist or credentials are incorrect
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<String> getBookedSeats(String flightId) {
        ArrayList<String> bookedSeats = new ArrayList<>();
        String sql = "SELECT seatNumber FROM Reservation WHERE flightId = ?";
        try (PreparedStatement preparedStatement = MysqlDB.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, flightId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookedSeats.add(resultSet.getString("seatNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return bookedSeats;
    }

    private static String generateRandomReservationNumber() {
        // Prefix for reservation number
        String prefix = "RS";

        // Generate a random number between 1 and 99999
        int randomNumber = new Random().nextInt(99999) + 1;

        // Format the random number as a 5-digit string
        String formattedNumber = String.format("%05d", randomNumber);

        // Combine the prefix and formatted number
        return prefix + formattedNumber;
    }

    @Override
    public ArrayList<Reservation> reservations(String username) {

        ArrayList<Reservation> reservations = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String sql = "SELECT * FROM Reservation WHERE customerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.println("jsnakjn");
                    String reservationNumber = resultSet.getString("reservationNumber");
                    String flightId = resultSet.getString("flightId");
                    String seatNumber = resultSet.getString("seatNumber");
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
            System.out.println("Here");
            e.printStackTrace();
        }

        System.out.println(reservations.size());
        return reservations;

    }

    public ArrayList<Reservation> reservationsData (String username) {

        ArrayList<Reservation> reservations = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            //String sql = "SELECT * FROM Reservation WHERE customerId = ?";
            String sql = "SELECT * FROM ReservationTest WHERE customerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.println("jsnakjn");
                    String reservationNumber = resultSet.getString("reservationNumber");
                    String flightId = resultSet.getString("flightId");
                    String seatNumber = resultSet.getString("seatNumber");
                    int recordId = resultSet.getInt("recordId");
                    //Reservation reservation = new Reservation(reservationNumber, flightId, username, seatNumber);
                    Reservation reservation = new Reservation(reservationNumber, flightId, username, seatNumber, recordId);
                    Flight flight = searchFlight(flightId, recordId);
                    reservation.setDate(flight.getDate());
                    reservation.setTime(flight.getTime());
                    reservation.setFrom(flight.getFromCity());
                    reservation.setTo(flight.getToCity());
                    //reservation.setrecordId(recordId);
                    System.out.println(reservation.toString());

                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            System.out.println("Here");
            e.printStackTrace();
        }

        System.out.println(reservations.size());
        return reservations;

    }

    @Override
    public boolean cancelReservation(String res) {

        try (Connection connection = MysqlDB.getConnection()) {
            //String sql = "DELETE FROM Reservation WHERE reservationNumber = ?";
            String sql = "DELETE FROM Reservationtest WHERE reservationNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, res);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String retrievePassword(String username, String securityQuestion) {
        try (Connection connection = MysqlDB.getConnection()) {
            String sql = "SELECT password FROM Customer WHERE username = ? AND securityQuestion = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, securityQuestion);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
