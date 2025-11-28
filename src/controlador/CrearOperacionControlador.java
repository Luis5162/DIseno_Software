package controlador;

import modelo.Operacion;
import repositorio.OperacionRepositorio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CrearOperacionControlador {
    
    @FXML private TextField txtNombreOperacion;
    @FXML private TextArea txtDescripcionOperacion;
    @FXML private Label lblMensaje;
    
    private OperacionRepositorio operacionRepositorio;
    
    public CrearOperacionControlador() {
        this.operacionRepositorio = new OperacionRepositorio();
    }
    
    @FXML
    public void initialize() {
        // Enfocar el campo nombre al iniciar
        txtNombreOperacion.requestFocus();
    }
    
    @FXML
    private void guardarOperacion() {
        String nombre = txtNombreOperacion.getText().trim();
        String descripcion = txtDescripcionOperacion.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("El nombre de la operación es obligatorio");
            txtNombreOperacion.requestFocus();
            return;
        }
        
        try {
            // Crear nueva operación
            Operacion nuevaOperacion = new Operacion(nombre, descripcion);
            
            // Guardar en la base de datos
            boolean guardadoExitoso = operacionRepositorio.guardarOperacion(nuevaOperacion);
            
            if (guardadoExitoso) {
                mostrarExito("✅ Operación creada exitosamente!\n" +
                            "ID: " + nuevaOperacion.getId() + "\n" +
                            "Nombre: " + nuevaOperacion.getNombre() + "\n" +
                            "Descripción: " + nuevaOperacion.getDescripcion());
                
                // Limpiar formulario después de guardar
                limpiarFormulario();
            } else {
                mostrarError("❌ Error al guardar la operación en la base de datos");
            }
            
        } catch (Exception e) {
            mostrarError("❌ Error al crear la operación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void limpiarFormulario() {
        txtNombreOperacion.clear();
        txtDescripcionOperacion.clear();
        lblMensaje.setText("");
        txtNombreOperacion.requestFocus();
    }
    
    @FXML
    private void volverAlPanelPrincipal() {
        try {
            Stage stageActual = (Stage) txtNombreOperacion.getScene().getWindow();
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