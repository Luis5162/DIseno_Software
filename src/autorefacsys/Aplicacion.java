package autorefacsys;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacion extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL urlRel = getClass().getResource("vista/login.fxml");
        System.out.println("Recurso relativo: " + urlRel);

        URL fxmlUrl = urlRel;
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource("/com/autorefacsys/vista/login.fxml");
            System.out.println("Recurso absoluto: " + fxmlUrl);
        }

        if (fxmlUrl == null) {
            throw new IllegalStateException(
                "No se encontró login.fxml. Debe estar en 'src/com/autorefacsys/vista/login.fxml' " +
                "y ser incluido en el classpath al compilar. Rutas probadas: " +
                "getResource(\"vista/login.fxml\") y getResource(\"/com/autorefacsys/vista/login.fxml\")."
            );
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Sistema General - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}