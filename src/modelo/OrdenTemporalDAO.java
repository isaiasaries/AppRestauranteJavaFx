package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class OrdenTemporalDAO {

    public boolean insertar(ObservableList<OrdenTemporal> lstOrdenTemp) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        LocalDateTime fecha = LocalDateTime.now();
        try {
            cnn.setAutoCommit(false);
            for (OrdenTemporal ordenTemp : lstOrdenTemp) {
                cadenaSql = "insert into ordenestemporal values(?,?,?,?,?,?,?,?,?,?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, ordenTemp.getIdMesa());
                ps.setInt(2, ordenTemp.getCantidadClientes());
                ps.setInt(3, ordenTemp.getNoCliente());
                ps.setInt(4, ordenTemp.getIdMenu());
                ps.setDouble(5, ordenTemp.getCantidad());
                ps.setDouble(6, ordenTemp.getImporte());
                ps.setInt(7, ordenTemp.getIdTurno());
                ps.setString(8, ordenTemp.getEntregado());
                ps.setString(9, ordenTemp.getObservaciones());
                ps.setObject(10, fecha);
                ps.setObject(11, fecha);
                // ps.setString (12,"00:00");
                ps.execute();
            }
            //Modificar el estado de la mesa Ocupado(O) o Libre(L)
            cadenaSql = "update mesa set estadoMesa='O' where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, lstOrdenTemp.get(0).getIdMesa());
            ps.execute();
            //Si no hay error, Confirmar cambios en la BD
            cnn.commit();
            return true;
        } catch (SQLException e) {
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

    public boolean insertar2(ObservableList<OrdenTemporal> lstOrdenTemp, int numMesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        LocalDateTime fecha = LocalDateTime.now();
        try {
            cnn.setAutoCommit(false);
            //Borrar la orden previa
            cadenaSql = "delete from ordenestemporal where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, numMesa);
            ps.execute();
            //Guardar la orden Actual con modificaciones
            for (OrdenTemporal ordenTemp : lstOrdenTemp) {
                cadenaSql = "insert into ordenestemporal values(?,?,?,?,?,?,?,?,?,?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, ordenTemp.getIdMesa());
                ps.setInt(2, ordenTemp.getCantidadClientes());
                ps.setInt(3, ordenTemp.getNoCliente());
                ps.setInt(4, ordenTemp.getIdMenu());
                ps.setDouble(5, ordenTemp.getCantidad());
                ps.setDouble(6, ordenTemp.getImporte());
                ps.setInt(7, ordenTemp.getIdTurno());
                ps.setString(8, ordenTemp.getEntregado());
                ps.setString(9, ordenTemp.getObservaciones());
                ps.setObject(10, fecha);
                ps.setObject(11, fecha);
                // ps.setString (12,"00:00");
                ps.execute();
            }
            //Si no hay error, Confirmar cambios en la BD
            cnn.commit();
            return true;
        } catch (SQLException e) {
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

    public boolean eliminar(OrdenTemporal ordenTemp) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from ordenestemporal where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, ordenTemp.getIdMesa());
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

    public boolean eliminarXMesa(int mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from ordenestemporal where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, mesa);
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

    public boolean upEntregado(OrdenTemporal ordenTemp) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            cadenaSql = "update ordenestemporal set entregado=?, fechaEntregado=?  "
                    + " where idMesa=? and noCliente=? and idMenu=?";
            // System.out.println("Consulta: " + cadenaSql);
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, ordenTemp.getEntregado());
            ps.setObject(2, currentTime);
            ps.setInt(3, ordenTemp.getIdMesa());
            ps.setInt(4, ordenTemp.getNoCliente());
            ps.setInt(5, ordenTemp.getIdMenu());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

    //Obtener Orden de la tabla temporal seleccioada por mesa
    //pasar parametro mesa
    public ObservableList<OrdenTemporal> getOrdenTemporalxMesa(int mesa) {
        Connection cnn = Conexion.getConexion();
        ObservableList<OrdenTemporal> lstOT = FXCollections.observableArrayList();
        PreparedStatement ps;

        try {
            ps = cnn.prepareStatement("select o.idMesa, o.cantidadClientes, o.noCLiente, o.idMenu,"
                    + " m.descripcionMenu, m.precioMenu, o.cantidad, o.importe, o.idTurno, t.turno,"
                    + " o.entregado, o.observaciones, TIMEDIFF(fechaEntregado,fechaSolicitado) as minutos \n"
                    + " from ordenestemporal o\n"
                    + " inner join menu m on o.idmenu = m.idMenu\n"
                    + " inner join turnos t on t.idTurno= o.idTurno\n"
                    + " where idMesa=?;");
            ps.setInt(1, mesa);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstOT.add(new OrdenTemporal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de ordenTemps");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstOT;
    }

    //Obtener Orden de la tabla temporal seleccioada por mesa y cliente
    //pasar parametro mesa y cliente
    public ObservableList<OrdenTemporal> getTempxMesaxCliente(int mesa, String noCliente) {
        Connection cnn = Conexion.getConexion();
        ObservableList<OrdenTemporal> lstOT = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadenaSql = "select o.idMesa, o.cantidadClientes, o.noCliente, o.idMenu,"
                + " m.descripcionMenu, m.precioMenu, o.cantidad, o.importe, o.idTurno, t.turno,"
                + " o.entregado, o.observaciones, TIMEDIFF(fechaEntregado,fechaSolicitado) as minutos \n"
                + " from ordenestemporal o\n"
                + " inner join menu m on o.idmenu = m.idMenu\n"
                + " inner join turnos t on t.idTurno= o.idTurno\n"
                + " where o.idMesa=? and o.noCliente IN (" + noCliente + ");";
        // System.out.println("" + cadenaSql);
        try {
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, mesa);
            // ps.setInt(2, noCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstOT.add(new OrdenTemporal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de ordenTemps");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstOT;
    }

    /* public ObservableList<OrdenTemporal> getTempxMesaxCliente(int mesa, int noCliente) {
        Connection cnn = Conexion.getConexion();
        ObservableList<OrdenTemporal> lstOT = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select o.idMesa, o.cantidadClientes, o.noCliente, o.idMenu,"
                    + " m.descripcionMenu, m.precioMenu, o.cantidad, o.importe, o.idTurno, t.turno,"
                    + " o.entregado, o.observaciones, TIMEDIFF(fechaEntregado,fechaSolicitado) as minutos \n"
                    + " from ordenestemporal o\n"
                    + " inner join menu m on o.idmenu = m.idMenu\n"
                    + " inner join turnos t on t.idTurno= o.idTurno\n"
                    + " where o.idMesa=? and o.noCliente=?;");
            ps.setInt(1, mesa);
            ps.setInt(2, noCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstOT.add(new OrdenTemporal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de ordenTemps");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstOT;
    }*/
    //Obtener Orden de la tabla temporal seleccioada por responsable de elaboracion
    //pasar parametro responsable
    public ObservableList<OrdenTemporal> getOrdenTemporalxResp(int responsable) {
        Connection cnn = Conexion.getConexion();
        ObservableList<OrdenTemporal> lstOT = FXCollections.observableArrayList();
        PreparedStatement ps;
        String cadAdicional = ";";
        String cadenaSql = "select o.idMesa, o.cantidadClientes, o.noCLiente, o.idMenu, "
                + " m.descripcionMenu, m.precioMenu, o.cantidad, o.importe, o.idTurno, t.turno,"
                + " o.entregado, o.observaciones, TIMEDIFF(fechaEntregado,fechaSolicitado) as minutos \n"
                + " from ordenestemporal o\n"
                + " inner join menu m on o.idmenu = m.idMenu\n"
                + " inner join turnos t on t.idTurno= o.idTurno\n"
                + " where m.idResponsable =?  and o.entregado IN ('N','No','Listo')";
        if (responsable == 1 || responsable == 2) {
            cadAdicional = " or m.idCategoriaMenu=17;";
        }
        try {

            ps = cnn.prepareStatement(cadenaSql + cadAdicional);
            ps.setInt(1, responsable);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstOT.add(new OrdenTemporal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13)));
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar llenar lista de ordenTemps");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstOT;
    }

    //Buscar mesa por id, para identificar si esta o no ocupada.
    public boolean buscar(int mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        ResultSet rs;
        try {
            cadenaSql = "select idMesa from ordenestemporal where idMesa=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, mesa);
            rs = ps.executeQuery();
            if (rs.next()) {
                //Mesa ocupada
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la mesa");
            return false;
        }
        return false;
    }

    //Buscar Cantidad de clientes por mesa ocupada.
    public int getClientesXMesa(int mesa) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        ResultSet rs;
        try {
            cadenaSql = "select cantidadClientes from ordenestemporal where idMesa = ?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, mesa);
            rs = ps.executeQuery();
            if (rs.next()) {
                //retprna Cantidad de clientes en mesa
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la mesa");
            return 0;
        }
        return 0;
    }

}
