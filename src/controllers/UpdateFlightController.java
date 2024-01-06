package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @FXML
    private TextField rIDT;

    AdminDatabase adminDatabase;
   // private final DisplayFlightsController DFC;
   // Flight flight;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminDatabase = new AdminDatabase();
        //fIDT.setText(flight.getFlightId());
    }

    @FXML
    private void save(ActionEvent event) {

        if (fIDT.getText().isEmpty() || fromT.getText().isEmpty() || toT.getText().isEmpty()
                || dateT.getText().isEmpty() || timeT.getText().isEmpty() || capacityT.getText().isEmpty() || rIDT.getText().isEmpty()) {
            Utils.showError("Empty Fields", "No field should be empty.");
        } else {
            try {

                Flight f = new Flight(fIDT.getText(), fromT.getText(), toT.getText(), dateT.getText(), timeT.getText(),
                        Integer.parseInt(capacityT.getText()), 0, Integer.parseInt(rIDT.getText()));
                adminDatabase.updateFlight(f);
                Utils.showInfo("Success", "Successfully updated Flight");
                fIDT.setText("");
                fromT.setText("");
                toT.setText("");
                dateT.setText("");
                timeT.setText("");
                capacityT.setText("");
                rIDT.setText("");

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
        if (fIDT.getText().isEmpty() && rIDT.getText().isEmpty()) {
            Utils.showError("Empty data", "Please enter flight id and record id");
        } else {
            Flight flight = searchFlight(fIDT.getText(), Integer.parseInt(rIDT.getText()));
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
    public Flight searchFlight(String flightId, int rID) {
        Flight foundFlight = null;

        try (Connection connection = MysqlDB.getConnection()) {
            // Prepare the SQL query
            String sql = "SELECT * FROM FlightData WHERE flightId = ? AND  recordId = ?";
           //String sql = "SELECT FT.flightId, fromCity, toCity, date, time, capacity, FD.bookedPassengers FROM flight_sim.flightdestinations FD right OUTER JOIN flight_sim.flighttest FT ON FD.flightId = FT.flightId WHERE FD.flightId = ?";
           try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set value for the placeholder in the SQL query
                preparedStatement.setString(1, flightId);
                preparedStatement.setInt(2, rID);
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                // Process the results
                if (resultSet.next()) {
                    foundFlight = new Flight(
                            resultSet.getString("flightId"),
                            resultSet.getString("fromCity"),
                            resultSet.getString("toCity"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("capacity"),
                            resultSet.getInt("bookedPassengers"),
                            resultSet.getInt("recordId"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundFlight;
    }
  //  public UpdateFlightController(Flight f) {
   //     this.flight = f; }
      /*  try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/UpdateFlight.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                //(Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Update Flight");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                UpdateFlightController controller = fxmlLoader.getController();
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
    } */

}
