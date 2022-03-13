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
public class PersonalDAO {
    public boolean insertar(Personal personal) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into personal values(null,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, personal.getNombrePersonal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean eliminar(Personal personal) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from personal where idPersonal=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, personal.getIdPersonal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean actualizar(Personal personal) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update personal set nombrePersonal=? "
                    + " where idPersonal=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, personal.getNombrePersonal());
            ps.setInt(2, personal.getIdPersonal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public ObservableList<Personal> getAllPersonal() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Personal> lstPersonal = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idPersonal, nombrePersonal from personal order by nombrePersonal ASC;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstPersonal.add(new Personal(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar lista de personals");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
        return lstPersonal;
    } 
    
}
