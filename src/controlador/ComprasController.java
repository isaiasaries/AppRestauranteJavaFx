package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Compra;
import modelo.CompraDAO;
import modelo.DetalleCompra;
import modelo.Producto;
import modelo.ProductoDAO;
import modelo.Proveedor;
import modelo.ProveedorDAO;
import modelo.Validaciones;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author isaia
 */
public class ComprasController implements Initializable {

    @FXML
    private ComboBox<Proveedor> cboProveedor;
    @FXML
    private SearchableComboBox<Producto> cboProducto;
    @FXML
    private Label lblFolio;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<DetalleCompra> tvCompras;
    @FXML
    private TableColumn<DetalleCompra, Integer> colIdC;
    @FXML
    private TableColumn<DetalleCompra, Integer> colIdM;
    @FXML
    private TableColumn<DetalleCompra, String> colMenu;
    @FXML
    private TableColumn<DetalleCompra, Double> colCantidad;
    @FXML
    private TableColumn<DetalleCompra, Double> colPrecio;
    @FXML
    private TableColumn<DetalleCompra, Double> colImporte;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblImporte;

    private ProveedorDAO proveedordao;
    private ProductoDAO productodao;
    private Compra compra;
    private CompraDAO compradao;
    private ObservableList<Producto> lstProductos;
    private ObservableList<Proveedor> lstProveedor;
    private ObservableList<DetalleCompra> lstDetalleC;
    private int folio; 
    private double totalAPagar = 0d;
    private int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lstProductos = FXCollections.observableArrayList();
        lstProveedor = FXCollections.observableArrayList();
        lstDetalleC = FXCollections.observableArrayList();
        dpFecha.setValue(LocalDate.now());
        nextFolio();
        validarCajas();
        iniciaTableView();
        llenarCombos();
        tvCompras.setItems(lstDetalleC);
    }

    private void iniciaTableView() {
        colIdC.setCellValueFactory(new PropertyValueFactory<>("idCompras"));
        colIdM.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        colMenu.setCellValueFactory(new PropertyValueFactory<>("platillo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
    }

    private void llenarCombos() {
        //Llenado de combos menu y proveedor
        proveedordao = new ProveedorDAO();
        productodao = new ProductoDAO();
        lstProveedor = proveedordao.getAllProveedores();
        lstProductos = productodao.getAllProductos();
        cboProducto.setItems(lstProductos);
        cboProveedor.setItems(lstProveedor);
    }

    private void validarCajas() {
        //Valida solo numeros y punto decimal
        tfCantidad.setTextFormatter(new TextFormatter<>(filter));
        tfPrecio.setTextFormatter(new TextFormatter<>(filter));
    }
    //Validar TextField ingresar solo numeros de coma flotante
    UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
        @Override
        public TextFormatter.Change apply(TextFormatter.Change t) {
            if (t.isReplaced()) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                }
            }
            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        }
    };

    private void nextFolio() {
        //Obtener el id siguiente
        compradao = new CompraDAO();
        folio = compradao.getNextFolio();
        lblFolio.setText(folio + "");
        compra = new Compra();
        compra.setIdCompras(folio);
    }

    private void limpiarCajas() {
        //Borra los datos del servicio actual
        tfCantidad.setText("");
        tfPrecio.setText("");
    }

    private boolean validarDatos() {
        if (tfCantidad.getText().trim().length() > 0) {
            if (Double.parseDouble(tfCantidad.getText().trim()) <= 0) {
                Validaciones.alertError("Validar", "La Cantidad debe ser mayor a Cero");
                return false;
            }
        } else {
            Validaciones.alertError("Validar", "Coloca la cantidad");
            return false;
        }
        if (tfPrecio.getText().trim().length() > 0) {
            if (Double.parseDouble(tfPrecio.getText().trim()) <= 0) {
                Validaciones.alertError("Validar", "El Precio debe ser mayor a Cero");
                return false;
            }
        } else {
            Validaciones.alertError("Validar", "Coloca el Precio");
            return false;
        }
        if (cboProveedor.getValue() == null) {
            Validaciones.alertError("Validar", "Elige Proveedor");
            return false;
        }
        if (cboProducto.getValue() == null) {
            Validaciones.alertError("Validar", "Elige Platillo o Bebida");
            return false;
        }

        return true;
    }

    private boolean validarObjetos() {
        //Si la lista esta vacia, no hay nada que guardar
        if (lstDetalleC.isEmpty()) {
            Validaciones.alertError("Guardar", "Nada que guardar");
            return false;
        }
        return true;
    }

    private void sumarImporte() {
        totalAPagar = 0;
        for (DetalleCompra simporte : lstDetalleC) {
            totalAPagar += simporte.getImporte();
        }
        lblImporte.setText("" + totalAPagar);
    }

    private void clearAll() {
        limpiarCajas();
        nextFolio();
        lblImporte.setText("");
        lstDetalleC.clear();
    }

    private void asignarCompra() {
        compra.setIdCompras(folio);
        compra.setIdProveedor(cboProveedor.getValue().getIdProveedor());
        compra.setImporteTotal(totalAPagar);
        compra.setFechaCompra(dpFecha.getValue());
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        boolean guardo;
        if (validarObjetos()) {
            asignarCompra();
            guardo = compradao.insertarCompra(compra, lstDetalleC);
            if (guardo) {
                Validaciones.alertConfirm("Guardar", "Servicio guardado con Exito!");
                clearAll();
            } else {
                Validaciones.alertError("Error al Guardar", "Asegurate que los datos son correctos");
            }
        }
    }

    @FXML
    private void c_btnAgregar(ActionEvent event) {
        if (validarDatos()) {
            double cantidad = Double.parseDouble(tfCantidad.getText().trim());
            double precio = Double.parseDouble(tfPrecio.getText().trim());
            double importe = Math.round((cantidad * precio) * 100.0) / 100.0;
            lstDetalleC.add(new DetalleCompra(folio, cboProducto.getValue().getIdProd(),
                    cboProducto.getValue().getDescripcionProd(), cantidad, precio, importe));
            sumarImporte();
            limpiarCajas();
        }
    }

    @FXML
    private void onMouseCliked(MouseEvent event) {
        index = tvCompras.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            tfCantidad.setText(colCantidad.getCellData(index).toString());
            tfPrecio.setText(colPrecio.getCellData(index).toString());
            cboProducto.getSelectionModel().select(colIdM.getCellData(index) - 1);
            lstDetalleC.remove(index);
            sumarImporte();
        }
    }
}
