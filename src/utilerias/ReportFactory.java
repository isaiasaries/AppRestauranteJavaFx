
package utilerias;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.swing.WindowConstants;
import modelo.Validaciones;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author isaia
 */
public class ReportFactory {
    public void crearReporte(Connection cnn, String ruta){
        try {
            final JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(ruta));
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, null, cnn);
            JasperViewer jviewer = new JasperViewer(jp, false);
            jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);
        } catch (JRException e) {
            Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
        }
    }
    //Crear reporte .Jasper ya compilado
    public void crearReporteConParametros(Connection cnn, String ruta, Map parametro){
         try {
                    InputStream inputStream = getClass().getResourceAsStream(ruta);
                    JasperPrint jp = JasperFillManager.fillReport(inputStream, parametro, cnn);
                    JasperViewer jviewer = new JasperViewer(jp, false);
                    jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    jviewer.setVisible(true);
                } catch (JRException e) {
                    Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
                }
        
          /*  try {
                final JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(ruta));
                JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametro, cnn);
                JasperViewer jviewer = new JasperViewer(jp, false);
                jviewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jviewer.setVisible(true);
            } catch (JRException e) {
                Validaciones.alertError("Reportes", "Error al abrir el reporte" + e.getMessage());
            }*/
    }
}
