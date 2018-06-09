package com.example.bvarg.ubicacion;

public class Contactos {

    String Nombre;
    String Telefono;

    public Contactos() {
    }

    public Contactos(String nombre, String telefono) {
        Nombre = nombre;
        Telefono = telefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
