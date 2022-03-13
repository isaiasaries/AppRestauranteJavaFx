package controlador;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Categoria;
import modelo.CategoriaDAO;
import modelo.Menu;
import modelo.MenuDAO;
import modelo.OrdenTemporal;
import modelo.OrdenTemporalDAO;
import modelo.Turno;
import modelo.TurnoDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes
 */
public class OrdenController implements Initializable {

    @FXML
    private ChoiceBox chbPersonas;
    @FXML
    private TextField tfNoPersonas;
    @FXML
    private ComboBox<Categoria> cboCategoria;
    @FXML
    private ComboBox<Menu> cboPlatillo;
    @FXML
    private ComboBox<Turno> cboTurno;
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
    private Pane pnServicios;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfObservaciones;
    @FXML
    private Pane pnTotales;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private Label lblNumeroMesa;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnProcesarOrden;
    private CategoriaDAO categoriadao;
    private MenuDAO menudao;
    private TurnoDAO turnodao;
    private OrdenTemporalDAO ordenTemporalDao;
    private ObservableList<Categoria> lstCategorias;
    private ObservableList<Menu> lstMenus;
    private ObservableList<Turno> lstTurnos;
    private ObservableList<OrdenTemporal> lstOrdenes;

    private double totalAPagar = 0d;
    private LocalDateTime currentTime;
    private int index = -1;
    private int cantidadClientes;

    private int numMesa;
    private boolean modificar;
    private ObservableList listaClientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentTime = LocalDateTime.now();
        lstOrdenes = FXCollections.observableArrayList();
        lstCategorias = FXCollections.observableArrayList();
        lstMenus = FXCollections.observableArrayList();
        listaClientes = FXCollections.observableArrayList();
        tfCantidad.setTextFormatter(new TextFormatter<>(filter));
        menudao = new MenuDAO();
        muestraFecha();
        muestraHora();
        ocultarPaneles();
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
    }

    private void muestraFecha() {
        lblFecha.setText(currentTime.getDayOfMonth() + "/" + currentTime.getMonthValue() + "/" + currentTime.getYear());
    }

    private void muestraHora() {
        lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute());
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

    private void ocultarPaneles() {
        pnServicios.setVisible(false);
        pnTotales.setVisible(false);
        tvOrdenes.setVisible(false);
    }

    private void mostrarPaneles() {
        pnServicios.setVisible(true);
        pnTotales.setVisible(true);
        btnProcesarOrden.setDisable(true);
        tvOrdenes.setVisible(true);
        iniciaTVOrdenes();
        tvOrdenes.setItems(lstOrdenes);
    }

    private void llenarCombos() {
        
        categoriadao = new CategoriaDAO();
        lstCategorias = categoriadao.getAllCategorias();
        cboCategoria.setItems(lstCategorias);
       // cboCategoria.getSelectionModel().selectFirst();
        
        turnodao = new TurnoDAO();
        lstTurnos = turnodao.getAllTurnos();
        cboTurno.setItems(lstTurnos);
        cboTurno.getSelectionModel().selectFirst();
    }

    @FXML
    private void c_btnGenerar(ActionEvent event) {
        if (tfNoPersonas.getText().isEmpty()) {
            Validaciones.alertError("Error", "Ingresa cantidad de clientes en la mesa");
        } else {
            cantidadClientes = Integer.parseInt(tfNoPersonas.getText());
            if (cantidadClientes <= 0) {
                Validaciones.alertError("Error", "Numero de clientes no valido");
            } else {
                generarClientes(cantidadClientes);
                llenarCombos();
                mostrarPaneles();
            }
        }
    }

    private void generarClientes(int noClientes) {
        listaClientes.clear();
        for (int i = 1; i <= noClientes; i++) {
            listaClientes.add(i);
        }
            chbPersonas.setItems(listaClientes);
            chbPersonas.getSelectionModel().selectFirst();  
    }

    private boolean validar() {
        if (chbPersonas.getValue() == null) {
            Validaciones.alertError("Error", "Elige un Cliente");
            return false;
        }
        if (cboCategoria.getValue() == null) {
            Validaciones.alertError("Error", "Elige una Categoria");
            return false;
        }
        if (cboPlatillo.getValue() == null) {
            Validaciones.alertError("Error", "Elige un Platillo o Bebida");
            return false;
        }
        if (tfCantidad.getText().isEmpty()) {
            Validaciones.alertError("Error", "Coloca la Cantidad");
            return false;
        }
        if (cboTurno.getValue() == null) {
            Validaciones.alertError("Error", "Elige un Turno de Entrega");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        tfCantidad.setText("");
        tfObservaciones.setText("");
    }

    private void sumarImporte() {
        totalAPagar = 0;
        for (OrdenTemporal importe : lstOrdenes) {
            totalAPagar += importe.getImporte();
        }
        lblTotal.setText("$ " + totalAPagar);
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        if (validar()) {
            double cantidad = Double.parseDouble(tfCantidad.getText().trim());
            double importe = Math.round((cantidad * cboPlatillo.getValue().getPrecioMenu()) * 100.0) / 100.0;
            String observaciones = tfObservaciones.getText().trim();
            lstOrdenes.add(new OrdenTemporal(numMesa, cantidadClientes, Integer.parseInt(chbPersonas.getValue().toString()),
                    cboPlatillo.getValue().getIdMenu(), cboPlatillo.getValue().getDescripcionMenu(),
                    cboPlatillo.getValue().getPrecioMenu(), cantidad, importe, cboTurno.getValue().getIdTurno(),
                    cboTurno.getValue().getTurno(), "No", observaciones, "00:00"));
            sumarImporte();
            limpiarCampos();
            btnProcesarOrden.setDisable(false);
        }
    }

    @FXML
    private void c_cboPlatillo(ActionEvent event) {

    }

    public void setNumMesa(int numMesa, boolean modificar, int cantidad) {
        lblNumeroMesa.setText("Mesa No. " + numMesa);
        this.numMesa = numMesa;
        this.modificar = modificar;
        //Modificar Pedidio?
        if (modificar) {
            mostrarPaneles();
            generarClientes(cantidad);
            llenarCombos();
            ordenTemporalDao = new OrdenTemporalDAO();
            lstOrdenes = ordenTemporalDao.getOrdenTemporalxMesa(numMesa);
            tvOrdenes.setItems(lstOrdenes);
            sumarImporte();
        }
    }

    @FXML
    private void c_cboCategoria(ActionEvent event) {
        //Esta OrdenController no se utiliza. Fue Sustituida
       /* byte idCat = cboCategoria.getValue().getIdCat();
        lstMenus = menudao.getAllMenuXCategoria(idCat);
        cboPlatillo.setItems(lstMenus);*/
    }

    private boolean validarTV() {
        if (tvOrdenes.getItems().isEmpty()) {
            Validaciones.alertError("Error", "No existen datos");
            return false;
        }
        return true;
    }

    @FXML
    private void c_btnProcesarOrden(ActionEvent event) {
        boolean guardo;
        //Si los datos de la tableView estan validados y no se requiere modificacion
        if (validarTV() && !modificar) {
            ordenTemporalDao = new OrdenTemporalDAO();
            guardo = ordenTemporalDao.insertar(lstOrdenes);
            if (guardo) {
                Validaciones.alertConfirm("Guardado", "Orden en Proceso");
                btnProcesarOrden.setDisable(true);
            } else {
                Validaciones.alertError("Guardando", "Error al procesar Orden");
            }
        }else{
            //si requiere modificacion, borrar el servicio anterior y poner el nuevo
            guardo = ordenTemporalDao.insertar2(lstOrdenes, numMesa);
            if (guardo) {
                Validaciones.alertConfirm("Guardar Cambios", "Orden en Proceso");
                btnProcesarOrden.setDisable(true);
            } else {
                Validaciones.alertError("Guardar", "Error al procesar Orden");
            }
        }
        
    }

    @FXML
    private void tvOnMouseClicked(MouseEvent event) {
        index = tvOrdenes.getSelectionModel().getSelectedIndex();
        boolean segura = Validaciones.alertConfirm("Eliminar Platillo o Bebida",
                "Esta segura(o) de eliminar este platillo o bebida?");
        if (segura) {
            if (index >= 0) {
                lstOrdenes.remove(index);
                sumarImporte();
            }
        }
    }
}
