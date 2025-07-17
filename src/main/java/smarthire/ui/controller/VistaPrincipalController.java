package smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane; 
import java.io.IOException;
import smarthire.logic.SmartHireController;

public class VistaPrincipalController {

    @FXML private BorderPane contenedorCentral; 

    private SmartHireController smartHireController;

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
        irGestionEmpleados(); 
    }

    private void cargarVistaEnCentro(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent vista = loader.load();

            Object controller = loader.getController();

            if (controller instanceof DashboardController) {
                ((DashboardController) controller).setSmartHireController(smartHireController);
            } else if (controller instanceof RedSocialController) {
                ((RedSocialController) controller).setSmartHireController(smartHireController);
            } else if (controller instanceof AnalisisController) {
                ((AnalisisController) controller).setSmartHireController(smartHireController);
            } else if (controller instanceof HistorialController) {
                ((HistorialController) controller).setSmartHireController(smartHireController);
            } else if (controller instanceof CrearProyectoController) { 
                ((CrearProyectoController) controller).setSmartHireController(smartHireController);
            }

            contenedorCentral.setCenter(vista);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }

    @FXML
    private void irGestionEmpleados() {
        cargarVistaEnCentro("/smarthire/ui/DashboardView.fxml");
    }

    @FXML
    private void irRedColaboracion() {
        cargarVistaEnCentro("/smarthire/ui/RedSocialView.fxml");
    }

    @FXML
    private void irAnalisis() {
        cargarVistaEnCentro("/smarthire/ui/AnalisisView.fxml");
    }

    @FXML
    private void irHistorial() {
        cargarVistaEnCentro("/smarthire/ui/HistorialView.fxml");
    }
    
    @FXML
    private void irRecomendadorEquipo() {
        cargarVistaEnCentro("/smarthire/ui/CrearProyectoView.fxml");
    }
}