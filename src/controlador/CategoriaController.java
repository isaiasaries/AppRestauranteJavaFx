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
import modelo.Categoria;
import modelo.CategoriaDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class CategoriaController implements Initializable {

    @FXML
    private TextField tfCategoria;
    @FXML
    private TableView<Categoria> tvCategoria;
    @FXML
    private TableColumn<Categoria, Byte> colClave;
    @FXML
    private TableColumn<Categoria, String> colCategoria;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnNuevo;
    @FXML
    private TextField tfIdCategoria;

    private ObservableList<Categoria> lstCategorias;
    private int index = -1;
    private Categoria categoria;
    private CategoriaDAO categoriadao;
    private byte clave;
    private String descripcion;
    @FXML
    private Label lblMensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfIdCategoria.setDisable(true);
        colClave.setCellValueFactory(new PropertyValueFactory<>("idCat"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCat"));

        categoriadao = new CategoriaDAO();
        llenarTabla();
    }

    private void llenarTabla() {
        lstCategorias = categoriadao.getAllCategorias();
        tvCategoria.setItems(lstCategorias);
    }

    private void asignarValores() {
        //boolean resultado = false;
        if (!tfIdCategoria.getText().isEmpty()) {
            clave = Byte.parseByte(tfIdCategoria.getText().trim());
        }
        descripcion = tfCategoria.getText();
    }

    private boolean validarDatos() {
        asignarValores();
        if (descripcion.trim().isEmpty()) {
            lblMensaje.setText("Coloca la Descripción");
            return false;
        } else {
            categoria = new Categoria();
            categoria.setIdCat(clave);
            categoria.setDescripcionCat(descripcion);
            return true;
        }
    }

    private void limpiarCajas() {
        tfIdCategoria.clear();
        tfCategoria.clear();
        categoria=null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean guardo;
        if (tfIdCategoria.getText().trim().isEmpty()) {
            if (validarDatos()) {
                //  ServiciosDAO serviciosdao = new ServiciosDAO();
                guardo = categoriadao.insertar(categoria);
                lblMensaje.setText("Nuevo Categoria guardada con Exito!");
                if (guardo) {
                    limpiarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Guardar Categoria");
                }
            }
        } else {
            lblMensaje.setText("La Categoria ya existe");
        }
    }

    @FXML
    private void clickNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void clickModificar(ActionEvent event) {
        if (categoria == null) {
            lblMensaje.setText("Debes de seleccionar a una Categoria");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = categoriadao.actualizar(categoria);
                lblMensaje.setText("Categoria Modificada Correctamente");
                if (modifico) {
                    limpiarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Intentar Modificar, asegurate que los datos son correctos");
                }
            }
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        if (categoria == null) {
            lblMensaje.setText("Debes de seleccionar a una Categoria");
        } else {
            boolean elimino;
            elimino = categoriadao.eliminar(categoria);
            lblMensaje.setText("Categoria Eliminada Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTabla();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar!");
            }
        }
    }

    @FXML
    private void onMouseOut(MouseEvent event) {
        lblMensaje.setText("");
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
         index = tvCategoria.getSelectionModel().getSelectedIndex();
        categoria = null;
        categoria = this.tvCategoria.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfIdCategoria.setText(colClave.getCellData(index).toString());
            tfCategoria.setText(colCategoria.getCellData(index));
            btnGuardar.setVisible(false);
            btnNuevo.setVisible(true);
        }
    }
}
