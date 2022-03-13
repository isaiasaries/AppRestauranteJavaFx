
package modelo;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class OrdenTemporal {
    private int idMesa;
    private int cantidadClientes;
    private int noCliente;
    private int idMenu;
    private String descMenu;
    private double precio;
    private double cantidad;
    private double importe;
    private int idTurno;
    private String turno;
    private String entregado;
    private String observaciones;
    private String tiempo;

    public OrdenTemporal() {
    }

    public OrdenTemporal(int idMesa, int cantidadClientes, int noCliente, int idMenu, String descMenu, double precio, double cantidad, double importe, int idTurno, String turno, String entregado, String observaciones, String tiempo) {
        this.idMesa = idMesa;
        this.cantidadClientes = cantidadClientes;
        this.noCliente = noCliente;
        this.idMenu = idMenu;
        this.descMenu = descMenu;
        this.precio = precio;
        this.cantidad = cantidad;
        this.importe = importe;
        this.idTurno = idTurno;
        this.turno = turno;
        this.entregado = entregado;
        this.observaciones = observaciones;
        this.tiempo = tiempo;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(int cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public int getNoCliente() {
        return noCliente;
    }

    public void setNoCliente(int noCliente) {
        this.noCliente = noCliente;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getDescMenu() {
        return descMenu;
    }

    public void setDescMenu(String descMenu) {
        this.descMenu = descMenu;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
