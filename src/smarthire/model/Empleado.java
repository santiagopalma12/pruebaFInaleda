package src.smarthire.model;

public class Empleado {
    private String id;
    private String nombre;
    private int edad;
    private int experiencia;
    private int evaluaciones;
    private String area;
    private double puntuacion;


    // Constructor para la clase Empleado
    public Empleado (String id, String nombre, int edad, int experiencia, int evaluaciones, String area) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.experiencia = experiencia;
        this.evaluaciones = evaluaciones;
        this.area = area;
    }
    
    // Getters y Setters para cada atributo 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }

    public int getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(int evaluaciones) { this.evaluaciones = evaluaciones; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public double getPuntuacion() { return puntuacion; }  
    public void setPuntuacion(double puntuacion) { this.puntuacion = puntuacion; }


    /**
     * Calcula la puntuación de un Empleado según su experiencia y evaluaciones.
     * @return
     */
    public double calcularPuntuacion() { return experiencia * 0.6 + evaluaciones * 0.6; }

    public String toString() {
        return "ID: " + getId() + " | Nombre: " + getNombre() +
                               " | Puntaje: " + getPuntuacion();
    }
}