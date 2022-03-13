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
public class ResponsableDAO {
     public boolean insertar(Responsable responsable) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into responsables values(null,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, responsable.getNombreResponsable());
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

    public boolean eliminar(Responsable responsable) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from responsables where idResponsable=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, responsable.getIdResponsable());
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

    public boolean actualizar(Responsable responsable) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update responsable set nombreResponsable=? "
                    + " where idResponsable=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, responsable.getNombreResponsable());
            ps.setInt(2, responsable.getIdResponsable());
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

    public ObservableList<Responsable> getAllResponsables() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Responsable> lstResponsables = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idResponsable, nombreResponsable from responsables;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstResponsables.add(new Responsable(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar lista de responsables");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
        return lstResponsables;
    }    
}
