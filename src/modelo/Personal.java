
package modelo;

/**
 *
 * @author isaia
 */
public class Personal {
    private int idPersonal;
    private String nombrePersonal;

    public Personal() {
    }

    public Personal(int idPersonal, String nombrePersonal) {
        this.idPersonal = idPersonal;
        this.nombrePersonal = nombrePersonal;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    @Override
    public String toString() {
        return this.nombrePersonal;
    }
    
}
