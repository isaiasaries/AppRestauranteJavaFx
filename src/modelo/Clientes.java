package modelo;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Clientes {
    private int idClientes;
    private String nombreClientes;
    private String datosClientes;

    public Clientes() {
    }

    public Clientes(int idClientes, String nombreClientes, String datosClientes) {
        this.idClientes = idClientes;
        this.nombreClientes = nombreClientes;
        this.datosClientes = datosClientes;
    }

    public int getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(int idClientes) {
        this.idClientes = idClientes;
    }

    public String getNombreClientes() {
        return nombreClientes;
    }

    public void setNombreClientes(String nombreClientes) {
        this.nombreClientes = nombreClientes;
    }

    public String getDatosClientes() {
        return datosClientes;
    }

    public void setDatosClientes(String datosClientes) {
        this.datosClientes = datosClientes;
    }

    @Override
    public String toString() {
        return this.nombreClientes;
    } 
}
