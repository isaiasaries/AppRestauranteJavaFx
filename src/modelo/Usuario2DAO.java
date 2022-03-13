package modelo;

import conectar.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Usuario2DAO {

    private PreparedStatement ps;
    private String cadenaSql;

    public boolean insertar(String nombre, String usr, String pass, byte nivel, byte vigente, String ruta) {
        Connection cnn = Conexion.getConexion();
        try {
            File file = new File(ruta);
            FileInputStream fis = new FileInputStream(file);
            cadenaSql = "insert into usuario (nombreUsuario, usuario, passwordUsuario, idNivel, vigenteUsuario, imagenUsuario) values(?,?,?,?,?,?);";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, nombre);
            ps.setString(2, usr);
            ps.setString(3, pass);
            ps.setByte(4, nivel);
            ps.setByte(5, vigente);
            ps.setBlob(6, fis, (int) file.length());
            ps.execute();
            System.out.println("Usuario Guardado Exitosamente!");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar:" + e.getMessage());
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Usuario2DAO.class.getName()).log(Level.SEVERE, null, ex);
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

    /* public boolean eliminar(int id) {
        try {
            cadenaSql = "delete from usuario where idUsuario=?;";
            ps = conexion.prepareStatement(cadenaSql);
            ps.setInt(1, id);
            ps.execute();
            System.out.println("Se han eliminado los datos del usuario");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar:" + e.getMessage());
            return false;
        }
    }*/
    public boolean modificar(int id, String nombre, String usr, String pass, byte nivel, byte vigente, String ruta) {
        Connection cnn = Conexion.getConexion();
        try {
            File file = new File(ruta);
            FileInputStream fis = new FileInputStream(file);
            cadenaSql = "update usuario set nombreUsuario=?,"
                    + "usuario=?, passwordUsuario=?, idNivel=?, vigenteUsuario=?, "
                    + " imagenUsuario = ? where idUsuario=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, nombre);
            ps.setString(2, usr);
            ps.setString(3, pass);
            ps.setByte(4, nivel);
            ps.setByte(5, vigente);
            ps.setBlob(6, fis, (int) file.length());
            ps.setInt(7, id);
            ps.executeUpdate();
            System.out.println("Se han actualizado los datos del usuario");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar:" + e.getMessage());
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Usuario2DAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean eliminar(int id) {
        Connection cnn = Conexion.getConexion();
        try {
            cadenaSql = "delete from usuario  where idUsuario=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Se han eliminado los datos del usuario");
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

    public boolean modificarSinImagen(int id, String nombre, String usr, String pass, byte nivel, byte vigente) {
        Connection cnn = Conexion.getConexion();;
        try {
            cadenaSql = "update usuario set nombreUsuario=?,"
                    + "usuario=?, passwordUsuario=?, idNivel=?, vigenteUsuario=? "
                    + " where idUsuario=?";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, nombre);
            ps.setString(2, usr);
            ps.setString(3, pass);
            ps.setByte(4, nivel);
            ps.setByte(5, vigente);
            // ps.setBlob(6, fis, (int) file.length());
            ps.setInt(6, id);
            ps.executeUpdate();
            System.out.println("Se han actualizado los datos del usuario");
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

    public ObservableList<Usuario2> getUsuarios() {
        Connection cnn = Conexion.getConexion();
        ObservableList<Usuario2> listaUsuarios = FXCollections.observableArrayList();
        try {
            ps = cnn.prepareStatement("select * from usuario;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //Obtener boolean y asignarlo a un checkbox
                CheckBox cb = new CheckBox();
                cb.setSelected(rs.getBoolean(6));
                ImageView iv = new ImageView();
                if(rs.getBinaryStream(7) != null){
                    InputStream binaryStream = rs.getBinaryStream(7);
                    Image imagen = new Image(binaryStream);
                    iv.setImage(imagen);
                    iv.setFitWidth(60);
                    iv.setPreserveRatio(true);
                }
                listaUsuarios.add(new Usuario2(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), cb, iv));
            }
        } catch (SQLException e) {
            System.out.println("No llena lista de usuarios");
       } finally {
            try {
                if (cnn != null) {
                    cnn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al intentar cerrar la conexion " + ex.getMessage());
            }
        }
        return listaUsuarios;
    }

    //Metodo para el Login
    //Busca un usuario segun su nombre de usuario y password
    public Usuario2 login(String usuario, String pass, short nivel) {
        Connection cnn = Conexion.getConexion();
        ResultSet rs;
        Usuario2 usr = null;
        try {
            cadenaSql = "Select * from usuario "
                    + "where usuario=? and  passwordUsuario=? and idNivel=? and vigenteUsuario = 1";
            ps = cnn.prepareStatement(cadenaSql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            ps.setShort(3, nivel);
            rs = ps.executeQuery();
            if (rs.next()) {
                //Obtener boolean y asignarlo a un checkbox
                CheckBox cb = new CheckBox();
                cb.setSelected(rs.getBoolean(6));
                ImageView iv = new ImageView();
                if(rs.getBinaryStream(7) != null){
                    InputStream binaryStream = rs.getBinaryStream(7);
                    Image imagen = new Image(binaryStream);
                    iv.setImage(imagen);
                    iv.setFitWidth(60);
                    iv.setPreserveRatio(true);
                    usr = new Usuario2(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), cb, iv);
                }else{
                    usr = new Usuario2(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), cb, iv);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return usr;
    }
}
