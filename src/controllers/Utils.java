package controllers;

import javafx.scene.control.Alert;

public class Utils {

    public static void showError(String title, String header) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(header);
        error.showAndWait();
    }

    public static void showInfo(String title, String header) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(header);
        info.showAndWait();
    }
}
