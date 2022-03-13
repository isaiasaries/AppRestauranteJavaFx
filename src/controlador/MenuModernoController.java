package controlador;

import conectar.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.swing.WindowConstants;
import modelo.Usuario2;
import modelo.Validaciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import utilerias.WindowFactory;

/**
 * FXML Controller class Menu Moderno Principal
 *
 * @author Isaias Lagunes Pérez
 */
public class MenuModernoController implements Initializable {

    @FXML
    private Pane pnlCatalogos;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Pane pnlLogoHome;
    @FXML
    private Label lblUsuario;
    @FXML
    private ImageView ivUsuario;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnLogoHome;
    @FXML
    private Button btnCatalogos;
    @FXML
    private Button btnOrden;
    @FXML
    private Button btnSegMesas;
    @FXML
    private Button btnReporteVentas;
    @FXML
    private Button btnOrdenXEncargado;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnNiveles;
    @FXML
    private Button btnMesas;
    @FXML
    private Button btnCategorias;
    @FXML
    private Button btnResponsable;
    @FXML
    private Button btnOrdenXMesa;
    @FXML
    private Button btnPersonal;
    @FXML
    private Button btnProveedores;
    @FXML
    private Button btnCompras;
    @FXML
    private Button btnReportes;
    @FXML
    private Pane pnlReportes;
    @FXML
    private Button btnProductos;
    @FXML
    private Button btnMenuProd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se carga el logo imagen desdes la carpeta recursos
        Image imagen = new Image("/recursos/imagenes/logo mezquite.jpg");
        imgLogo.setImage(imagen);
        btnLogoHome.setDisable(true);
        pnlLogoHome.toFront();
        
    }

    @FXML
    private void c_btnUsuarios(ActionEvent event) {
        btnUsuarios.setDisable(true);
        crearVentana("/vista/UsuarioVista.fxml", "Usuarios del Sistema");
        btnUsuarios.setDisable(false);
    }

    @FXML
    private void c_btnNiveles(ActionEvent event) {
        btnNiveles.setDisable(true);
        crearVentana("/vista/NivelVista.fxml", "Nivel de Acceso a Usuarios");
        btnNiveles.setDisable(false);
    }

    @FXML
    private void c_btnMesas(ActionEvent event) {
        btnMesas.setDisable(true);
        crearVentana("/vista/MesaVista.fxml", "Mesas Disponibles para Uso");
        btnMesas.setDisable(false);
    }

    @FXML
    private void c_btnLogoHome(ActionEvent event) {
        pnlLogoHome.toFront();
        btnLogoHome.setDisable(true);
        btnCatalogos.setDisable(false);
    }

    @FXML
    private void c_btnOrden(ActionEvent event) {
        //OrdenMesasVista Ventana VAcia para modificacion
        //Cambio el nombre a DistribucionMesasVista
        btnCatalogos.setDisable(false);
        btnOrden.setDisable(true);
        crearVentana("/vista/DistribucionMesasVista.fxml", "Seleciona una mesa...");
        btnOrden.setDisable(false);
    }

    @FXML
    private void c_btnCatalogos(ActionEvent event) {
        pnlCatalogos.toFront();
        btnLogoHome.setDisable(false);
        btnReporteVentas.setDisable(false);
        btnCatalogos.setDisable(true);
    }

    public void setUsuario(Usuario2 usr) {
        ivUsuario.setImage(usr.getIvUsuario().getImage());
        lblUsuario.setText(usr.getNombreUsuario());
        if (usr.getIdNivel() == 2 || usr.getIdNivel() == 3 ) {
            btnLogoHome.setVisible(false);
            btnOrden.setVisible(false);
            btnOrdenXMesa.setVisible(false);
            btnSegMesas.setVisible(false);
            btnReporteVentas.setVisible(false);
            btnCatalogos.setVisible(false);
            btnCompras.setVisible(false);
            btnReportes.setVisible(false);
            
        }
        if (usr.getIdNivel() == 4) {
            btnLogoHome.setVisible(false);
            btnCatalogos.setVisible(false);
            btnSegMesas.setVisible(false);
            btnReporteVentas.setVisible(false);
        }
    }

    @FXML
    private void c_btnCategorias(ActionEvent event) {
        btnCategorias.setDisable(true);
        crearVentana("/vista/CategoriaVista.fxml", "Categorias de Platillos o Bebidas");
        btnCategorias.setDisable(false);
    }

    @FXML
    private void c_btnMenu(ActionEvent event) {
        btnMenu.setDisable(true);
        crearVentana("/vista/MenuVista.fxml", "Platillos y Bebidas de la Carta");
        btnMenu.setDisable(false);
    }

    @FXML
    private void c_btnResponsable(ActionEvent event) {
        btnResponsable.setDisable(true);
        crearVentana("/vista/ResponsableVista.fxml", "Encargados");
        btnResponsable.setDisable(false);
    }

    @FXML
    private void c_btnSegmesas(ActionEvent event) {
        btnCatalogos.setDisable(false);
        btnSegMesas.setDisable(true);
        crearVentana("/vista/MesasVentaVista.fxml", "Seguimiento a Mesas Ocupadas");
        btnSegMesas.setDisable(false);
    }

    @FXML
    private void c_btnClientes(ActionEvent event) {
        btnClientes.setDisable(true);
        crearVentana("/vista/ClientesVista.fxml", "Catálogo de Clientes");
        btnClientes.setDisable(false);
    }

    @FXML
    private void c_btnReporteVentas(ActionEvent event) {
        //Deshabilita el boton y abre la ventana de ventas
        //Al terminar, habilita el boton 
        btnReporteVentas.setDisable(true);
       // crearVentana("/vista/ReporteVentasVista.fxml", "Reporte de Ventas por Rango de Fechas");
        crearVentana("/vista/VentasFullVista.fxml", "Busqueda y Reportes de Ventas");
        btnReporteVentas.setDisable(false);
        btnCatalogos.setDisable(false);
        btnLogoHome.setDisable(false);;
    }

    @FXML
    private void c_btnOrdenXMesa(ActionEvent event) {
        btnCatalogos.setDisable(false);
        btnOrdenXMesa.setDisable(true);
        crearVentana("/vista/MeseroMesaVista.fxml", "Seguimiento por mesa");
        btnOrdenXMesa.setDisable(false);
    }

    @FXML
    private void c_btnOrdenXEncargado(ActionEvent event) {
        btnCatalogos.setDisable(false);
        btnOrdenXEncargado.setDisable(true);
        crearVentana("/vista/OrdenesResponsablesVista.fxml", "Seguimiento a Encargados de Elaborar Ordenes");
        btnOrdenXEncargado.setDisable(false);
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/OrdenesResponsablesVista.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Seguimiento a Encargados de Elaborar Ordenes");
            stage.setResizable(true);
            stage.centerOnScreen();
           // stage.initModality(Modality.APPLICATION_MODAL);
           // stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                                
            }
        });
            stage.setScene(escena);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error al intentar crear ventana, revisar initialize");
            System.out.println("/vista/OrdenesResponsablesVista.fxml" + ex.getMessage());
        }*/  
    }

    private void crearVentana(String ruta, String titulo) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root;
        try {
            root = loader.load();
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setResizable(true);
            stage.centerOnScreen();
           // stage.initModality(Modality.APPLICATION_MODAL);
           // stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(escena);
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error al intentar crear ventana, revisar initialize");
            System.out.println(ruta + ex.getMessage());
        }
    }


    @FXML
    private void c_btnPersonal(ActionEvent event) {
        btnPersonal.setDisable(true);
        crearVentana("/vista/PersonalVista.fxml", "Personal que Atiende");
        btnPersonal.setDisable(false);
    }

    @FXML
    private void c_btnProveedores(ActionEvent event) {
        btnProveedores.setDisable(true);
        crearVentana("/vista/ProveedorVista.fxml", "Proveedores");
        btnProveedores.setDisable(false);
    }

    @FXML
    private void c_btnCompras(ActionEvent event) {
        WindowFactory ventana = new WindowFactory();
        btnCompras.setDisable(true);
        ventana.crearVentanaSinBordes("/vista/ComprasFullVista.fxml");
        //ventana.crearVentanaSinBordes("/vista/ComprasMenuVista.fxml");
        btnCompras.setDisable(false);
    }

    private void c_btnVentasXMes(ActionEvent event) {
          Connection cnn = Conexion.getConexion();
        try {
      
            final JasperReport jasperReport = JasperCompileManager.compileReport(ReporteVentasController.class.getResourceAsStream("/recursos/Reportes/VentasMesXYear.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }

    @FXML
    private void c_btnReportes(ActionEvent event) {
        btnReportes.setDisable(true);
        pnlReportes.toFront();
        btnReportes.setDisable(false);
    }

    @FXML
    private void c_btnProductos(ActionEvent event) {
        btnProductos.setDisable(true);
        crearVentana("/vista/ProductoVista.fxml", "Productos Base de Platillos");
        btnProductos.setDisable(false);
    }

    @FXML
    private void c_btnMenuProd(ActionEvent event) {
        btnMenuProd.setDisable(true);
        crearVentana("/vista/IngredientesPlatilloVista.fxml", "Platillos y sus Ingredientes");
        btnMenuProd.setDisable(false);
    }

    @FXML
    private void c_btnExistencias(ActionEvent event) {
         Connection cnn = Conexion.getConexion();
        try {           
            final JasperReport jasperReport = JasperCompileManager.compileReport(ReporteVentasController.class.getResourceAsStream("/recursos/Reportes/rptComprasVentasXProd.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }

    @FXML
    private void c_btnRptComparativo(ActionEvent event) {
        Connection cnn = Conexion.getConexion();
        try {           
            final JasperReport jasperReport = JasperCompileManager.compileReport(ReporteVentasController.class.getResourceAsStream("/recursos/Reportes/rptUtilidadesXProd.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }

}
