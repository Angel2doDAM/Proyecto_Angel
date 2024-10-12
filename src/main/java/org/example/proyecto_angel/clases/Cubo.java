package org.example.proyecto_angel.clases;

public class Cubo {

    private int ID;
    private String tipo;
    private String modelo;
    private double precio;

    public Cubo() {
    }

    public Cubo(String tipo, String modelo, double precio) {
        this.ID = ID;
        this.tipo = tipo;
        this.modelo = modelo;
        this.precio = precio;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}