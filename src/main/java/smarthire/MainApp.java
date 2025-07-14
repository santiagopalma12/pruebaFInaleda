package smarthire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.ui.controller.VistaPrincipalController;
import smarthire.logic.GestorDeArchivos;
import smarthire.model.Empleado;

import java.io.*;
import java.util.ArrayList;
import smarthire.model.Empleado;


public class MainApp extends Application {

    private SmartHireController smartHireController;

    public void start(Stage primaryStage) {
        try {
            smartHireController = new SmartHireController();

            ArrayList<Empleado> empleadosCargados = GestorDeArchivos.cargarEmpleados();
            smartHireController.setListaEmpleados(empleadosCargados);

            String fxmlPath = "/smarthire/ui/VistaPrincipal.fxml";
            java.net.URL resourceUrl = getClass().getResource(fxmlPath);

            if (resourceUrl == null) {
                System.err.println("Error: No se encontró el archivo FXML en: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            VistaPrincipalController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            primaryStage.setTitle("SmartHire - Evaluación Inteligente");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                GestorDeArchivos.guardarEmpleados(smartHireController.listarTodosLosEmpleados());
                System.out.println("Empleados guardados correctamente al cerrar.");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}