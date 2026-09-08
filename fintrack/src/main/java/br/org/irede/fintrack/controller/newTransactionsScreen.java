package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.model.TransacaoMensal;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.Formatador;
import javafx.stage.Stage;

public class newTransactionsScreen extends FinTrack{

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtVal;

    @FXML
    private RadioButton rdbDespesa;

    @FXML
    private RadioButton rdbReceita;

    private ToggleGroup grpType;

    @FXML
    private ComboBox<String> cbCategory;

    @FXML
    private RadioButton rdbSim;

    @FXML
    private RadioButton rdbNao;

    private ToggleGroup grpAgreement;

    @FXML
    private DatePicker dpDate;

    @FXML
    private DatePicker dpIniDate;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblIniDate;

    @FXML
    private Label lblEndDate;

    @FXML
    public void initialize() {
        config();
    }

    private void config(){
        grpType = new ToggleGroup();
        grpAgreement = new ToggleGroup();
        rdbReceita.setToggleGroup(grpType);
        rdbDespesa.setToggleGroup(grpType);
        rdbSim.setToggleGroup(grpAgreement);
        rdbNao.setToggleGroup(grpAgreement);
        cbCategory.setItems(FXCollections.observableArrayList("Conta Essencial", "Seguro","Saúde","Assinatura","Lazer","Financeiro",
                                                                "Empresarial","Fiscal","Salario","Trabalho/Freelance","Educacao","Venda",
                                                                "Outras Saídas","Outras Entradas"));
        dpDate.setValue(LocalDate.now());
        dpIniDate.setValue(LocalDate.now());
        dpEndDate.setValue(LocalDate.now());
    }

    @FXML
    private void novaTransacao(){
        try{
            String descricao = txtDesc.getText();
            Double valor = Formatador.conversorDouble(txtVal.getText());
            Boolean isR = grpType.getSelectedToggle() == rdbReceita;
            String cat = cbCategory.getValue();

            if(grpType.getSelectedToggle() == rdbSim){
                LocalDate date = dpDate.getValue();
                Transacao t = new Transacao(descricao, valor, date, isR, cat);
                transacaoDAO.save(t);
            }else{
                LocalDate ini = dpIniDate.getValue();
                LocalDate end = dpEndDate.getValue();
                TransacaoMensal t = new TransacaoMensal(descricao, valor, ini, isR, cat, ini, end);
                transacaoDAO.saveMensal(t);
            }

            Main.setRoot("homeScreen");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco de dados: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao navegar para a tela principal: " + e.getMessage());
        }


    }

    @FXML
    private void saveTransaction() throws Exception {
        novaTransacao();
    }

    @Override
    protected void switchToHome(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
