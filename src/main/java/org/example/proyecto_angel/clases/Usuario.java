package org.example.proyecto_angel.clases;

//Clase para crear objetos usuario
public class Usuario {

    private int Id;
    private String nombre;
    private String contrasenia;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String contrasenia) {
        Id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

//    Getter y setter de todas las variable
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

//    Tostring para mostrar solo el nombre en la lista
    @Override
    public String toString() {
        return nombre;
    }
}
