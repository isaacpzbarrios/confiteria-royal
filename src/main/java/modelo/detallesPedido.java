/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaac
 */
public class detallesPedido {

    private List<String> menu = new ArrayList<String>();
    private List<String> menuDetalle = new ArrayList<String>();
    private List<String> menuCompleto = new ArrayList<String>();

    private float precioFull;
    private String nombre;
    private String componente;
    private String detalle;

    public float getPrecioFull() {
        return precioFull;
    }

    public void setPrecioFull(float precioFull) {
        this.precioFull = precioFull;
    }

    
    public List<String> getMenuCompleto() {
        return menuCompleto;
    }

    public void setMenuCompleto(List<String> menuCompleto) {
        this.menuCompleto = menuCompleto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public List<String> getMenuDetalle() {
        return menuDetalle;
    }

    public void setMenuDetalle(List<String> menuDetalle) {
        this.menuDetalle = menuDetalle;
    }

}
