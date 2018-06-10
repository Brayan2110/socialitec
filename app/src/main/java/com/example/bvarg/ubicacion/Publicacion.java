package com.example.bvarg.ubicacion;

import android.widget.ImageView;

public class Publicacion {
    String Foto;
    String Nombre;
    String Mensaje;
    String Fecha;
    String Imagen;

    public Publicacion(String foto, String nombre, String mensaje, String fecha, String imagen) {
        Foto = foto;
        Nombre = nombre;
        Mensaje = mensaje;
        Fecha = fecha;
        Imagen = imagen;
    }

    public Publicacion() {
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

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
