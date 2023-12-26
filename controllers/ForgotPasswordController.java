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

public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField usernameT;
    @FXML
    private TextField answerT;
    @FXML
    private ComboBox<String> securityQuestionC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        securityQuestionC.getItems().add("What's your first school name?");
        securityQuestionC.getItems().add("What's your city name where you born?");
        securityQuestionC.getItems().add("What's your favourite Meal?");

        securityQuestionC.getSelectionModel().select(0);
    }

    @FXML
    private void done(ActionEvent event) {
        if (usernameT.getText().isEmpty() || securityQuestionC.getSelectionModel().getSelectedIndex() == 0) {
            Utils.showError("Error", "Please value in all fields");
        } else {

            // change password be using username and security answer.

            String pass = new CustomerDatabase().retrievePassword(usernameT.getText(), answerT.getText());
            if (pass == null) {
                Utils.showError("Error", "Invalid username or answer");
            } else {
                Utils.showInfo("Success", "Your password is " + pass + "");
            }
        }
    }

    @FXML
    private void mainMenu(ActionEvent event) {
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
