package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Nivel;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class NivelGuardarController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtDescripcionNivel;
    @FXML
    private Label lblId;
    @FXML
    private Label lblMensaje;

    private Nivel nivel;
    private ObservableList<Nivel> lstNiveles;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initAtributos(ObservableList<Nivel> lstNiveles) {
        this.lstNiveles = lstNiveles;
    }

    public void initAtributos(ObservableList<Nivel> lstNiveles, Nivel n) {
        this.lstNiveles = lstNiveles;
        this.nivel = n;
        lblId.setText(n.getIdNivel() + "");
        txtDescripcionNivel.setText(n.getDescripcionNivel());
    }
    @FXML
    private void clickBtnGuardar(ActionEvent event) {
        if (txtDescripcionNivel.getText().isEmpty()) {
            Validaciones.alertError("Error", "Coloca una descripción del nivel");
        } else {
            String descripcion = txtDescripcionNivel.getText();
            try {
                if (!lblId.getText().isEmpty()) {
                    //Modificar
                   this.nivel.setDescripcionNivel(descripcion);
                    if (lstNiveles.contains(nivel)) {
                        lblMensaje.setText("El nivel ya Existe");
                    }
                } else {
                    //insertar
                    Nivel n = new Nivel();
                    n.setIdNivel((byte) 0);
                    n.setDescripcionNivel(descripcion);
                    if (!lstNiveles.contains(n)) {
                        //Si no se repite, procedemos a la Insert  
                        this.nivel = n;
                    }
                }
                //Cerrar la ventana actual una vez realizada la operacion
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                System.out.println("Error Niveles" + e.getMessage());
            }
        }

    }

    @FXML
    private void clickBtnCancelar(ActionEvent event) {
        this.nivel = null;
        //Cerrar la ventana actual una vez realizada la operacion
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public Nivel getNivel() {
        return nivel;
    }
}
