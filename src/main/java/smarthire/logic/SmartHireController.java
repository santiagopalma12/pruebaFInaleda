package smarthire.logic;

import java.util.ArrayList;
import smarthire.manager.EmpleadoManager;
import smarthire.model.Empleado;
import smarthire.structures.ArbolEmpleados;
import smarthire.structures.GrafoEmpleados;
import smarthire.structures.PilaEmpleado;
import smarthire.model.Accion;

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
    private final PilaEmpleado historialEmpleados;


    public SmartHireController() {
        this.empleadoManager = new EmpleadoManager();
        this.arbolEmpleados = new ArbolEmpleados();
        this.grafoEmpleados = new GrafoEmpleados();
        this.historialEmpleados = new PilaEmpleado(); 

    }

    public void agregarEmpleado(String id, String nombre, String area, int edad, int experiencia, int evaluaciones) {
        Empleado nuevoEmpleado = new Empleado(id, nombre, edad, experiencia, evaluaciones, area);

        boolean exito = empleadoManager.agregarEmpleado(nuevoEmpleado);
        
        if (exito) {
            arbolEmpleados.insertar(nuevoEmpleado);
            grafoEmpleados.agregarEmpleado(nuevoEmpleado);
        }
    }

    public void editarEmpleado(Empleado empleadoActualizado) {
        empleadoActualizado.setPuntuacion(empleadoActualizado.calcularPuntuacion());

        Empleado existente = empleadoManager.buscarEmpleado(empleadoActualizado.getId());
        if (existente != null) {
            empleadoManager.eliminarEmpleado(existente.getId());
            arbolEmpleados.eliminar(existente.getId());
            grafoEmpleados.eliminarEmpleado(existente);
        }

        agregarEmpleado(
            empleadoActualizado.getId(),
            empleadoActualizado.getNombre(),
            empleadoActualizado.getArea(),
            empleadoActualizado.getEdad(),
            empleadoActualizado.getExperiencia(),
            empleadoActualizado.getEvaluaciones()
        );
    }

    public void eliminarEmpleado(Empleado empleado) {
        empleadoManager.eliminarEmpleado(empleado.getId());
        arbolEmpleados.eliminar(empleado.getId());
        grafoEmpleados.eliminarEmpleado(empleado);
    }
    
    public void setListaEmpleados(ArrayList<Empleado> empleados) {
        this.empleadoManager.setEmpleados(empleados); // O lo que uses internamente
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
        System.out.println("Análisis iniciado con pesos: Desempeño=" + pesoPuntuacion + ", Conectividad=" + pesoConectividad);
        
        // Simulación funcional: usa el método del manager para obtener el mejor por puntuación.
        return empleadoManager.obtenerEmpleadoConMayorPuntuacion();
    }
}