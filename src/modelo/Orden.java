package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Orden {
    private int idOrdenes;
    private int idMesa;
    private int idCliente;
    private int idPersonal;
    private int cantClientes;
    private double importeTotal;
    private LocalDateTime fecha;

    public Orden() {
    }

    public Orden(int idOrdenes, int idMesa, int idCliente, int idPersonal, int cantClientes, double importeTotal, LocalDateTime fecha) {
        this.idOrdenes = idOrdenes;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.idPersonal = idPersonal;
        this.cantClientes = cantClientes;
        this.importeTotal = importeTotal;
        this.fecha = fecha;
    }

    public int getIdOrdenes() {
        return idOrdenes;
    }

    public void setIdOrdenes(int idOrdenes) {
        this.idOrdenes = idOrdenes;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public int getCantClientes() {
        return cantClientes;
    }

    public void setCantClientes(int cantClientes) {
        this.cantClientes = cantClientes;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
