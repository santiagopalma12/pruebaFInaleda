package src.smarthire.structures;

import java.util.ArrayList;

/**
 * Clase MiTablaHash.java
 * Impleementada desde 0 y utilicé las linkedlist para manejar cuando dos claves caen en el mismo lugar.
 * 
 * @param <K> El tipo de las clave
 * @param <V> El tipo de los valor
 * @author Santiago Palma
 */
public class MiTablaHash<K, V> {

    private MiListaEnlazada<K, V>[] tabla;
    private final int capacidad;

    /**
     * Crea una tabla hash con una cantidad de espacios, indicados previamente, cada espacio empieza con una lista enlazada.
     * @param capacidadInicial Cuántos espacios va a tener la tabla.
     */
    
    public MiTablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial; 
        this.tabla = (MiListaEnlazada<K, V>[]) new MiListaEnlazada[capacidad];
        
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new MiListaEnlazada<>();
        }
    }

    /**
     * Crea una tabla hash de 16 espacios
     */
    public MiTablaHash() {
        this(16); 
    }
    
    /**
     * Calcula en cuál espacio del arreglo tiene que ir la clave.
     * @param clave La clave a ubicar.
     * @return Dónde deberia ir esta clave.
     */
    private int getIndice(K clave) {
        int hashCode = clave.hashCode();
        
        return Math.abs(hashCode) % capacidad;
    }

    /**
     * Agrega un nuevo dato a la tabla usando una clave.
     * 
     * Si la clave ya está en la tabla, se reemplaza su valor con el nuevo.
     * Si la clave no existe, se guarda todo como una nueva entrada 
     * 
     * @param clave La clave 
     * @param valor El valor 
     */

    public void put(K clave, V valor) {       
        int indice = getIndice(clave);
        MiListaEnlazada<K, V> listaBucket = tabla[indice];
        
        NodoSimple<K, V> nodoExistente = listaBucket.buscar(clave);
        
        if (nodoExistente != null) {
            nodoExistente.setValor(valor);
        } else {
            listaBucket.agregar(clave, valor);
        }
    }

    /**
     * Busca un valor usando su clave.
     * 
     * @param clave La clave .
     * @return El valor guardado con esa clave, o null .
     */

    public V get(K clave) {
        int indice = getIndice(clave);
        MiListaEnlazada<K, V> listaBucket = tabla[indice];
        
        NodoSimple<K, V> nodo = listaBucket.buscar(clave);
        
        return (nodo != null) ? nodo.getValor() : null;
    }

    /**
     * Elimina un elemento de la tabla usando su clave.
     * 
     * @param clave La clave.
     */
    public void remove(K clave) {
        int indice = getIndice(clave);
        MiListaEnlazada<K, V> listaBucket = tabla[indice];
        
        listaBucket.eliminar(clave);
    }

    /**
     * Devuelve una lista con todas las claves guardadas en la tabla.
     * Recorre cada espacio de la tabla y recoge las claves.
     * @return Una lista con todas las claves !
     */
    public ArrayList<K> keySet() {
        ArrayList<K> todasLasClaves = new ArrayList<>();

        for (int i = 0; i < capacidad; i++) {
            todasLasClaves.addAll(tabla[i].obtenerClaves());
        }

        return todasLasClaves;
    }
}
