package smarthire.structures;
import smarthire.model.Empleado;

public class NodoEmpleado {
    Empleado empleado;
    NodoEmpleado izquierdo;
    NodoEmpleado derecho;

    public NodoEmpleado(Empleado empleado) {
        this.empleado = empleado;
        this.izquierdo = null;
        this.derecho = null;
    }
}