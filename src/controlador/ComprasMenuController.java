package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilerias.WindowFactory;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ComprasMenuController implements Initializable {

    @FXML
    private Button btnElaborar;
    @FXML
    private Button btnResumen;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void c_btnElaborar(ActionEvent event) {
        WindowFactory ventana = new WindowFactory();
        ventana.crearVentana("/vista/ComprasVista.fxml", "Compras");
    }

    @FXML
    private void c_btnResumen(ActionEvent event) {
         WindowFactory ventana = new WindowFactory();
        ventana.crearVentana("/vista/ReporteComprasVista.fxml", "Resumen de Compras");
    }

    @FXML
    private void c_btnReportes(ActionEvent event) {
    }

    @FXML
    private void c_btnCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
}
