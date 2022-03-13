package conectar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Conexión a la BD de Mysql Server
 *
 * @author Isaias Lagunes Pérez
 */
public class Conexion {
     private static Connection conexion;

    public static Connection getConexion() {
        conexion = null;
        try {
            InputStream in=Conexion.class.getResourceAsStream("/conectar/db.properties");
            Properties p = new Properties();
            p.load(in);
            String ruta_bd = (String) p.get("ruta_bd");
            String usuario = (String) p.get("usuario");
            String pass = (String) p.get("clave");
            conexion = DriverManager.getConnection(ruta_bd, usuario, pass);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
}
/*public void closeConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al intentar cerrar la conexion " + ex.getMessage());
            System.exit(1);
        }
}*/
