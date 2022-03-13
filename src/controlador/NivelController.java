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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Nivel;
import modelo.NivelDAO;

/**
 * FXML Controller class Controlador de la Vista Nivel Realizado en dos
 * pantallas, una para la consulta general utilizando un TableView y otra para
 * agregar y modificar.
 *
 * @author Isaias Lagunes Pérez
 */
public class NivelController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private TableView<Nivel> tvNivel;
    @FXML
    private TableColumn<Nivel, Byte> colClave;
    @FXML
    private TableColumn<Nivel, String> colDescripcion;
    @FXML
    private Label lblMensaje;
    @FXML
    private TextField tfFiltro;

    private ObservableList<Nivel> lstNiveles;
    private ObservableList<Nivel> lstNivelesFiltro;
    private NivelDAO niveldao;

    /* private int index = -1;
    private byte nivel;
    private String descripcion;*/
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstNiveles = FXCollections.observableArrayList();
        lstNivelesFiltro = FXCollections.observableArrayList();
        niveldao = new NivelDAO();
        colClave.setCellValueFactory(new PropertyValueFactory<>("idNivel"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionNivel"));
        tvNivel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        llenarTVNiveles();
    }

    private void llenarTVNiveles() {
        lstNiveles = niveldao.getNiveles();
        tvNivel.setItems(lstNiveles);
    }

    @FXML
    private void clickBtnGuardar(ActionEvent event) {
        //Invocar la vista NivelGuardar
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/NivelGuardarVista.fxml"));
        try {
            Parent root = loader.load();
            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setScene(escena);
            NivelGuardarController ctrlNivelGuardar = loader.getController();
            ctrlNivelGuardar.initAtributos(lstNiveles);
            escenario.showAndWait();
            Nivel n = ctrlNivelGuardar.getNivel();
            if (n != null) {
                if (!niveldao.insertar(n)) {
                    lblMensaje.setText("Error al Guardar");
                    lblMensaje.setVisible(true);
                }
                if (n.getDescripcionNivel().toLowerCase().contains(tfFiltro.getText().toLowerCase())) {
                    lstNivelesFiltro.add(n);
                }
            }
        } catch (IOException ex) {
            lblMensaje.setText(ex.getMessage());
        }
        llenarTVNiveles();
    }

    @FXML
    private void clickBtnModificar(ActionEvent event) {
        Nivel n = this.tvNivel.getSelectionModel().getSelectedItem();
        if (n == null) {
            lblMensaje.setText("Selecciona un Nivel");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/NivelGuardarVista.fxml"));
            try {
                Parent root = loader.load();
                Scene escena = new Scene(root);
                Stage escenario = new Stage();
                escenario.initModality(Modality.APPLICATION_MODAL);
                escenario.initStyle(StageStyle.UNDECORATED);
                escenario.setScene(escena);
                NivelGuardarController ctrlNivelGuardar = loader.getController();
                ctrlNivelGuardar.initAtributos(lstNiveles, n);
                escenario.showAndWait();

                Nivel nSeleccionado = ctrlNivelGuardar.getNivel();
                if (nSeleccionado != null) {
                    if (!niveldao.modificar(n)) {
                        lblMensaje.setText("Error al Guardar");
                        lblMensaje.setVisible(true);
                    }
                    if (!nSeleccionado.getDescripcionNivel().toLowerCase().contains(tfFiltro.getText().toLowerCase())) {
                        lstNivelesFiltro.remove(nSeleccionado);
                    }
                }
                 llenarTVNiveles();
            } catch (IOException ex) {
                lblMensaje.setText(ex.getMessage());
            }
        }
       
    }

    @FXML
    private void filtrarDescripcion(KeyEvent event) {
        String filtroNombre = tfFiltro.getText();
        if (filtroNombre.isEmpty()) {
            tvNivel.setItems(lstNiveles);
        } else {
            lstNivelesFiltro.clear();
            for (Nivel n : lstNiveles) {
                if (n.getDescripcionNivel().toLowerCase().contains(filtroNombre.toLowerCase())) {
                    lstNivelesFiltro.add(n);
                }
                tvNivel.setItems(lstNivelesFiltro);
            }
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        boolean elimino;
        Nivel n = this.tvNivel.getSelectionModel().getSelectedItem();
        if (n == null) {
            lblMensaje.setText("Selecciona un Nivel");
        } else {
            // lstNiveles.add(n);
            elimino = niveldao.eliminar(n);
            if (elimino) {
                llenarTVNiveles();
                lblMensaje.setText("Se ha eliminado el Nivel");
            } else {
                lblMensaje.setText("Error al Eliminar el Nivel");
                lblMensaje.setVisible(true);
            }
            if (n.getDescripcionNivel().toLowerCase().contains(tfFiltro.getText().toLowerCase())) {
                lstNivelesFiltro.remove(n);
            }
            //System.out.println("Entro a borrar");
        }
    }
}
