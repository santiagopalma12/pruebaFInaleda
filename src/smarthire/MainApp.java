package src.smarthire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.smarthire.logic.SmartHireController;
import src.smarthire.ui.controller.VistaPrincipalController;

public class MainApp extends Application {

    private SmartHireController smartHireController;

    public void start(Stage primaryStage) throws Exception {
        // 1. Inicializar la lógica central del sistema
        smartHireController = new SmartHireController();

        // 2. Cargar VistaPrincipal.fxml (aquí llamada DashboardView.fxml según tu estructura)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/smarthire/ui/view/VistaPrincipal.fxml"));
        Parent root = loader.load();

        // 3. Obtener el controlador gráfico y pasarle la lógica
        VistaPrincipalController controller = loader.getController();
        controller.setSmartHireController(smartHireController);

        // 4. Mostrar la ventana
        primaryStage.setTitle("SmartHire - Plataforma de Evaluación");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        // 5. Guardar datos al cerrar (más adelante)
        // primaryStage.setOnCloseRequest(e -> guardarDatos());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
