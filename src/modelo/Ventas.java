package modelo;

import java.time.LocalDate;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Ventas {
    private int idOrdenes;
    private int idMesa;
    private String rzCliente;
    private double importe;
    private LocalDate fecha;

    public Ventas() {
    }

    public Ventas(int idOrdenes, int idMesa, String rzCliente, double importe, LocalDate fecha) {
        this.idOrdenes = idOrdenes;
        this.idMesa = idMesa;
        this.rzCliente = rzCliente;
        this.importe = importe;
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

    public String getRzCliente() {
        return rzCliente;
    }

    public void setRzCliente(String rzCliente) {
        this.rzCliente = rzCliente;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}
