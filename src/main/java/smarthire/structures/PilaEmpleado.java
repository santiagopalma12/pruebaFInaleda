package smarthire.structures;

import smarthire.model.Empleado;
import java.util.ArrayList;

public class PilaEmpleado {
    private ArrayList<Empleado> pila;

    public PilaEmpleado() {
        this.pila = new ArrayList<>();
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public void apilar(Empleado e) {
        pila.add(e); // O(1) amortizado
    }

    public Empleado desapilar() {
        if (estaVacia()) {
            System.out.println("La pila está vacía...");
            return null;
        }
        return pila.remove(pila.size() - 1);
    }

    public Empleado verTope() {
        if (estaVacia()) {
            System.out.println("La pila está vacía...");
            return null;
        }
        return pila.get(pila.size() - 1);
    }

    public void mostrarPila() {
        if (estaVacia()) {
            System.out.println("No hay empleados en la pila...");
        } else {
            System.out.println("Empleados en la pila desde el último hasta el primero:");
            for (int i = pila.size() - 1; i >= 0; i--) {
                System.out.println(pila.get(i));
            }
        }
    }

    public int tamaño() {
        return pila.size();
    }

    public ArrayList<Empleado> obtenerCopia() {
        return new ArrayList<>(pila); // Para mostrar en tabla si deseas más adelante
    }
}
