
package modelo;

import java.util.Objects;

/**
 * Clase del Nivel de Acceso de los Usuarios
 * @author Isaias Lagunes Pérez
 */
public class Nivel {
    private byte idNivel;
    private String descripcionNivel;

    public Nivel(byte idNivel, String descripcionNivel) {
        this.idNivel = idNivel;
        this.descripcionNivel = descripcionNivel;
    }

    public Nivel() {
    }

      
   /*  @Override
    public String toString() {
        return this.idNivel + " - " + this.descripcionNivel;
    }*/
    @Override
    public String toString() {
        return this.descripcionNivel;
    }

    public byte getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(byte idNivel) {
        this.idNivel = idNivel;
    }

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Nivel other = (Nivel) obj;
        if (!Objects.equals(this.descripcionNivel, other.descripcionNivel)) {
            return false;
        }
        return true;
    }
    
}
