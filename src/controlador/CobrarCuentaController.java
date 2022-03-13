package controlador;

import conectar.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Clientes;
import modelo.ClientesDAO;
import modelo.Orden;
import modelo.OrdenDAO;
import modelo.OrdenDetalle;
import modelo.OrdenTemporal;
import modelo.OrdenTemporalDAO;
import modelo.Personal;
import modelo.PersonalDAO;
import modelo.Validaciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class CobrarCuentaController implements Initializable {

    @FXML
    private ComboBox<Clientes> cboClientes;
    @FXML
    private ComboBox<Personal> cboPersonal;
    @FXML
    private Label lblMesa;
    @FXML
    private TableView<OrdenTemporal> tvOrdenes;
    @FXML
    private TableColumn<OrdenTemporal, Integer> colIdMesa;
    @FXML
    private TableColumn<OrdenTemporal, Integer> colCantClientes;
    @FXML
    private TableColumn<OrdenTemporal, Integer> colCliente;
    @FXML
    private TableColumn<OrdenTemporal, Integer> colClave;
    @FXML
    private TableColumn<OrdenTemporal, String> colPlatillo;
    @FXML
    private TableColumn<OrdenTemporal, Double> colPrecio;
    @FXML
    private TableColumn<OrdenTemporal, Double> colCantidad;
    @FXML
    private TableColumn<OrdenTemporal, Double> colImporte;
    @FXML
    private TableColumn<OrdenTemporal, Integer> colIdTurno;
    @FXML
    private TableColumn<OrdenTemporal, String> colTurno;
    @FXML
    private TableColumn<OrdenTemporal, String> colEntregado;
    @FXML
    private TableColumn<OrdenTemporal, String> colObservaciones;
    @FXML
    private TableColumn<OrdenTemporal, String> colTiempo;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblTipoCuenta;

    private ClientesDAO clientesdao;
    private ObservableList<Clientes> lstClientes;

    private PersonalDAO personaldao;
    private ObservableList<Personal> lstPersonal;

    private OrdenTemporalDAO ordentemporaldao;
    private ObservableList<OrdenTemporal> lstOrdenes;

    private double totalAPagar = 0d;
    private LocalDateTime currentTime;

    private Orden orden;
    private OrdenDetalle ordenDetalle;
    private ObservableList<OrdenDetalle> lstOrdenDetalle;
    private int tipo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstClientes = FXCollections.observableArrayList();
        lstPersonal = FXCollections.observableArrayList();
        ordentemporaldao = new OrdenTemporalDAO();
        llenarCombos();
        iniciaTVOrdenes();
        tipo = 0;
    }

    private void llenarCombos() {
        if (clientesdao == null) {
            clientesdao = new ClientesDAO();
            lstClientes = clientesdao.getAllClientes();
            cboClientes.setItems(lstClientes);
        }
        if (personaldao == null) {
            personaldao = new PersonalDAO();
            lstPersonal = personaldao.getAllPersonal();
            cboPersonal.setItems(lstPersonal);
        }
    }

    private void iniciaTVOrdenes() {
        colIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        colCantClientes.setCellValueFactory(new PropertyValueFactory<>("cantidadClientes"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("noCliente"));
        colClave.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        colPlatillo.setCellValueFactory(new PropertyValueFactory<>("descMenu"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        colIdTurno.setCellValueFactory(new PropertyValueFactory<>("idTurno"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colEntregado.setCellValueFactory(new PropertyValueFactory<>("entregado"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));

        colPrecio.setStyle("-fx-alignment: CENTER-RIGHT;");
        colCantidad.setStyle("-fx-alignment: CENTER-RIGHT;");
        colImporte.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void sumarImporte() {
        totalAPagar = 0;
        for (OrdenTemporal importe : lstOrdenes) {
            totalAPagar += importe.getImporte();
        }
        lblTotal.setText("$ " + totalAPagar);
    }

    public void setDatos(ObservableList<OrdenTemporal> lstOrdenTemp, int tipo) {
        if (lstOrdenTemp.size() > 0) {
            this.lstOrdenes = lstOrdenTemp;
            this.tipo = tipo;
            tvOrdenes.setItems(lstOrdenes);
            sumarImporte();
            if (tipo == 2) {
                lblTipoCuenta.setText("I N D I V I D U A L");
            }
            lblMesa.setText("MESA  " + lstOrdenes.get(0).getIdMesa());
        }else{
             Validaciones.alertError("Guardar", "Cliente no valido");
        }
    }

    //Extraemos valores de la TV general y repartimos en orden y ordendetalle
    private void asignarValores() {
        orden = new Orden();
        lstOrdenDetalle = FXCollections.observableArrayList();
        orden.setIdOrdenes(0);
        orden.setIdMesa(lstOrdenes.get(0).getIdMesa());
        orden.setIdCliente(cboClientes.getValue().getIdClientes());
        orden.setIdPersonal(cboPersonal.getValue().getIdPersonal());
        orden.setCantClientes(lstOrdenes.get(0).getCantidadClientes());
        orden.setImporteTotal(totalAPagar);
        currentTime = LocalDateTime.now();
        orden.setFecha(currentTime);
        for (OrdenTemporal ordenTemp : lstOrdenes) {
            ordenDetalle = new OrdenDetalle();
            ordenDetalle.setIdOrdenes(0);
            ordenDetalle.setNoClientes(ordenTemp.getNoCliente());
            ordenDetalle.setIdMenu(ordenTemp.getIdMenu());
            ordenDetalle.setCantidad(ordenTemp.getCantidad());
            ordenDetalle.setPrecio(ordenTemp.getPrecio());
            ordenDetalle.setImporte(ordenTemp.getImporte());
            ordenDetalle.setIdTurno(ordenTemp.getIdTurno());
            ordenDetalle.setObservaciones(ordenTemp.getObservaciones());
            lstOrdenDetalle.add(ordenDetalle);
        }
    }

    private boolean validarInfo() {
        if (cboClientes.getValue() == null) {
            Validaciones.alertError("Guardar", "Selecciona un Cliente");
            return false;
        }
        if (cboPersonal.getValue() == null) {
            Validaciones.alertError("Guardar", "Elige nombre del Mesero");
            return false;
        }
        return true;
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        boolean guardo;
        if (lstOrdenes.size() > 0) {
            if (validarInfo()) {
                asignarValores();
                OrdenDAO ordendao = new OrdenDAO();
                guardo = ordendao.inOrden(orden, lstOrdenDetalle, tipo);
                if (guardo) {
                   // Validaciones.alertConfirm("Guardar", "Orden Guardada");
                    lstOrdenes.clear();
                    tvOrdenes.setItems(lstOrdenes);
                    mostrarTicket(ordendao.getLastFolio());
                    //llenarCombos();
                } else {
                    Validaciones.alertError("Guardar", "Error al Guardar");
                }
            }
        }
    }

    private void mostrarTicket(int folio) {
        Connection cnn = Conexion.getConexion();
        try {
            Map parametro = new HashMap();
            parametro.put("Folio", folio);
            final JasperReport jasperReport = JasperCompileManager.compileReport(MesasVentaController.class.getResourceAsStream("/recursos/Reportes/TicketVenta_Fin.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el ticket de venta" + e.getMessage());
        }
    }
     private void imprimeDirecto(int folio) {
        Connection cnn = Conexion.getConexion();
        try {
            Map parametro = new HashMap();
            parametro.put("Folio", folio);
            final JasperReport jasperReport = JasperCompileManager.compileReport(MesasVentaController.class.getResourceAsStream("/recursos/Reportes/TicketVenta_Fin.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
            //Aqui esta el cambio para imprimir directo
            JasperPrintManager.printReport(jp, false);
          
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el ticket de venta" + e.getMessage());
        }
    }

    @FXML
    private void c_btnTicketDirecto(ActionEvent event) {
         boolean guardo;
        if (lstOrdenes.size() > 0) {
            if (validarInfo()) {
                asignarValores();
                OrdenDAO ordendao = new OrdenDAO();
                guardo = ordendao.inOrden(orden, lstOrdenDetalle, tipo);
                if (guardo) {
                   // Validaciones.alertConfirm("Guardar", "Orden Guardada");
                    lstOrdenes.clear();
                    tvOrdenes.setItems(lstOrdenes);
                    imprimeDirecto(ordendao.getLastFolio());
                } else {
                    Validaciones.alertError("Guardar", "Error al Guardar");
                }
            }
        }
    }

}
