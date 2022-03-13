package modelo;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Responsable {
    private int idResponsable;
    private String nombreResponsable;
    
    public Responsable() {
    }

    public Responsable(int idResponsable, String nombreResponsable) {
        this.idResponsable = idResponsable;
        this.nombreResponsable = nombreResponsable;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }
    
    @Override
    public String toString() {
        return this.nombreResponsable;
    }   
}
