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
import model.Flight;

public class UpdateFlightController implements Initializable {

    @FXML
    private TextField fIDT;
    @FXML
    private TextField fromT;
    @FXML
    private TextField toT;
    @FXML
    private TextField dateT;
    @FXML
    private TextField timeT;
    @FXML
    private TextField capacityT;

    AdminDatabase adminDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminDatabase = new AdminDatabase();
    }

    @FXML
    private void save(ActionEvent event) {

        if (fIDT.getText().isEmpty() || fromT.getText().isEmpty() || toT.getText().isEmpty()
                || dateT.getText().isEmpty() || timeT.getText().isEmpty() || capacityT.getText().isEmpty()) {
            Utils.showError("Empty Fields", "No field should be empty.");
        } else {
            try {

                Flight f = new Flight(fIDT.getText(), fromT.getText(), toT.getText(), dateT.getText(), timeT.getText(),
                        Integer.parseInt(capacityT.getText()), 0);
                adminDatabase.updateFlight(f);
                Utils.showInfo("Success", "Successfully updated Flight");
                fIDT.setText("");
                fromT.setText("");
                toT.setText("");
                dateT.setText("");
                timeT.setText("");
                capacityT.setText("");

            } catch (Exception e) {
                Utils.showError("Valid Data", "Please add valid data in respective fields");

            }
        }
    }

    @FXML
    private void mainMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/AdminMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Menu");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AdminMenuController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void searchFlight(ActionEvent event) {
        if (fIDT.getText().isEmpty()) {
            Utils.showError("Empty Flight ID", "Please enter flight id first");
        } else {

            Flight flight = adminDatabase.searchFlight(fIDT.getText());
            if (flight == null) {
                Utils.showError("Error", "No flight found with this id");
            } else {
                fromT.setText(flight.getFromCity());
                toT.setText(flight.getToCity());
                dateT.setText(flight.getDate());
                timeT.setText(flight.getTime());
                capacityT.setText(flight.getCapacity() + "");
                fIDT.setEditable(false);
            }

        }
    }

}
