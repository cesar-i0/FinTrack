package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.dao.TransacaoDAO;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import br.org.irede.fintrack.app.Main;


public abstract class FinTrack {

    protected final TransacaoDAO transacaoDAO = new TransacaoDAO();

    @FXML
    protected void switchToHome(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("homeScreen");
        }
    }

    @FXML
    protected void switchToTransactions(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("transactionsScreen");
        }
    }

    @FXML
    protected void switchToReport(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("reportScreen");
        }
    }

    @FXML
    protected void switchToNewTransactions(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("newTransactionsScreen");
        }
    }

}
