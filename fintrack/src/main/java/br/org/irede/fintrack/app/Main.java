package br.org.irede.fintrack.app;
import br.org.irede.fintrack.utils.DataBaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author cesar.i0
 */

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException{
        DataBaseConnection.initDB();
        scene = new Scene(loadFXML("homeScreen"),1280,720);
        stage.setTitle("FinTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/br/org/irede/fintrack/view/"+ fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args){
        launch();
    }

}
