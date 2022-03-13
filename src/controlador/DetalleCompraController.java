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
import modelo.DetalleCompra;
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
public class DetalleCompraController implements Initializable {

    @FXML
    private Label lblFolio;
    @FXML
    private Label lblProveedor;
    @FXML
    private Label lblImporte;
    @FXML
    private Label lblFecha;
    @FXML
    private TableView<DetalleCompra> tvDetallesVenta;
    @FXML
    private TableColumn<DetalleCompra, Integer> colFolio;
    @FXML
    private TableColumn<DetalleCompra, Integer> colIdProd;
    @FXML
    private TableColumn<DetalleCompra, String> colProducto;
    @FXML
    private TableColumn<DetalleCompra, Double> colCantidad;
    @FXML
    private TableColumn<DetalleCompra, Double> colPrecio;
    @FXML
    private TableColumn<DetalleCompra, Double> colImporte;

    private Compra compra;
    private ObservableList<DetalleCompra> lstDetalleCompra;
    private CompraDAO comprasdao;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compra = new Compra();
        comprasdao = new CompraDAO();
        lstDetalleCompra = FXCollections.observableArrayList();
        iniTvDetallesVenta();
    }  
   
    private void iniTvDetallesVenta() {
        colFolio.setCellValueFactory(new PropertyValueFactory<>("idCompras"));
        colIdProd.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
    }

    public void setDatos(Compra compra) {
        if (compra != null) {
            this.compra = compra;
            lblFolio.setText(compra.getIdCompras() + "");
            lblProveedor.setText(compra.getNombreProveedor());
            lblImporte.setText("$" + compra.getImporteTotal() + "");
            lblFecha.setText(compra.getFechaCompra() + "");
            lstDetalleCompra = comprasdao.getDetalleCompra(compra.getIdCompras());
            tvDetallesVenta.setItems(lstDetalleCompra);
        }
    }

    @FXML
    void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) tvDetallesVenta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void c_btnImprimir(ActionEvent event) {
       /* int folio = Integer.parseInt(lblFolio.getText().trim());
        //System.out.println("valor del foli" + folio);
        Connection cnn = Conexion.getConexion();
        try {
            Map parametro = new HashMap();
            parametro.put("Folio", folio);
            final JasperReport jasperReport = JasperCompileManager.compileReport(DetalleCompraController.class.getResourceAsStream("/recursos/Reportes/TicketVenta_3.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el ticket de compra" + e.getMessage());
        }*/
    }
}
