package smarthire.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;
import smarthire.logic.GestorDeArchivos;



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
        Empleado empleado = obtenerEmpleadoDelFormulario();

        if (esEdicion) {
            smartHireController.editarEmpleado(empleado);
        } else {
            smartHireController.agregarEmpleado(
                empleado.getId(), empleado.getNombre(), empleado.getArea(),
                empleado.getEdad(), empleado.getExperiencia(), empleado.getEvaluaciones()
            );
        }

        GestorDeArchivos.guardarEmpleados(smartHireController.listarTodosLosEmpleados());

        dashboardController.recargarEmpleados();
        cerrarVentana();
    }



    private Empleado obtenerEmpleadoDelFormulario() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String area = txtArea.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        int experiencia = Integer.parseInt(txtExperiencia.getText());
        int evaluaciones = Integer.parseInt(txtEvaluaciones.getText());

        return new Empleado(id, nombre, edad, experiencia, evaluaciones, area);
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