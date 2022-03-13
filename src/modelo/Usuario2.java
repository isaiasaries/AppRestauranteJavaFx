package modelo;

import java.util.Objects;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

/**
 *
 * @author isaia
 */
public class Usuario2 {

    private int idUsuario;
    private String nombreUsuario;
    private String usuario;
    private String passwordUsuario;
    private byte idNivel;
    private CheckBox cbVigente;
    private ImageView ivUsuario;

    public Usuario2() {
    }

    public Usuario2(int idUsuario, String nombreUsuario, String usuario, String passwordUsuario, byte idNivel, CheckBox cbVigente, ImageView ivUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.usuario = usuario;
        this.passwordUsuario = passwordUsuario;
        this.idNivel = idNivel;
        this.cbVigente = cbVigente;
        this.ivUsuario = ivUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public byte getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(byte idNivel) {
        this.idNivel = idNivel;
    }

    public CheckBox getCbVigente() {
        return cbVigente;
    }

    public void setCbVigente(CheckBox cbVigente) {
        this.cbVigente = cbVigente;
    }

    public ImageView getIvUsuario() {
        return ivUsuario;
    }

    public void setIvUsuario(ImageView ivUsuario) {
        this.ivUsuario = ivUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Usuario2 other = (Usuario2) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

}
