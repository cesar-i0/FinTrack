package br.org.irede.fintrack.controller;

import br.org.irede.fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private void searchTransaction(){
        try{
            List<Transacao> lis_t = transacaoDAO.findAll();
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(lis_t);
            tblTransactions.setItems(observableList);
            configTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colType.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

}
