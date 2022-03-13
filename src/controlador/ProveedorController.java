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
import modelo.Proveedor;
import modelo.ProveedorDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ProveedorController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField tfProveedor;
    @FXML
    private TableView<Proveedor> tvProveedor;
    @FXML
    private TableColumn<Proveedor, Integer> colClave;
    @FXML
    private TableColumn<Proveedor, String> colProveedor;
    @FXML
    private TextField tfClave;
    @FXML
    private Label lblMensaje;

    private Proveedor proveedor;
    private ProveedorDAO proveedordao;
    private ObservableList<Proveedor> lstProveedor;
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
        colClave.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        llenarTV();
    }

    private void llenarTV() {
        proveedordao = new ProveedorDAO();
        lstProveedor = proveedordao.getAllProveedores();
        tvProveedor.setItems(lstProveedor);
    }

    private void asignarValores() {
        if (!tfClave.getText().isEmpty()) {
            clave = Integer.parseInt(tfClave.getText().trim());
        }
        nombre = tfProveedor.getText();
    }

    private boolean validarDatos() {
        asignarValores();
        if (nombre.trim().isEmpty()) {
            lblMensaje.setText("Coloca Nombre del Proveedor");
            return false;
        } else {
            proveedor = new Proveedor();
            proveedor.setIdProveedor(clave);
            proveedor.setNombreProveedor(nombre);
        }
        return true;
    }

    private void limpiarCajas() {
        tfClave.clear();
        tfProveedor.clear();
        proveedor = null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void onMouseCliked(MouseEvent event) {
        index = tvProveedor.getSelectionModel().getSelectedIndex();
        proveedor = null;
        proveedor = this.tvProveedor.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfClave.setText(colClave.getCellData(index).toString());
            tfProveedor.setText(colProveedor.getCellData(index));
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
                guardo = proveedordao.insertar(proveedor);
                lblMensaje.setText("¡Nuevo Proveedor Guardado!");
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
        if (proveedor == null) {
            lblMensaje.setText("Selecciona un Proveedor");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = proveedordao.actualizar(proveedor);
                lblMensaje.setText("Proveedor Modificado Correctamente");
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
        if (proveedor == null) {
            lblMensaje.setText("Debes de seleccionar un Proveedor");
        } else {
            boolean elimino;
            elimino = proveedordao.eliminar(proveedor);
            lblMensaje.setText("Proveedor Eliminado Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }
}
