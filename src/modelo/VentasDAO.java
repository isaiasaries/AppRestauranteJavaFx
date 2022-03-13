package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class VentasDAO {

    public ObservableList<Ventas> getVentasXFecha(LocalDate fechaIni, LocalDate fechaFin) {
        Connection cnn = Conexion.getConexion();
        ObservableList<Ventas> lstVentas = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "select o.idOrdenes, o.idMesa, c.nombreClientes, o.importeTotal, DATE(o.fecha)\n"
                    + "from ordenes o\n"
                    + "inner join clientes c on o.idClientes = c.idclientes\n"
                    + "where DATE(o.fecha) BETWEEN ? AND ?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setObject(1, fechaIni);
            ps.setObject(2, fechaFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstVentas.add(new Ventas(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDate(5).toLocalDate()));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de Ventas");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstVentas;
    }

    public ObservableList<DetalleVentas> getDetalleVenta(int folio) {
        Connection cnn = Conexion.getConexion();
        ObservableList<DetalleVentas> lstDetalleVentas = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "select d.idOrdenes, d.noClientes, d.cantidad,d.idmenu, m.descripcionMenu,\n"
                    + " m.precioMenu, d.importe, d.idTurno, d.observaciones\n"
                    + "from  det_orden as d\n"
                    + "inner join menu as m on m.idmenu = d.idmenu\n"
                    + "where d.idOrdenes=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setObject(1, folio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstDetalleVentas.add(new DetalleVentas(rs.getInt(1), rs.getInt(2), 
                        rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getDouble(6),
                        rs.getDouble(7), rs.getInt(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de DetalleVentas");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstDetalleVentas;
    }   
}
