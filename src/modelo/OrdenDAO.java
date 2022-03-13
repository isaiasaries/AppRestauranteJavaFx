package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class OrdenDAO {

    public boolean inOrden(Orden orden, ObservableList<OrdenDetalle> lstOrdDetalle, int tipo) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;

        try {
            //se deshabilita el modo de confirmación automática
            //Permite hacer varias operaciones y si no existe error, 
            //se confirma bloque. Si existe error en alguna, se anula todo.
            cnn.setAutoCommit(false);
            cadenaSql = "insert into ordenes values(null,?,?,?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, orden.getIdMesa());
            ps.setInt(2, orden.getIdCliente());
            ps.setInt(3, orden.getIdPersonal());
            ps.setInt(4, orden.getCantClientes());
            ps.setDouble(5, orden.getImporteTotal());
            ps.setObject(6, orden.getFecha());
            ps.execute();
            //Conocer el valor del auto_increment para usarlo en detalle
            cadenaSql = "select max(idOrdenes) as folio from ordenes;";
            ps = cnn.prepareStatement(cadenaSql);
            ResultSet rs = ps.executeQuery();
            int idOrden;
            if (rs.next()) {
                idOrden = rs.getInt(1);
            } else {
                idOrden = 1;
            }

            for (OrdenDetalle ordDet : lstOrdDetalle) {
                cadenaSql = "insert into det_orden values(?,?,?,?,?,?,?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, idOrden);
                ps.setInt(2, ordDet.getNoClientes());
                ps.setDouble(3, ordDet.getIdMenu());
                ps.setDouble(4, ordDet.getCantidad());
                ps.setDouble(5, ordDet.getPrecio());
                ps.setDouble(6, ordDet.getImporte());
                ps.setInt(7, ordDet.getIdTurno());
                ps.setString(8, ordDet.getObservaciones());
                ps.execute();
                //Update para modificar las existencias
                cadenaSql = "update productos set existenciaProd = existenciaProd - ? "
                        + " where idProd in (select idProd from productosdemenu "
                        + " where idmenu=?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setDouble(1, ordDet.getCantidad());
                ps.setInt(2, ordDet.getIdMenu());
                ps.executeUpdate();
            }
            //Borrar lo que esta en la tabla OrdenesTemporal
            //Seleccionar el tipo de venta, Total(1) o Individual(2)
            if (tipo == 1) {
                cadenaSql = "delete from ordenestemporal where idMesa=?";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, orden.getIdMesa());
            }
            if (tipo == 2) {
                //Obtener lista sin duplicados de los clientes a borrar de la tabla temporal
                List<Integer> listaSinDuplicados = lstOrdDetalle.stream()
                        .map(item -> item.getNoClientes())
                        .distinct()
                        .collect(Collectors.toList());
                String cadClientes = "";
                int total=1;
                for (Integer cliente : listaSinDuplicados) {
                    //Crear cadena de clientes
                    if(total==1){
                        cadClientes = "" + cliente;
                    }else{
                        cadClientes += "," + cliente;
                    }
                    total++;
                }
              //  System.out.println("" + cadClientes);
                cadenaSql = "delete from ordenestemporal where idMesa=? and noCliente IN (" + cadClientes + ");";
              //  System.out.println("" + cadenaSql);
                //int numCliente = lstOrdDetalle.get(0).getNoClientes();
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, orden.getIdMesa());
                //ps.setInt(2, numCliente);
            }

            ps.execute();
            //Si no hay error, Confirmar cambios en la BD
            cnn.commit();
            return true;
        } catch (SQLException e) {
            if (cnn != null) {
                System.out.println("Rollback");
                System.out.println(e.getMessage());
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

    public boolean inOrdenParcial(Orden orden, ObservableList<OrdenDetalle> lstOrdDetalle, int tipo, String numCliente) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            //se deshabilita el modo de confirmación automática
            //Permite hacer varias operaciones y si no existe error, 
            //se confirma bloque. Si existe error en alguna, se anula todo.
            cnn.setAutoCommit(false);
            cadenaSql = "insert into ordenes values(null,?,?,?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, orden.getIdMesa());
            ps.setInt(2, orden.getIdCliente());
            ps.setInt(3, orden.getIdPersonal());
            ps.setInt(4, orden.getCantClientes());
            ps.setDouble(5, orden.getImporteTotal());
            ps.setObject(6, orden.getFecha());
            ps.execute();
            //Conocer el valor del auto_increment para usarlo en detalle
            cadenaSql = "select max(idOrdenes) as folio from ordenes;";
            ps = cnn.prepareStatement(cadenaSql);
            ResultSet rs = ps.executeQuery();
            int idOrden;
            if (rs.next()) {
                idOrden = rs.getInt(1);
            } else {
                idOrden = 1;
            }

            for (OrdenDetalle ordDet : lstOrdDetalle) {
                cadenaSql = "insert into det_orden values(?,?,?,?,?,?,?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, idOrden);
                ps.setInt(2, ordDet.getNoClientes());
                ps.setDouble(3, ordDet.getIdMenu());
                ps.setDouble(4, ordDet.getCantidad());
                ps.setDouble(5, ordDet.getPrecio());
                ps.setDouble(6, ordDet.getImporte());
                ps.setInt(7, ordDet.getIdTurno());
                ps.setString(8, ordDet.getObservaciones());
                ps.execute();
                //Update para modificar las existencias
                cadenaSql = "update productos set existenciaProd = existenciaProd - ? "
                        + " where idProd in (select idProd from productosdemenu "
                        + " where idmenu=?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setDouble(1, ordDet.getCantidad());
                ps.setInt(2, ordDet.getIdMenu());
                ps.executeUpdate();
            }
            //Borrar lo que esta en la tabla OrdenesTemporal
            //Seleccionar el tipo de venta, Total(1) o Individual(2)
            if (tipo == 1) {
                cadenaSql = "delete from ordenestemporal where idMesa=?";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, orden.getIdMesa());
            }
            if (tipo == 2) {
                cadenaSql = "delete from ordenestemporal where idMesa=? and noCliente IN (" + numCliente + ");";
                //  int numCliente = lstOrdDetalle.get(0).getNoClientes();
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, orden.getIdMesa());
                //ps.setInt(2, numCliente);
            }

            ps.execute();
            //Si no hay error, Confirmar cambios en la BD
            cnn.commit();
            return true;
        } catch (SQLException e) {
            if (cnn != null) {
                System.out.println("Rollback");
                System.out.println(e.getMessage());
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

    public ObservableList<Orden> getAllOrdenes() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Orden> lstOrdenes = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select * from ordenes;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstOrdenes.add(new Orden(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getTimestamp(7).toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de Ordenes");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstOrdenes;
    }

    public int getLastFolio() {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        int folio = 0;
        try {
            cadenaSql = "SELECT max(idOrdenes) as folio FROM ordenes;";
            ps = cnn.prepareStatement(cadenaSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                folio = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar obtener el folio mayor");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return folio;
    }
}
