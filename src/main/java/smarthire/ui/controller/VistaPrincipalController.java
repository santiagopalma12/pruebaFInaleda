package smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import smarthire.logic.SmartHireController;

public class VistaPrincipalController {

    @FXML private StackPane contenedorCentral;

    private SmartHireController smartHireController;

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    @FXML
    private void irGestionEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/DashboardView.fxml"));
            Parent vista = loader.load();

            DashboardController controller = loader.getController();
            controller.setSmartHireController(smartHireController); 

            Stage stage = new Stage();
            stage.setTitle("Gestión de Empleados");
            stage.setScene(new Scene(vista));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void irRedColaboracion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/RedSocialView.fxml"));
            Parent vista = loader.load();

            RedSocialController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            Stage stage = new Stage();
            stage.setTitle("Red de Colaboración");
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irAnalisis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/AnalisisView.fxml"));
            Parent vista = loader.load();

            AnalisisController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            Stage stage = new Stage();
            stage.setTitle("Análisis Inteligente");
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}