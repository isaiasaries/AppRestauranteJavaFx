package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
public class LoginController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContra;
    @FXML
    private Button btnIngresar;
    @FXML
    private Label lblMensaje;
    @FXML
    private ComboBox<Nivel> cmbNivel;
    @FXML
    private ImageView ivLogin;
    @FXML
    private Button btnCerrar;

    private Usuario2 usr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagenLogin = new Image("/recursos/imagenes/iconomezquite.jpg");
        ivLogin.setImage(imagenLogin);
        //Para llenar el combo de niveles      
        llenarCboNiveles();
    }

    private void llenarCboNiveles() {
        NivelDAO niveldao = new NivelDAO();
        ObservableList<Nivel> lstNiveles;
        lstNiveles = niveldao.getNiveles();
        cmbNivel.setItems(lstNiveles);
        cmbNivel.getSelectionModel().selectFirst();
    }

    @FXML
    private void clickIngresar(ActionEvent event) {
        if (tfUsuario.getText().isEmpty() || tfContra.getText().isEmpty()) {
            lblMensaje.setText("Ingresa tus datos por favor!");
        } else if (cmbNivel.getValue() == null) {
            lblMensaje.setText("Selecciona tu nivel de acceso");
        } else {
            String usuario = tfUsuario.getText().trim();
            String pass = tfContra.getText().trim();
            short nivel = cmbNivel.getValue().getIdNivel();
            Usuario2DAO usuariodao = new Usuario2DAO();
            usr = usuariodao.login(usuario, pass, nivel);
            if (usr == null) {
                lblMensaje.setText("Usuario no Existe");
            } else {
                nextWindow();
            }
        }
    }

    private void nextWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/menuModerno.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root, 1024, 680);
            Stage stage = new Stage();
            stage.setTitle("Restaurante El Mezquite");
            Image icono = new Image(getClass().getResourceAsStream("/recursos/imagenes/iconomez.jpg"));
            stage.getIcons().add(icono);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            // Pasamos la lista al controlador usando el método implementado
            MenuModernoController menuModerno = (MenuModernoController) fxmlLoader.getController();
            menuModerno.setUsuario(usr);
            Stage myStage = (Stage) btnIngresar.getScene().getWindow();
            myStage.close();
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void onMouseOut(MouseEvent mouseEvent) {
        lblMensaje.setText("");
    }

    @FXML
    private void c_btnCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

}
