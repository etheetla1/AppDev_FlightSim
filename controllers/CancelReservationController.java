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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class CancelReservationController implements Initializable {

    @FXML
    private TextField resIDT;

    Customer customer;
    CustomerDatabase customerDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerDatabase = new CustomerDatabase();
    }

    /**
     * If no customer is passed, then it's an admin, else we get the customer data
     * from the previous screen before the page is rendered
     * 
     * @param customer
     */

    public void init(Customer customer) {
        this.customer = customer;
    }

    /**
     * 
     * By entering the reservation id, we search the db and delete the flight
     * 
     */

    @FXML
    private void delete(ActionEvent event) {
        if (resIDT.getText().isEmpty()) {
            Utils.showError("Error", "Please enter reservation id");
        } else {
            boolean check = customerDatabase.cancelReservation(resIDT.getText());
            if (check) {
                Utils.showInfo("Success", "Successfully cancelled reservation " + resIDT.getText());
                resIDT.setText("");
            } else {
                Utils.showError("Error", "Invalid Reservation ID");
            }
        }
    }

    /**
     * 
     * go back the main menu, if there is no customer, we will move them into admin
     * menu
     * 
     * else go to customer menu
     * 
     */

    @FXML
    private void mainMenu(ActionEvent event) {

        if (customer == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/AdminMenu.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Admin Mneu");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                AdminMenuController controller = fxmlLoader.getController();
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
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

    }

}
