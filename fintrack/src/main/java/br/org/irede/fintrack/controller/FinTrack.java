package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.dao.TransacaoDAO;
import br.org.irede.fintrack.model.Transacao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


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
    protected TableColumn<Transacao, Boolean> colT;

    @FXML
    protected TableColumn<Transacao, String> colCat;

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

    protected static List<String> Categorias =
        List.of("Conta Essencial", "Seguro","Saúde","Assinatura","Lazer","Financeiro", "Empresarial",
                "Fiscal","Salario","Trabalho/Freelance","Educacao","Venda", "Outras Saídas","Outras Entradas");

    @FXML
    protected void switchToEdit(Transacao t) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/br/org/irede/fintrack/view/editTransactionScreen.fxml"));
        Parent root = loader.load();
        Stage transactionModal = new Stage();
        transactionModal.setScene(new Scene(root));
        transactionModal.setTitle("Editar transação");

        editTransactionScreen controller = loader.getController();
        controller.setObject(t);

        Stage homeScreen = (Stage) tblTransactions.getScene().getWindow();
        transactionModal.initOwner(homeScreen);
        transactionModal.initModality(Modality.APPLICATION_MODAL);

        transactionModal.setX(homeScreen.getX()/2);
        transactionModal.setY(homeScreen.getY()/2);

        transactionModal.showAndWait();
    }

}
