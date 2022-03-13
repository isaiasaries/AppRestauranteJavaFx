
package modelo;

/**
 *
 * @author isaia
 */
public class Turno {
    private int idTurno;
    private String turno;

    public Turno() {
    }

    public Turno(int idTurno, String turno) {
        this.idTurno = idTurno;
        this.turno = turno;
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

    @Override
    public String toString() {
        return this.turno;
    }
    
    
}
