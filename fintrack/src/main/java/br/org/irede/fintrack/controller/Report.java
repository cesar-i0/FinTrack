package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.dao.TransacaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import br.org.irede.fintrack.app.Main;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class Report {

    @FXML
    private void switchToPrimary(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("PrimaryScreen");
        }
    }

    @FXML
    private void switchToNewTransaction(MouseEvent event) throws IOException {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            Main.setRoot("NewTransaction");
        }
    }

    @FXML
    private PieChart pieChart;

    private final TransacaoDAO transacaoDAO = new TransacaoDAO();

    @FXML
    private void initialize() {
        loadGrafics();
    }

    public  void loadGrafics() {
        try{
            Double totalEntradas = transacaoDAO.getTotalPorTipo(true);
            Double totalSaidas = transacaoDAO.getTotalPorTipo(false);
            if (totalEntradas == null) totalEntradas = 0.0;
            if (totalSaidas == null) totalSaidas = 0.0;
            popularPieChart(totalEntradas, totalSaidas);
        }catch (SQLException e){
            System.out.println("Erro ao carregar dados dos gráficos: " + e.getMessage());
        }
    }

    private void popularPieChart(Double entradas, Double saidas) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Entradas", entradas),
            new PieChart.Data("Saídas", saidas)
        );
        pieChart.setData(pieData);
        pieChart.setTitle("Distribuição Financeira");
    }


}
