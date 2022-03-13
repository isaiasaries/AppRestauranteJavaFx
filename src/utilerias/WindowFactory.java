package utilerias;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class WindowFactory {

    public void crearVentana(String ruta, String titulo) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.setScene(escena);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ruta + ex.getMessage());
        }
    }

    public void crearVentanaSinBordes(String ruta) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setScene(escena);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ruta + ex.getMessage());
        }
    }
}
