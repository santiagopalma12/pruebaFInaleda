package smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;
import java.io.IOException;
import smarthire.ui.controller.ResultadosController;

public class AnalisisController {

    @FXML
    private Slider desempenoSlider;

    @FXML
    private Label desempenoLabel;

    @FXML
    private Slider colaboracionSlider;

    @FXML
    private Label colaboracionLabel;

    @FXML
    private Button findButton;

    private SmartHireController smartHireController;

    // Este método será llamado por el MainController para darnos acceso a la lógica
    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    @FXML
    public void initialize() {
        // Añadir listeners para que los sliders actualicen las etiquetas en tiempo real
        desempenoSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            desempenoLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
        });

        colaboracionSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            colaboracionLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
        });
    }

    @FXML
    private void encontrarMejorCandidato() {
        if (smartHireController == null) {
            mostrarAlerta("Error", "El controlador principal no está inicializado.");
            return;
        }

        double pesoDesempeno = desempenoSlider.getValue() / 100.0;
        double pesoColaboracion = colaboracionSlider.getValue() / 100.0;

        // Llamamos al método "estrella" del controlador principal
        Empleado mejorCandidato = smartHireController.getMejorCandidato(pesoDesempeno, pesoColaboracion);

        if (mejorCandidato != null) {
            // Si encontramos a alguien, abrimos la ventana de resultados
            abrirVentanaResultados(mejorCandidato);
        } else {
            // Si no, mostramos un mensaje
            mostrarAlerta("Sin Resultados", "No se encontró ningún candidato que cumpla los criterios o no hay empleados registrados.");
        }
    }

    private void abrirVentanaResultados(Empleado mejor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/ResultadosView.fxml"));
            Parent root = loader.load();

            ResultadosController controller = loader.getController();
            controller.setEmpleado(mejor);

            Stage stage = new Stage();
            stage.setTitle("Mejor Candidato");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}