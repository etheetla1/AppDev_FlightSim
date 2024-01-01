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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class SignupUserController implements Initializable {

    @FXML
    private TextField firstNameT;
    @FXML
    private TextField lastNameT;
    @FXML
    private TextField addressT;
    @FXML
    private TextField zipT;
    @FXML
    private TextField stateT;
    @FXML
    private TextField emailT;
    @FXML
    private TextField ssnT;
    @FXML
    private TextField usernameT;
    @FXML
    private TextField passwordT;
    @FXML
    private TextField answerT;
    @FXML
    private ComboBox<String> securityQuestionC;

    CustomerDatabase customerDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerDatabase = new CustomerDatabase();
        securityQuestionC.getItems().add("Please select security question");
        securityQuestionC.getItems().add("What's your first school name?");
        securityQuestionC.getItems().add("What's your city name where you born?");
        securityQuestionC.getItems().add("What's your favourite Meal?");
      /*  Customer[] Customers;
         Customer customer = new Customer("Sahith", "Sangabathula", "aa",
                    "30005", "GA", "Sahith.Sangabathula@gmail.com", "123456789", "pizza",
                    "Sahith", "Sangabathula");
        Customers[0] = customer; */
        

        securityQuestionC.getSelectionModel().select(0);
    }

    @FXML
    private void saveUser(ActionEvent event) {

        // Check if no fields are empty

        if (firstNameT.getText().isEmpty() || lastNameT.getText().isEmpty() || addressT.getText().isEmpty()
                || zipT.getText().isEmpty() || stateT.getText().isEmpty()
                || emailT.getText().isEmpty() || ssnT.getText().isEmpty() || usernameT.getText().isEmpty()
                || passwordT.getText().isEmpty()
                || securityQuestionC.getSelectionModel().getSelectedIndex() == 0
                || securityQuestionC.getSelectionModel().getSelectedIndex() == 1 || answerT.getText().isEmpty()) {

            Utils.showError("Error", "Please fill all the fields!");
        } else {

            Customer customer = new Customer(firstNameT.getText(), lastNameT.getText(), addressT.getText(),
                    zipT.getText(), stateT.getText(), emailT.getText(), ssnT.getText(), answerT.getText(),
                    usernameT.getText(), passwordT.getText());

            // Put the user into DB

            boolean check = customerDatabase.signUp(customer);

            if (check) {
                Utils.showInfo("Success", "Successfully registered User");
                firstNameT.setText("");
                lastNameT.setText("");
                addressT.setText("");
                zipT.setText("");
                stateT.setText("");
                emailT.setText("");
                ssnT.setText("");
                usernameT.setText("");
                passwordT.setText("");
                answerT.setText("");
                securityQuestionC.getSelectionModel().select(0);

            } else {
                Utils.showError("Error", "Unable to register User");
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
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

}
