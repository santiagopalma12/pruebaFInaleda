package smarthire.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import smarthire.model.Empleado;
import smarthire.ui.controller.AnalisisController;

public class ResultadosController {

    @FXML
    private ImageView fotoImageView;

    @FXML
    private Label nombreEmpleadoLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label areaLabel;
    
    @FXML
    private Label puntuacionLabel;
    
    @FXML
    private Label colaboracionScoreLabel;

    @FXML
    private Label puntuacionFinalLabel;


    /**
     * Este es el método clave. Recibe el empleado ganador desde el AnalisisController
     * y actualiza la interfaz de usuario con sus datos.
     * @param empleado El empleado seleccionado como el mejor candidato.
     */
    public void setEmpleado(Empleado empleado) {
        // Aquí podrías tener lógica para cargar una imagen real si la tuvieras.
        // Por ahora, usamos una imagen genérica como placeholder.
        try {
            Image image = new Image(getClass().getResourceAsStream("../assets/user_placeholder.png"));
            fotoImageView.setImage(image);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen placeholder.");
        }
        
        nombreEmpleadoLabel.setText(empleado.getNombre());
        idLabel.setText(empleado.getId());
        areaLabel.setText(empleado.getArea());
        
        // Usa los getters para obtener los valores REALES calculados
        puntuacionLabel.setText(String.format("%.2f", empleado.getPuntuacion()));
        colaboracionScoreLabel.setText(String.format("%.2f", empleado.getScoreColaboracion()));
        puntuacionFinalLabel.setText(String.format("%.2f", empleado.getPuntuacionFinalPonderada()));
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        // Obtiene el Stage (la ventana) a partir del evento del botón y la cierra
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}