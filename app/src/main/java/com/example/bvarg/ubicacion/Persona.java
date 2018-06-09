package com.example.bvarg.ubicacion;

public class Persona {
    String Nombre;
    String Apellido;
    String Id;
    String Foto;

    public Persona(String nombre, String apellido, String id, String foto) {
        Nombre = nombre;
        Apellido = apellido;
        Id = id;
        Foto = foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
