package modelo;

import conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Acceso a Datos de Categoria
 *
 * @author Isaias Lagunes Pérez
 */
public class CategoriaDAO {

    public boolean insertar(Categoria categoria) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "insert into categorias values(null,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, categoria.getDescripcionCat());
            ps.execute();
            System.out.println("Categoria Guardada Exitosamente!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar:" + e.getMessage());
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

    public boolean eliminar(Categoria categoria) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "delete from categorias where idCategorias=?;";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, categoria.getIdCat());
            ps.execute();
            System.out.println("Categoria Eliminada Exitosamente!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar:" + e.getMessage());
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

    public boolean actualizar(Categoria categoria) {
        Connection cnn = Conexion.getConexion();
        PreparedStatement ps;
        String cadenaSql;
        try {
            cadenaSql = "update categorias set descripcionCategorias=? "
                    + " where idCategorias=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, categoria.getDescripcionCat());
            ps.setInt(2, categoria.getIdCat());
            ps.executeUpdate();
            System.out.println("Se han actualizado correctamente los datos del Servicio");
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

    public ObservableList<Categoria> getAllCategorias() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Categoria> lstCategoria = FXCollections.observableArrayList();
        PreparedStatement ps;
        try {
            ps = cnn.prepareStatement("select * from categorias;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lstCategoria.add(new Categoria(rs.getByte(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de categoria");
        } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return lstCategoria;
    }

}
