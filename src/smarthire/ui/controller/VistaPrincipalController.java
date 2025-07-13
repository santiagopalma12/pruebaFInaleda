package src.smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import src.smarthire.logic.SmartHireController;

public class VistaPrincipalController {

    @FXML private StackPane contenedorCentral;

    private SmartHireController smartHireController;

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    @FXML
    private void irGestionEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/smarthire/ui/view/DashboardView.fxml"));
            Parent vista = loader.load();

            DashboardController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            contenedorCentral.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irRedColaboracion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/smarthire/ui/view/RedSocialView.fxml"));
            Parent vista = loader.load();

            RedSocialController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            contenedorCentral.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irAnalisis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/smarthire/ui/view/AnalisisView.fxml"));
            Parent vista = loader.load();

            AnalisisController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            contenedorCentral.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
