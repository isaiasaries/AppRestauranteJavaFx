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
public class NivelDAO {

    private PreparedStatement ps;
    private String cadenaSql;

    public boolean insertar(Nivel nivel) {
        Connection cnn = Conexion.getConexion();
        try {
            cadenaSql = "insert into nivel (descripcionNivel) values(?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, nivel.getDescripcionNivel());
            ps.execute();
           // System.out.println("Se ha guardado el Nivel de Acceso");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar el Nivel de Acceso: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean eliminar(Nivel n) {
        Connection cnn = Conexion.getConexion();
        try {
            cadenaSql = "delete from nivel where idNivel=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, n.getIdNivel());
            ps.execute();
           // System.out.println("Se han eliminado los datos del Nivel");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al Eliminar el Nivel: " + e.getMessage());
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean modificar(Nivel n) {
        Connection cnn = Conexion.getConexion();
        try {
            cadenaSql = "update nivel set descripcionNivel=? where idNivel=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, n.getDescripcionNivel());
            ps.setInt(2, n.getIdNivel());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar:" + e.getMessage());
            return false;
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public ObservableList<Nivel> getNiveles() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Nivel> lstNiveles = FXCollections.observableArrayList();
        try {
            ps = cnn.prepareStatement("select idNivel, descripcionNivel from nivel order by idNivel;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstNiveles.add(new Nivel(rs.getByte(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("no llena la Lista de Niveles");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstNiveles;
    }
    //Metodo generico para buscar y saber si algun elemento ya existe en la tabla

    public boolean ifExists(String sSQL, String descripcion) {
        Connection cnn = Conexion.getConexion();
        ResultSet rs;
        try {
            ps = cnn.prepareStatement(sSQL);
            ps.setString(1, descripcion);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Existe el duplicado");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar" + ex.getMessage());
        }
        return false;
    }

}
