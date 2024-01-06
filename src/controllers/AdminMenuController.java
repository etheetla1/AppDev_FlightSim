package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * 
     * By the function name, we move the user into the needed page.
     * 
     * If customer data is needed, we will send the customer data into the next
     * screen
     * 
     */

    @FXML
    private void addFlight(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/AddFlight.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer Record");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AddFlightController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

     @FXML
    private void updateFlight(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/UpdateFlight.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Update Flight");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            UpdateFlightController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

  /*  @FXML
    private void updateFlight(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DisplayFlights.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Display Flights");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            UpdateFlightController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
*/
    @FXML
    private void deleteFlight(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DeleteFlight.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Delete Flight");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DeleteFlightController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void logout(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            LoginController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void displayFlights(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DisplayFlights.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Display Flights");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DisplayFlightsController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showReservations(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DisplayCustomerReservations.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Show Reservations");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DisplayCustomerReservationsController controller = fxmlLoader.getController();
            controller.init(null);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void cancelReservation(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CancelReservation.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cancel Reservation");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            CancelReservationController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
