package src.smarthire.structures;
import src.smarthire.model.Empleado;

public class PilaEmpleado {
    private Empleado[] pila;
    private int tope;
    private int capacidad;

    public PilaEmpleado(int capacidad) {
        this.capacidad = capacidad;
        pila = new Empleado[capacidad];
        this.tope = -1;
    }

    public boolean estaVacia() {
        return tope == -1;
    }

    public boolean estaLlena() {
        return tope == capacidad - 1;
    }

    public boolean apilar(Empleado e) {
        if(estaLlena()) {
            System.out.println("La pila está llena... ");
            return false;
        }
        pila[++tope] = e;
        return false;
    }

    public Empleado desapilar() {
        if(estaVacia()) {
            System.out.println("La pila está vacia... ");
            return null;
        }
        return pila[tope--];
    }

    public void mostrarPila() {
        if(estaVacia()) {
            System.out.println("No hay empleados en la pila... ");
        } else {
            System.out.println("Empleados en la pila desde el último hasta el primero: ");
            for(int i = tope; i >= 0; i-- ) {
                System.out.println(pila[i]);
            }
        }
    }

    public Empleado verTope() {
        if(estaVacia()) {
            System.out.println("La pila está vacia... ");
            return null;
        }
        return pila[tope];
    }
}