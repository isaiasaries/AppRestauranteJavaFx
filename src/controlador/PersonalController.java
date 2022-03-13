package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Personal;
import modelo.PersonalDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class PersonalController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfResponsable;
    @FXML
    private TableView<Personal> tvResponsables;
    @FXML
    private TableColumn<Personal, Integer> colClave;
    @FXML
    private TableColumn<Personal, String> colResponsable;
    @FXML
    private TextField tfClave;
    @FXML
    private Label lblMensaje;

    private Personal personal;
    private PersonalDAO personaldao;
    private ObservableList<Personal> lstPersonal;
    private int index = -1;

    private int clave;
    private String nombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarTV();
    }

    private void inicializarTV() {
        colClave.setCellValueFactory(new PropertyValueFactory<>("idPersonal"));
        colResponsable.setCellValueFactory(new PropertyValueFactory<>("nombrePersonal"));
        llenarTV();
    }

    private void llenarTV() {
        personaldao = new PersonalDAO();
        lstPersonal = personaldao.getAllPersonal();
        tvResponsables.setItems(lstPersonal);
    }

    private void asignarValores() {
        if (!tfClave.getText().isEmpty()) {
            clave = Integer.parseInt(tfClave.getText().trim());
        }
        nombre = tfResponsable.getText();
    }

    private boolean validarDatos() {
        asignarValores();
        if (nombre.trim().isEmpty()) {
            lblMensaje.setText("Coloca Cargo del Responsable");
            return false;
        } else {
            personal = new Personal();
            personal.setIdPersonal(clave);
            personal.setNombrePersonal(nombre);
        }
        return true;
    }

    private void limpiarCajas() {
        tfClave.clear();
        tfResponsable.clear();
        personal = null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void onMouseCliked(MouseEvent event) {
        index = tvResponsables.getSelectionModel().getSelectedIndex();
        personal = null;
        personal = this.tvResponsables.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfClave.setText(colClave.getCellData(index).toString());
            tfResponsable.setText(colResponsable.getCellData(index));
            btnGuardar.setVisible(false);
            btnNuevo.setVisible(true);
        }
    }

    @FXML
    private void onMouseOut(MouseEvent event) {
        lblMensaje.setText("");
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        boolean guardo;
        if (tfClave.getText().trim().isEmpty()) {
            if (validarDatos()) {
                guardo = personaldao.insertar(personal);
                lblMensaje.setText("¡Nuevo Personal Guardado!");
                if (guardo) {
                    limpiarCajas();
                    llenarTV();
                } else {
                    lblMensaje.setText("¡Error al Guardar!");
                }
            }
        }
    }

    @FXML
    private void c_btnModificar(ActionEvent event) {
        if (personal == null) {
            lblMensaje.setText("Selecciona un Personal");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = personaldao.actualizar(personal);
                lblMensaje.setText("Personal Modificado Correctamente");
                if (modifico) {
                    limpiarCajas();
                    llenarTV();
                } else {
                    lblMensaje.setText("Error al Intentar Modificar");
                }
            }
        }
    }

    @FXML
    private void c_btnEliminar(ActionEvent event) {
        if (personal == null) {
            lblMensaje.setText("Debes de seleccionar un Personal");
        } else {
            boolean elimino;
            elimino = personaldao.eliminar(personal);
            lblMensaje.setText("Personal Eliminado Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }
}
