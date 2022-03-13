package controlador;

import conectar.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.WindowConstants;
import modelo.Producto;
import modelo.Validaciones;
import modelo.Ventas;
import modelo.VentasDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class VentasFullController implements Initializable {

    @FXML
    private CheckBox chkRango;
    @FXML
    private Pane panRango;
    @FXML
    private DatePicker dpFechaIni1;
    @FXML
    private DatePicker dpFechaFin1;
    @FXML
    private ToggleGroup reportesCompras;
    @FXML
    private RadioButton rb1;
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
    private Pane panProd;
    @FXML
    private SearchableComboBox<Producto> cboProducto1;
    @FXML
    private Button btnCerrar1;
    @FXML
    private DatePicker dpFechaIni;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TableView<Ventas> tvVentas;
    @FXML
    private TableColumn<Ventas, Integer> colFolio;
    @FXML
    private TableColumn<Ventas, Integer> colMesa;
    @FXML
    private TableColumn<Ventas, String> colCliente;
    @FXML
    private TableColumn<Ventas, Double> colImporte;
    @FXML
    private TableColumn<Ventas, LocalDate> colFecha;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnReportesVentas;

    private Ventas ventas;
    private VentasDAO ventasDao;
    private ObservableList<Ventas> lstVentas;
    private double totalAPagar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ventasDao = new VentasDAO();
        lstVentas = FXCollections.observableArrayList();
        validarTvVentas();

        dpFechaIni.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
        dpFechaIni1.setValue(LocalDate.now());
        dpFechaFin1.setValue(LocalDate.now());
    }

    private void validarTvVentas() {
        tvVentas.setRowFactory(tv -> {
            TableRow<Ventas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ventas = row.getItem();
                    llamaPantalla(ventas);
                }
            });
            return row;
        });
        colFolio.setCellValueFactory(new PropertyValueFactory<>("idOrdenes"));
        colMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("rzCliente"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    private void inicializarTvVentas() {
        // System.out.println("Entra al doble click");
        tvVentas.setRowFactory(tv -> {
            TableRow<Ventas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ventas = row.getItem();
                    llamaPantalla(ventas);
                }
            });
            return row;
        });
        colFolio.setCellValueFactory(new PropertyValueFactory<>("idOrdenes"));
        colMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("rzCliente"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    private void llamaPantalla(Ventas venta) {
        //metodo que invoca a una pantalla adicional para indicar los cambios
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/DetalleOrdenVista.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Detalles de la Orden de Venta");
            stage.setScene(escena);
            if (venta == null) {
                Validaciones.alertError("Error", "Elige una fila con datos");
            }
            DetalleOrdenController detalleControler = (DetalleOrdenController) loader.getController();
            detalleControler.setDatos(venta);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void sumarImporte() {
        totalAPagar = 0;
        for (Ventas venta : lstVentas) {
            totalAPagar += venta.getImporte();
        }
        lblTotal.setText("$ " + totalAPagar);
    }

    @FXML
    private void c_chkRango(ActionEvent event) {
        if (chkRango.isSelected()) {
            panRango.setVisible(true);
        } else {
            panRango.setVisible(false);
        }
    }

    @FXML
    private void c_rb2(ActionEvent event) {
        panProd.setVisible(false);
        chkRango.setSelected(true);

    }

    @FXML
    private void c_rb3(ActionEvent event) {
        panProd.setVisible(false);
        chkRango.setSelected(true);
    }

    @FXML
    private void c_rb4(ActionEvent event) {
        panProd.setVisible(false);
        chkRango.setSelected(true);
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
    }

    @FXML
    private void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void c_btnMostrar(ActionEvent event) {
        if (dpFechaIni.getValue() == null && dpFechaFin.getValue() == null) {
            Validaciones.alertError("Error en Fechas", "Coloca una fecha de busqueda");
        } else {
            lstVentas = ventasDao.getVentasXFecha(dpFechaIni.getValue(), dpFechaFin.getValue());
            tvVentas.setItems(lstVentas);
            sumarImporte();
        }
    }

    @FXML
    private void c_btnImprimir(ActionEvent event) {
        Connection cnn = Conexion.getConexion();
        try {

            int dia = dpFechaIni.getValue().getDayOfMonth();
            int mes = dpFechaIni.getValue().getMonth().getValue();
            int anno = dpFechaIni.getValue().getYear();
            String fechaini = anno + "/" + mes + "/" + dia;

            //System.out.println(" " + fechaini);
            int dia2 = dpFechaFin.getValue().getDayOfMonth();
            int mes2 = dpFechaFin.getValue().getMonth().getValue();
            int anno2 = dpFechaFin.getValue().getYear();
            String fechafin = anno2 + "/" + mes2 + "/" + dia2;
            Map parametro = new HashMap();
            parametro.put("FechaIni", fechaini);
            parametro.put("FechaFin", fechafin);
            final JasperReport jasperReport = JasperCompileManager.compileReport(VentasFullController.class.getResourceAsStream("/recursos/Reportes/VentaFecha.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }

    @FXML
    private void c_btnReportesVentas(ActionEvent event) {
        Map parametro = new HashMap();
        String fechaini = "";
        String fechafin = "";

        if (dpFechaIni1.getValue() != null && dpFechaFin1.getValue() != null) {
            int dia = dpFechaIni1.getValue().getDayOfMonth();
            int mes = dpFechaIni1.getValue().getMonth().getValue();
            int anno = dpFechaIni1.getValue().getYear();
            fechaini = anno + "/" + mes + "/" + dia;

            //System.out.println(" " + fechaini);
            int dia2 = dpFechaFin1.getValue().getDayOfMonth();
            int mes2 = dpFechaFin1.getValue().getMonth().getValue();
            int anno2 = dpFechaFin1.getValue().getYear();
            fechafin = anno2 + "/" + mes2 + "/" + dia2;

            //Dependiendo del readiobutton elegido, se realiza la eleccion del reporte
            if (rb1.isSelected()) {
                Connection cnn = Conexion.getConexion();
                try {
                    parametro.put("FechaIni", fechaini);
                    parametro.put("FechaFin", fechafin);
                    final JasperReport jasperReport = JasperCompileManager.compileReport(VentasFullController.class.getResourceAsStream("/recursos/Reportes/VentaFecha.jrxml"));
                    JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
                    JasperViewer jviewer = new JasperViewer(jp, false);
                    jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jviewer.setVisible(true);
                } catch (JRException e) {
                    Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
                }
            }
            if (rb2.isSelected() && dpFechaIni1.getValue() != null && dpFechaFin1.getValue() != null) {
                Connection cnn = Conexion.getConexion();
                try {
                    /* Map parametro = new HashMap();
                parametro.put("FechaIni", dpFechaIni1.getValue());
                parametro.put("FechaFin", dpFechaFin1.getValue());*/
                    parametro.put("FechaIni", fechaini);
                    parametro.put("FechaFin", fechafin);
                    final JasperReport jasperReport = JasperCompileManager.compileReport(VentasFullController.class.getResourceAsStream("/recursos/Reportes/rptCantProdXCatRango2.jrxml"));
                    JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
                    JasperViewer jviewer = new JasperViewer(jp, false);
                    jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jviewer.setVisible(true);
                } catch (JRException e) {
                    Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
                }
            }

            if (rb3.isSelected() && dpFechaIni1.getValue() != null && dpFechaFin1.getValue() != null) {
                Connection cnn = Conexion.getConexion();
                try {
                    /*Map parametro = new HashMap();
                parametro.put("FechaIni", dpFechaIni1.getValue());
                parametro.put("FechaFin", dpFechaFin1.getValue());*/
                    parametro.put("FechaIni", fechaini);
                    parametro.put("FechaFin", fechafin);
                    final JasperReport jasperReport = JasperCompileManager.compileReport(VentasFullController.class.getResourceAsStream("/recursos/Reportes/rptVentasxCliente.jrxml"));
                    JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
                    JasperViewer jviewer = new JasperViewer(jp, false);
                    jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jviewer.setVisible(true);
                } catch (JRException e) {
                    Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
                }
            }
            if (rb4.isSelected() && dpFechaIni1.getValue() != null && dpFechaFin1.getValue() != null) {
                Connection cnn = Conexion.getConexion();
                try {
                    /*Map parametro = new HashMap();
                parametro.put("FechaIni", dpFechaIni1.getValue());
                parametro.put("FechaFin", dpFechaFin1.getValue());*/
                    parametro.put("FechaIni", fechaini);
                    parametro.put("FechaFin", fechafin);
                    final JasperReport jasperReport = JasperCompileManager.compileReport(VentasFullController.class.getResourceAsStream("/recursos/Reportes/VentasporPlatilloRango.jrxml"));
                    JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
                    JasperViewer jviewer = new JasperViewer(jp, false);
                    jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jviewer.setVisible(true);
                } catch (JRException e) {
                    Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
                }
            }
        }
    }

}
