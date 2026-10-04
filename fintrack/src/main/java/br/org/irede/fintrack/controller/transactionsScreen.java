package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.Formatador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import br.org.irede.fintrack.utils.AlertsUtils;

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
        allData();
    }

    private void allData(){
        try {
            List<Transacao> lis_t = transacaoDAO.findAll();
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(lis_t);
            tblTransactions.setItems(observableList);
            configTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void searchTransaction(){
        try{
            List<Transacao> lis_t = transacaoDAO.findByDescription(txtSearch.getText());
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(lis_t);
            tblTransactions.setItems(observableList);
            if(txtSearch.getText().isEmpty()){
                AlertsUtils.showWarning("A barra de pesquisa está vazia! Por favor, preencha com o que deseja encontrar!");
            }
            configTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void editTransactionScreen(ActionEvent event){
        Transacao selecionada = tblTransactions.getSelectionModel().getSelectedItem();
        try {
            if(selecionada != null) {
                switchToEdit(selecionada);
                searchTransaction();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteTransactionScreen(ActionEvent event) throws IOException {
        Transacao selecionada = tblTransactions.getSelectionModel().getSelectedItem();
        try {
            if(selecionada != null) {
                transacaoDAO.delete(selecionada.getId());
                searchTransaction();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colT.setCellValueFactory(new PropertyValueFactory<>("ehreceita"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        colDate.setCellFactory(column -> new TableCell<Transacao, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else{
                    setText(Formatador.conversorString(item));
                }
            }
        });
        colT.setCellFactory(column -> new TableCell<Transacao, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else{
                    setText(item ? "Receita" : "Despesa");
                }
            }
        });
    }

}
