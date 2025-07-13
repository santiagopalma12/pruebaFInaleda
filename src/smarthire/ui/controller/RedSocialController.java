package src.smarthire.ui.controller;

import src.smarthire.logic.SmartHireController;
import src.smarthire.model.Empleado;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType;

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
        // 1. Obtener los empleados seleccionados de los ComboBox.
        Empleado empleado1 = empleado1ComboBox.getValue();
        Empleado empleado2 = empleado2ComboBox.getValue();

        // 2. Realizar validaciones.
        if (empleado1 == null || empleado2 == null) {
            mostrarAlerta("Error de Selección", "Debe seleccionar ambos empleados.");
            return;
        }

        if (empleado1.equals(empleado2)) { // Compara si son el mismo objeto o si sus IDs son iguales.
            mostrarAlerta("Error de Lógica", "Un empleado no puede colaborar consigo mismo.");
            return;
        }

        // 3. Si todo es válido, llamar a la lógica de negocio.
        System.out.println("Registrando colaboración entre " + empleado1.getNombre() + " y " + empleado2.getNombre());
        smartHireController.registrarColaboracion(empleado1, empleado2);

        // 4. Mostrar una confirmación y limpiar la selección.
        mostrarAlerta("Éxito", "La colaboración ha sido registrada exitosamente.");
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