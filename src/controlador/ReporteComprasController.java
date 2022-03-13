package controlador;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.WindowConstants;
import conectar.Conexion;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Validaciones;
import modelo.Compra;
import modelo.CompraDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class ReporteComprasController implements Initializable {

    @FXML
    private TableView<Compra> tvCompra;
    @FXML
    private TableColumn<Compra, Integer> colFolio;
    @FXML
    private TableColumn<Compra, Integer> colIdProveedor;
    @FXML
    private TableColumn<Compra, String> colProveedor;
    @FXML
    private TableColumn<Compra, Double> colImporte;
    @FXML
    private TableColumn<Compra, LocalDate> colFecha;
    @FXML
    private Label lblTotal;
    @FXML
    private DatePicker dpFechaIni;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnCerrar;

    private Compra compra;
    private CompraDAO compraDao;
    private ObservableList<Compra> lstCompra;
    private double totalAPagar;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compraDao = new CompraDAO();
        lstCompra = FXCollections.observableArrayList();
        validarTvCompra();

        dpFechaIni.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
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
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importeTotal"));
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

    private void sumarImporte() {
        totalAPagar = 0;
        for (Compra compra : lstCompra) {
            totalAPagar += compra.getImporteTotal();
        }
        lblTotal.setText("$ " + totalAPagar);
    }

    @FXML
    private void c_btnMostrar(ActionEvent event) {
        if (dpFechaIni.getValue() == null && dpFechaFin.getValue() == null) {
            Validaciones.alertError("Error en Fechas", "Coloca una fecha de busqueda");
        } else {
            lstCompra = compraDao.getComprasXFecha(dpFechaIni.getValue(), dpFechaFin.getValue());
            tvCompra.setItems(lstCompra);
            sumarImporte();
        }
    }

    @FXML
    private void c_btnImprimir(ActionEvent event) {
        Connection cnn = Conexion.getConexion();
        try {
            Map parametro = new HashMap();
            parametro.put("FechaIni", dpFechaIni.getValue());
            parametro.put("FechaFin", dpFechaFin.getValue());
            final JasperReport jasperReport = JasperCompileManager.compileReport(ReporteComprasController.class.getResourceAsStream("/recursos/Reportes/rptComprasXRango.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }

    @FXML
    private void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
