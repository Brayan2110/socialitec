package com.example.bvarg.ubicacion;

public class HorariosLugares {

    String Nombre;
    String Horario;
    int Imagen;

    public HorariosLugares() {
    }

    public HorariosLugares(String nombre, String horario, int imagen) {
        Nombre = nombre;
        Horario = horario;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
