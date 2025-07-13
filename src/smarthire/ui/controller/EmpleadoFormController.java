package src.smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.smarthire.logic.SmartHireController;
import src.smarthire.model.Empleado;


public class EmpleadoFormController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtArea;
    @FXML private TextField txtExperiencia;
    @FXML private TextField txtEvaluaciones;

    private boolean esEdicion = false;
    private Empleado empleadoExistente;
    private SmartHireController smartHireController;
    private DashboardController dashboardController;
    
    public void inicializarParaEditar(Empleado empleado) {
        this.empleadoExistente = empleado;
        this.esEdicion = true;

        txtId.setText(empleado.getId());
        txtId.setDisable(true); // No permitir editar ID
        txtNombre.setText(empleado.getNombre());
        txtEdad.setText(String.valueOf(empleado.getEdad()));
        txtArea.setText(empleado.getArea());
        txtExperiencia.setText(String.valueOf(empleado.getExperiencia()));
        txtEvaluaciones.setText(String.valueOf(empleado.getEvaluaciones()));
    }


    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }


    @FXML
    private void onGuardar() {
        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String area = txtArea.getText();
            int experiencia = Integer.parseInt(txtExperiencia.getText());
            int evaluaciones = Integer.parseInt(txtEvaluaciones.getText());

            if (esEdicion) {
                empleadoExistente.setNombre(nombre);
                empleadoExistente.setEdad(edad);
                empleadoExistente.setArea(area);
                empleadoExistente.setExperiencia(experiencia);
                empleadoExistente.setEvaluaciones(evaluaciones);
                smartHireController.editarEmpleado();
            } else {
                smartHireController.agregarEmpleado(id, nombre, area, edad, experiencia, evaluaciones);
            }

            // Recarga la tabla principal
            if (dashboardController != null) {
                dashboardController.recargarEmpleados();
            }

            cerrarVentana();

        } catch (NumberFormatException e) {
            System.out.println("Error: Edad, experiencia y evaluaciones deben ser números.");
        }
        if (dashboardController != null) {
            dashboardController.recargarEmpleados();
        }
    }


    @FXML
    private void onCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }
}
