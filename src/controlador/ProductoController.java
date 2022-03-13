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
import modelo.Producto;
import modelo.ProductoDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ProductoController implements Initializable {

    @FXML
    private TableView<Producto> tvProductos;
    @FXML
    private TableColumn<Producto, Integer> colClave;
    @FXML
    private TableColumn<Producto, String> colProducto;
    @FXML
    private TableColumn<Producto, Integer> colExistencia;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TextField tfProducto;
    @FXML
    private TextField tfStockMin;
    @FXML
    private TextField tfClave;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;

    private Producto producto;
    private ProductoDAO productodao;
    private ObservableList<Producto> lstProductos;
    private int index = -1;
    private int clave;
    private String descripcion;
   // private int existencia;
   // private double precio;
    private int stock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarTV();
    }

    private void inicializarTV() {
        colClave.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("descripcionProd"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("existenciaProd"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProd"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockMin"));
        llenarTV();
    }

    private void llenarTV() {
        productodao = new ProductoDAO();
        lstProductos = productodao.getAllProductos();
        tvProductos.setItems(lstProductos);
    }

    private void asignarValores() {
        if (!tfClave.getText().isEmpty()) {
            clave = Integer.parseInt(tfClave.getText().trim());
        }
        if (!tfProducto.getText().isEmpty()) {
            descripcion = tfProducto.getText();
        }
       /* if (!tfExistencia.getText().isEmpty()) {
            existencia = Integer.parseInt(tfExistencia.getText().trim());
        }
        if (!tfPrecio.getText().isEmpty()) {
            precio = Double.parseDouble(tfPrecio.getText().trim());
        }*/
        if (!tfStockMin.getText().isEmpty()) {
            stock = Integer.parseInt(tfStockMin.getText().trim());
        }
    }

    private boolean validarDatos() {
        asignarValores();
        if (descripcion.trim().isEmpty()) {
            lblMensaje.setText("Coloca el Producto");
            return false;
        } /*else if (existencia < 0) {
            lblMensaje.setText("Coloca el Existencia");
            return false;
        } else if (precio < 0) {
            lblMensaje.setText("Coloca el Precio");
            return false;
        }*/ else if (stock < 0) {
            lblMensaje.setText("Coloca el Stock Minimo");
            return false;
        } else {
            producto = new Producto();
            producto.setIdProd(clave);
            producto.setDescripcionProd(descripcion);
            producto.setExistenciaProd(0);
            producto.setPrecioProd(0);
            producto.setStockMin(stock);
        }
        return true;
    }

    private void limpiarCajas() {
        tfClave.clear();
        tfProducto.clear();
      /*  tfExistencia.setText("0");
        tfPrecio.setText("0.0");*/
        tfStockMin.setText("0");
        producto = null;
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void onMouseCliked(MouseEvent event) {
        index = tvProductos.getSelectionModel().getSelectedIndex();
        producto = null;
        producto = this.tvProductos.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfClave.setText(colClave.getCellData(index).toString());
            tfProducto.setText(colProducto.getCellData(index));
           /* tfExistencia.setText(colExistencia.getCellData(index).toString());
            tfPrecio.setText(colPrecio.getCellData(index).toString());*/
            tfStockMin.setText(colStock.getCellData(index).toString());
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
                guardo = productodao.insertar(producto);
                lblMensaje.setText("¡Nuevo Producto Guardado!");
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
        if (producto == null) {
            lblMensaje.setText("Selecciona un Producto");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = productodao.actualizar(producto);
                lblMensaje.setText("Producto Modificado Correctamente");
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
        if (producto == null) {
            lblMensaje.setText("Debes de seleccionar un Producto");
        } else {
            boolean elimino;
            elimino = productodao.eliminar(producto);
            lblMensaje.setText("Producto Eliminado Correctamente");
            if (elimino) {
                limpiarCajas();
                llenarTV();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar");
            }
        }
    }
}
