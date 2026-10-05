package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.AlertsUtils;
import br.org.irede.fintrack.utils.Formatador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
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
        colValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colT.setCellValueFactory(new PropertyValueFactory<>("ehreceita"));

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

    @FXML
    private void resultReport() {
        LocalDate ini = dpIni.getValue();
        if(ini == null){
            AlertsUtils.showWarning("Não foi selecionado uma data inicial! Por favor, preencha o campo data final.");
            return;
        }
        LocalDate end = dpEnd.getValue();
        if(end == null){
             AlertsUtils.showWarning("Não foi selecionado uma data final! Por favor, preencha o campo da data final.");
             return;
        }else if(end.isBefore(ini)){
            AlertsUtils.showError("A data final selecionada não pode ser anterior a data de inicial! Por favor, selecione uma data válida!");
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
        if (cat == null || cat.isEmpty()) {
            pieChart.setData(FXCollections.emptyObservableList());
            return;
        }
        double total = cat.values().stream().mapToDouble(Double::doubleValue).sum();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(Map.Entry<String,Double> entry : cat.entrySet()){
            double valor = entry.getValue();
            double percentagem = (total > 0) ? (valor / total) * 100 : 0.0;
            String label = String.format("%s (%.1f%%)", entry.getKey(), percentagem);
            pieData.add(new PieChart.Data(label,valor));
        }
        pieChart.setData(pieData);
        pieChart.setTitle("Relatório Financeiro");
    }


}
