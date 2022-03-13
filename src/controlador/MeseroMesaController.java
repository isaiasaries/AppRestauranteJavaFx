package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
public class MeseroMesaController implements Initializable {

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
    private ComboBox<Mesa> cboMesas;

    private MesaDAO mesadao;
    private OrdenTemporal ordenTemp;
    private OrdenTemporalDAO ordentemporaldao;
    private ObservableList<Mesa> lstMesa;
    private ObservableList<OrdenTemporal> lstOrdenes;
    // private LocalDateTime currentTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstMesa = FXCollections.observableArrayList();
        lstOrdenes = FXCollections.observableArrayList();
        ordenTemp = new OrdenTemporal();
        ordentemporaldao = new OrdenTemporalDAO();
        llenarCombos();
        validaTV();
        // colObservaciones.setStyle("-fx-wrap-text: true;-fx-cell-size: 160px ;-fx-pref-width: 140px;");
    }

    private void validaTV() {
        //Metodo que agrega un evento doble click para llamar a la pantalla
        //y hacer cambios de estado. Listo y Entregado
        tvOrdenes.setRowFactory(tv -> {
            TableRow<OrdenTemporal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ordenTemp = row.getItem();
                   // llamaPantalla(ordenTemp);
                   actualizaEstado(ordenTemp);
                }
            });
            return row;
        });
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

    }

    private void llenarCombos() {
        mesadao = new MesaDAO();
        lstMesa = mesadao.getMesasOcupadas();
        cboMesas.setItems(lstMesa);
    }

    @FXML
    private void tvOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void c_cboMesas(ActionEvent event) {
        llenaTV();
    }

    private void llenaTV() {
        lstOrdenes.clear();
        if (cboMesas.getValue() != null) {
            int claveMesa = cboMesas.getValue().getIdMesa();
            lstOrdenes = ordentemporaldao.getOrdenTemporalxMesa(claveMesa);
            tvOrdenes.setItems(lstOrdenes);
        }
    }

    private void actualizaEstado(OrdenTemporal ordenTemp) {
        ordenTemp.setEntregado("Entregado");
        if (!ordentemporaldao.upEntregado(ordenTemp)) {
            Validaciones.alertError("Modificacion del Estado de la Orden", "Error al Modificar");
        }
        llenaTV();
    }

    /* private void llamaPantalla(OrdenTemporal ordenTemp) {
        //metodo que invoca a una pantalla adicional para indicar los cambios
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ModificarEstadoVista.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Modificación del Estado de la Orden");
            stage.setScene(escena);
            if (ordenTemp == null) {
                Validaciones.alertError("Error", "Elige una fila con datos");
            }
            ModificarEstadoController estadoDeOrden = (ModificarEstadoController) loader.getController();
            estadoDeOrden.setDatos(ordenTemp);
            stage.showAndWait();
            llenaTV();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
    @FXML
    private void saleMouse(MouseEvent event) {
    }

    @FXML
    private void entraMouse(MouseEvent event) {
    }

}
