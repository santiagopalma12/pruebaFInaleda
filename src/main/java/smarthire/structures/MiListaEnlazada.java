package smarthire.structures;

import java.util.ArrayList; 
import java.util.List; 

/**
 * MiListaEnlazada.java
 * Implementación simple de una lista enlazada para manejar colisiones en la tabla hash.
 * @author Santiago Palma
 */
public class MiListaEnlazada<K, V> {
    private NodoSimple<K, V> cabeza;
    private int tamano;

    public MiListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    /**
     * Agrega un nuevo nodo al principio de la lista.
     * @param clave La clave del elemento.
     * @param valor El valor del elemento.
     */
    public void agregar(K clave, V valor) {
        NodoSimple<K, V> nuevoNodo = new NodoSimple<>(clave, valor);

        nuevoNodo.siguiente = cabeza;
        
        cabeza = nuevoNodo;
        
        tamano++;
    }
    
    /**
     * Busca un nodo viendo uno por uno desde el principio de la lista.
     * Es importante que la clave se pueda comparar bien con .equals().
     * 
     * @param clave La clave que queremos encontrar.
     * @return El nodo que tiene esa clave, o null si no está.
     */
    public NodoSimple<K, V> buscar(K clave) {
        NodoSimple<K, V> actual = cabeza;
        
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return actual; 
            }
            actual = actual.siguiente;
        }
        
        return null;
    }
    /**
     * Elimina un nodo de la lista usando su clave.
     * Tiene en cuenta si justo el que hay que borrar está al inicio .
     * 
     * @param clave La clave del nodo que queremos borrar.
     * @return true si lo encontró y lo borró; false si no estaba en la lista.
     */
    public boolean eliminar(K clave) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getClave().equals(clave)) {
            cabeza = cabeza.siguiente; 
            tamano--;
            return true;
        }

        NodoSimple<K, V> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.getClave().equals(clave)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamano--;
            return true;
        }

        return false; 
    }

    /**
     * Recorre la lista y junta todas las claves en un ArrayList.
     * Sirve, por ejemplo, para cuando se necesita el keySet() en la tabla hash.
     * 
     * @return Un ArrayList<K> con todas las claves que hay en la lista.
     */
    public List<K> obtenerClaves() {
        List<K> claves = new ArrayList<>();
        NodoSimple<K, V> actual = cabeza;
        
        while (actual != null) {
            claves.add(actual.getClave());
            actual = actual.siguiente;
        }
        
        return claves;
    }

}