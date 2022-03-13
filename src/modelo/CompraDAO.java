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
public class CompraDAO {

    public boolean insertarCompra(Compra compra, ObservableList<DetalleCompra> lstDetalleCompra) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            //se deshabilita el modo de confirmación automática
            cnn.setAutoCommit(false);
            cadenaSql = "insert into compras values(?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, compra.getIdCompras());
            ps.setInt(2, compra.getIdProveedor());
            ps.setDouble(3, compra.getImporteTotal());
            ps.setObject(4, compra.getFechaCompra());
            ps.execute();
            for (DetalleCompra detail : lstDetalleCompra) {
                cadenaSql = "insert into det_compras values(?,?,?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, detail.getIdCompras());
                ps.setInt(2, detail.getIdProd());
                ps.setDouble(3, detail.getCantidad());
                ps.setDouble(4, detail.getPrecio());
                ps.execute();
                cadenaSql = "update productos set existenciaProd = existenciaProd + ?, "
                        + " precioProd= ? where idProd=?;";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setDouble(1, detail.getCantidad());
                ps.setDouble(2, detail.getPrecio());
                ps.setInt(3, detail.getIdProd());
                ps.executeUpdate();
            }
            //Si no hay error, Confirmar cambios en la BD
            cnn.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
            if (cnn != null) {
                System.out.println("Rollback");
                try {
                    //En caso de error deshace los cambios en la BD
                    cnn.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer" + ex1.getMessage());
                }
            }
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

    public int getNextFolio() {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        int resultado = 0;
        try {
            ps = cnn.prepareStatement("SELECT max(idcompras) FROM compras;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt(1) + 1;
            } else {
                resultado = 1;
            }

        } catch (SQLException e) {
            System.out.println("Error al Obtener folio de compra");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return resultado;
    }

    public ObservableList<Compra> getComprasXFecha(LocalDate fechaIni, LocalDate fechaFin) {
        Connection cnn = Conexion.getConexion();
        ObservableList<Compra> lstCompras = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "SELECT compras.idcompras, compras.idproveedor, proveedores.nombreProveedor, "
                    + "    compras.importeTotal, compras.fechacompra "
                    + " FROM compras "
                    + " inner join proveedores ON proveedores.idproveedor=compras.idproveedor"
                    + " where DATE(compras.fechacompra) BETWEEN ? AND ?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setObject(1, fechaIni);
            ps.setObject(2, fechaFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstCompras.add(new Compra(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDate(5).toLocalDate()));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de Compras");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstCompras;
    }
//por modificar

    public ObservableList<DetalleCompra> getDetalleCompra(int folio) {
        Connection cnn = Conexion.getConexion();
        ObservableList<DetalleCompra> lstDetalleCompra = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "SELECT d.idcompras, d.idProd, p.descripcionProd, d.cantidad, d.precio, (d.cantidad * d.precio) as importe\n"
                    + "FROM det_compras as d\n"
                    + "inner join productos as p ON p.idProd = d.idProd\n"
                    + "where d.idcompras=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setObject(1, folio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstDetalleCompra.add(new DetalleCompra(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getDouble(4), rs.getDouble(5),rs.getDouble(6)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de DetalleCompra");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstDetalleCompra;
    }
}
