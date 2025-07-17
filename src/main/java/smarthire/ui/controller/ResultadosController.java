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
            // --- LÓGICA DE CARGA DE FOTO ACTUALIZADA Y ROBUSTA ---
        String rutaFoto = empleado.getRutaFoto();
        Image image = null;
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            try {
                image = new Image(rutaFoto);
                if (image.isError()) image = null;
            } catch (Exception e) { image = null; }
        }

        if (image == null) {
            try {
                image = new Image(getClass().getResourceAsStream("/smarthire/assets/user_placeholder.png"));
            } catch (Exception e) {
                System.err.println("Placeholder no encontrado.");
            }
        }
        fotoImageView.setImage(image);
        
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