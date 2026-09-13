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
    requires transitive javafx.graphics;

    opens br.org.irede.fintrack.app to javafx.graphics, javafx.fxml;
    opens br.org.irede.fintrack.controller to javafx.fxml;
    opens br.org.irede.fintrack.model to javafx.base, javafx.fxml;

    exports br.org.irede.fintrack.app;
}