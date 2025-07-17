package smarthire.model;

import smarthire.logic.SmartHireController;

public class Accion {
    private String tipo; 
    private Empleado empleado;
    private SmartHireController controlador;

    public Accion(String tipo, Empleado empleado, SmartHireController controlador) {
        this.tipo = tipo.toLowerCase();
        this.empleado = empleado;
        this.controlador = controlador;
    }

    public void deshacer() {
        switch (tipo) {
            case "agregar":
                controlador.eliminarEmpleado(empleado, false);
                break;
            case "eliminar":
                controlador.agregarEmpleado(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getArea(),
                    empleado.getEdad(),
                    empleado.getExperiencia(),
                    empleado.getEvaluaciones(),
                    false,
                    empleado.getHabilidades(),
                    empleado.getRutaFoto()
                );
                break;
            case "editar":
                controlador.editarEmpleado(this.empleado, false); 
                break;
            default:
                System.out.println("Acción no reconocida: " + tipo);
        }
    }

    public String toString() {
        return tipo.toUpperCase() + " -> " + empleado.getNombre() + " (" + empleado.getId() + ")";
    }
}
