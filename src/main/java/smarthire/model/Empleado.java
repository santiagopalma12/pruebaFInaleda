package smarthire.model;

public class Empleado {
    private String id;
    private String nombre;
    private int edad;
    private int experiencia;
    private int evaluaciones;
    private String area;
    private double puntuacion;
    private double scoreColaboracion;
    private double puntuacionFinalPonderada;


    // Constructor para la clase Empleado
    public Empleado (String id, String nombre, int edad, int experiencia, int evaluaciones, String area) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.experiencia = experiencia;
        this.evaluaciones = evaluaciones;
        this.area = area;
        this.puntuacion = calcularPuntuacion();
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

    public double getScoreColaboracion() { return scoreColaboracion; }
    public void setScoreColaboracion(double scoreColaboracion) { this.scoreColaboracion = scoreColaboracion; }

    public double getPuntuacionFinalPonderada() { return puntuacionFinalPonderada; }
    public void setPuntuacionFinalPonderada(double puntuacionFinalPonderada) { this.puntuacionFinalPonderada = puntuacionFinalPonderada; }


    /**
     * Calcula la puntuación de un Empleado según su experiencia y evaluaciones.
     * Los pesos ahora suman 1.0 (60% evaluaciones, 40% experiencia).
     * @return
     */
    public double calcularPuntuacion() { 
        return (this.evaluaciones * 0.6) + (this.experiencia * 0.4); 
    }

    public String toString() {
        return "ID: " + getId() + " | Nombre: " + getNombre() +
                               " | Puntaje: " + getPuntuacion();
    }
}