package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class TurnoDAO {
    public ObservableList<Turno> getAllTurnos() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Turno> lstTurnos = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idTurno, turno from turnos;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstTurnos.add(new Turno(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("no llena la Lista de Turnos");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstTurnos;
    }
}
