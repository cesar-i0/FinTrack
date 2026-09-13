package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class transactionsScreen extends FinTrack{

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSerach;

    @FXML
    private TextField txtSearch;

    @FXML
    private void initialize() {

    }

    @FXML
    private void switchToEdit(Transacao t) throws IOException {
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

    @FXML
    private void searchTransaction(){
        try{
            List<Transacao> lis_t = transacaoDAO.findByDescription(txtSearch.getText());
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(lis_t);
            tblTransactions.setItems(observableList);
            configTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void editTransactionScreen(ActionEvent event) throws IOException {
        Transacao selecionada = tblTransactions.getSelectionModel().getSelectedItem();
        try {
            switchToEdit(selecionada);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteTransactionScreen(ActionEvent event) throws IOException {
        Transacao selecionada = tblTransactions.getSelectionModel().getSelectedItem();
        try {
            transacaoDAO.delete(selecionada.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

}
