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

public class DeleteFlightController implements Initializable {

    // Only admin has access to this page

    @FXML
    private TextField flightIDT;

    AdminDatabase adminDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminDatabase = new AdminDatabase();
    }

    /**
     *
     * Delete flight by flight id
     * 
     */

    @FXML
    private void delete(ActionEvent event) {
        if (flightIDT.getText().isEmpty()) {
            Utils.showError("Empty Field", "Flight ID can't be empty");
        } else {
            boolean check = adminDatabase.deleteFlight(flightIDT.getText());
            if (check) {
                Utils.showInfo("Success", "Successfully deleted Flight " + flightIDT.getText());
                flightIDT.setText("");
            } else {
                Utils.showInfo("Fail", "No flight found with this ID");
            }
        }
    }

    @FXML
    private void mainMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/AdminMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Mneu");

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
