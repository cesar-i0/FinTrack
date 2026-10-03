package br.org.irede.fintrack.utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertsUtils {

    public static void showWarning(String m){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Atenção!");
        alert.setContentText(m);
        alert.showAndWait();
    }

    public static void showError(String m){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Erro!");
        alert.setContentText(m);
        alert.showAndWait();
    }

    public static void showConfirmation(String m){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informação!");
        alert.setContentText(m);
        alert.showAndWait();
    }
}
