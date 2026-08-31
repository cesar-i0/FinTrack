package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.Formatador;
import javafx.scene.control.Button;

public class newTransactionsScreen extends FinTrack{

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtVal;

    @FXML
    private ComboBox<String> cbType;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Button btnSave;


    @FXML
    public void initialize() {
        cbType.setItems(FXCollections.observableArrayList("Receita", "Despesa"));
        cbType.getSelectionModel().selectFirst();
        dpDate.setValue(LocalDate.now());
    }

    @FXML
    public void novaTransacao(){
        try{
            String descricao = txtDesc.getText();
            Double valor = Formatador.conversorDouble(txtVal.getText());
            Boolean isR = "Receita".equals(cbType.getValue());
            LocalDate localDate = dpDate.getValue();

            Transacao t = new Transacao(descricao, valor, localDate, isR);
            transacaoDAO.save(t);

            Main.setRoot("PrimaryScreen");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco de dados: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao navegar para a tela principal: " + e.getMessage());
        }


    }

    @FXML
    public void saveTransaction(MouseEvent event) throws Exception {
        if(event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            novaTransacao();
        }
    }

}
