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
import modelo.Clientes;
import modelo.ClientesDAO;
import modelo.Responsable;
import modelo.ClientesDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ClientesController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;    
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDatos;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Clientes> tvClientes;
    @FXML
    private TableColumn<Clientes, Integer> colClave; 
    @FXML
    private TableColumn<Clientes, String> colNombre;
    @FXML
    private TableColumn<Clientes, String> colDatos;
        
    private int clave;
    private String nombre;  
    private String datos;
    private Clientes clientes;
    private ClientesDAO clientesdao;
    private ObservableList<Clientes> lstClientes;
    private int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTV();
    }

    private void inicializarTV() {
        colClave.setCellValueFactory(new PropertyValueFactory<>("idClientes"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreClientes"));
        colDatos.setCellValueFactory(new PropertyValueFactory<>("datosClientes"));
        llenarTV();
    }

    private void llenarTV() {
        clientesdao = new ClientesDAO();
        lstClientes = clientesdao.getAllClientes();
        tvClientes.setItems(lstClientes);
    }

    private void asignarValores() {
        if (!tfClave.getText().isEmpty()) {
            clave = Integer.parseInt(tfClave.getText().trim());
        }
        nombre = tfNombre.getText();
        datos = tfDatos.getText();
    }

    private boolean validarDatos() {
        asignarValores();
        if (nombre.trim().isEmpty()) {
            lblMensaje.setText("Coloca el Nombre del Cliente");
            return false;
        } else {
            clientes = new Clientes();
            clientes.setIdClientes(clave);
            clientes.setNombreClientes(nombre);
            clientes.setDatosClientes(datos);
        }
        return true;
    }

    private void limpiarCajas() {
        tfClave.clear();
        tfNombre.clear();
        tfDatos.clear();
        clientes = null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
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
                guardo = clientesdao.insertar(clientes);
                lblMensaje.setText("¡Nuevo Cliente Guardado!");
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
        if (clientes == null) {
            lblMensaje.setText("Selecciona un Responsable");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = clientesdao.actualizar(clientes);
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
        if (clientes == null) {
            lblMensaje.setText("Debes de seleccionar un Responsable");
        } else {
            boolean elimino;
            elimino = clientesdao.eliminar(clientes);
            lblMensaje.setText("Responsable Eliminado Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        index = tvClientes.getSelectionModel().getSelectedIndex();
        clientes = null;
        clientes = this.tvClientes.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfClave.setText(colClave.getCellData(index).toString());
            tfNombre.setText(colNombre.getCellData(index));
            tfDatos.setText(colDatos.getCellData(index));
            btnGuardar.setVisible(false);
            btnNuevo.setVisible(true);
        }
    }
}
