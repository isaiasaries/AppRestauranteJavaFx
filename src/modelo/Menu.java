package modelo;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Isaias Lagunes Pérez
 */
public class Menu {
    private int idMenu;
    private String descripcionMenu;
    private byte idCat;
    private String descripcionCat;
    private double precioMenu;
    private int idResponsable;
    private String nombreResponsable;
    private CheckBox vigenteMenu;

    public Menu() {
    }
    @Override
    public String toString() {
        return this.descripcionMenu + " -> $" + precioMenu;
    }

    public Menu(int idMenu, String descripcionMenu, byte idCat, String descripcionCat, double precioMenu, int idResponsable, String nombreResponsable, CheckBox vigenteMenu) {
        this.idMenu = idMenu;
        this.descripcionMenu = descripcionMenu;
        this.idCat = idCat;
        this.descripcionCat = descripcionCat;
        this.precioMenu = precioMenu;
        this.idResponsable = idResponsable;
        this.nombreResponsable = nombreResponsable;
        this.vigenteMenu = vigenteMenu;
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

    public double getPrecioMenu() {
        return precioMenu;
    }

    public void setPrecioMenu(double precioMenu) {
        this.precioMenu = precioMenu;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public CheckBox getVigenteMenu() {
        return vigenteMenu;
    }

    public void setVigenteMenu(CheckBox vigenteMenu) {
        this.vigenteMenu = vigenteMenu;
    }   
}
