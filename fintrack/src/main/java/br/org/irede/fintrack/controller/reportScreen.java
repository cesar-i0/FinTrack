package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.dao.TransacaoDAO;
import br.org.irede.fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class reportScreen extends FinTrack{

    @FXML
    private PieChart pieChart;

    @FXML
    private DatePicker dpIni;

    @FXML
    private DatePicker dpEnd;

    @FXML
    private Button btnFilter;

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
    private void initialize() {
        configTable();
        loadGrafics();
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colType.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML
    private void resultReport() {
        LocalDate ini = dpIni.getValue();
        LocalDate end = dpEnd.getValue();
        try {
            List<Transacao> list_t = transacaoDAO.findByPeriod(ini, end);
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(list_t);
            tblTransactions.setItems(observableList);
            tblTransactions.refresh();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  void loadGrafics() {
        try{
            Double totalEntradas = transacaoDAO.getTotalPorTipo(true);
            Double totalSaidas = transacaoDAO.getTotalPorTipo(false);
            if (totalEntradas == null) totalEntradas = 0.0;
            if (totalSaidas == null) totalSaidas = 0.0;
            reportPieChart(totalEntradas, totalSaidas);
        }catch (SQLException e){
            System.out.println("Erro ao carregar dados dos gráficos: " + e.getMessage());
        }
    }
    private void reportPieChart(Double entradas, Double saidas) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Entradas", entradas),
            new PieChart.Data("Saídas", saidas)
        );
        pieChart.setData(pieData);
        pieChart.setTitle("Distribuição Financeira");
    }


}
