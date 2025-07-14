package smarthire.structures;

import java.util.ArrayList;
import smarthire.model.Empleado;

/**
 * GrafoEmpleados.java
 * Empleamos una estructura de datos de Grafo no dirigido para modelar las relaciones entre empleados, utilizamos una lista de adyacencia implementada con un HashMap.
 * @author Santiago Palma
 */
public class GrafoEmpleados {
    private final MiTablaHash<Empleado, ArrayList<Arista>> listaAdyacencia;

    public GrafoEmpleados() {
        this.listaAdyacencia = new MiTablaHash<>();
    }
    
    /**
     * Agrega un nuevo empleado (nodo) al grafo si no existe previamente.
     * @param empleado El empleado a agregar.
     */
    public void agregarEmpleado(Empleado empleado) { 
        if (listaAdyacencia.get(empleado) == null) {
            listaAdyacencia.put(empleado, new ArrayList<>());
        }
    }
    
    /**
     * Crea una conexión entre dos empleados para las dos direcciones, si es que alguno no está en el grafo, se le añade.
     *
     * @param origen   Empleado origen
     * @param destino  Empleado receptor
     * @param peso     Peso de la relación.
     * @param tipo     Tipo de relación.
     */
    public void agregarRelacion(Empleado origen, Empleado destino, double peso, String tipo) { 
        this.agregarEmpleado(origen);
        this.agregarEmpleado(destino);
        
        listaAdyacencia.get(origen).add(new Arista(destino, peso, tipo));
        listaAdyacencia.get(destino).add(new Arista(origen, peso, tipo));
    }
    
    /**
     * Nos retorna  la lista de conexiones de un empleado.
     * @param empleado El empleado necesitado.
     * @return La lista de aristas. 
     */
    public ArrayList<Arista> obtenerConexiones(Empleado empleado) { 
        ArrayList<Arista> conexiones = listaAdyacencia.get(empleado);
        
        if (conexiones == null) {
            return new ArrayList<>();
        }
        return conexiones;
    }

    public void eliminarEmpleado(Empleado empleado) {
        // Obtener las conexiones del empleado
        ArrayList<Arista> conexiones = listaAdyacencia.get(empleado);

        // Eliminar al empleado del grafo (del mapa)
        listaAdyacencia.remove(empleado);

        // Si el empleado tenía conexiones, eliminar las referencias a él en los vecinos
        if (conexiones != null) {
            for (Arista arista : conexiones) {
                Empleado vecino = arista.getDestino();
                ArrayList<Arista> adyacentes = listaAdyacencia.get(vecino);

                if (adyacentes != null) {
                    // Crear una nueva lista sin el empleado eliminado
                    ArrayList<Arista> nuevasAristas = new ArrayList<>();
                    for (Arista a : adyacentes) {
                        if (!a.getDestino().equals(empleado)) {
                            nuevasAristas.add(a);
                        }
                    }
                    // Reemplazar la lista de aristas del vecino
                    listaAdyacencia.put(vecino, nuevasAristas);
                }
            }
        }
    }

    /**
     * Devuelve todos los empleados registrados
     * @return Un ArrayList con todos los empleados (nodos) del grafo.
     */
    public ArrayList<Empleado> obtenerTodosLosEmpleados() { 
        return listaAdyacencia.keySet();
    }
}