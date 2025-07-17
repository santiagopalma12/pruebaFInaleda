package smarthire.ui.controller;

import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CrearProyectoController {

    @FXML private TextField habilidadTextField;
    @FXML private ListView<String> sugerenciasListView;
    @FXML private ListView<String> habilidadesSeleccionadasListView;
    @FXML private Spinner<Integer> tamanoEquipoSpinner;

    private SmartHireController smartHireController;
    private ObservableList<String> listaHabilidadesSeleccionadas = FXCollections.observableArrayList();

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    @FXML
    public void initialize() {
        habilidadesSeleccionadasListView.setItems(listaHabilidadesSeleccionadas);
        
        sugerenciasListView.setVisible(false);
        habilidadTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.trim().isEmpty()) {
                sugerenciasListView.setVisible(false);
                return;
            }
            if (smartHireController != null) {
                ArrayList<String> sugerencias = smartHireController.sugerirHabilidades(newText.trim().toLowerCase());
                if (!sugerencias.isEmpty()) {
                    sugerenciasListView.setItems(FXCollections.observableArrayList(sugerencias));
                    sugerenciasListView.setVisible(true);
                } else {
                    sugerenciasListView.setVisible(false);
                }
            }
        });
        
        sugerenciasListView.setOnMouseClicked(event -> {
            String seleccionada = sugerenciasListView.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                habilidadTextField.setText(seleccionada);
                sugerenciasListView.setVisible(false);
            }
        });
    }

    @FXML
    private void handleAddHabilidad() {
        String habilidad = habilidadTextField.getText().trim();
        if (!habilidad.isEmpty() && !listaHabilidadesSeleccionadas.contains(habilidad)) {
            listaHabilidadesSeleccionadas.add(habilidad);
            habilidadTextField.clear();
            sugerenciasListView.setVisible(false);
        }
    }

    @FXML
    private void handleRecomendarEquipo() {
        ArrayList<String> habilidadesRequeridas = new ArrayList<>(listaHabilidadesSeleccionadas);
        int tamanoEquipo = tamanoEquipoSpinner.getValue();

        if (habilidadesRequeridas.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar al menos una habilidad para el proyecto.");
            return;
        }

        ArrayList<Empleado> equipoRecomendado = smartHireController.recomendarEquipo(habilidadesRequeridas, tamanoEquipo);
        
        if (equipoRecomendado != null && !equipoRecomendado.isEmpty()) {
            mostrarVentanaDeResultados(equipoRecomendado);
        } else {
            mostrarAlerta("Sin resultados", "No se pudo encontrar un equipo adecuado con los criterios especificados.");
        }
    }

    private void mostrarVentanaDeResultados(ArrayList<Empleado> equipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/EquipoRecomendadoView.fxml"));
            Parent root = loader.load();


            EquipoRecomendadoController controller = loader.getController();
            controller.mostrarEquipo(equipo);

            Stage stage = new Stage();
            stage.setTitle("Equipo Ideal Recomendado");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de UI", "No se pudo cargar la vista de resultados.");
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