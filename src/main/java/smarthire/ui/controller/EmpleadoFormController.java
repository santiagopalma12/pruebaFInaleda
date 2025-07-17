package smarthire.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import smarthire.logic.SmartHireController;
import smarthire.model.Empleado;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class EmpleadoFormController {
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtArea;
    @FXML private TextField txtExperiencia;
    @FXML private TextField txtEvaluaciones;
    @FXML private TextField habilidadTextField;
    @FXML private ListView<String> habilidadesAsignadasListView;
    @FXML private ImageView imgPreview;
    @FXML private ListView<String> sugerenciasListView;

    private boolean esEdicion = false;
    private Empleado empleadoActual; 
    private SmartHireController smartHireController;
    private DashboardController dashboardController;
    private File archivoFotoSeleccionada;
    
    public void initialize() {
        try {
            imgPreview.setImage(new Image(getClass().getResourceAsStream("/smarthire/assets/user_placeholder.png")));
        } catch (Exception e) { /* ... */ }
        
        sugerenciasListView.setVisible(false);
        habilidadTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (smartHireController == null || newText == null || newText.trim().isEmpty()) {
                sugerenciasListView.setVisible(false);
                return;
            }
            ArrayList<String> sugerencias = smartHireController.sugerirHabilidades(newText.trim());
            if (!sugerencias.isEmpty()) {
                sugerenciasListView.setItems(FXCollections.observableArrayList(sugerencias));
                sugerenciasListView.setVisible(true);
            } else {
                sugerenciasListView.setVisible(false);
            }
        });

        sugerenciasListView.setOnMouseClicked(event -> {
            String seleccionada = sugerenciasListView.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                habilidadTextField.setText(seleccionada);
                sugerenciasListView.setVisible(false);
            }
        });
    }

    public void setEmpleado(Empleado empleado, boolean esModoEdicion) {
        this.esEdicion = esModoEdicion;
        this.empleadoActual = empleado;
        txtId.setDisable(true);
        
        if (esEdicion) {
            txtId.setText(empleado.getId());
            txtNombre.setText(empleado.getNombre());
            txtEdad.setText(String.valueOf(empleado.getEdad()));
            txtArea.setText(empleado.getArea());
            txtExperiencia.setText(String.valueOf(empleado.getExperiencia()));
            txtEvaluaciones.setText(String.valueOf(empleado.getEvaluaciones()));
            if (empleado.getRutaFoto() != null && !empleado.getRutaFoto().isEmpty()) {
                try {
                    imgPreview.setImage(new Image(empleado.getRutaFoto()));
                } catch (Exception e) { /* ... */ }
            }
        } else {
            // Generamos un ID nuevo y se imprime
            txtId.setText(smartHireController.generarNuevoId());
            this.empleadoActual.setId(txtId.getText());
        }

        if (empleado.getHabilidades() != null) {
            habilidadesAsignadasListView.setItems(FXCollections.observableArrayList(empleado.getHabilidades()));
        }
    }

    public void setSmartHireController(SmartHireController controller) {
        this.smartHireController = controller;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void onGuardar() {
        if (!validarEntradas()) {
            return;
        }

        actualizarEmpleadoDesdeFormulario();
        
        if (archivoFotoSeleccionada != null) {
            try {
                String nuevaRuta = copiarFotoYObtenerRuta(archivoFotoSeleccionada, empleadoActual.getId());
                empleadoActual.setRutaFoto(nuevaRuta);
            } catch (IOException e) {
                mostrarAlerta("Error de Archivo", "No se pudo guardar la imagen.");
                e.printStackTrace();
                return;
            }
        }

        if (esEdicion) {
            smartHireController.editarEmpleado(empleadoActual, true);  
        } else {
            smartHireController.agregarEmpleado(
                empleadoActual.getId(), empleadoActual.getNombre(), empleadoActual.getArea(),
                empleadoActual.getEdad(), empleadoActual.getExperiencia(), empleadoActual.getEvaluaciones(),
                true, empleadoActual.getHabilidades(), empleadoActual.getRutaFoto()
            );
        }
        
        dashboardController.recargarEmpleados();
        cerrarVentana();
    }
    
    @FXML
    private void handleAddHabilidad() {
        String habilidad = habilidadTextField.getText().trim();
        if (!habilidad.isEmpty() && !habilidadesAsignadasListView.getItems().contains(habilidad)) {
            // Siempre trabajamos sobre el objeto 'empleadoActual'
            empleadoActual.agregarHabilidad(habilidad);
            habilidadesAsignadasListView.setItems(FXCollections.observableArrayList(empleadoActual.getHabilidades()));
            habilidadTextField.clear();
        }
    }

    private void actualizarEmpleadoDesdeFormulario() {
        empleadoActual.setNombre(txtNombre.getText().trim());
        empleadoActual.setArea(txtArea.getText().trim());
        empleadoActual.setEdad(Integer.parseInt(txtEdad.getText()));
        empleadoActual.setExperiencia(Integer.parseInt(txtExperiencia.getText()));
        empleadoActual.setEvaluaciones(Integer.parseInt(txtEvaluaciones.getText()));
        
        ArrayList<String> habilidadesDelForm = new ArrayList<>(habilidadesAsignadasListView.getItems());
        empleadoActual.getHabilidades().clear();
        for (String h : habilidadesDelForm) {
            empleadoActual.agregarHabilidad(h);
        }
    }

    @FXML
    private void onSeleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto del Empleado");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imgPreview.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);
        
        if (archivoSeleccionado != null) {
            this.archivoFotoSeleccionada = archivoSeleccionado;
            Image image = new Image(archivoSeleccionado.toURI().toString());
            imgPreview.setImage(image);
        }
    }

    @FXML private void onCancelar() { cerrarVentana(); }
    private void cerrarVentana() { ((Stage) txtId.getScene().getWindow()).close(); }

    private boolean validarEntradas() {
        try {
            if (txtNombre.getText().trim().isEmpty()) {
                mostrarAlerta("Error de Validación", "El Nombre es obligatorio.");
                return false;
            }
            Integer.parseInt(txtEdad.getText());
            Integer.parseInt(txtExperiencia.getText());
            Integer.parseInt(txtEvaluaciones.getText());
            return true;
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "Edad, Experiencia y Evaluaciones deben ser números.");
            return false;
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String copiarFotoYObtenerRuta(File archivoOriginal, String empleadoId) throws IOException {
        Path carpetaDestino = Paths.get("fotos_empleados");
        if (!Files.exists(carpetaDestino)) {
            Files.createDirectories(carpetaDestino);
        }
        String nombreOriginal = archivoOriginal.getName();
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        String nuevoNombreArchivo = empleadoId.replaceAll("[^a-zA-Z0-9.-]", "_") + extension;
        Path rutaDestino = carpetaDestino.resolve(nuevoNombreArchivo);
        Files.copy(archivoOriginal.toPath(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
        return rutaDestino.toUri().toString();
    }
}