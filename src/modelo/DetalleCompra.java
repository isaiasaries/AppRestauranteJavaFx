package modelo;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class DetalleCompra {
    private int idCompras;
    private int idProd;
    private String producto;
    private double cantidad;
    private double precio;
    private double importe;

    public DetalleCompra() {
    }

    public DetalleCompra(int idCompras, int idProd, String producto, double cantidad, double precio, double importe) {
        this.idCompras = idCompras;
        this.idProd = idProd;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
    }

    public int getIdCompras() {
        return idCompras;
    }

    public void setIdCompras(int idCompras) {
        this.idCompras = idCompras;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
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
    
}
