package src.smarthire.ui.controller;

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
import src.smarthire.logic.SmartHireController;
import src.smarthire.model.Empleado;

import java.io.IOException;

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
    
    private void abrirVentanaResultados(Empleado ganador) {
        try {
            // 1. Cargar el FXML de la ventana de resultados
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ResultadosView.fxml"));
            Parent root = loader.load();

            // 2. Obtener el controlador de la nueva ventana ANTES de mostrarla
            ResultadosController resultadosController = loader.getController();

            // 3. Pasar el objeto Empleado ganador al controlador de resultados
            resultadosController.setEmpleado(ganador);

            // 4. Configurar y mostrar la nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("¡Candidato Encontrado!");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de resultados.");
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