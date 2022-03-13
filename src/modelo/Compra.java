package modelo;

import java.time.LocalDate;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Compra {
    private int idCompras;
    private int idProveedor;
    private String nombreProveedor;
    private double importeTotal;
    private LocalDate fechaCompra;

    public Compra() {
    }

    public Compra(int idCompras, int idProveedor, String nombreProveedor, double importeTotal, LocalDate fechaCompra) {
        this.idCompras = idCompras;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.importeTotal = importeTotal;
        this.fechaCompra = fechaCompra;
    }

    public int getIdCompras() {
        return idCompras;
    }

    public void setIdCompras(int idCompras) {
        this.idCompras = idCompras;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
