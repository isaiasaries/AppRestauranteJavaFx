package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Nivel;
import modelo.NivelDAO;
import modelo.Usuario2;
import modelo.Usuario2DAO;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class UsuarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtClave;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtPassword;
    @FXML
    private CheckBox chkVigente;

    @FXML
    private ComboBox<Nivel> cboNivel;

    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Usuario2> tvUsuario;
    @FXML
    private TableColumn<Usuario2, Integer> colClave;
    @FXML
    private TableColumn<Usuario2, String> colNombre;
    @FXML
    private TableColumn<Usuario2, String> colUsuario;
    @FXML
    private TableColumn<Usuario2, String> colPassword;
    @FXML
    private TableColumn<Usuario2, Byte> colNivel;
    @FXML
    private TableColumn<Usuario2, CheckBox> colVigente;
    @FXML
    private TableColumn<Usuario2, ImageView> colImagen;
    @FXML
    private ImageView ivUsuario;
    @FXML
    private Button btnGuardar;

    private ObservableList<Nivel> lstNiveles;
    private ObservableList<Usuario2> lstUsuariosTv;
    private String rutaUrl;
    private int index = -1;
    private int clave;
    private String nombre;
    private String usuario;
    private String password;
    private byte nivel;
    private byte vigente;
    private Image imagenBase;

    private Usuario2DAO usuario2dao;
    private Usuario2 usuario2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtClave.setDisable(true);
        //Para llenar el combo de niveles
        NivelDAO niveldao = new NivelDAO();
        lstNiveles = niveldao.getNiveles();
        cboNivel.setItems(lstNiveles);
        //Cargar la ruta con la imagen por default
        rutaUrl = "/recursos/imagenes/user.png";
        imagenBase = new Image(getClass().getResourceAsStream(rutaUrl));
        ivUsuario.setImage(imagenBase);

        //Para llenar el Table View de Usuarios
        colClave.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("passwordUsuario"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("idNivel"));
        colVigente.setCellValueFactory(new PropertyValueFactory<>("cbVigente"));
        colImagen.setCellValueFactory(new PropertyValueFactory<>("ivUsuario"));
        tvUsuario.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        usuario2dao = new Usuario2DAO();
        llenarTabla();
    }

    private void borrarCajas() {
        txtClave.clear();
        txtNombre.clear();
        txtUsuario.clear();
        txtPassword.clear();
        chkVigente.setSelected(false);
        ivUsuario.setImage(imagenBase);
       // rutaUrl = null;
        btnGuardar.setDisable(false);
    }

    private void llenarTabla() {
        //lstUsuariosTv.clear();
        lstUsuariosTv = usuario2dao.getUsuarios();
        tvUsuario.setItems(lstUsuariosTv);
    }

    private void asignar() {
        if (!txtClave.getText().isEmpty()) {
            clave = Integer.parseInt(txtClave.getText().trim());
        }
        nombre = txtNombre.getText();
        usuario = txtUsuario.getText();
        password = txtPassword.getText();
        if (cboNivel.getValue() != null) {
            nivel = cboNivel.getValue().getIdNivel();
        }
        vigente = chkVigente.isSelected() ? (byte) 1 : (byte) 0;
    }

    private boolean validar() {
        asignar();
        if (nombre.trim().isEmpty()) {
            lblMensaje.setText("Coloca el nombre completo del Usuario");
            return false;
        } else if (password.trim().isEmpty()) {
            lblMensaje.setText("Coloca la Contraseña");
            return false;
        } else if (usuario.trim().isEmpty()) {
            lblMensaje.setText("Coloca el Nombre del Usuario");
            return false;
        } else if (nivel <= 0) {
            lblMensaje.setText("Selecciona un Nivel de Acceso");
            return false;
        }
       /*  if (rutaUrl == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Elegir Imagen...");
            alert.setTitle("Confirmacion");
            alert.setContentText("¿Deseas Guardar sin Imagen?");
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if ("Aceptar".equals(result.getText())) {
                File file = new File(UsuarioController.class.getResourceAsStream("/recursos/imagenes/user.png"));
               // File file = new File("C:\\Users\\isaia\\Documents\\NetBeansProjects\\RestauranteMezquite\\src\\recursos\\imagenes\\user.png");
                rutaUrl = file.getPath();
            } else {
                lblMensaje.setText("Selecciona una Imagen");
                return false;
            }
        }*/
        return true;
    }

    @FXML
    private void clickGuardar(ActionEvent event) {
        boolean guardo;
        if (txtClave.getText().trim().isEmpty()) {
            if (validar()) {
//                Usuario2DAO usuario2dao = new Usuario2DAO();
                guardo = usuario2dao.insertar(nombre, usuario, password, nivel, vigente, rutaUrl);
                lblMensaje.setText("Nuevo usuario guardado con Exito!");
                if (guardo) {
                    borrarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Guardar, asegurate que los datos son correctos");
                }
            }
        } else {
            lblMensaje.setText("El usuario ya existe");
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        usuario2 = this.tvUsuario.getSelectionModel().getSelectedItem();
        if (usuario2 == null) {
            lblMensaje.setText("Debes de seleccionar a un Usuario");
        } else {
            boolean elimino;
            elimino = usuario2dao.eliminar(clave);
            lblMensaje.setText("Usuario Eliminado Correctamente");
            if (elimino) {
                borrarCajas();
                llenarTabla();
            } else {
                lblMensaje.setText("Error al Intentar Eliminar, asegurate que los datos son correctos");
            }
        }
    }

    @FXML
    private void clickActualizar(ActionEvent event) {
      
       usuario2 = this.tvUsuario.getSelectionModel().getSelectedItem();
        if (usuario2 == null) {
            lblMensaje.setText("Debes de seleccionar a un Usuario");
        } else {
            boolean modifico;
            if (validar()) {
                if (rutaUrl == null) {
                    modifico = usuario2dao.modificarSinImagen(clave, nombre, usuario, password, nivel, vigente);
                    lblMensaje.setText("Usuario sin foto Modificado Correctamente");
                } else {
                    modifico = usuario2dao.modificar(clave, nombre, usuario, password, nivel, vigente, rutaUrl);
                    lblMensaje.setText("Usuario con foto Modificado Correctamente");
                }
                if (modifico) {
                    borrarCajas();
                    llenarTabla();
                } else {
                    lblMensaje.setText("Error al Intentar Modificar, asegurate que los datos son correctos");
                }
            }
        }
    }

@FXML
        private void clickcboNivel(ActionEvent event) {
    }

    @FXML
        private void mouseClicked(MouseEvent event) {
        lblMensaje.setVisible(false);
    }

    @FXML
        private void clickMouse(MouseEvent event) {
        index = tvUsuario.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            txtClave.setText(colClave.getCellData(index).toString());
            txtNombre.setText(colNombre.getCellData(index));
            txtUsuario.setText(colUsuario.getCellData(index));
            txtPassword.setText(colPassword.getCellData(index));
            cboNivel.getSelectionModel().select(colNivel.getCellData(index) - 1);
            chkVigente.setSelected(colVigente.getCellData(index).isSelected());
            ivUsuario.setImage(colImagen.getCellData(index).getImage());
            btnGuardar.setDisable(true);
        }
    }
    @FXML
        private void clickCargarImg(ActionEvent event) {
        Stage stage = (Stage) ivUsuario.getScene().getWindow();
        //Obtener URL de la imagen
        FileChooser fileChooser = new FileChooser();
        configurarFileChooser(fileChooser);
        try {
            File file = fileChooser.showOpenDialog(stage);
            if (file.isFile()) {
                rutaUrl = file.getPath();
                Image imgLoad = new Image(rutaUrl);
                ivUsuario.setImage(imgLoad);
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar la imagen, URL erronea" + ex.getMessage());
        }
    }

    private static void configurarFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Abrir archivo de imagen...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    @FXML
        public void onMouseOut(MouseEvent mouseEvent) {
        lblMensaje.setText("");
    }
}
