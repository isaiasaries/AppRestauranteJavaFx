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
import modelo.Responsable;
import modelo.ResponsableDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ResponsableController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfResponsable;
    @FXML
    private TableView<Responsable> tvResponsables;
    @FXML
    private TableColumn<Responsable, Integer> colClave;
    @FXML
    private TableColumn<Responsable, String> colResponsable;
    @FXML
    private TextField tfClave;
    @FXML
    private Label lblMensaje;

    private Responsable responsable;
    private ResponsableDAO responsabledao;
    private ObservableList<Responsable> lstResponsables;
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
        colClave.setCellValueFactory(new PropertyValueFactory<>("idResponsable"));
        colResponsable.setCellValueFactory(new PropertyValueFactory<>("nombreResponsable"));
        llenarTV();
    }

    private void llenarTV() {
        responsabledao = new ResponsableDAO();
        lstResponsables = responsabledao.getAllResponsables();
        tvResponsables.setItems(lstResponsables);
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
            responsable = new Responsable();
            responsable.setIdResponsable(clave);
            responsable.setNombreResponsable(nombre);
        }
        return true;
    }

    private void limpiarCajas() {
        tfClave.clear();
        tfResponsable.clear();
        responsable = null;
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
        responsable = null;
        responsable = this.tvResponsables.getSelectionModel().getSelectedItem();
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
                guardo = responsabledao.insertar(responsable);
                lblMensaje.setText("¡Nuevo Responsable Guardado!");
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
        if (responsable == null) {
            lblMensaje.setText("Selecciona un Responsable");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = responsabledao.actualizar(responsable);
                lblMensaje.setText("Responsable Modificado Correctamente");
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
        if (responsable == null) {
            lblMensaje.setText("Debes de seleccionar un Responsable");
        } else {
            boolean elimino;
            elimino = responsabledao.eliminar(responsable);
            lblMensaje.setText("Responsable Eliminado Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }
}
