package modelo;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Mesa {
    private int idMesa;
    private String nombreMesa;
    private String estadoMesa;

    public Mesa() {
    }

    public Mesa(int idMesa, String nombreMesa, String estadoMesa) {
        this.idMesa = idMesa;
        this.nombreMesa = nombreMesa;
        this.estadoMesa = estadoMesa;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getNombreMesa() {
        return nombreMesa;
    }

    public void setNombreMesa(String nombreMesa) {
        this.nombreMesa = nombreMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    @Override
    public String toString() {
        return this.nombreMesa;
    }   
}
