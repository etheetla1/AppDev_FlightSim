/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Flight;

public class DisplayFlightsController implements Initializable {

    @FXML
    private TableView<Flight> table;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> from;
    @FXML
    private TableColumn<?, ?> to;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> time;
    @FXML
    private TableColumn<?, ?> capacity;
    @FXML
    private TableColumn<?, ?> booked;

    /**
     * Initializes the controller class.
     */
    AdminDatabase adminDatabase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        adminDatabase = new AdminDatabase();

        id.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        from.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        to.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        booked.setCellValueFactory(new PropertyValueFactory<>("bookedPassengers"));

        showData();

    }

    /**
     * Adds all the flights that are running onto the flight table
     */

    public void showData() {
        try {
            table.setItems(data());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<Flight> data() throws SQLException {
        ArrayList<Flight> cs = adminDatabase.getFlights();
        ObservableList<Flight> cst = FXCollections.observableArrayList();
        for (Flight c : cs) {
            cst.add(c);

        }
        return cst;
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
            AdminMenuController controller = fxmlLoader.getController();
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
