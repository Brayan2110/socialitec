package com.example.bvarg.ubicacion;

import android.widget.ImageView;

public class Publicacion {
    String Foto;
    String Nombre;
    String Mensaje;
    String Fecha;
    int Imagen;

    public Publicacion(String foto, String nombre, String mensaje, String fecha, int imagen) {
        Foto = foto;
        Nombre = nombre;
        Mensaje = mensaje;
        Fecha = fecha;
        Imagen = imagen;
    }

    public Publicacion() {
    }

    public Publicacion(String foto, String nombre, String mensaje, String fecha) {
        Foto = foto;
        Nombre = nombre;
        Mensaje = mensaje;
        Fecha = fecha;
        Imagen = 0;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
