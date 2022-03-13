package modelo;

/**
 * Modelo de datos de Categoria
 * Las categorias son comida, bebida, postre, etc.
 * @author Isaias Lagunes Pérez
 */
public class Categoria {
    private byte idCat;
    private String descripcionCat;

    public Categoria() {
    }

    public Categoria(byte idCat, String descripcionCat) {
        this.idCat = idCat;
        this.descripcionCat = descripcionCat;
    }

    public byte getIdCat() {
        return idCat;
    }

    public void setIdCat(byte idCat) {
        this.idCat = idCat;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }
     @Override
    public String toString() {
        return this.descripcionCat;
    }
}
