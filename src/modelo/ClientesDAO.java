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
public class ClientesDAO {
    public boolean insertar(Clientes clientes) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into clientes values(null,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, clientes.getNombreClientes());
            ps.setString(2, clientes.getDatosClientes());
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
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean eliminar(Clientes clientes) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from clientes where idClientes=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, clientes.getIdClientes());
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
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean actualizar(Clientes clientes) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update clientes set nombreClientes=?, "
                    + " datosClientes=? "
                    + " where idclientes=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, clientes.getNombreClientes());
            ps.setString(2, clientes.getDatosClientes());
            ps.setInt(3, clientes.getIdClientes());
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
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public ObservableList<Clientes> getAllClientes() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Clientes> listClientes = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idclientes, nombreClientes, datosClientes "
                    + " from clientes order by nombreClientes;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listClientes.add(new Clientes(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de clientes");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return listClientes;
    }
    
}
