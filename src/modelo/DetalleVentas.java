package modelo;

import java.time.LocalDate;

/**
 *
 * @author isaia
 */
public class DetalleVentas {
    private int idOrdenes;
    private int noClientes;
    private double cantidad;
    private int idMenu;
    private String descripcionMenu;
    private double precioMenu;
    private double importe;
    private int idTurno;
    private String observaciones;

    public DetalleVentas() {
    }

    public DetalleVentas(int idOrdenes, int noClientes, double cantidad, int idMenu, String descripcionMenu, double precioMenu, double importe, int idTurno, String observaciones) {
        this.idOrdenes = idOrdenes;
        this.noClientes = noClientes;
        this.cantidad = cantidad;
        this.idMenu = idMenu;
        this.descripcionMenu = descripcionMenu;
        this.precioMenu = precioMenu;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getDescripcionMenu() {
        return descripcionMenu;
    }

    public void setDescripcionMenu(String descripcionMenu) {
        this.descripcionMenu = descripcionMenu;
    }

    public double getPrecioMenu() {
        return precioMenu;
    }

    public void setPrecioMenu(double precioMenu) {
        this.precioMenu = precioMenu;
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
