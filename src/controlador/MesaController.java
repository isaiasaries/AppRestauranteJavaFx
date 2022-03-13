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
import modelo.Mesa;
import modelo.MesaDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class MesaController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private TextField tfClave;
    @FXML
    private TableView<Mesa> tvMesas;
    @FXML
    private TableColumn<Mesa, Integer> colClave;
    @FXML
    private TableColumn<Mesa, String> colMesa;
    @FXML
    private TableColumn<Mesa, String> colEstado;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblMensaje;
    
    private Mesa mesa;
    private MesaDAO mesadao;
    private ObservableList<Mesa> lstMesas;
    private int index = -1;

    private int clave;
    private String nombre;
    private String estado;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTV();
    }
    private void inicializarTV(){
    colClave.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        colMesa.setCellValueFactory(new PropertyValueFactory<>("nombreMesa"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoMesa"));
        llenarTV();
    }

    private void llenarTV() {
        mesadao = new MesaDAO();
        lstMesas = mesadao.getAllMesas();
        tvMesas.setItems(lstMesas);
    }
    private void asignarValores() {
        if (!tfClave.getText().isEmpty()) {
            clave = Integer.parseInt(tfClave.getText().trim());
        }
        nombre = tfNombre.getText();
        estado="L";
    }

    private boolean validarDatos() {
        asignarValores();
        if (nombre.trim().isEmpty()) {
            lblMensaje.setText("Coloca la Nombre de la Mesa");
            return false;
        } else {
            mesa = new Mesa();
            mesa.setIdMesa(clave);
            mesa.setNombreMesa(nombre);
            mesa.setEstadoMesa(estado);
        }
        return true;
    }
    private void limpiarCajas() {
        tfClave.clear();
        tfNombre.clear();
        mesa = null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void onMouseCliked(MouseEvent event) {
         index = tvMesas.getSelectionModel().getSelectedIndex();
        mesa = null;
        mesa = this.tvMesas.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfClave.setText(colClave.getCellData(index).toString());
            tfNombre.setText(colMesa.getCellData(index));
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
                guardo = mesadao.insertar(mesa);
                lblMensaje.setText("¡Nuevo Mesa Guardada!");
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
        if (mesa == null) {
            lblMensaje.setText("Selecciona un Nivel");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = mesadao.actualizar(mesa);
                lblMensaje.setText("Nivel Modificado Correctamente");
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
        if (mesa == null) {
            lblMensaje.setText("Debes de seleccionar una Mesa");
        } else {
            boolean elimino;
            elimino = mesadao.eliminar(mesa);
            lblMensaje.setText("Mesa Eliminada Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }
    
}
