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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.Customer;

public class LoginController implements Initializable {

    @FXML
    private TextField userT;

    @FXML
    private ComboBox<String> usertTypeC;

    @FXML
    private PasswordField passwordT;

    /**
     * On initialization we add admin and customer option into the select box
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usertTypeC.getItems().add("Admin");
        usertTypeC.getItems().add("Customer");
        usertTypeC.getSelectionModel().select(0);
    }

    @FXML
    private void loginUser(ActionEvent event) {
        if (userT.getText().isEmpty() || passwordT.getText().isEmpty()) {
            Utils.showError("Error", "Please enter values in all fields");
        } else {
            // Based on the selection index, we render admin or customer panel

            if (usertTypeC.getSelectionModel().getSelectedIndex() == 0) {

                // Initialize Admin based on provided credentials

                Admin admin = new Admin(userT.getText(), passwordT.getText());
                boolean loggedIn = admin.login();

                if (loggedIn) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/AdminMenu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Admin Menu");
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                } else {
                    Utils.showError("Error", "Invalid username or password");
                }
            } else {

                // Initialize customer based on provided credentials

                Customer customer = new Customer(userT.getText(), passwordT.getText());
                boolean loggedIn = customer.login();

                if (loggedIn) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CustomerMenu.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Customer Menu");
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        CustomerMenuController controller = fxmlLoader.getController();
                        controller.init(customer);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                } else {
                    Utils.showError("Error", "Invalid username or password");
                }
            }
        }
    }

    /**
     * 
     * The function just changes the scene into signupUser fxml
     * 
     * @param event
     */

    @FXML
    private void signupUser(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/SignupUser.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Signup Customer");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * On forget password, we just change the scene to forget password fxml
     * 
     * @param event
     */

    @FXML
    private void forgotPassword(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ForgotPassword.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
 /*    @FXML
    private void Hello(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ForgotPassword.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
        */

}
