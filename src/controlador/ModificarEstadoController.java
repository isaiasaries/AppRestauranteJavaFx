package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.OrdenTemporal;
import modelo.OrdenTemporalDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ModificarEstadoController implements Initializable {

    @FXML
    private RadioButton rbListo;
    @FXML
    private ToggleGroup togEstado;
    @FXML
    private RadioButton rbEntregado;
    @FXML
    private Label lblMesa;
    @FXML
    private Label lblMenu;

    private OrdenTemporal ordenTemp;
    private OrdenTemporalDAO ordenTempDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordenTemp = new OrdenTemporal();
    }

    public void setDatos(OrdenTemporal ordenTemp) {
        this.ordenTemp = ordenTemp;
        lblMesa.setText(ordenTemp.getIdMesa() + "");
        lblMenu.setText(ordenTemp.getDescMenu());
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        ordenTempDao = new OrdenTemporalDAO();
        if (rbListo.isSelected()) {
            ordenTemp.setEntregado("Listo");
        } else if (rbEntregado.isSelected()) {
            ordenTemp.setEntregado("Entregado");
        }
        if (ordenTempDao.upEntregado(ordenTemp)) {
          //  Validaciones.alertInfo("Modificacion del Estado de la Orden", "Orden Modificada");
            c_btnCancelar(event);
        } else {
            Validaciones.alertError("Modificacion del Estado de la Orden", "Error al Modificar");
        }
    }

    @FXML
    private void c_btnCancelar(ActionEvent event) {
        Stage stage = (Stage) lblMesa.getScene().getWindow();
        stage.close();
    }

}
