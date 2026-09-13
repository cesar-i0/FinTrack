package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.AlertsUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    private void initialize() {
        configTable();
        loadGrafics();
    }

    private void configTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML
    private void resultReport() {
        LocalDate ini = dpIni.getValue();
        if(ini == null){
            AlertsUtils.emptyField("Não foi selecionado uma data inicial! Por favor, preencha o campo data final.");
            return;
        }
        LocalDate end = dpEnd.getValue();
        if(end == null){
             AlertsUtils.emptyField("Não foi selecionado uma data final! Por favor, preencha o campo da data final.");
             return;
        }
        try {
            List<Transacao> list_t = transacaoDAO.findByPeriod(ini, end);
            ObservableList<Transacao> observableList = FXCollections.observableArrayList(list_t);
            tblTransactions.setItems(observableList);
            tblTransactions.refresh();
            loadGrafics();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  void loadGrafics() {
        try{
            LocalDate ini = dpIni.getValue();
            LocalDate end = dpEnd.getValue();
            Map<String,Double> cat = transacaoDAO.getTotalByCategory(ini, end);
            reportPieChart(cat);
        }catch (SQLException e){
            System.out.println("Erro ao carregar dados dos gráficos: " + e.getMessage());
        }
    }
    private void reportPieChart(Map<String,Double> cat) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(Map.Entry<String,Double> entry : cat.entrySet()){
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieData);
        pieChart.setTitle("Relatório Financeiro");
    }


}
