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
        ArrayList<Arista> conexiones = listaAdyacencia.get(empleado);

        listaAdyacencia.remove(empleado);

        if (conexiones != null) {
            for (Arista arista : conexiones) {
                Empleado vecino = arista.getDestino();
                ArrayList<Arista> adyacentes = listaAdyacencia.get(vecino);

                if (adyacentes != null) {
                    ArrayList<Arista> nuevasAristas = new ArrayList<>();
                    for (Arista a : adyacentes) {
                        if (!a.getDestino().equals(empleado)) {
                            nuevasAristas.add(a);
                        }
                    }
                    listaAdyacencia.put(vecino, nuevasAristas);
                }
            }
        }
    }

    public ArrayList<Empleado> obtenerTodosLosEmpleados() { 
        return listaAdyacencia.keySet();
    }

    public MiTablaHash<Empleado, ArrayList<Empleado>> getListaAdyacencia() {
        MiTablaHash<Empleado, ArrayList<Empleado>> mapaEstandar = new MiTablaHash();
        
        ArrayList<Empleado> empleados = this.listaAdyacencia.keySet();
        
        for (Empleado empleado : empleados) {
            ArrayList<Arista> aristas = this.listaAdyacencia.get(empleado);
            ArrayList<Empleado> destinos = new ArrayList<>();
            
            for (Arista arista : aristas) {
                destinos.add(arista.getDestino());
            }

            mapaEstandar.put(empleado, destinos);
        }
        
        return mapaEstandar;
    }

    public ArrayList<ArrayList<Empleado>> exportarTodasLasRelaciones() {
        ArrayList<ArrayList<Empleado>> todasLasRelaciones = new ArrayList<>();
        ArrayList<Empleado> nodos = this.obtenerTodosLosEmpleados();
        
        for (Empleado origen : nodos) {
            ArrayList<Arista> conexiones = this.obtenerConexiones(origen);
            for (Arista arista : conexiones) {
                Empleado destino = arista.getDestino();
                ArrayList<Empleado> parRelacion = new ArrayList<>();
                parRelacion.add(origen);
                parRelacion.add(destino);
                todasLasRelaciones.add(parRelacion);
            }
        }
        return todasLasRelaciones;
    }
}