package br.org.irede.fintrack.utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertsUtils {
    public static void emptyField(String m){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Atenção!");
        alert.setHeaderText(null);
        alert.setContentText(m);
        alert.showAndWait();
    }
}
