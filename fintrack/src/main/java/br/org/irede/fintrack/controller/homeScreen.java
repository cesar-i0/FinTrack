package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.dao.TransacaoDAO;
import br.org.irede.fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class homeScreen extends FinTrack {

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblEntradas;

    @FXML
    private Label lblSaidas;

    @FXML
    private Button btnAdd;

    private final TransacaoDAO teste= new TransacaoDAO();

    @FXML
    public void initialize() {
        configTable();
        refreshDashboard();
    }

    public void refreshDashboard() {
        try{
            Double totalEntradas = transacaoDAO.getTotalPorTipo(true);
            Double totalSaidas = transacaoDAO.getTotalPorTipo(false);
            Double SaldoAtual = totalEntradas - totalSaidas;
            lblEntradas.setText(String.format("R$ %.2f", totalEntradas));
            lblSaidas.setText(String.format("R$ %.2f",totalSaidas));
            lblSaldo.setText(String.format("R$ %.2f",SaldoAtual));
            List<Transacao> transacoesHoje = transacaoDAO.findByData(LocalDate.now());
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(transacoesHoje);
            tblTransactions.setItems(observableList);
        }catch(SQLException e){
            lblSaldo.setText("Erro ao carregar");
            lblEntradas.setText("Erro ao carregar");
            lblSaidas.setText("Erro ao carregar");
        }
    }

    @FXML
    private void switchToNewTransactions() throws IOException {
        Main.setRoot("newTransactionScreen");
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colType.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

}
