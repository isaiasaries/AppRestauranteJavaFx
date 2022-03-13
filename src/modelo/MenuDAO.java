package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class MenuDAO {

    public boolean insertar(Menu menu) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        boolean vig = false;
        try {
            cadenaSql = "insert into menu values(null,?,?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, menu.getDescripcionMenu());
            ps.setByte(2, menu.getIdCat());
            ps.setDouble(3, menu.getPrecioMenu());
            ps.setInt(4, menu.getIdResponsable());
            if (menu.getVigenteMenu().isSelected()) {
                vig = true;
            }
            ps.setBoolean(5, vig);
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

    //Insertar en tabla productosdemenu
    public boolean inPDM(ObservableList<Menu> lstMenu, ObservableList<Producto> lstProducto) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            //se deshabilita el modo de confirmación automática
            cnn.setAutoCommit(false);
            cadenaSql = "delete from productosdemenu where idMenu=?;";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, lstMenu.get(0).getIdMenu());
                ps.execute();
            
            for (Producto producto : lstProducto) {
                cadenaSql = "insert into productosdemenu values(?,?);";
                ps = cnn.prepareStatement(cadenaSql);
                ps.setInt(1, lstMenu.get(0).getIdMenu());
                ps.setInt(2, producto.getIdProd());
                ps.execute();
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
                System.err.println("Error al cerrar la conexion " + ex.getMessage());
            }
        }
    }

    public boolean eliminar(Menu menu) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from menu where idMenu=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, menu.getIdMenu());
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

    public boolean actualizar(Menu menu) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        boolean vig = false;
        try {
            cadenaSql = "update menu set descripcionMenu=?, "
                    + " idCategoriaMenu=?, precioMenu=?, idResponsable=?, "
                    + " vigenteMenu=? where idmenu=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, menu.getDescripcionMenu());
            ps.setByte(2, menu.getIdCat());
            ps.setDouble(3, menu.getPrecioMenu());
            ps.setInt(4, menu.getIdResponsable());
            if (menu.getVigenteMenu().isSelected()) {
                vig = true;
            }
            ps.setBoolean(5, vig);
            ps.setInt(6, menu.getIdMenu());
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

    public ObservableList<Menu> getAllMenu() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Menu> lstMenu = FXCollections.observableArrayList();
        PreparedStatement ps;
        CheckBox check;
        try {
            ps = cnn.prepareStatement("select m.idMenu, m.descripcionMenu, m.idCategoriaMenu, "
                    + " c.descripcionCategorias, m.precioMenu, m.idResponsable, r.nombreResponsable, "
                    + " m.vigenteMenu from menu m "
                    + " inner join categorias c ON m.idCategoriaMenu = c.idCategorias"
                    + " inner join responsables r ON m.idResponsable = r.idResponsable"
                    + " order by m.idMenu");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                check = new CheckBox();
                check.setSelected(false);
                if (rs.getBoolean(8)) {
                    check.setSelected(true);
                }
                lstMenu.add(new Menu(rs.getInt(1), rs.getString(2),
                        rs.getByte(3), rs.getString(4), rs.getDouble(5),
                        rs.getInt(6), rs.getString(7), check));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de menú");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstMenu;
    }

    public ObservableList<Producto> getMenuXId(int clave) {
        Connection cnn = Conexion.getConexion();
        ObservableList<Producto> lstProducto = FXCollections.observableArrayList();
        String cadenaSql;
        PreparedStatement ps;
        try {
            cadenaSql="select productos.* from menu, productos, productosdemenu "
                    + " where productosdemenu.idProd = productos.idProd "
                    + " and menu.idmenu= productosdemenu.idmenu "
                    + " and menu.idmenu=?;";
            ps = cnn.prepareStatement(cadenaSql);
          //  System.out.println("sql :" + cadenaSql);
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstProducto.add(new Producto(rs.getInt(1), rs.getString(2), rs.getInt(3), 
                        rs.getDouble(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de menú vigente");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstProducto;
    }

    /* public ObservableList<Menu> getAllMenuXCategoria(byte categoria) {
        Connection cnn = Conexion.getConexion();
        ObservableList<Menu> lstMenu = FXCollections.observableArrayList();
        PreparedStatement ps;
        CheckBox check;
        try {
            ps = cnn.prepareStatement("select m.idMenu, m.descripcionMenu, m.idCategoriaMenu, "
                    + " c.descripcionCategorias, m.precioMenu, m.idResponsable, r.nombreResponsable, "
                    + " m.vigenteMenu, m.existencias from menu m "
                    + " inner join categorias c ON m.idCategoriaMenu = c.idCategorias  and m.idCategoriaMenu=?"
                    + " inner join responsables r ON m.idResponsable = r.idResponsable"
                    + " where m.vigenteMenu=true");
            ps.setByte(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                check = new CheckBox();
                check.setSelected(false);
                if (rs.getBoolean(8)) {
                    check.setSelected(true);
                }
                lstMenu.add(new Menu(rs.getInt(1), rs.getString(2),
                        rs.getByte(3), rs.getString(4), rs.getDouble(5), 
                        rs.getInt(6), rs.getString(7), check, rs.getInt(9)));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de menú");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstMenu;
    }*/
    public ObservableList<Menu> getAllMenuVig() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Menu> lstMenu = FXCollections.observableArrayList();
        PreparedStatement ps;
        CheckBox check;
        try {
            ps = cnn.prepareStatement("select m.idMenu, m.descripcionMenu, m.idCategoriaMenu, "
                    + " c.descripcionCategorias, m.precioMenu, m.idResponsable, r.nombreResponsable, "
                    + " m.vigenteMenu from menu m "
                    + " inner join categorias c ON m.idCategoriaMenu = c.idCategorias "
                    + " inner join responsables r ON m.idResponsable = r.idResponsable"
                    + " where m.vigenteMenu=true order by descripcionMenu");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                check = new CheckBox();
                check.setSelected(false);
                if (rs.getBoolean(8)) {
                    check.setSelected(true);
                }
                lstMenu.add(new Menu(rs.getInt(1), rs.getString(2),
                        rs.getByte(3), rs.getString(4), rs.getDouble(5),
                        rs.getInt(6), rs.getString(7), check));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de menú vigente");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstMenu;
    }
}
