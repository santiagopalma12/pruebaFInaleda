package smarthire.logic;

import java.io.*;
import java.util.*;
import smarthire.model.Empleado;
public class GestorDeArchivos {

    private static final String ARCHIVO_EMPLEADOS = "empleados.csv";

    public static void guardarEmpleados(ArrayList<Empleado> empleados) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            for (Empleado e : empleados) {
                writer.println(e.getId() + "," + e.getNombre() + "," + e.getEdad() + "," +
                        e.getExperiencia() + "," + e.getEvaluaciones() + "," + e.getArea());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    public static ArrayList<Empleado> cargarEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_EMPLEADOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 6) {
                    Empleado e = new Empleado(
                        partes[0],                     
                        partes[1],                     
                        Integer.parseInt(partes[2]),   
                        Integer.parseInt(partes[3]),   
                        Integer.parseInt(partes[4]),   
                        partes[5]                      
                    );
                    e.setPuntuacion(e.calcularPuntuacion());
                    empleados.add(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado o error al leer: " + e.getMessage());
        }
        return empleados;
    }

    public static void guardarColaboraciones(ArrayList<String[]> colaboraciones) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("colaboraciones.csv"))) {
            for (String[] par : colaboraciones) {
                writer.println(par[0] + "," + par[1]);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar colaboraciones: " + e.getMessage());
        }
    }

    public static ArrayList<String[]> cargarColaboraciones() {
        ArrayList<String[]> colaboraciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("colaboraciones.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    colaboraciones.add(partes);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer colaboraciones: " + e.getMessage());
        }
        return colaboraciones;
    }

}