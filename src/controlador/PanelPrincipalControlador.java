package controlador;

import modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PanelPrincipalControlador {
    @FXML private Label lblUsuario;
    @FXML private MenuItem menuCrearOperacion;
    @FXML private MenuItem menuGestionarOperaciones;
    @FXML private MenuItem menuCrearEmpleado; // Nuevo MenuItem
    @FXML private MenuItem menuCerrarSesion;
    
    private Usuario usuarioLogueado;
    
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        actualizarInterfaz();
    }
    
    @FXML
    public void initialize() {
        menuCrearOperacion.setOnAction(e -> abrirCrearOperacion());
        menuGestionarOperaciones.setOnAction(e -> abrirGestionOperaciones());
        menuCrearEmpleado.setOnAction(e -> abrirCrearEmpleado()); // Nuevo handler
        menuCerrarSesion.setOnAction(e -> cerrarSesion());
    }
    
    private void actualizarInterfaz() {
        if (usuarioLogueado != null) {
            lblUsuario.setText("Bienvenido: " + usuarioLogueado.getNombre() + " - Administrador del Sistema General");
        }
    }
    
    private void abrirCrearOperacion() {
        try {
            Stage stageActual = (Stage) lblUsuario.getScene().getWindow();
            stageActual.hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/crear_operacion.fxml"));
            Parent root = loader.load();
            
            Stage crearOpStage = new Stage();
            crearOpStage.setTitle("Sistema General - Crear Operación");
            crearOpStage.setScene(new Scene(root, 500, 400));
            crearOpStage.setOnHidden(e -> stageActual.show());
            crearOpStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir crear operación: " + e.getMessage());
        }
    }
    
    private void abrirCrearEmpleado() {
        try {
            Stage stageActual = (Stage) lblUsuario.getScene().getWindow();
            stageActual.hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/crear_empleado.fxml"));
            Parent root = loader.load();
            
            Stage crearEmpleadoStage = new Stage();
            crearEmpleadoStage.setTitle("Sistema General - Crear Empleado");
            crearEmpleadoStage.setScene(new Scene(root, 500, 350));
            crearEmpleadoStage.setOnHidden(e -> stageActual.show());
            crearEmpleadoStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir crear empleado: " + e.getMessage());
        }
    }
    
    private void abrirGestionOperaciones() {
        mostrarInfo("Función 'Gestionar Operaciones' estará disponible próximamente");
    }
    
    private void cerrarSesion() {
        try {
            Stage stageActual = (Stage) lblUsuario.getScene().getWindow();
            stageActual.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/login.fxml"));
            Parent root = loader.load();
            
            Stage loginStage = new Stage();
            loginStage.setTitle("Sistema General - Login");
            loginStage.setScene(new Scene(root, 500, 400));
            loginStage.show();
            
        } catch (Exception e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}