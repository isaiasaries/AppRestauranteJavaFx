package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Menu;
import modelo.MenuDAO;
import modelo.Producto;
import modelo.ProductoDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class IngredientesPlatilloController implements Initializable {

    @FXML
    private TableView<Menu> tvPatillo;
    @FXML
    private TableColumn<Menu, Integer> colClaveP;
    @FXML
    private TableColumn<Menu, String> colPlatillo;
    @FXML
    private TableView<Producto> tvProducto;
    @FXML
    private TableColumn<Producto, Integer> colClave;
    @FXML
    private TableColumn<Producto, String> colProducto;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<Menu> cboPlatillo1;
    @FXML
    private ComboBox<Producto> cboProducto1;

    private ProductoDAO productodao;
    private ObservableList<Producto> lstProductos;

    private MenuDAO menudao;
    private ObservableList<Menu> lstMenu;

    private ObservableList<Producto> lstProductosNew;
    private ObservableList<Menu> lstMenuNew;

    private int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstProductos = FXCollections.observableArrayList();
        lstMenu = FXCollections.observableArrayList();

        lstProductosNew = FXCollections.observableArrayList();
        lstMenuNew = FXCollections.observableArrayList();
        iniciaTVs();
        llenarCombos();
        tvPatillo.setItems(lstMenuNew);
        tvProducto.setItems(lstProductosNew);
    }

    private void iniciaTVs() {
        colClaveP.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        colPlatillo.setCellValueFactory(new PropertyValueFactory<>("descripcionMenu"));

        colClave.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("descripcionProd"));
    }

    private void llenarCombos() {
        //Llenado de combos menu y proveedor
        menudao = new MenuDAO();
        lstMenu = menudao.getAllMenuVig();
        cboPlatillo1.setItems(lstMenu);
        productodao = new ProductoDAO();
        lstProductos = productodao.getAllProductos();
        cboProducto1.setItems(lstProductos);
    }

    private boolean validar() {
        if (lstMenuNew.isEmpty()) {
            Validaciones.alertError("Guardar", "Debes Seleccionar un Platillo o Bebida");
            return false;
        }
        if (lstProductosNew.isEmpty()) {
            Validaciones.alertError("Guardar", "Debes Seleccionar uno o más Productos");
            return false;
        }
        return true;
    }

    private void limpiarTablas() {
        lstMenuNew.clear();
        lstProductosNew.clear();
    }

    @FXML
    private void c_btnGuardar(ActionEvent event) {
        boolean guardo;
        if (validar()) {
            guardo = menudao.inPDM(lstMenuNew, lstProductosNew);
            if (guardo) {
                Validaciones.alertConfirm("Guardar", "Relación guardada con Exito!");
                limpiarTablas();
            } else {
                Validaciones.alertError("Error al Guardar", "Existe Error");
            }
        }
    }

    @FXML
    private void c_cboPlatillo1(ActionEvent event) {
        lstMenuNew.clear();
        lstMenuNew.add(cboPlatillo1.getValue());
      //  System.out.println(" "+ cboPlatillo1.getValue().getIdMenu());
        lstProductosNew = menudao.getMenuXId(cboPlatillo1.getValue().getIdMenu());
        tvProducto.setItems(lstProductosNew);
    }

    @FXML
    private void c_cboProducto1(ActionEvent event) {
        //Producto prod = new Producto(cboProducto1.getValue().getIdProd(),cboProducto1.getValue().getDescripcionProd(), cboProducto1.getValue().getExistenciaProd(),cboProducto1.getValue().getPrecioProd(),cboProducto1.getValue().getStockMin());
        // lstProductosNew.add(prod);
        lstProductosNew.add(cboProducto1.getValue());
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        //System.out.println(" " + event.getSource().toString());
        if (event.getSource() == tvProducto) {
            index = tvProducto.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                lstProductosNew.remove(index);
            }
        } else {
            index = tvPatillo.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                lstMenuNew.remove(index);
            }
        }

    }

}
