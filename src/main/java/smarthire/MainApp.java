package smarthire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import smarthire.logic.GestorDeArchivos;
import smarthire.logic.SmartHireController;
import smarthire.ui.controller.VistaPrincipalController;

public class MainApp extends Application {
    private SmartHireController smartHireController;

    public void start(Stage primaryStage) {
        try {
            // 1. Crear el controlador de lógica
            smartHireController = new SmartHireController();

            // 2. Cargar TODOS los datos (habilidades, empleados, relaciones) usando el método unificado
            GestorDeArchivos.cargarDatos(smartHireController);
            System.out.println("Datos iniciales cargados en el sistema.");
            smartHireController.inicializarGeneradorId();

            // 3. Cargar la vista principal
            String fxmlPath = "/smarthire/ui/VistaPrincipal.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // 4. Inyectar el controlador de lógica (ya con los datos cargados) en el de la vista
            VistaPrincipalController controller = loader.getController();
            controller.setSmartHireController(smartHireController);

            // 5. Mostrar la aplicación
            primaryStage.setTitle("SmartHire - Evaluación Inteligente");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();

            // 6. Configurar el guardado completo al cerrar usando el método unificado
            primaryStage.setOnCloseRequest(event -> {
                GestorDeArchivos.guardarDatos(smartHireController);
                System.out.println("Datos de la aplicación guardados correctamente al cerrar.");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}