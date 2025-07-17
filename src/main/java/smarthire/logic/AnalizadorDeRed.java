package smarthire.logic;

import smarthire.model.Empleado;
import smarthire.structures.GrafoEmpleados;
import java.util.ArrayList; 
import smarthire.structures.Arista; 

public class AnalizadorDeRed {
    

    private final GrafoEmpleados grafo;

    public AnalizadorDeRed(GrafoEmpleados grafo) {
        this.grafo = grafo;
    }

    public ArrayList<Empleado> recomendarEquipoIdeal(ArrayList<Empleado> todosLosEmpleados, ArrayList<String> habilidadesRequeridas, int tamanoEquipo) {
        if (todosLosEmpleados.size() < tamanoEquipo) {
            System.out.println("No tenemos suficientes empleados para crear el equipo solicitado, ERROR.");
            return new ArrayList<>();
        }

        ArrayList<PuntuacionCandidato> candidatosPuntuados = new ArrayList<>();
        for (Empleado empleado : todosLosEmpleados) {
            double scoreAfinidad = calcularScoreAfinidad(empleado, habilidadesRequeridas);
            candidatosPuntuados.add(new PuntuacionCandidato(empleado, scoreAfinidad));
        }

        ordenarCandidatosPorScore(candidatosPuntuados);
        int tamanoSubconjunto = Math.max(10, tamanoEquipo * 3); 
        ArrayList<Empleado> mejoresCandidatos = new ArrayList<>();
        for (int i = 0; i < Math.min(tamanoSubconjunto, candidatosPuntuados.size()); i++) {
            mejoresCandidatos.add(candidatosPuntuados.get(i).getEmpleado());
        }

        ArrayList<ArrayList<Empleado>> todasLasCombinaciones = new ArrayList<>();
        generarCombinaciones(mejoresCandidatos, tamanoEquipo, 0, new ArrayList<>(), todasLasCombinaciones);
        
        if (todasLasCombinaciones.isEmpty()) {
            System.err.println("No se pudieron generar combinaciones de equipos.");
            return new ArrayList<>();
        }

        ArrayList<Empleado> mejorEquipo = new ArrayList<>();
        double maxPuntuacionSinergia = -1.0;

        for (ArrayList<Empleado> equipoPotencial : todasLasCombinaciones) {
            double puntuacionSinergia = calcularPuntuacionSinergia(equipoPotencial, habilidadesRequeridas);
            if (puntuacionSinergia > maxPuntuacionSinergia) {
                maxPuntuacionSinergia = puntuacionSinergia;
                mejorEquipo = equipoPotencial;
            }
        }

        System.out.println("Equipo recomendado encontrado con una puntuación de sinergia de: " + maxPuntuacionSinergia);
        return mejorEquipo;
    }
    
    private double calcularScoreAfinidad(Empleado empleado, ArrayList<String> habilidadesRequeridas) {
        if (habilidadesRequeridas == null || habilidadesRequeridas.isEmpty()) {
            return 0.0;
        }
        int contador = 0;
        for (String habilidadReq : habilidadesRequeridas) {
            if (empleado.tieneHabilidad(habilidadReq)) {
                contador++;
            }
        }
        return (double) contador / habilidadesRequeridas.size();
    }

    private void ordenarCandidatosPorScore(ArrayList<PuntuacionCandidato> candidatos) {
        int n = candidatos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (candidatos.get(j).getScore() < candidatos.get(j + 1).getScore()) {
                    PuntuacionCandidato temp = candidatos.get(j);
                    candidatos.set(j, candidatos.get(j + 1));
                    candidatos.set(j + 1, temp);
                }
            }
        }
    }

    private static class PuntuacionCandidato {
        private Empleado empleado;
        private double score;

        public PuntuacionCandidato(Empleado empleado, double score) {
            this.empleado = empleado;
            this.score = score;
        }

        public Empleado getEmpleado() { return empleado; }
        public double getScore() { return score; }
    }

    private void generarCombinaciones(ArrayList<Empleado> candidatos, int tamano, int inicio, 
                                      ArrayList<Empleado> combinacionActual, 
                                      ArrayList<ArrayList<Empleado>> resultado) {
        if (combinacionActual.size() == tamano) {
            resultado.add(new ArrayList<>(combinacionActual));
            return;
        }
        for (int i = inicio; i < candidatos.size(); i++) {
            combinacionActual.add(candidatos.get(i));
            generarCombinaciones(candidatos, tamano, i + 1, combinacionActual, resultado);
            combinacionActual.remove(combinacionActual.size() - 1);
        }
    }

    private double calcularPuntuacionSinergia(ArrayList<Empleado> equipo, ArrayList<String> habilidadesRequeridas) {
        double pesoCobertura = 0.5;
        double pesoConectividad = 0.3;
        double pesoRendimiento = 0.2;
        //HABILIDADES
        ArrayList<String> habilidadesCubiertas = new ArrayList<>();
        for (Empleado miembro : equipo) {
            for (String habilidadMiembro : miembro.getHabilidades()) {
                boolean yaExiste = false;
                for (String habilidadCubierta : habilidadesCubiertas) {
                    if (habilidadCubierta.equals(habilidadMiembro)) {
                        yaExiste = true;
                        break;
                    }
                }
                if (!yaExiste) {
                    habilidadesCubiertas.add(habilidadMiembro);
                }
            }
        }
        int contadorCobertura = 0;
        for (String habilidadReq : habilidadesRequeridas) {
            for (String habilidadCubierta : habilidadesCubiertas) {
                if (habilidadReq.equals(habilidadCubierta)) {
                    contadorCobertura++;
                    break;
                }
            }
        }
        double scoreCobertura = habilidadesRequeridas.isEmpty() ? 1.0 : (double) contadorCobertura / habilidadesRequeridas.size();

        //CONEXIÓN INTERNA
        int conexionesInternas = 0;
        for (int i = 0; i < equipo.size(); i++) {
            for (int j = i + 1; j < equipo.size(); j++) {
                Empleado miembro1 = equipo.get(i);
                Empleado miembro2 = equipo.get(j);
                for (Arista arista : grafo.obtenerConexiones(miembro1)) {
                    if (arista.getDestino().getId().equals(miembro2.getId())) {
                        conexionesInternas++;
                        break;
                    }
                }
            }
        }
        int tamanoEquipo = equipo.size();
        int maxConexionesPosibles = (tamanoEquipo > 1) ? tamanoEquipo * (tamanoEquipo - 1) / 2 : 0;
        double scoreConectividad = (maxConexionesPosibles > 0) ? (double) conexionesInternas / maxConexionesPosibles : 1.0;

        // RENDIMIENTO DEL EQUIPO PROMEDIO
        double sumaRendimiento = 0;
        for (Empleado miembro : equipo) {
            sumaRendimiento += miembro.getPuntuacion();
        }
        double scoreRendimiento = (equipo.size() > 0) ? sumaRendimiento / equipo.size() : 0.0;

        return (scoreCobertura * pesoCobertura) + (scoreConectividad * pesoConectividad) + (scoreRendimiento * pesoRendimiento);
    }
}