package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;
import model.Flight;
import model.Reservation;

public class ReserveFlightController implements Initializable {

    @FXML
    private TableView<Flight> table;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> from;
    @FXML
    private TableColumn<?, ?> to;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> time;
    @FXML
    private TableColumn<?, ?> capacity;
    @FXML
    private TableColumn<?, ?> booked;
    @FXML
    private ComboBox<String> seatC;

    AdminDatabase adminDatabase;
    CustomerDatabase customerDatabase;

    Customer customer;
    Flight flight;

    public void init(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        adminDatabase = new AdminDatabase();
        customerDatabase = new CustomerDatabase();

        id.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        from.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        to.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        booked.setCellValueFactory(new PropertyValueFactory<>("bookedPassengers"));

        showData();

    }

    public void showData() {
        try {
            table.setItems(data());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<Flight> data() throws SQLException {
        ArrayList<Flight> flights = adminDatabase.getFlights();
        ObservableList<Flight> obFlights = FXCollections.observableArrayList();

        for (Flight c : flights) {
            obFlights.add(c);
        }

        return obFlights;
    }

    @FXML
    private void reserve(ActionEvent event) {

        if (table.getSelectionModel().getSelectedIndex() == -1) {
            Utils.showError("Error", "Please first select any flight to reserve seat");
        } else {

            if (seatC.getSelectionModel().getSelectedIndex() == 0) {
                Utils.showError("Error", "Please select seat number");
            } else {

                boolean check = false;
                ArrayList<Reservation> reservations = customerDatabase.reservations(customer.getUsername());
                for (Reservation reservation : reservations) {
                    if (reservation.getFlightId().equals(flight.getFlightId())
                            && reservation.getCustomerId().equals(customer.getUsername())) {
                        check = true;
                    }
                }

                if (check) {
                    Utils.showError("Error", "User already has booking in this flight");
                } else {

                    customerDatabase.bookFlight(flight.getFlightId(), seatC.getSelectionModel().getSelectedItem(),
                            customer.getUsername());
                    Utils.showInfo("Success", "Successfully booked flight in " + flight.getFlightId());
                    flight.setBookedPassengers(flight.getBookedPassengers() + 1);
                    adminDatabase.updateFlight(flight);
                    showData();
                    flight = null;
                    table.getSelectionModel().clearSelection();
                    seatC.getItems().clear();
                    seatC.getItems().add("Please select seat number");
                    seatC.getSelectionModel().select(0);
                }
            }

        }

    }

    private void setAvailableSeats() {

        seatC.getItems().clear();
        seatC.getItems().add("Please select seat");

        ArrayList<String> availableSeats = new ArrayList<>();

        ArrayList<String> s = customerDatabase.getBookedSeats(flight.getFlightId());

        /**
         * We get the available seats by checking if any of the seat is already taken by
         * a user or no in the db
         */

        for (char row = 'A'; row <= 'Z'; row++) {
            for (int seatNum = 1; seatNum <= flight.getCapacity(); seatNum++) {
                String seat = row + String.valueOf(seatNum);
                if (!s.contains(seat)) {
                    seatC.getItems().add(seat);
                    // availableSeats.add(seat);
                }
            }
        }

        seatC.getSelectionModel().select(0);

    }

    @FXML
    private void mainMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CustomerMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer Mneu");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            CustomerMenuController controller = fxmlLoader.getController();
            controller.init(customer);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * On table clicked, we run the available seats function that gives us what
     * seats are free to take
     * 
     */

    @FXML
    private void tableClicked(MouseEvent event) {

        if (table.getSelectionModel().getSelectedIndex() != -1) {
            flight = table.getSelectionModel().getSelectedItem();
            setAvailableSeats();
        }
    }

}
