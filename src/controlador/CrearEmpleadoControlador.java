package controlador;

import modelo.Empleado;
import servicio.EmpleadoServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CrearEmpleadoControlador {
    
    @FXML private TextField txtNombreEmpleado;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnCancelar;
    
    private EmpleadoServicio empleadoServicio;
    
    public CrearEmpleadoControlador() {
        this.empleadoServicio = new EmpleadoServicio();
    }
    
    @FXML
    public void initialize() {
        // Enfocar el campo nombre al iniciar
        txtNombreEmpleado.requestFocus();
        
        // Permitir guardar con Enter
        txtNombreEmpleado.setOnAction(e -> guardarEmpleado());
    }
    
    @FXML
    private void guardarEmpleado() {
        String nombre = txtNombreEmpleado.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("El nombre del empleado es obligatorio");
            txtNombreEmpleado.requestFocus();
            return;
        }
        
        try {
            boolean guardadoExitoso = empleadoServicio.crearEmpleado(nombre);
            
            if (guardadoExitoso) {
                mostrarExito("✅ Empleado creado exitosamente!\nNombre: " + nombre);
                limpiarFormulario();
            } else {
                mostrarError("❌ Error al guardar el empleado en la base de datos");
            }
            
        } catch (Exception e) {
            mostrarError("❌ Error al crear el empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void limpiarFormulario() {
        txtNombreEmpleado.clear();
        lblMensaje.setText("");
        txtNombreEmpleado.requestFocus();
    }
    
    @FXML
    private void volverAlPanelPrincipal() {
        try {
            Stage stageActual = (Stage) txtNombreEmpleado.getScene().getWindow();
            stageActual.close();
            
            // Mostrar nuevamente el panel principal
            Stage panelPrincipalStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/panel_principal.fxml"));
            Parent root = loader.load();
            
            panelPrincipalStage.setTitle("Sistema General - Panel Principal");
            panelPrincipalStage.setScene(new Scene(root, 1000, 700));
            panelPrincipalStage.show();
            
        } catch (Exception e) {
            System.err.println("Error al volver al panel principal: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: red;");
    }
    
    private void mostrarExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: green;");
    }
}