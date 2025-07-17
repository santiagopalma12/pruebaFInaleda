package smarthire.ui.controller;

import smarthire.model.Empleado;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EquipoRecomendadoController {

    @FXML
    private HBox contenedorEquipo;

    public void mostrarEquipo(ArrayList<Empleado> equipo) {
        contenedorEquipo.getChildren().clear(); 
        for (Empleado miembro : equipo) {
            contenedorEquipo.getChildren().add(crearTarjetaEmpleado(miembro));
        }
    }

    private VBox crearTarjetaEmpleado(Empleado empleado) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle("-fx-background-color: white; -fx-border-color: #007bff; -fx-border-radius: 10; -fx-background-radius: 10;");
        
        Label nombre = new Label(empleado.getNombre());
        nombre.setFont(new Font("System Bold", 18));
        
        Label area = new Label("Área: " + empleado.getArea());
        Label puntuacion = new Label(String.format("Score: %.2f", empleado.getPuntuacion()));
        
        Label tituloHabilidades = new Label("Aporta:");
        tituloHabilidades.setStyle("-fx-font-weight: bold;");
        
        tarjeta.getChildren().addAll(nombre, area, puntuacion, tituloHabilidades);
        
        for (String habilidad : empleado.getHabilidades()) {
            tarjeta.getChildren().add(new Label("- " + habilidad));
        }
        
        return tarjeta;
    }
}