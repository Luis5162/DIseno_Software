package controlador;

import modelo.Operacion;
import repositorio.OperacionRepositorio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

public class GestionTareasControlador {
    
    @FXML private ListView<Operacion> listViewTareas;
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    
    private OperacionRepositorio operacionRepositorio;
    private Operacion tareaSeleccionada;
    
    public GestionTareasControlador() {
        this.operacionRepositorio = new OperacionRepositorio();
    }
    
    @FXML
    public void initialize() {
        cargarTareas();
        configurarListeners();
        deshabilitarBotonesEdicion();
        
        // Configurar evento Enter para guardar
        txtNombre.setOnAction(e -> {
            if (btnGuardar.isDisabled()) {
                actualizarTarea();
            } else {
                guardarTarea();
            }
        });
    }
    
    private void cargarTareas() {
        try {
            List<Operacion> tareas = operacionRepositorio.obtenerTodasLasOperaciones();
            listViewTareas.getItems().clear();
            listViewTareas.getItems().addAll(tareas);
            
            // Configurar cómo mostrar las tareas
            listViewTareas.setCellFactory(lv -> new ListCell<Operacion>() {
                @Override
                protected void updateItem(Operacion tarea, boolean empty) {
                    super.updateItem(tarea, empty);
                    if (empty || tarea == null) {
                        setText(null);
                    } else {
                        setText("ID: " + tarea.getId() + " - " + tarea.getNombre());
                    }
                }
            });
            
            if (tareas.isEmpty()) {
                lblMensaje.setText("No hay tareas disponibles. Crea algunas tareas primero.");
                lblMensaje.setStyle("-fx-text-fill: #666;");
            } else {
                lblMensaje.setText("");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void configurarListeners() {
        // Listener para selección de tareas
        listViewTareas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    tareaSeleccionada = newValue;
                    cargarDatosTarea(newValue);
                    habilitarBotonesEdicion();
                    lblMensaje.setText("");
                } else {
                    limpiarFormulario();
                    deshabilitarBotonesEdicion();
                }
            });
    }
    
    private void cargarDatosTarea(Operacion tarea) {
        txtNombre.setText(tarea.getNombre());
        txtDescripcion.setText(tarea.getDescripcion());
    }
    
    private void limpiarFormulario() {
        txtNombre.clear();
        txtDescripcion.clear();
        tareaSeleccionada = null;
        listViewTareas.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNombre.requestFocus();
    }
    
    private void habilitarBotonesEdicion() {
        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
        btnGuardar.setDisable(true);
        
        // Cambiar estilos para indicar modo edición
        btnActualizar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 8 15;");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 8 15;");
        btnGuardar.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #666; -fx-padding: 8 15;");
    }
    
    private void deshabilitarBotonesEdicion() {
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(false);
        
        // Restaurar estilos normales
        btnGuardar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        btnActualizar.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #666; -fx-padding: 8 15;");
        btnEliminar.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #666; -fx-padding: 8 15;");
    }
    
    @FXML
    private void guardarTarea() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("❌ El nombre de la tarea es obligatorio");
            txtNombre.requestFocus();
            return;
        }
        
        if (nombre.length() < 3) {
            mostrarError("❌ El nombre debe tener al menos 3 caracteres");
            txtNombre.requestFocus();
            return;
        }
        
        try {
            Operacion nuevaTarea = new Operacion(nombre, descripcion);
            boolean exito = operacionRepositorio.guardarOperacion(nuevaTarea);
            
            if (exito) {
                mostrarExito("✅ Tarea creada exitosamente!\nID: " + nuevaTarea.getId() + "\nNombre: " + nuevaTarea.getNombre());
                cargarTareas();
                limpiarFormulario();
                deshabilitarBotonesEdicion();
            } else {
                mostrarError("❌ Error al guardar la tarea en la base de datos");
            }
        } catch (Exception e) {
            mostrarError("❌ Error al crear tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void actualizarTarea() {
        if (tareaSeleccionada == null) {
            mostrarError("❌ Debe seleccionar una tarea para actualizar");
            return;
        }
        
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("❌ El nombre de la tarea es obligatorio");
            txtNombre.requestFocus();
            return;
        }
        
        if (nombre.length() < 3) {
            mostrarError("❌ El nombre debe tener al menos 3 caracteres");
            txtNombre.requestFocus();
            return;
        }
        
        try {
            // Verificar si realmente hay cambios
            if (nombre.equals(tareaSeleccionada.getNombre()) && 
                descripcion.equals(tareaSeleccionada.getDescripcion())) {
                mostrarError("ℹ️ No se detectaron cambios para actualizar");
                return;
            }
            
            // Actualizar los datos de la tarea seleccionada
            tareaSeleccionada.setNombre(nombre);
            tareaSeleccionada.setDescripcion(descripcion);
            
            // Usar el método real de actualización
            boolean exito = actualizarTareaEnBD(tareaSeleccionada);
            
            if (exito) {
                mostrarExito("✅ Tarea actualizada exitosamente!\nID: " + tareaSeleccionada.getId() + "\nNombre: " + tareaSeleccionada.getNombre());
                cargarTareas();
                limpiarFormulario();
                deshabilitarBotonesEdicion();
            } else {
                mostrarError("❌ Error al actualizar la tarea en la base de datos");
            }
        } catch (Exception e) {
            mostrarError("❌ Error al actualizar tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void eliminarTarea() {
        if (tareaSeleccionada == null) {
            mostrarError("❌ Debe seleccionar una tarea para eliminar");
            return;
        }
        
        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta tarea?");
            confirmacion.setContentText("Tarea: " + tareaSeleccionada.getNombre() + 
                                      "\nID: " + tareaSeleccionada.getId() +
                                      "\n\nEsta acción no se puede deshacer.");
            
            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                boolean exito = operacionRepositorio.eliminarOperacion(tareaSeleccionada.getId());
                
                if (exito) {
                    mostrarExito("✅ Tarea eliminada exitosamente!\nTarea: " + tareaSeleccionada.getNombre());
                    cargarTareas();
                    limpiarFormulario();
                    deshabilitarBotonesEdicion();
                } else {
                    mostrarError("❌ Error al eliminar la tarea de la base de datos");
                }
            }
        } catch (Exception e) {
            mostrarError("❌ Error al eliminar tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void limpiarSeleccion() {
        limpiarFormulario();
        deshabilitarBotonesEdicion();
        lblMensaje.setText("📝 Listo para crear nueva tarea");
        lblMensaje.setStyle("-fx-text-fill: #666;");
    }
    
    /**
     * Método para actualizar la tarea en la base de datos
     * Usa el método real del repositorio en lugar de simulación
     */
    private boolean actualizarTareaEnBD(Operacion tarea) {
        return operacionRepositorio.actualizarOperacion(tarea);
    }
    
    @FXML
    private void volverAlPanelPrincipal() {
        try {
            Stage stageActual = (Stage) listViewTareas.getScene().getWindow();
            stageActual.close();
            
            Stage panelPrincipalStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/panel_principal.fxml"));
            Parent root = loader.load();
            
            panelPrincipalStage.setTitle("Sistema General - Panel Principal");
            panelPrincipalStage.setScene(new Scene(root, 1000, 700));
            panelPrincipalStage.show();
            
        } catch (Exception e) {
            System.err.println("Error al volver al panel principal: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo volver al panel principal");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
    
    private void mostrarExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
    }
}