package br.org.irede.fintrack.controller;

import br.org.irede.fintrack.app.Main;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.model.TransacaoMensal;
import br.org.irede.fintrack.utils.Formatador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class editTransactionScreen extends FinTrack{

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
    private Button btnUpdate;

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
        grpType = new ToggleGroup();
        rdbReceita.setToggleGroup(grpType);
        rdbDespesa.setToggleGroup(grpType);
        grpAgreement = new ToggleGroup();
        rdbSim.setToggleGroup(grpAgreement);
        rdbNao.setToggleGroup(grpAgreement);
    }

    private Transacao t;

    public void setObject(Transacao t) {
        this.t = t;
        if(t != null) {
            config();
        }
    }

    @FXML
    private void config(){
        try{
            grpType.selectToggle(t.getReceita() ? rdbReceita : rdbDespesa);
            cbCategory.setValue(t.getCategoria());
            if(t instanceof TransacaoMensal){
                TransacaoMensal tm = (TransacaoMensal) t;
                grpAgreement.selectToggle(rdbSim);
                txtDesc.setText(tm.getDescricao());
                txtVal.setText(tm.getValor().toString());
                dpIniDate.setValue(tm.getDataInicial());
                dpEndDate.setValue(tm.getDataFinal() != null ? tm.getDataFinal() : LocalDate.now());
                grpAgreement.selectToggle(((TransacaoMensal) t).getDataInicial() == null ? rdbSim : rdbNao);
            }else{
                grpAgreement.selectToggle(rdbNao);
                txtDesc.setText(t.getDescricao());
                txtVal.setText(t.getValor().toString());
                dpDate.setValue(t.getDate());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void updateTransaction(){
        try{
            String descricao = txtDesc.getText();
            Double valor = Formatador.conversorDouble(txtVal.getText());
            Boolean isR = grpType.getSelectedToggle() == rdbReceita;
            String cat = cbCategory.getValue();

            if(grpAgreement.getSelectedToggle() == rdbNao){
                LocalDate date = dpDate.getValue();
                Transacao atual = new Transacao(descricao, valor, date, isR, cat);
                atual.setId(t.getId());
                transacaoDAO.update(atual);
            }else{
                LocalDate ini = dpIniDate.getValue();
                LocalDate end = dpEndDate.getValue();
                TransacaoMensal atual = new TransacaoMensal(descricao, valor, ini, isR, cat, ini, end);
                atual.setId(t.getId());
                transacaoDAO.updateMensal(atual);
            }
            switchToTransactions();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco de dados: " + e.getMessage());
        }

    }

    @Override
    protected void switchToTransactions(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
