/**
 * Configuração de Módulos (Java Platform Module System - JPMS).
 *
 * Este arquivo define as dependências e as permissões de acesso do sistema FinTrack:
 * - Define quais módulos do JavaFX e bibliotecas externas (ex: java.sql) o projeto utiliza.
 * - Exporta os pacotes internos para disponibilizar suas classes para o ambiente de execução.
 * - Abre os pacotes (opens) para reflexão, permitindo que o JavaFX/FXML acesse controllers
 *   e modelos privados.
 */
module br.org.irede.fintrack {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    // Permite que o JavaFX instancie a classe Main (app) e inicie a aplicação
    opens br.org.irede.fintrack.app to javafx.graphics, javafx.fxml;
    // Permite que o FXMLLoader acesse os Controllers definidos no FXML
    opens br.org.irede.fintrack.controller to javafx.fxml;
    // Permite que o TableView/PropertyValueFactory acesse os getters dos seus Models
    opens br.org.irede.fintrack.model to javafx.base, javafx.fxml;

    exports br.org.irede.fintrack.app;
}