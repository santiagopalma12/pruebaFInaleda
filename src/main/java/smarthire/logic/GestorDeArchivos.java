package smarthire.logic;

import java.io.*;
import java.util.ArrayList; 
import smarthire.model.Empleado;
import smarthire.structures.GrafoEmpleados;
import smarthire.structures.Trie;

public class GestorDeArchivos {

    private static final String EMPLEADOS_CSV = "empleados.csv";
    private static final String RELACIONES_CSV = "colaboraciones.csv";
    private static final String HABILIDADES_TXT = "habilidades.txt";
    private static final String SEPARADOR_HABILIDADES = ";";

    public static void guardarDatos(SmartHireController controller) {
        guardarEmpleados(controller.listarTodosLosEmpleados());
        guardarRelaciones(controller.getGrafoEmpleados());
        guardarHabilidadesMaestro(controller.getTrieHabilidades());
    }

    public static void cargarDatos(SmartHireController controller) {
        cargarHabilidadesMaestro(controller.getTrieHabilidades());
        cargarEmpleados(controller);
        cargarRelaciones(controller);
    }
    
    private static void guardarEmpleados(ArrayList<Empleado> empleados) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLEADOS_CSV))) {
            writer.println("ID,Nombre,Edad,Experiencia,Evaluaciones,Area,RutaFoto,Habilidades");
            for (Empleado e : empleados) {
                String rutaFoto = (e.getRutaFoto() != null) ? e.getRutaFoto() : "";
                
                StringBuilder habilidadesStrBuilder = new StringBuilder();
                if (e.getHabilidades() != null && !e.getHabilidades().isEmpty()) {
                    for (int i = 0; i < e.getHabilidades().size(); i++) {
                        habilidadesStrBuilder.append(e.getHabilidades().get(i));
                        if (i < e.getHabilidades().size() - 1) {
                            habilidadesStrBuilder.append(SEPARADOR_HABILIDADES);
                        }
                    }
                }
                
                writer.println(
                    e.getId() + "," + e.getNombre() + "," + e.getEdad() + "," +
                    e.getExperiencia() + "," + e.getEvaluaciones() + "," + e.getArea() + "," +
                    rutaFoto + "," + habilidadesStrBuilder.toString()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    private static void guardarRelaciones(GrafoEmpleados grafo) {
        if (grafo == null) return;
        try (PrintWriter writer = new PrintWriter(new FileWriter(RELACIONES_CSV))) {
            writer.println("ID_Empleado1,ID_Empleado2");
            
            ArrayList<ArrayList<Empleado>> todasLasRelaciones = grafo.exportarTodasLasRelaciones();
            ArrayList<String> relacionesGuardadas = new ArrayList<>();

            for (ArrayList<Empleado> par : todasLasRelaciones) {
                Empleado origen = par.get(0);
                Empleado destino = par.get(1);
                
                String id1 = origen.getId();
                String id2 = destino.getId();
                
                String claveRelacion;
                if (id1.compareTo(id2) < 0) {
                    claveRelacion = id1 + "-" + id2;
                } else {
                    claveRelacion = id2 + "-" + id1;
                }
                
                boolean yaExiste = false;
                for (String guardada : relacionesGuardadas) {
                    if (guardada.equals(claveRelacion)) {
                        yaExiste = true;
                        break;
                    }
                }

                if (!yaExiste) {
                    writer.println(id1 + "," + id2);
                    relacionesGuardadas.add(claveRelacion);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar relaciones: " + e.getMessage());
        }
    }
    
    private static void guardarHabilidadesMaestro(Trie trie) {
        if (trie == null || trie.obtenerTodasLasPalabras() == null) return;
        try (PrintWriter writer = new PrintWriter(new FileWriter(HABILIDADES_TXT))) {
            for (String habilidad : trie.obtenerTodasLasPalabras()) {
                writer.println(habilidad);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el diccionario de habilidades: " + e.getMessage());
        }
    }

    private static void cargarEmpleados(SmartHireController controller) {
        File archivo = new File(EMPLEADOS_CSV);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            reader.readLine(); 
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", -1);
                if (partes.length >= 8) {
                    ArrayList<String> habilidades = new ArrayList<>();
                    if (!partes[7].isEmpty()) {
                        String[] habilidadesArray = partes[7].split(SEPARADOR_HABILIDADES);
                        for(String habilidad : habilidadesArray) {
                            habilidades.add(habilidad);
                        }
                    }
                    controller.agregarEmpleado(partes[0], partes[1], partes[5], Integer.parseInt(partes[2]), 
                                              Integer.parseInt(partes[3]), Integer.parseInt(partes[4]),
                                              false,
                                              habilidades, partes[6]); 
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar empleados: " + e.getMessage());
        }
    }

    private static void cargarRelaciones(SmartHireController controller) {
        File archivo = new File(RELACIONES_CSV);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            reader.readLine(); 
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    Empleado emp1 = controller.buscarEmpleadoPorId(partes[0]);
                    Empleado emp2 = controller.buscarEmpleadoPorId(partes[1]);
                    if (emp1 != null && emp2 != null) {
                        controller.registrarColaboracion(emp1, emp2);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar relaciones: " + e.getMessage());
        }
    }

    private static void cargarHabilidadesMaestro(Trie trie) {
        File archivo = new File(HABILIDADES_TXT);
        if (!archivo.exists()) {
            ArrayList<String> habsDefault = new ArrayList<>();
            habsDefault.add("Java");
            habsDefault.add("Python");
            habsDefault.add("Liderazgo");
            habsDefault.add("Comunicacion");
            habsDefault.add("SQL");
            habsDefault.add("Scrum");
            habsDefault.add("Git");
            habsDefault.add("React");
            
            for (String hab : habsDefault) {
                trie.insert(hab);
            }
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    trie.insert(linea.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de habilidades: " + e.getMessage());
        }
    }
}