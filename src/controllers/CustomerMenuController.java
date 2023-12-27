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
import model.Customer;

public class CustomerMenuController implements Initializable {

    Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init(Customer customer) {
        this.customer = customer;
    }

    /**
     * 
     * By the function name, we move the user into the needed page.
     * 
     * We get the user info by the init function. Which is called before logginin
     * in. And we move the data to any page that we redirect it to.
     * 
     */

    @FXML
    private void reserveFlight(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ReserveFlight.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Reserve Flight");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ReserveFlightController controller = fxmlLoader.getController();
            controller.init(customer);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void cancelFlight(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CancelReservation.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cancel Flight");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            CancelReservationController controller = fxmlLoader.getController();
            controller.init(customer);
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
    private void showReservations(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/DisplayCustomerReservations.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer Reservations");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            DisplayCustomerReservationsController controller = fxmlLoader.getController();
            controller.init(customer);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
