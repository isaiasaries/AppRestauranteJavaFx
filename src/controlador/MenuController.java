package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Categoria;
import modelo.CategoriaDAO;
import modelo.Menu;
import modelo.MenuDAO;
import modelo.Responsable;
import modelo.ResponsableDAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Perez
 */
public class MenuController implements Initializable {

    @FXML
    private TextField tfIdMenu;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfPrecio;
    @FXML
    private ComboBox<Categoria> cmbCategoria;
    @FXML
    private ComboBox<Responsable> cboResponsable;
    @FXML
    private TableView<Menu> tvMenu;
    @FXML
    private TableColumn<Menu, Integer> colClave;
    @FXML
    private TableColumn<Menu, String> colDescripcion;
    @FXML
    private TableColumn<Menu, Byte> colIdCategoria;
    @FXML
    private TableColumn<Menu, String> colCategoria;
    @FXML
    private TableColumn<Menu, Double> colPrecio;
    @FXML
    private TableColumn<Menu, Integer> colIdResponsable;
    @FXML
    private TableColumn<Menu, String> colResponsable;
    @FXML
    private TableColumn<Menu, CheckBox> colVigente;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblMensaje;
    @FXML
    private CheckBox chkVigente;
    
    private ObservableList<Menu> lstmenu;
    private int index = -1;
    private Menu menu;
    private MenuDAO menudao;

    private int clave;
    private String descripcion;
    private double costo;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfIdMenu.setDisable(true);
        menudao = new MenuDAO();
        inicializarTV();
        llenarCboCategoria();
        llenarCboResponsable();
        //Validando solo numeros con punto decimal
        tfPrecio.setTextFormatter(new TextFormatter<>(filter));
        llenarTabla();
    }

    private void inicializarTV() {
        colClave.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionMenu"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCat"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCat"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioMenu"));
        colIdResponsable.setCellValueFactory(new PropertyValueFactory<>("idResponsable"));
        colResponsable.setCellValueFactory(new PropertyValueFactory<>("nombreResponsable"));
        colVigente.setCellValueFactory(new PropertyValueFactory<>("vigenteMenu"));
    }

    private void llenarCboCategoria() {
        //Para llenar el combo de Categorias
        CategoriaDAO categoriadao = new CategoriaDAO();
        ObservableList<Categoria> lstCategorias;
        lstCategorias = categoriadao.getAllCategorias();
        cmbCategoria.setItems(lstCategorias);
    }

    private void llenarCboResponsable() {
        //Para llenar el combo de Categorias
        ResponsableDAO responsabledao = new ResponsableDAO();
        ObservableList<Responsable> lstResponsables;
        lstResponsables = responsabledao.getAllResponsables();
        cboResponsable.setItems(lstResponsables);
    }

    //Validador de numeros de punto flotante
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

    private void llenarTabla() {
        lstmenu = menudao.getAllMenu();
        tvMenu.setItems(lstmenu);
    }

    private boolean asignarValores() {
        boolean resultado = false;
        if (!tfIdMenu.getText().isEmpty()) {
            clave = Integer.parseInt(tfIdMenu.getText().trim());
            resultado = true;
        }
        descripcion = tfDescripcion.getText();
        if (!tfPrecio.getText().isEmpty()) {
            costo = Double.parseDouble(tfPrecio.getText().trim());
            resultado =  true;
        } else {
            lblMensaje.setText("Ingresa el costo del servicio");
            resultado =  false;
        }
         return resultado;
    }

    private boolean validarDatos() {
        if (asignarValores()) {
            if (descripcion.trim().isEmpty()) {
                lblMensaje.setText("Coloca la Descripción del Menu");
                return false;
            } else if (cmbCategoria.getValue() == null) {
                lblMensaje.setText("Debes Seleccionar una Categoria");
                return false;
            } else if (cboResponsable.getValue() == null) {
                lblMensaje.setText("Debes Seleccionar un Responsable");
                return false;
            } else {
                menu = new Menu();
                menu.setIdMenu(clave);
                menu.setDescripcionMenu(descripcion);
                menu.setIdCat(cmbCategoria.getValue().getIdCat());
                menu.setDescripcionCat(cmbCategoria.getValue().getDescripcionCat());
                menu.setPrecioMenu(costo);
                menu.setIdResponsable(cboResponsable.getValue().getIdResponsable());
                menu.setVigenteMenu(chkVigente);
                return true;
            }
        } else {
            return false;
        }
    }

    private void limpiarCajas() {
        menu = null;
        tfIdMenu.clear();
        tfDescripcion.clear();
        cmbCategoria.setValue(null);
        tfPrecio.clear();
        cboResponsable.setValue(null);
        btnGuardar.setVisible(true);
        btnNuevo.setVisible(false);
        chkVigente.setSelected(false);
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        index = tvMenu.getSelectionModel().getSelectedIndex();
        menu = null;
        menu = this.tvMenu.getSelectionModel().getSelectedItem();
        if (index >= 0) {
            tfIdMenu.setText(colClave.getCellData(index).toString());
            tfDescripcion.setText(colDescripcion.getCellData(index));
            System.out.println(" valor del indice " + index + " seleccionado"  + colIdCategoria.getCellData(index));
            cmbCategoria.getSelectionModel().select(colIdCategoria.getCellData(index) - 2);
          //  cmbCategoria.getSelectionModel().select();
            tfPrecio.setText(colPrecio.getCellData(index).toString());
            cboResponsable.getSelectionModel().select(colIdResponsable.getCellData(index) - 1);
            chkVigente.setSelected(colVigente.getCellData(index).isSelected());
            btnGuardar.setVisible(false);
            btnNuevo.setVisible(true);
        }
    }

    @FXML
    private void c_btnNuevo(ActionEvent event) {
        limpiarCajas();
    }

    @FXML
    private void onMouseOut(MouseEvent event) {
        lblMensaje.setText("");
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        boolean guardo;
        if (tfIdMenu.getText().trim().isEmpty()) {
            if (validarDatos()) {
                guardo = menudao.insertar(menu);
                lblMensaje.setText("Menu guardado con Exito!");
                if (guardo) {
                    limpiarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Guardar Menú");
                }
            }
        } else {
            lblMensaje.setText("El Menu ya existe");
        }
    }

    @FXML
    private void c_btnModificar(ActionEvent event) {
        if (menu == null) {
            lblMensaje.setText("Debes de seleccionar a un Platillo o Bebida");
        } else {
            boolean modifico;
            if (validarDatos()) {
                modifico = menudao.actualizar(menu);
                lblMensaje.setText("Menu Modificado Correctamente");
                if (modifico) {
                    limpiarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Intentar Modificar Menu");
                }
            }
        }
    }

    @FXML
    private void c_btnEliminar(ActionEvent event) {
        if (menu == null) {
            lblMensaje.setText("Selecciona un Platillo o Bebida");
        } else {
            boolean elimino;
            elimino = menudao.eliminar(menu);
            lblMensaje.setText("Platillo o Bebida Eliminado");
            if (elimino) {
                limpiarCajas();
                llenarTabla();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar Menu");
            }
        }
    }

}
