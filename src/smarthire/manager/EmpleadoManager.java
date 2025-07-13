package src.smarthire.manager;
import src.smarthire.model.Empleado;

import java.util.*;

public class EmpleadoManager {
    private ArrayList<Empleado> empleados;
    
    public EmpleadoManager() {
        empleados = new ArrayList<>();
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
     /**
      * Lista todos los empleados registrados.
      * Si no hay empleados, muestra un mensaje indicándolo
      */
    public void listarEmpleados() {
        if(empleados.isEmpty()) {
            System.out.println("No hay empleados registrados... ");
        } else {
            for(Empleado e : empleados) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Elimina un empleado según su ID.
     */
    public boolean eliminarEmpleado(String id) {
        for(Empleado e : empleados) {
            if(e.getId().equalsIgnoreCase(id)) {
                empleados.remove(e);
                return true;
            }
        }
        return false;
    }


    /**
     * Edita los datos de un empleado existente según su ID.
     */
    public boolean editarEmpleado(String id, int nuevaEdad, int nuevaExperiencia, int nuevasEvaluaciones) {
        for(Empleado e : empleados) {
            if(e.getId().equalsIgnoreCase(id)) {
                e.setEdad(nuevaEdad);
                e.setExperiencia(nuevaExperiencia);
                e.setEvaluaciones(nuevasEvaluaciones);
                e.setPuntuacion(e.calcularPuntuacion());
                return true;
            }
        }
        return false;
    }


    /**
     * Busca un empleado según su ID.
     */
    public Empleado buscarEmpleado(String id) {
        for(Empleado e : empleados) {
            if(e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }
    

    /**
     * Ordena la lista de empleados por puntuación de forma descendente.
    */
    public void ordenarPorPuntuacionDescendente() {
        int n = empleados.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (empleados.get(j).getPuntuacion() > empleados.get(maxIndex).getPuntuacion()) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                Empleado temp = empleados.get(i);
                empleados.set(i, empleados.get(maxIndex));
                empleados.set(maxIndex, temp);
            }
        }
    }

    /**
     * Muestra los empleados que pertenecen a un área.
     */
    public void filtrarPorArea(String area) {
        boolean encontrado = false;
        for(Empleado e : empleados) {
            if(e.getArea().equalsIgnoreCase(area)) {
                System.out.println(e.toString());
                encontrado = true;
            }
        }
        if(!encontrado) {
            System.out.println("No se encontraron empleados en el area de "+area);
        }
    }

    /**
     * Retorna el empleado con la mayor puntuación entre todos sus compañeros
     */

    public Empleado obtenerEmpleadoConMayorPuntuacion() {
        if (empleados.isEmpty()) return null;

        Empleado mejor = empleados.get(0);
        for (Empleado e : empleados) {
            if (e.getPuntuacion() > mejor.getPuntuacion()) {
                mejor = e;
            }
        }
        return mejor;
    }

    /**
     * Agrega un nuevo Empleado a la lista si su ID no se repite, y los datos son válidos
     */
    public boolean agregarEmpleado(Empleado nuevo) {
        if(nuevo.getId().isEmpty() || nuevo.getNombre().isEmpty() || nuevo.getEdad() <= 0) {
            System.out.println("Datos inválidos... ");
            return false;
        }

        for(Empleado e : empleados) {
            if(e.getId().equalsIgnoreCase(nuevo.getId())) {
                System.out.println("Ya existe un empleado con ese ID... ");
                return false;
            }
        }
        nuevo.setPuntuacion(nuevo.calcularPuntuacion());
        empleados.add(nuevo);
        return true;
    }
}