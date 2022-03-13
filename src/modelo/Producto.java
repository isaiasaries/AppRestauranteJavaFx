package modelo;

import java.util.Objects;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Producto {
    private int idProd;
    private String descripcionProd;
    private int existenciaProd;
    private double precioProd;
    private int stockMin;

    public Producto() {
    }

    public Producto(int idProd, String descripcionProd, int existenciaProd, double precioProd, int stockMin) {
        this.idProd = idProd;
        this.descripcionProd = descripcionProd;
        this.existenciaProd = existenciaProd;
        this.precioProd = precioProd;
        this.stockMin = stockMin;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getDescripcionProd() {
        return descripcionProd;
    }

    public void setDescripcionProd(String descripcionProd) {
        this.descripcionProd = descripcionProd;
    }

    public int getExistenciaProd() {
        return existenciaProd;
    }

    public void setExistenciaProd(int existenciaProd) {
        this.existenciaProd = existenciaProd;
    }

    public double getPrecioProd() {
        return precioProd;
    }

    public void setPrecioProd(double precioProd) {
        this.precioProd = precioProd;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    @Override
    public String toString() {
        return this.descripcionProd;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.descripcionProd, other.descripcionProd)) {
            return false;
        }
        return true;
    }
}
