package controlador;

import conectar.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.WindowConstants;
import modelo.DetalleVentas;
import modelo.Validaciones;
import modelo.Ventas;
import modelo.VentasDAO;
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
public class DetalleOrdenController implements Initializable {

    @FXML
    private Label lblFolio;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblMesa;
    @FXML
    private Label lblImporte;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblNoCliente;
    @FXML
    private TableView<DetalleVentas> tvDetallesVenta;
    @FXML
    private TableColumn<DetalleVentas, Integer> colFolio;
    @FXML
    private TableColumn<DetalleVentas, Integer> colCliente;
    @FXML
    private TableColumn<DetalleVentas, Double> colCantidad;
    @FXML
    private TableColumn<DetalleVentas, Integer> colIdMenu;
    @FXML
    private TableColumn<DetalleVentas, String> colPlatillo;
    @FXML
    private TableColumn<DetalleVentas, Double> colPrecio;
    @FXML
    private TableColumn<DetalleVentas, Double> colImporte;
    @FXML
    private TableColumn<DetalleVentas, Integer> colTurno;
    @FXML
    private TableColumn<DetalleVentas, String> colObservaciones;
    
    private Ventas venta;
    private ObservableList<DetalleVentas> lstDetalleVentas;
    private VentasDAO ventasdao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        venta = new Ventas();
        ventasdao = new VentasDAO();
        lstDetalleVentas = FXCollections.observableArrayList();
        iniTvDetallesVenta();
    }

    private void iniTvDetallesVenta() {
        colFolio.setCellValueFactory(new PropertyValueFactory<>("idOrdenes"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("noClientes"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colIdMenu.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        colPlatillo.setCellValueFactory(new PropertyValueFactory<>("descripcionMenu"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioMenu"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("idTurno"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
    }

    public void setDatos(Ventas venta) {
        if (venta != null) {
            this.venta = venta;
            lblFolio.setText(venta.getIdOrdenes() + "");
            lblCliente.setText(venta.getRzCliente());
            lblMesa.setText(venta.getIdMesa() + "");
            lblImporte.setText("$" + venta.getImporte() + "");
            lblFecha.setText(venta.getFecha() + "");
            lstDetalleVentas = ventasdao.getDetalleVenta(venta.getIdOrdenes());
            tvDetallesVenta.setItems(lstDetalleVentas);
        }
    }

   /* private void llenaTv() {
        // folio= Integer.parseInt(lblFolio.getText().trim());
        // System.out.println("cuanto vale el folio "+ folio);
         if (venta != null) {
            lstDetalleVentas = ventasdao.getDetalleVenta(folio);
            tvDetallesVenta.setItems(lstDetalleVentas);
        }

    }*/

    @FXML
    void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) tvDetallesVenta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void c_btnImprimir(ActionEvent event) {
        int folio = Integer.parseInt(lblFolio.getText().trim());
       // System.out.println("Ventana DetalleOrdenController  valor del folio =" + folio);
        Connection cnn = Conexion.getConexion();
        try {
            Map parametro = new HashMap();
            parametro.put("Folio", folio);
            final JasperReport jasperReport = JasperCompileManager.compileReport(DetalleOrdenController.class.getResourceAsStream("/recursos/Reportes/TicketVenta_Fin.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el ticket de venta" + e.getMessage());
        }
    }
}
