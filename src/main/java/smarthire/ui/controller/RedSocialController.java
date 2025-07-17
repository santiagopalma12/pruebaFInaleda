package smarthire.ui.controller;

import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType;
import smarthire.logic.GestorDeArchivos;
import java.util.ArrayList;


/**
 * Controlador para la vista RedSocialView.fxml.
 * Permite al usuario seleccionar dos empleados y registrar una colaboración entre ellos.
 */
public class RedSocialController {

    @FXML
    private ComboBox<Empleado> empleado1ComboBox;

    @FXML
    private ComboBox<Empleado> empleado2ComboBox;

    private SmartHireController smartHireController;

    /**
     * Inyecta la instancia del controlador principal. Este método es llamado por
     * el MainController cuando carga esta vista.
     * @param controller La instancia única del SmartHireController.
     */
    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
        poblarComboBoxes();
    }

    /**
     * El método initialize se llama automáticamente después de que el FXML ha sido cargado.
     * Aquí configuramos cómo se mostrarán los objetos Empleado en el ComboBox.
     */
    @FXML
    public void initialize() {

        
        // Pídele al Integrante 1 que añada esto en la clase Empleado.java:
        /*
         @Override
         public String toString() {
             return getNombre() + " (" + getId() + ")";
         }
        */
    }
    /**
     * Se ejecuta cuando el usuario hace clic en el botón "Registrar Colaboración".
     * Valida la selección y, si es correcta, llama al controlador central para
     * registrar la nueva relación.
     */
    @FXML
    private void handleRegistrarColaboracion() {
        Empleado empleado1 = empleado1ComboBox.getValue();
        Empleado empleado2 = empleado2ComboBox.getValue();

        if (empleado1 == null || empleado2 == null) {
            mostrarAlerta("Error de Selección", "Debe seleccionar ambos empleados.");
            return;
        }

        if (empleado1.equals(empleado2)) {
            mostrarAlerta("Error de Lógica", "Un empleado no puede colaborar consigo mismo.");
            return;
        }

        smartHireController.registrarColaboracion(empleado1, empleado2);

        mostrarAlerta("Éxito", "La colaboración ha sido registrada y guardada.");
        empleado1ComboBox.getSelectionModel().clearSelection();
        empleado2ComboBox.getSelectionModel().clearSelection();
    }

    
    /**
     * Método de ayuda para mostrar ventanas emergentes de alerta al usuario.
     * @param titulo El título de la ventana de alerta.
     * @param mensaje El mensaje a mostrar.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Rellena ambos ComboBox con la lista actual de empleados del sistema.
     */
    private void poblarComboBoxes() {
        if (smartHireController != null) {
            empleado1ComboBox.getItems().setAll(smartHireController.listarTodosLosEmpleados());
            empleado2ComboBox.getItems().setAll(smartHireController.listarTodosLosEmpleados());
        }
    }

    public void refrescarVista() {
        poblarComboBoxes();
    }
}