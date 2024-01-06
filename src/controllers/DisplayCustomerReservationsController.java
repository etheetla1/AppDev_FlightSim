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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;
import model.Reservation;

public class DisplayCustomerReservationsController implements Initializable {

    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<?, ?> resCol;
    @FXML
    private TableColumn<?, ?> fIDCol;
    @FXML
    private TableColumn<?, ?> fromCol;
    @FXML
    private TableColumn<?, ?> toCol;
    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> timeCol;
    @FXML
    private TableColumn<?, ?> seatCol;

    CustomerDatabase customerDatabase;
    Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerDatabase = new CustomerDatabase();

        resCol.setCellValueFactory(new PropertyValueFactory<>("reservationNumber"));
        fIDCol.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

    }

    public void init(Customer customer) {
        this.customer = customer;
        showData();

    }

    /**
     * Function gets user flights and set the data inside the table
     */

    public void showData() {
        try {
            table.setItems(data());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * If customer is null, its an admin, we then return all the flights
     * 
     * else we query customer's flights by username
     * 
     */

    public ObservableList<Reservation> data() throws SQLException {

        ArrayList<Reservation> reservations;

        if (customer == null)
            //reservations = new AdminDatabase().getReservations();
            reservations = new AdminDatabase().getReservations();
        else
            //reservations = customerDatabase.reservations(customer.getUsername());
            reservations = customerDatabase.reservationsData(customer.getUsername());

        ObservableList<Reservation> reservationsOb = FXCollections.observableArrayList();

        for (Reservation res : reservations)
            reservationsOb.add(res);

        return reservationsOb;
    }

    @FXML
    private void tableClicked(MouseEvent event) {
    }

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
                stage.setTitle("Customer Menu");
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
