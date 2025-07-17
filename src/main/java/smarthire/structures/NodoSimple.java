package smarthire.structures;

/**
 * NodoSimple.java:
 * Representa un nodo en una lista enlazada para la tabla hash, además almacena (clave,valor).
 * @author Santiago Palma
 */
public class NodoSimple<K, V> {
    final K clave;
    V valor;
    NodoSimple<K, V> siguiente;

    public NodoSimple(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }
    
    public void setValor(V valor) {
        this.valor = valor;
    }
}