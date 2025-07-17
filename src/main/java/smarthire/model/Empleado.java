package smarthire.model;
import java.util.ArrayList;

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
    private ArrayList<String> habilidades;
    private String rutaFoto; 

    public Empleado (String id, String nombre, int edad, int experiencia, int evaluaciones, String area) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.experiencia = experiencia;
        this.evaluaciones = evaluaciones;
        this.area = area;
        this.puntuacion = calcularPuntuacion();
        this.habilidades = new ArrayList<>();
        this.rutaFoto = "";
    }

    public Empleado(Empleado otro) {
        this.id = otro.id;
        this.nombre = otro.nombre;
        this.edad = otro.edad;
        this.experiencia = otro.experiencia;
        this.evaluaciones = otro.evaluaciones;
        this.area = otro.area;
        this.puntuacion = otro.puntuacion;
        this.habilidades = new ArrayList<>(otro.habilidades);
    }
    
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
    
    public ArrayList<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(ArrayList<String> habilidades) {
        this.habilidades = habilidades;
    }

    public void agregarHabilidad(String habilidad) {
        if (!this.habilidades.contains(habilidad)) {
            this.habilidades.add(habilidad);
        }
    }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

    /**
     * Calcula la puntuación de un Empleado según su experiencia y evaluaciones.
     * Los pesos ahora suman 1.0 (60% evaluaciones, 40% experiencia).
     * @return
     */
    public double calcularPuntuacion() { 
        return (this.evaluaciones * 0.6) + (this.experiencia * 0.4); 
    }

    public boolean tieneHabilidad(String habilidad) {
        return this.habilidades.contains(habilidad);
    }

    public String toString() {
        return "ID: " + getId() + " | Nombre: " + getNombre() +
                               " | Puntaje: " + getPuntuacion();
    }
}