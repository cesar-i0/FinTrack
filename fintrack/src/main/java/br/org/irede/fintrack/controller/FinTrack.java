package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.dao.TransacaoDAO;
import br.org.irede.fintrack.model.Transacao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;


public abstract class FinTrack {

    protected final TransacaoDAO transacaoDAO = new TransacaoDAO();

    @FXML
    protected TableView<Transacao> tblTransactions;

    @FXML
    protected TableColumn<Transacao, LocalDate> colDate;

    @FXML
    protected TableColumn<Transacao, Double> colValue;

    @FXML
    protected TableColumn<Transacao, String> colDescription;

    @FXML
    protected TableColumn<Transacao, String> colType;

    @FXML
    protected Button btnHome;

    @FXML
    protected Button btnTransactions;

    @FXML
    protected Button btnReport;

    @FXML
    protected void switchToHome() throws IOException {
        Main.setRoot("homeScreen");
    }

    @FXML
    protected void switchToTransactions() throws IOException {
        Main.setRoot("transactionsScreen");
    }

    @FXML
    protected void switchToReport() throws IOException {
        Main.setRoot("reportScreen");
    }

}
