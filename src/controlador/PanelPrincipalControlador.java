package controlador;

import modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PanelPrincipalControlador {
    @FXML private Label lblUsuario;
    @FXML private MenuItem menuCrearOperacion;
    @FXML private MenuItem menuGestionarTareas;
    @FXML private MenuItem menuGestionarOperaciones;
    @FXML private MenuItem menuCrearEmpleado;
    @FXML private MenuItem menuCerrarSesion;
    @FXML private Button btnCrearOperacion;
    @FXML private Button btnGestionarTareas;
    @FXML private Button btnAsignarTareas;
    @FXML private Button btnCrearEmpleado;
    
    private Usuario usuarioLogueado;
    
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        actualizarInterfaz();
    }
    
    @FXML
    public void initialize() {
        // Configurar menús
        menuCrearOperacion.setOnAction(e -> abrirCrearOperacion());
        menuGestionarTareas.setOnAction(e -> abrirGestionTareas());
        menuGestionarOperaciones.setOnAction(e -> abrirGestionOperaciones());
        menuCrearEmpleado.setOnAction(e -> abrirCrearEmpleado());
        menuCerrarSesion.setOnAction(e -> cerrarSesion());
        
        // Configurar botones (si existen)
        if (btnCrearOperacion != null) {
            btnCrearOperacion.setOnAction(e -> abrirCrearOperacion());
        }
        if (btnGestionarTareas != null) {
            btnGestionarTareas.setOnAction(e -> abrirGestionTareas());
        }
        if (btnAsignarTareas != null) {
            btnAsignarTareas.setOnAction(e -> abrirGestionOperaciones());
        }
        if (btnCrearEmpleado != null) {
            btnCrearEmpleado.setOnAction(e -> abrirCrearEmpleado());
        }
    }
    
    private void actualizarInterfaz() {
        if (usuarioLogueado != null) {
            lblUsuario.setText("Bienvenido: " + usuarioLogueado.getNombre() + " - Administrador del Sistema General");
        }
    }
    
    // Los métodos deben ser PRIVATE para que FXML los reconozca
    @FXML
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
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionTareas() {
        try {
            Stage stageActual = (Stage) lblUsuario.getScene().getWindow();
            stageActual.hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/gestion_tareas.fxml"));
            Parent root = loader.load();
            
            Stage gestionTareasStage = new Stage();
            gestionTareasStage.setTitle("Sistema General - Gestión de Tareas");
            gestionTareasStage.setScene(new Scene(root, 800, 600));
            gestionTareasStage.setOnHidden(e -> stageActual.show());
            gestionTareasStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir gestión de tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionOperaciones() {
        try {
            Stage stageActual = (Stage) lblUsuario.getScene().getWindow();
            stageActual.hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/gestion_tareas_empleados.fxml"));
            Parent root = loader.load();
            
            Stage gestionStage = new Stage();
            gestionStage.setTitle("Sistema General - Asignar Tareas a Empleados");
            gestionStage.setScene(new Scene(root, 800, 600));
            gestionStage.setOnHidden(e -> stageActual.show());
            gestionStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al abrir gestión de tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
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
            e.printStackTrace();
        }
    }
    
    @FXML
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
            e.printStackTrace();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}