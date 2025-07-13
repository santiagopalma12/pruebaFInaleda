package src.smarthire.structures;

import src.smarthire.model.Empleado;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * GrafoEmpleados.java
 * Empleamos una estructura de datos de Grafo no dirigido para modelar las relaciones entre empleados, utilizamos una lista de adyacencia implementada con un HashMap.
 * @author Santiago Palma
 */
public class GrafoEmpleados {
    private final Map<Empleado, List<Arista>> listaAdyacencia;

    public GrafoEmpleados() {
        this.listaAdyacencia = new HashMap<>();
    }
    /**
     * Agrega un nuevo empleado (nodo) al grafo si no existe previamente.
     * @param empleado El empleado a agregar.
     */
    public void agregarEmpleado(Empleado empleado) { 
        listaAdyacencia.putIfAbsent(empleado, new ArrayList<>());
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
    public List<Arista> obtenerConexiones(Empleado empleado) { 
        return listaAdyacencia.getOrDefault(empleado, Collections.emptyList());
    }
    
    // Devuelve todos los empleados registrados
    public List<Empleado> obtenerTodosLosEmpleados() { 
        return new ArrayList<>(listaAdyacencia.keySet());
    }
}