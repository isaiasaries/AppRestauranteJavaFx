package controlador;

import conectar.Conexion;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Compra;
import modelo.CompraDAO;
import modelo.DetalleCompra;
import modelo.Producto;
import modelo.ProductoDAO;
import modelo.Proveedor;
import modelo.ProveedorDAO;
import modelo.Validaciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.SearchableComboBox;
import utilerias.ReportFactory;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ComprasFullController implements Initializable {

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
    @FXML
    private TableView<Compra> tvCompra;
    @FXML
    private TableColumn<Compra, Integer> colFolio;
    @FXML
    private TableColumn<Compra, Integer> colIdProveedor;
    @FXML
    private TableColumn<Compra, String> colProveedor;
    @FXML
    private TableColumn<Compra, Double> colImporte1;
    @FXML
    private TableColumn<Compra, LocalDate> colFecha;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnImprimir;
    @FXML
    private DatePicker dpFechaIni;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Button btnCerrar;
    @FXML
    private DatePicker dpFechaIni1;
    @FXML
    private DatePicker dpFechaFin1;
    @FXML
    private ToggleGroup reportesCompras;
    @FXML
    private SearchableComboBox<Producto> cboProducto1;
    @FXML
    private Button btnCerrar2;
    @FXML
    private CheckBox chkRango;
    @FXML
    private Pane panRango;
    @FXML
    private Pane panProd;
    @FXML
    private Button btnCerrar1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private RadioButton rb4;
    @FXML
    private RadioButton rb5;
    @FXML
    private RadioButton rb6;
    @FXML
    private RadioButton rb1;

    private ProveedorDAO proveedordao;
    private ProductoDAO productodao;
    private Compra compra;
    private CompraDAO compradao;
    private ObservableList<Producto> lstProductos;
    private ObservableList<Proveedor> lstProveedor;
    private ObservableList<DetalleCompra> lstDetalleC;
    private ObservableList<Compra> lstCompra;
    private int folio;
    private double totalAPagar = 0d;
    private int index = -1;
    private ReportFactory reportes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstProductos = FXCollections.observableArrayList();
        lstProveedor = FXCollections.observableArrayList();
        lstDetalleC = FXCollections.observableArrayList();

        //   compraDao = new CompraDAO();
        lstCompra = FXCollections.observableArrayList();
        validarTvCompra();

        reportes = new ReportFactory();
        dpFechaIni.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
        dpFechaIni1.setValue(LocalDate.now());
        dpFechaFin1.setValue(LocalDate.now());
        dpFecha.setValue(LocalDate.now());
        nextFolio();
        validarCajas();
        iniciaTableView();
        llenarCombos();
        tvCompras.setItems(lstDetalleC);
        panProd.setVisible(false);
        panRango.setVisible(false);

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
        cboProducto1.setItems(lstProductos);
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

    private void validarTvCompra() {
        tvCompra.setRowFactory(tv -> {
            TableRow<Compra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    compra = row.getItem();
                    llamaPantalla(compra);
                }
            });
            return row;
        });
        colFolio.setCellValueFactory(new PropertyValueFactory<>("idCompras"));
        colIdProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        colImporte1.setCellValueFactory(new PropertyValueFactory<>("importeTotal"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
    }

    private void llamaPantalla(Compra compraInd) {
        //metodo que invoca a una pantalla adicional para indicar los cambios
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/DetalleCompraVista.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Detalles de la Orden de Compra");
            stage.setScene(escena);
            if (compraInd == null) {
                Validaciones.alertError("Error", "Elige una fila con datos");
            }
            DetalleCompraController detalleControler = (DetalleCompraController) loader.getController();
            detalleControler.setDatos(compraInd);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void c_btnImprimir(ActionEvent event) {
        Connection cnn = Conexion.getConexion();
        String ruta = "/recursos/Reportes/rptComprasXRango.jasper";
        Map parametro = new HashMap();
        parametro.put("FechaIni", dpFechaIni1.getValue());
        parametro.put("FechaFin", dpFechaFin1.getValue());
        reportes.crearReporteConParametros(cnn, ruta, parametro);
    }

    @FXML
    private void c_btnMostrar(ActionEvent event) {
        if (dpFechaIni.getValue() == null && dpFechaFin.getValue() == null) {
            Validaciones.alertError("Error en Fechas", "Coloca una fecha de busqueda");
        } else {
            lstCompra = compradao.getComprasXFecha(dpFechaIni.getValue(), dpFechaFin.getValue());
            tvCompra.setItems(lstCompra);
            sumarImporte();
        }
    }

    @FXML
    private void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void c_chkRango(ActionEvent event) {
        if (chkRango.isSelected()) {
            panRango.setVisible(true);
        } else {
            panRango.setVisible(false);
        }
    }

    private void c_rbxProd(ActionEvent event) {
        panProd.setVisible(true);
    }

    @FXML
    private void c_rb2(ActionEvent event) {
        chkRango.setVisible(false);
        panRango.setVisible(false);
        chkRango.setSelected(false);
        panProd.setVisible(false);
    }

    @FXML
    private void c_rb3(ActionEvent event) {
        panProd.setVisible(false);
    }

    @FXML
    private void c_rb4(ActionEvent event) {
        panProd.setVisible(false);
    }

    @FXML
    private void c_rb5(ActionEvent event) {
        panProd.setVisible(false);
    }

    @FXML
    private void c_rb6(ActionEvent event) {
        panProd.setVisible(true);
    }

    @FXML
    private void c_rb1(ActionEvent event) {
        panProd.setVisible(false);
        if (!chkRango.isVisible()) {
            chkRango.setVisible(true);
        }
        chkRango.setSelected(true);
    }

    @FXML
    private void c_showReport(ActionEvent event) {
        if (rb1.isSelected()) {
            chkRango.setSelected(true);
            if (dpFechaIni1.getValue() != null && dpFechaFin1.getValue() != null) {
                Connection cnn = Conexion.getConexion();
                String ruta = "/recursos/Reportes/CompraDeProdXRango.jasper";
                Map parametro = new HashMap();
                parametro.put("FechaIni", dpFechaIni1.getValue());
                parametro.put("FechaFin", dpFechaFin1.getValue());
                reportes.crearReporteConParametros(cnn, ruta, parametro);
            }
        }

        //rptComprasXProveedor
        if (rb2.isSelected()) {
            Connection cnn = Conexion.getConexion();
            String ruta = "/recursos/Reportes/rptComprasXProveedor.jasper";
            Map parametro = new HashMap();
            parametro.put("FechaIni", dpFechaIni1.getValue());
            parametro.put("FechaFin", dpFechaFin1.getValue());
            reportes.crearReporteConParametros(cnn, ruta, parametro);
        }
    }

}
