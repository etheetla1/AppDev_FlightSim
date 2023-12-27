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

/**
 * FXML Controller class
 *
 * @author
 */
public class AddFlightController implements Initializable {

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

    AdminDatabase AdminDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdminDatabase = new AdminDatabase();
    }

    /**
     * 
     * we get the data from textfeilds, make the flight class and save it in the db.
     * 
     */

    @FXML
    private void save(ActionEvent event) {
        if (fIDT.getText().isEmpty() || fromT.getText().isEmpty() || toT.getText().isEmpty()
                || dateT.getText().isEmpty() || timeT.getText().isEmpty() || capacityT.getText().isEmpty()) {
            Utils.showError("Empty Fields", "No field should be empty.");
        } else {
            try {

                Flight f = new Flight(fIDT.getText(), fromT.getText(), toT.getText(), dateT.getText(), timeT.getText(),
                        Integer.parseInt(capacityT.getText()), 0);
                AdminDatabase.addFlight(f);
                Utils.showInfo("Success", "Successfully added Flight to database");
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

    /**
     * 
     * Function for going back to main menu
     * 
     */

    @FXML
    private void mainMenu(ActionEvent event) {
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
    }

}
