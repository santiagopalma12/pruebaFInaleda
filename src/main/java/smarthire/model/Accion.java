package smarthire.model;

public class Accion {
    private String tipo; 
    private Object dato; 

    public Accion(String tipo, Object dato) {
        this.tipo = tipo;
        this.dato = dato;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getDato() {
        return dato;
    }
}
