package modelo;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Validaciones {

    /* public static void alertConfirm(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }*/
    public static boolean alertConfirm(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();

        /*if (action.get() == ButtonType.OK) {
            this.txtPedirConfi.setText("Has pulsado en aceptar");
        } else {
            this.txtPedirConfi.setText("Has pulsado en cancelar");
        }*/

        return action.get() == ButtonType.OK;
    }

    public static void alertInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }

    public static void alertError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }

}
