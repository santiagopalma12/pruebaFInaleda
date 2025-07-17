package smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.model.Accion;
import smarthire.model.Empleado;
import smarthire.structures.PilaEmpleados;

public class HistorialController {

    @FXML
    private ListView<String> historialListView;

    private SmartHireController smartHireController;

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
        cargarHistorial();
    }

    private void cargarHistorial() {
        historialListView.getItems().clear();
        for (Accion a : smartHireController.getHistorialAcciones().getElementosEnOrden()) {
            historialListView.getItems().add(a.toString());
        }
    }

    @FXML
    private void deshacerUltimaAccion() {
        smartHireController.deshacerUltimaAccion();
        cargarHistorial();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) historialListView.getScene().getWindow();
        stage.close();
    }
}
