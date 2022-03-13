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
import modelo.OrdenTemporal;
import modelo.OrdenTemporalDAO;
import modelo.Responsable;
import modelo.ResponsableDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class OrdenesResponsablesController implements Initializable {

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
    private ComboBox<Responsable> cboResponsables;

    private ResponsableDAO responsabledao;
    private OrdenTemporal ordenTemp;
    private OrdenTemporalDAO ordentemporaldao;
    private ObservableList<Responsable> lstResponsables;
    private ObservableList<OrdenTemporal> lstOrdenes;
    // private LocalDateTime currentTime;
 /*   Runnable runnable;
    // Creamos un hilo y le pasamos el runnable
    Thread hilo = new Thread(runnable);
    boolean suspender; //Suspende un hilo cuando es true
    boolean pausar;    //Detiene un hilo cuando es true

    public OrdenesResponsablesController() {
        this.runnable = new Runnable() {
            @Override
            public void run() {
                // Esto se ejecuta en segundo plano una única vez
                while (true) {
                    // Pero usamos un truco y hacemos un ciclo infinito
                    try {
                        // En él, hacemos que el hilo duerma
                        Thread.sleep(60000);
                        // Y después realizamos las operaciones
                        actualizarTV();
                        System.out.println("Me imprimo cada minuto");
                        // Así, se da la impresión de que se ejecuta cada cierto tiempo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
 //Pausar el hilo
    synchronized void pausarhilo(){
        pausar=true;
        //lo siguiente garantiza que un hilo suspendido puede detenerse.
        suspender=false;
        notify();
    }

    //Suspender un hilo
    synchronized void suspenderhilo(){
        suspender=true;
    }
    */

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstResponsables = FXCollections.observableArrayList();
        lstOrdenes = FXCollections.observableArrayList();
        ordenTemp = new OrdenTemporal();
        ordentemporaldao = new OrdenTemporalDAO();
        llenarCombos();
        validaTV();
        //suspender=false;
       // pausar=false;
      //  hilo.start();
    }

    private void validaTV() {
        //Metodo que agrega un evento doble click para llamar al metodo
        //y hacer cambios de estado. Listo y Entregado
        tvOrdenes.setRowFactory(tv -> {
            TableRow<OrdenTemporal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ordenTemp = row.getItem();
                    //llamaPantalla(ordenTemp);
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

        colCliente.setStyle("-fx-alignment: CENTER;");
        colCantidad.setStyle("-fx-alignment: CENTER;");
        colPlatillo.setStyle("fx-font-size: 14px;");
        colObservaciones.setStyle("-fx-wrap-text: true;-fx-font-size: 14px;");
        //colImporte.setStyle("-fx-alignment: CENTER-RIGHT;");
        //boton.setStyle("-fx-background-color: #ff5722;-fx-font-size: 14px; ");
        //colObservaciones.setStyle("-fx-wrap-text: true;-fx-cell-size: 160px ;-fx-pref-width: 140px;");
    }

    private void llenarCombos() {
        responsabledao = new ResponsableDAO();
        lstResponsables = responsabledao.getAllResponsables();
        cboResponsables.setItems(lstResponsables);
    }

    @FXML
    private void tvOnMouseClicked(MouseEvent event) {
    }

    @FXML
    private void c_cboResponsables(ActionEvent event) {
        //Al elegir un responsable, hace busqueda y muestra los datos
        llenaTV();
    }

    private void actualizarTV() {
        int oldSize = lstResponsables.size();
        llenaTV();
        int newSize = lstResponsables.size();
        if (newSize > oldSize) {
            //mensaje con sonido para informar de una nueva actualizacion
            Validaciones.alertInfo("Nueva Orden", "Ha llegado una nueva orden de servicio");
        }
    }

    private void actualizaEstado(OrdenTemporal ordenTemp) {
        if ("Mesero".equals(cboResponsables.getValue().getNombreResponsable())) {
            ordenTemp.setEntregado("Entregado");
        } else {
            ordenTemp.setEntregado("Listo");
        }
        if (!ordentemporaldao.upEntregado(ordenTemp)) {
            Validaciones.alertError("Modificacion del Estado de la Orden", "Error al Modificar");
        }
        llenaTV();
    }

    private void llenaTV() {
        if (cboResponsables.getValue() != null) {
            int claveResp = cboResponsables.getValue().getIdResponsable();
            lstOrdenes.clear();
            lstOrdenes = ordentemporaldao.getOrdenTemporalxResp(claveResp);
            tvOrdenes.setItems(lstOrdenes);
        }
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
    

}
