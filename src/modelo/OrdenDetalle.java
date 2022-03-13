package modelo;

/**
 *
 * @author isaia
 */
public class OrdenDetalle {
    private int idOrdenes;
    private int noClientes;
    private int idMenu;
    private double cantidad;
    private double precio;
    private double importe;
    private int idTurno;
    private String observaciones;

    public OrdenDetalle() {
    }

    public OrdenDetalle(int idOrdenes, int noClientes, int idMenu, double cantidad, double precio, double importe, int idTurno, String observaciones) {
        this.idOrdenes = idOrdenes;
        this.noClientes = noClientes;
        this.idMenu = idMenu;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
        this.idTurno = idTurno;
        this.observaciones = observaciones;
    }

    public int getIdOrdenes() {
        return idOrdenes;
    }

    public void setIdOrdenes(int idOrdenes) {
        this.idOrdenes = idOrdenes;
    }

    public int getNoClientes() {
        return noClientes;
    }

    public void setNoClientes(int noClientes) {
        this.noClientes = noClientes;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
