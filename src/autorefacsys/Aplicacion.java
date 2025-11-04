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
        // Intenta primero la ruta relativa (igual que tenías)
        URL urlRel = getClass().getResource("vista/login.fxml");
        System.out.println("Recurso relativo: " + urlRel);

        // Si es null, intenta con la ruta absoluta dentro del classpath
        URL fxmlUrl = urlRel;
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource("/com/autorefacsys/vista/login.fxml");
            System.out.println("Recurso absoluto: " + fxmlUrl);
        }

        if (fxmlUrl == null) {
            // Mensaje claro para que sepas exactamente qué buscar
            throw new IllegalStateException(
                "No se encontró login.fxml. Debe estar en 'src/com/autorefacsys/vista/login.fxml' " +
                "y ser incluido en el classpath al compilar. Rutas probadas: " +
                "getResource(\"vista/login.fxml\") y getResource(\"/com/autorefacsys/vista/login.fxml\")."
            );
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 400);

        // CSS comentado temporalmente (descomenta cuando confirmes que el FXML carga)
        // URL css = getClass().getResource("/com/autorefacsys/css/estilos.css");
        // if (css != null) scene.getStylesheets().add(css.toExternalForm());

        primaryStage.setTitle("AutoRefacSys - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
