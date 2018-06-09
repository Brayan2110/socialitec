package com.example.bvarg.ubicacion;

public class Mensaje {
    String Emisor;
    String Receptor;
    String Texto;

    public Mensaje() {
    }

    public Mensaje(String emisor, String receptor, String texto) {
        Emisor = emisor;
        Receptor = receptor;
        Texto = texto;
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String emisor) {
        Emisor = emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }
}
