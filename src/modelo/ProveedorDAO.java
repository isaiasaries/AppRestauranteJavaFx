
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
 * @author isaia
 */
public class ProveedorDAO {
    public boolean insertar(Proveedor proveedor) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into proveedores values(null,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, proveedor.getNombreProveedor());
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

    public boolean eliminar(Proveedor proveedor) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from proveedores where idproveedor=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, proveedor.getIdProveedor());
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

    public boolean actualizar(Proveedor proveedor) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update proveedores set nombreProveedor=? "
                    + " where idproveedor=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, proveedor.getNombreProveedor());
            ps.setInt(2, proveedor.getIdProveedor());
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

    public ObservableList<Proveedor> getAllProveedores() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Proveedor> lstProveedores = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idproveedor, nombreProveedor from proveedores order by nombreProveedor;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstProveedores.add(new Proveedor(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar lista de proveedores");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
        return lstProveedores;
    }
}
