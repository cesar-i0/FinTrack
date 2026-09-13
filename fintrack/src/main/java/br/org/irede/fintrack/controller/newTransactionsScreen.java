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
import br.org.irede.fintrack.utils.AlertsUtils;

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
    }

    @FXML
    private void novaTransacao(){
        try{
            String descricao = txtDesc.getText();
            if(descricao.isEmpty()){
                AlertsUtils.emptyField("O campo descrição está vazio! Por favor, preencha a descrição.");
                return;
            }
            Double valor = Formatador.conversorDouble(txtVal.getText());
            if(valor == null || valor <= 0){
                AlertsUtils.emptyField("O campo valor está vazio ou o valor é inválido! Por favor, preencha com um valor válido.");
                return;
            }
            Boolean isR = grpType.getSelectedToggle() == rdbReceita;
            if(grpType.getSelectedToggle() == null){
                AlertsUtils.emptyField("Não foi seleciondo o tipo de transação! Por favor, selecione o tipo de transação.");
                return;
            }
            String cat = cbCategory.getValue();
            if(cat == null){
                AlertsUtils.emptyField("Não foi selecionado uma categoria! Por favor, preencha a categoria.");
                return;
            }

            if(grpType.getSelectedToggle() == rdbSim){
                LocalDate date = dpDate.getValue();
                if(date == null){
                    AlertsUtils.emptyField("Não foi selecionado uma data! Por favor, preencha o campo data.");
                }
                Transacao t = new Transacao(descricao, valor, date, isR, cat);
                transacaoDAO.save(t);
            }else{
                LocalDate ini = dpDate.getValue();
                if(ini == null){
                    AlertsUtils.emptyField("Não foi selecionado uma data! Por favor, preencha o campo data.");
                    return;
                }
                LocalDate end = dpEndDate.getValue();
                if(end == null){
                    AlertsUtils.emptyField("Não foi selecionado uma data de término! Por favor, preencha o campo da data de término.");
                    return;
                }
                TransacaoMensal t = new TransacaoMensal(descricao, valor, ini, isR, cat, end);
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
