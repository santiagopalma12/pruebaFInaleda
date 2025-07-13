package src.smarthire.structures;
import src.smarthire.model.Empleado;

public class ArbolEmpleados {
    private NodoEmpleado raiz;

    /**
     * Constructor que inicializa el árbol vacío.
     */
    public ArbolEmpleados() {
        this.raiz = null;
    }

    /**
     * Inserta un empleado en el árbol binario de búsqueda según su puntuación.
     * La puntuación es calculada automáticamente antes de insertar.
     * 
     * @param empleado El empleado que se desea insertar.
     */
    public void insertar(Empleado empleado) {
        empleado.setPuntuacion(empleado.calcularPuntuacion());
        raiz = insertarRec(raiz, empleado);
    }

    /**
     * Método auxiliar recursivo para insertar un empleado en el árbol.
     * 
     * @param nodo Nodo actual del árbol.
     * @param empleado Empleado a insertar.
     * @return El nodo actualizado después de la inserción.
     */
    private NodoEmpleado insertarRec(NodoEmpleado nodo, Empleado empleado) {
        if (nodo == null) {
            return new NodoEmpleado(empleado);
        }

        if (empleado.getPuntuacion() < nodo.empleado.getPuntuacion()) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, empleado);
        } else {
            nodo.derecho = insertarRec(nodo.derecho, empleado);
        }

        return nodo;
    }

    /**
     * Busca el empleado con la mayor puntuación en el árbol.
     * 
     * @return El empleado con mayor puntuación, o null si el árbol está vacío.
     */
    public Empleado buscarMayorPuntuacion() {
        if (raiz == null) return null;
        NodoEmpleado actual = raiz;
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.empleado;
    }

    /**
     * Busca el empleado con la menor puntuación en el árbol.
     * 
     * @return El empleado con menor puntuación, o null si el árbol está vacío.
     */
    public Empleado buscarMenorPuntuacion() {
        if (raiz == null) return null;
        NodoEmpleado actual = raiz;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.empleado;
    }

    /**
     * Realiza un recorrido inorden del árbol (izquierda - raíz - derecha)
     * e imprime los empleados en orden ascendente de puntuación.
     */
    public void recorridoInorden() {
        recorridoInordenRec(raiz);
    }

    /**
     * Método recursivo auxiliar para el recorrido inorden.
     * 
     * @param nodo Nodo actual del árbol.
     */
    private void recorridoInordenRec(NodoEmpleado nodo) {
        if (nodo != null) {
            recorridoInordenRec(nodo.izquierdo);
            System.out.println(nodo.empleado);
            recorridoInordenRec(nodo.derecho);
        }
    }

    /**
     * Realiza un recorrido postorden del árbol (izquierda - derecha - raíz)
     * e imprime los empleados.
     */
    public void recorridoPostorden() {
        recorridoPostordenRec(raiz);
    }

    /**
     * Método recursivo auxiliar para el recorrido postorden.
     * 
     * @param nodo Nodo actual del árbol.
     */
    private void recorridoPostordenRec(NodoEmpleado nodo) {
        if (nodo != null) {
            recorridoPostordenRec(nodo.izquierdo);
            recorridoPostordenRec(nodo.derecho);
            System.out.println(nodo.empleado);
        }
    }
}