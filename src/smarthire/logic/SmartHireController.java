package src.smarthire.logic;

import java.util.ArrayList;
import java.util.List;

import src.smarthire.manager.EmpleadoManager;
import src.smarthire.model.Empleado;
import src.smarthire.structures.ArbolEmpleados;
import src.smarthire.structures.GrafoEmpleados;

/**
 * Controlador para conectar la interfaz con la lógica, maneja y coordina las funciones
 * 
 *  @author Santiago Palma
 *  @author Germán Chipana
 *  @author Giovanni Mejia
 *  @author Leonardo Baca
 */
public class SmartHireController {
    
    private final EmpleadoManager empleadoManager;
    private final ArbolEmpleados arbolEmpleados;
    private final GrafoEmpleados grafoEmpleados;

    public SmartHireController() {
        this.empleadoManager = new EmpleadoManager();
        this.arbolEmpleados = new ArbolEmpleados();
        this.grafoEmpleados = new GrafoEmpleados();
    }

    public void agregarEmpleado(String id, String nombre, String area, int edad, int experiencia, int evaluaciones) {
        Empleado nuevoEmpleado = new Empleado(id, nombre, edad, experiencia, evaluaciones, area);

        boolean exito = empleadoManager.agregarEmpleado(nuevoEmpleado);
        
        if (exito) {
            arbolEmpleados.insertar(nuevoEmpleado);
            grafoEmpleados.agregarEmpleado(nuevoEmpleado);
        }
    }

    public void editarEmpleado() {
    }
    
    public void eliminarEmpleado(Empleado empleado) {
    }

    /**
     * Devuelve la lista de todos los empleados 
     */
    public ArrayList<Empleado> listarTodosLosEmpleados() {
        return empleadoManager.getEmpleados();
    }
    
    /**
     * Busca un empleado por su ID
     */
    public Empleado buscarEmpleadoPorId(String id) {
        return empleadoManager.buscarEmpleado(id);
    }

    /**
     * Registra una relación de colaboración entre dos empleados en el grafo.
     * Este método será utilizado por la interfaz de la "Red Social".
     * @param e1 El primer empleado de la relación.
     * @param e2 El segundo empleado de la relación.
     */
    public void registrarColaboracion(Empleado e1, Empleado e2) {
        // Se delega la responsabilidad de crear la arista directamente al grafo.
        // Por ahora, se asume un peso y tipo de relación por defecto.
        grafoEmpleados.agregarRelacion(e1, e2, 1.0, "colaboracion");
    }

    /**
     * El método estrella. Realiza el análisis completo para encontrar al mejor candidato.
     * @param pesoPuntuacion Ponderación para el score de desempeño.
     * @param pesoConectividad Ponderación para el score de colaboración.
     * @return El empleado óptimo.
     */
    public Empleado getMejorCandidato(double pesoPuntuacion, double pesoConectividad) {
        List<Empleado> todos = empleadoManager.getEmpleados();
        if (todos == null || todos.isEmpty()) {
            return null; // No hay empleados para analizar
        }

        // --- Paso 1: Encontrar los valores máximos para normalizar ---
        double maxPuntuacion = 0;
        int maxColaboraciones = 0;
        for (Empleado emp : todos) {
            if (emp.getPuntuacion() > maxPuntuacion) {
                maxPuntuacion = emp.getPuntuacion();
            }
            // NOTA: Asumo que tu GrafoEmpleados tiene un método para obtener las conexiones de un empleado.
            int colaboraciones = grafoEmpleados.obtenerConexiones(emp).size(); 
            if (colaboraciones > maxColaboraciones) {
                maxColaboraciones = colaboraciones;
            }
        }
        // Evitar división por cero si nadie tiene puntuación o colaboraciones
        if (maxPuntuacion == 0) maxPuntuacion = 1; 
        if (maxColaboraciones == 0) maxColaboraciones = 1;


        // --- Paso 2: Calcular el score final para cada empleado ---
        Empleado mejorCandidato = null;
        double mejorScoreFinal = -1.0;

        for (Empleado emp : todos) {
            // Normalizar las puntuaciones a una escala de 0 a 1
            double puntuacionNormalizada = emp.getPuntuacion() / maxPuntuacion;
            double colaboracionNormalizada = (double) grafoEmpleados.obtenerConexiones(emp).size() / maxColaboraciones;

            // Calcular el score final ponderado
            double scoreFinal = (puntuacionNormalizada * pesoPuntuacion) + (colaboracionNormalizada * pesoConectividad);

            // Guardar los scores calculados en el objeto empleado para mostrarlos en la UI
            emp.setScoreColaboracion(colaboracionNormalizada);
            emp.setPuntuacionFinalPonderada(scoreFinal);

            // Comprobar si este es el mejor candidato hasta ahora
            if (scoreFinal > mejorScoreFinal) {
                mejorScoreFinal = scoreFinal;
                mejorCandidato = emp;
            }
        }

        return mejorCandidato;
    }
}