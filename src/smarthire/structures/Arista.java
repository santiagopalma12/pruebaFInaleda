package src.smarthire.structures;

import src.smarthire.model.Empleado;

/**
 * Santiago Enrique Palma Apaza: 
 * Clase Arista.java:
 * Tiene la función de representar una conexión  en el grafo de empleados y también contiene el empleado de destino, el peso de la relación y el tipo de esta.
 */
public class Arista {
    private final Empleado destino;
    private final double peso;
    private final String tipoRelacion;

    public Arista(Empleado destino, double peso, String tipoRelacion) {
        this.destino = destino;
        this.peso = peso;
        this.tipoRelacion = tipoRelacion;
    }

    public Empleado getDestino() { return destino; }
    public double getPeso() { return peso; }
    public String getTipoRelacion() { return tipoRelacion; }
}