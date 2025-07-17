package smarthire.logic;

import java.util.ArrayList;
import smarthire.manager.EmpleadoManager;
import smarthire.model.Empleado;
import smarthire.structures.ArbolEmpleados;
import smarthire.structures.GrafoEmpleados;
import smarthire.structures.PilaEmpleados;
import smarthire.model.Accion;
import smarthire.structures.Trie; 
import java.io.BufferedReader; 
import java.io.FileReader;    
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 

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
    private final PilaEmpleados<Accion> historialAcciones = new PilaEmpleados<>();
    private final Trie trieHabilidades;
    private int proximoId = 1;
    private final AnalizadorDeRed analizadorDeRed;

    public SmartHireController() {
        this.empleadoManager = new EmpleadoManager();
        this.arbolEmpleados = new ArbolEmpleados();
        this.grafoEmpleados = new GrafoEmpleados();
        this.trieHabilidades = new Trie();
        this.analizadorDeRed = new AnalizadorDeRed(this.grafoEmpleados); 
        cargarHabilidadesDesdeArchivo();
    }

    public void inicializarGeneradorId() {
        int maxId = 0;
        ArrayList<Empleado> empleados = empleadoManager.getEmpleados();
        
        for (Empleado e : empleados) {
            try {
                int idActual = Integer.parseInt(e.getId());
                if (idActual > maxId) {
                    maxId = idActual;
                }
            } catch (NumberFormatException ex) {
            }
        }
        this.proximoId = maxId + 1;
        System.out.println("Generador de ID inicializado. Próximo ID será: " + this.proximoId);
    }

    public String generarNuevoId() {
        int idGenerado = proximoId;
        proximoId++; 
        return Integer.toString(idGenerado);
    }
    
    public void agregarEmpleado(String id, String nombre, String area, int edad, int experiencia, int evaluaciones, boolean registrarHistorial, ArrayList<String> habilidades, String rutaFoto) {
        Empleado nuevoEmpleado = new Empleado(id, nombre, edad, experiencia, evaluaciones, area);
        
        if (habilidades != null) {
            for(String h : habilidades){
                nuevoEmpleado.agregarHabilidad(h);
            }
        }
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            nuevoEmpleado.setRutaFoto(rutaFoto);
        }
        
        nuevoEmpleado.setPuntuacion(nuevoEmpleado.calcularPuntuacion());

        boolean exito = empleadoManager.agregarEmpleado(nuevoEmpleado);

        if (exito) {
            arbolEmpleados.insertar(nuevoEmpleado);
            grafoEmpleados.agregarEmpleado(nuevoEmpleado);
            if(registrarHistorial) {
                historialAcciones.apilar(new Accion("agregar", nuevoEmpleado, this));
            }
        }
    }

    private void cargarHabilidadesDesdeArchivo() {
        String nombreRecurso = "/habilidades.txt";
        try (InputStream inputStream = getClass().getResourceAsStream(nombreRecurso);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                System.err.println("NO SE ENCONTRÓ EL ARCHIVO TXT '" + nombreRecurso + "'");
                return;
            }

            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    trieHabilidades.insert(linea.trim().toLowerCase()); 
                }
            }
            System.out.println("Habilidades cargadas exitosamente.");
        } catch (Exception e) {
            System.err.println("ERROR AL LEER EL ARCHIVO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarEmpleado(Empleado empleadoActualizado, boolean registrarHistorial) {
        Empleado existente = empleadoManager.buscarEmpleado(empleadoActualizado.getId());
        
        if (existente != null) {
            if (registrarHistorial) {
                Empleado empleadoOriginal = new Empleado(existente);
                historialAcciones.apilar(new Accion("editar", empleadoOriginal, this));
            }

            empleadoManager.eliminarEmpleado(existente.getId());
            arbolEmpleados.eliminar(existente.getId());
            grafoEmpleados.eliminarEmpleado(existente);

            agregarEmpleado(
                empleadoActualizado.getId(), empleadoActualizado.getNombre(), empleadoActualizado.getArea(),
                empleadoActualizado.getEdad(), empleadoActualizado.getExperiencia(), empleadoActualizado.getEvaluaciones(),
                false, 
                empleadoActualizado.getHabilidades(),
                empleadoActualizado.getRutaFoto()
            );
        } else {
            System.err.println("Error: Se intentó editar un empleado que no existe.");
        }
    }

    public ArrayList<Empleado> recomendarEquipo(ArrayList<String> habilidadesRequeridas, int tamanoEquipo) {
        ArrayList<Empleado> todosLosEmpleados = this.listarTodosLosEmpleados();
        return analizadorDeRed.recomendarEquipoIdeal(todosLosEmpleados, habilidadesRequeridas, tamanoEquipo);
    }

    public void eliminarEmpleado(Empleado empleado, boolean registrarHistorial) {
        Empleado copiaParaHistorial = new Empleado(empleado);

        empleadoManager.eliminarEmpleado(empleado.getId());
        arbolEmpleados.eliminar(empleado.getId());
        grafoEmpleados.eliminarEmpleado(empleado);
        if (registrarHistorial) {
            historialAcciones.apilar(new Accion("eliminar", copiaParaHistorial, this));
        }
    }
    
    public void setListaEmpleados(ArrayList<Empleado> empleados) {
        empleadoManager.setEmpleados(empleados); 
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
    ArrayList<Empleado> empleados = this.listarTodosLosEmpleados();
        
        return empleadoManager.obtenerEmpleadoConMayorPuntuacion();
    }

    public PilaEmpleados<Accion> getHistorialAcciones() {
        return historialAcciones;
    }

    public void deshacerUltimaAccion() {
        if (!historialAcciones.estaVacia()) {
            Accion ultima = historialAcciones.desapilar();
            ultima.deshacer(); 
        } else {
            System.out.println("No hay acciones para deshacer.");
        }
    }

    public ArrayList<String> sugerirHabilidades(String prefijo) {
        return trieHabilidades.buscarPorPrefijo(prefijo.toLowerCase()); 
    }

    /**
     * Devuelve la instancia del grafo de empleados para poder guardar sus relaciones.
     * @return El objeto GrafoEmpleados.
     */
    public GrafoEmpleados getGrafoEmpleados() {
        return this.grafoEmpleados;
    }

    /**
     * Devuelve la instancia del Trie de habilidades para poder guardar su diccionario.
     * @return El objeto Trie.
     */
    public Trie getTrieHabilidades() {
        return this.trieHabilidades;
    }
    public GrafoEmpleados getGrafo() {
        return this.grafoEmpleados;
    }
}
