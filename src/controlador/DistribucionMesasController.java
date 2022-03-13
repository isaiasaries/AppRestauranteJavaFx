package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.MesaDAO;
import modelo.OrdenTemporalDAO;
import modelo.Validaciones;

/**
 * FXML Controller class
 *
 * @author Isaias Lagunes Pérez
 */
public class DistribucionMesasController implements Initializable {

    @FXML
    private GridPane gridMesas;

    private OrdenTemporalDAO ordenTemporalDao;
    private MesaDAO mesadao;
    private ObservableList<Button> lstBotones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordenTemporalDao = new OrdenTemporalDAO();
        mesadao = new MesaDAO();
        crearBotones();
       // System.out.println("Hasta aqui llega");
        agregarAGrid();
    }

    private void crearBotones() {
        Button boton;
        int cantidad = mesadao.totalMesas();
        if (cantidad > 0) {
            //Se crea una lista de botones para poder actualizar
            lstBotones = FXCollections.observableArrayList();
            for (int i = 1; i <= cantidad; i++) {
                boton = new Button("Mesa " + i);
                boton.setPrefHeight(60);
                boton.setPrefWidth(120);
                boton.setId(i + "");
                personalizarBoton(boton, i);
                //this.boton.setOnAction(manejador)
                boton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String id = ((Button) event.getSource()).getId();
                        mesaDisponible(Integer.parseInt(id));
                    }
                });
                lstBotones.add(boton);
            }
        }
    }

    private void agregarAGrid() {
        //esto es para un grid de 4x4
        // hace falta modificar dinamicamente para cualquier cantidad
        int cantidad = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                gridMesas.add(lstBotones.get(cantidad), i, j);
                cantidad++;
            }
        }
    }

    private void personalizarBoton(Button boton, int numero) {
        if (ordenTemporalDao.buscar(numero)) {
            boton.setText("Ocupado");
            boton.setStyle("-fx-background-color: #ff5722;-fx-font-size: 14px; ");
            // boton.setStyle("-fx-text-fill: #ffffff");
        }
    }

    /*  private void agregarBotones() {
        Button boton;
        int numero = 1;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                boton = new Button("Mesa " + numero++);
                boton.setPrefHeight(60);
                boton.setPrefWidth(120);
                boton.setId((numero - 1) + "");
                personalizarBoton(boton, numero);
                /*if (ordenTemporalDao.buscar(numero - 1)) {
                    boton.setText("Ocupado");
                    boton.setStyle("-fx-background-color: #ff5722;-fx-font-size: 14px; ");
                    // boton.setStyle("-fx-text-fill: #ffffff");
                }
                
                gridMesas.add(boton, i, j);
                //this.boton.setOnAction(manejador)
                boton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String id = ((Button) event.getSource()).getId();
                        mesaDisponible(Integer.parseInt(id));
                    }
                });
            }
        }
    }*/
    private void mesaDisponible(int mesa) {
        if (ordenTemporalDao.buscar(mesa)) {
            if (Validaciones.alertConfirm("Mesa Ocupada", "¿Desea hacer modificaciones?")) {
                muestraOrden(mesa, true, ordenTemporalDao.getClientesXMesa(mesa));
            }
        } else {
            muestraOrden(mesa, false, ordenTemporalDao.getClientesXMesa(mesa));
            //Al Cerrar la pantalla, se actualiza el boton
            personalizarBoton(lstBotones.get(mesa - 1), mesa);
        }
    }

    /* private void muestraOrden(int idButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/OrdenVista.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stagenew = new Stage();
            stagenew.setScene(scene);
            // Pasamos la lista al controlador usando el método implementado
            OrdenController ordenController = (OrdenController) loader.getController();
            ordenController.setNumMesa(idButton);
            stagenew.show();
        } catch (IOException ex) {
            System.out.println("Error al cargar vista" + ex.getMessage());
        }
    }*/
    private void muestraOrden(int idButton, boolean modificar, int cantidad) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/OrdenVista.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/OrdenDeVentaVista.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stagenew = new Stage();
            stagenew.setTitle("Mesa No. " + idButton);
            stagenew.setScene(scene);
            // Pasamos parametros al controlador de la OrdenVista
            OrdenDeVentaController ordenDeVentaController = (OrdenDeVentaController) loader.getController();
            ordenDeVentaController.setNumMesa(idButton, modificar, cantidad);
            stagenew.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error al cargar vista" + ex.getMessage());
        }
    }
}
