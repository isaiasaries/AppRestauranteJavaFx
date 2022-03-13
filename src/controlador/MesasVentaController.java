package controlador;

import java.net.URL;
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
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Mesa;
import modelo.MesaDAO;
import modelo.OrdenTemporal;
import modelo.OrdenTemporalDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class MesasVentaController implements Initializable {

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
    private ComboBox<Mesa> cboMesas;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblNoCliente;
    @FXML
    private ToggleGroup ventaGroup;
    @FXML
    private RadioButton rbIndividual;
    @FXML
    private RadioButton rbTotal;
    @FXML
    private TextField tfNoCliente;

    private MesaDAO mesadao;
    private ObservableList<Mesa> lstMesas;
    private OrdenTemporalDAO ordentemporaldao;
    private ObservableList<OrdenTemporal> lstOrdenes;
    private double totalAPagar = 0d;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstMesas = FXCollections.observableArrayList();
        lstOrdenes = FXCollections.observableArrayList();
        ordentemporaldao = new OrdenTemporalDAO();
        mesadao = new MesaDAO();
        llenarCombos();
        iniciaTVOrdenes();
        lblNoCliente.setVisible(false);
        tfNoCliente.setVisible(false);
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

    private void llenarCombos() {
        lstMesas.clear();
        lstMesas = mesadao.getMesasOcupadas();
        cboMesas.setItems(lstMesas);
    }

    private void actualizar() {
        llenarCombos();
        lstOrdenes.clear();
        lblTotal.setText("$ 0.0");
    }

    private void sumarImporte() {
        totalAPagar = 0;
        for (OrdenTemporal importe : lstOrdenes) {
            totalAPagar += importe.getImporte();
        }
        lblTotal.setText("$ " + totalAPagar);
    }

    private boolean numClienteExiste(int numCliente) {
        for (OrdenTemporal nocliente : lstOrdenes) {
            if (nocliente.getNoCliente() == numCliente) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void c_cboMesas(ActionEvent event) {
        if (cboMesas.getValue() != null) {
            int claveMesa = cboMesas.getValue().getIdMesa();
            lstOrdenes = ordentemporaldao.getOrdenTemporalxMesa(claveMesa);
            tvOrdenes.setItems(lstOrdenes);
            sumarImporte();
        }
    }

    @FXML
    void c_rbIndividual(ActionEvent event) {
        if (rbIndividual.isSelected()) {
            lblNoCliente.setVisible(true);
            tfNoCliente.setVisible(true);
        }
    }

    @FXML
    void c_rbTotal(ActionEvent event) {
        if (rbTotal.isSelected()) {
            lblNoCliente.setVisible(false);
            tfNoCliente.setVisible(false);
        }
    }

    private void llamaPantalla(ObservableList<OrdenTemporal> lstTemporal, int tipo) {
        //metodo que invoca a una pantalla adicional para indicar los cambios
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/CobrarCuentaVista.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Generar Ticket de Venta");
            stage.setScene(escena);
            CobrarCuentaController cobrarCuenta = (CobrarCuentaController) loader.getController();
            //Tipo 1 es cuenta total
            //Tipo 2 es cuenta parcial
            cobrarCuenta.setDatos(lstTemporal, tipo);
            stage.showAndWait();
            actualizar();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean validarNoCliente() {
        if (!tfNoCliente.getText().trim().isEmpty()) {
            String regex = "^(\\d+\\,?)+$";
            Pattern pat = Pattern.compile(regex);
            String input = tfNoCliente.getText();
            Matcher mat = pat.matcher(input);
            if (mat.find()) {
                return true;
            }
        }
        return false;
    }

    //Tipo 1 es cuenta total
    //Tipo 2 es cuenta Individual
    @FXML
    void c_btnCuenta(ActionEvent event) {
        if (cboMesas.getValue() != null && lstOrdenes.size() > 0) {
            int claveMesa = cboMesas.getValue().getIdMesa();
            ObservableList<OrdenTemporal> lstOT;
            if (rbIndividual.isSelected()) {
                if (validarNoCliente()) {
                    String numCliente = tfNoCliente.getText();
                    lstOT = ordentemporaldao.getTempxMesaxCliente(claveMesa, numCliente);
                    llamaPantalla(lstOT, 2);
                } else {
                    Validaciones.alertError("Orden de Venta", "Numero de Cliente no valido");
                }
            } else if (rbTotal.isSelected()) {
                lstOT = ordentemporaldao.getOrdenTemporalxMesa(claveMesa);
                llamaPantalla(lstOT, 1);
            }
        } else {
            Validaciones.alertError("Orden de Venta", "Elige una mesa ocupada");
        }
    }

    @FXML
    private void c_btnCancelar(ActionEvent event) {
        boolean borro = false;
        if (cboMesas.getValue() != null && lstOrdenes.size() > 0) {
            int claveMesa = cboMesas.getValue().getIdMesa();
            if(Validaciones.alertConfirm("P R E C A U C I O N", 
                    "Esta segura(o) de Cancelar la orden de la mesa No. " + claveMesa + "\n" 
                    + "Esta accion no se puede revertir")){
                //borrar mesa
                borro = ordentemporaldao.eliminarXMesa(claveMesa);
                if(borro){
                    actualizar();
                }
            }
        }
    }
}
