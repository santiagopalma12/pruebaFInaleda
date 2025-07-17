package smarthire.structures;

import java.util.ArrayList;

public class PilaEmpleados<T> {
    private ArrayList<T> elementos;

    public PilaEmpleados() {
        elementos = new ArrayList<>();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    public void apilar(T elemento) {
        elementos.add(elemento); 
    }

    public T desapilar() {
        if (estaVacia()) {
            System.out.println("La pila está vacía...");
            return null;
        }
        return elementos.remove(elementos.size() - 1);
    }

    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return elementos.get(elementos.size() - 1);
    }

    public ArrayList<T> getElementosEnOrden() {
        ArrayList<T> enOrden = new ArrayList<>();
        for (int i = elementos.size() - 1; i >= 0; i--) {
            enOrden.add(elementos.get(i));
        }
        return enOrden;
    }


    public void mostrarPila() {
        if (estaVacia()) {
            System.out.println("La pila está vacía.");
        } else {
            System.out.println("Elementos en la pila (último al primero):");
            for (int i = elementos.size() - 1; i >= 0; i--) {
                System.out.println(elementos.get(i));
            }
        }
    }
}
