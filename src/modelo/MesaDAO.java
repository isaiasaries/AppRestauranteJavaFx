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
public class MesaDAO {

    public boolean insertar(Mesa mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into mesa values(null,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, mesa.getNombreMesa());
            ps.setString(2, mesa.getEstadoMesa());
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

    public boolean eliminar(Mesa mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from mesa where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, mesa.getIdMesa());
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

    public boolean actualizar(Mesa mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update mesas set nombreMesa=?, estadoMesa=? "
                    + " where idMesa=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, mesa.getNombreMesa());
            ps.setString(2, mesa.getEstadoMesa());
            ps.setInt(3, mesa.getIdMesa());
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

    public ObservableList<Mesa> getAllMesas() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Mesa> lstMesas = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select idMesa, nombreMesa, estadoMesa from mesa;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstMesas.add(new Mesa(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de mesas");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstMesas;
    }
     public int totalMesas() {
        Connection cnn = Conexion.getConexion();
      //  ObservableList<Mesa> lstMesas = FXCollections.observableArrayList();
        PreparedStatement ps;
        int cantidad=0;
        try {
            ps = cnn.prepareStatement("select count(idMesa) as cantidad from mesa;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cantidad=rs.getInt(1);
                //lstMesas.add(new Mesa(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la cantidad de mesas");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return cantidad;
    }

    public ObservableList<Mesa> getMesasOcupadas() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Mesa> lstMesas = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement(" select distinct(o.idMesa) , m.nombreMesa,  m.estadoMesa\n"
                    + " from ordenestemporal o\n"
                    + "  inner join mesa m on o.idMesa = m.idMesa\n"
                    + " order by idMesa asc");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstMesas.add(new Mesa(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de mesas");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstMesas;
    }
}
