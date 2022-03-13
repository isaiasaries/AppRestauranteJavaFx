package AppMain;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Modulo de Inicio de la Aplicacion "Control de Restaurante"
 * @author Isaias Lagunes Pérez
 * Version 1.0 10/02/2022
 */
public class AppMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/loginVista.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Control de Acceso");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
