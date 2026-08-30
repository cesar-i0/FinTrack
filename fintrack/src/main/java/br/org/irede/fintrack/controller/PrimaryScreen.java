package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.dao.TransacaoDAO;
import br.org.irede.fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import br.org.irede.fintrack.app.Main;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.scene.control.Label;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrimaryScreen {

    @FXML
    private void switchToNewTransaction(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("NewTransaction");
        }
    }

    @FXML
    private void switchToReport(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("Report");
        }
    }

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblEntradas;

    @FXML
    private Label lblSaidas;

    private final TransacaoDAO transacaoDAO = new TransacaoDAO();

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
    private TableView<Transacao> tblTransactions;

    @FXML
    private TableColumn<Transacao, LocalDate> colDate;

    @FXML
    private TableColumn<Transacao, Double> colValue;

    @FXML
    private TableColumn<Transacao, String> colDescription;

    @FXML
    private TableColumn<Transacao, String> colType;

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colType.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

}
