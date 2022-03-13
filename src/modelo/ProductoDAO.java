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
public class ProductoDAO {
    public boolean insertar(Producto producto) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into productos values(null,?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, producto.getDescripcionProd());
            ps.setInt(2, producto.getExistenciaProd());
            ps.setDouble(3, producto.getPrecioProd());
            ps.setInt(4, producto.getStockMin());
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

    public boolean eliminar(Producto producto) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from productos where idProd=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, producto.getIdProd());
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

    public boolean actualizar(Producto producto) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update productos set descripcionProd=?, existenciaProd=?,"
                    + " precioProd=?, stockMin=? "
                    + " where idProd=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, producto.getDescripcionProd());
            ps.setInt(2, producto.getExistenciaProd());
            ps.setDouble(3, producto.getPrecioProd());
            ps.setInt(4, producto.getStockMin());
            ps.setInt(5, producto.getIdProd());
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

    public ObservableList<Producto> getAllProductos() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Producto> lstProductos = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idProd, descripcionProd, existenciaProd, "
                    + "precioProd, stockMin from productos order by descripcionProd ASC;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstProductos.add(new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar lista de productos");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
        return lstProductos;
    } 
    
}
