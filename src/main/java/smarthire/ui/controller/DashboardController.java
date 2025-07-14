package smarthire.ui.controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;

public class DashboardController {

    @FXML private TableView<Empleado> tablaEmpleados;
    @FXML private TableColumn<Empleado, String> colId;
    @FXML private TableColumn<Empleado, String> colNombre;
    @FXML private TableColumn<Empleado, String> colArea;
    @FXML private TableColumn<Empleado, Double> colPuntuacion;

    private SmartHireController smartHireController;
    private ObservableList<Empleado> empleadosObservable;

    public void initialize() {
        smartHireController = new SmartHireController();
        configurarTabla();
        cargarEmpleados();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colArea.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getArea()));
        colPuntuacion.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPuntuacion()).asObject());

        empleadosObservable = FXCollections.observableArrayList();
        tablaEmpleados.setItems(empleadosObservable);
    }

    private void cargarEmpleados() {
        empleadosObservable.setAll(smartHireController.listarTodosLosEmpleados());
    }

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
        cargarEmpleados(); 
    }


    @FXML
    private void onAgregarEmpleado() {
        System.out.println("Agregar empleado");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/EmpleadoFormView.fxml"));
            Parent root = loader.load();

            EmpleadoFormController formController = loader.getController();
            formController.setSmartHireController(smartHireController);
            formController.setDashboardController(this);

            Stage stage = new Stage();
            stage.setTitle("Agregar Empleado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recargarEmpleados() {
        cargarEmpleados();
    }


    @FXML
    private void onEditarEmpleado() {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/smarthire/ui/EmpleadoFormView.fxml"));
                Parent root = loader.load();

                EmpleadoFormController formController = loader.getController();
                formController.setSmartHireController(this.smartHireController);
                formController.inicializarParaEditar(seleccionado);

                Stage stage = new Stage();
                stage.setTitle("Editar Empleado");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void onEliminarEmpleado() {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            System.out.println("Eliminando: " + seleccionado.getNombre());
            smartHireController.eliminarEmpleado(seleccionado);
            recargarEmpleados();
        } else {
            System.out.println("No se ha seleccionado ningún empleado para eliminar.");
        }
    }
}