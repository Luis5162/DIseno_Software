package controlador;

import modelo.Operacion;
import modelo.Empleado;
import repositorio.OperacionRepositorio;
import servicio.EmpleadoServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

public class GestionTareasEmpleadosControlador {
    
    @FXML private ListView<Operacion> listaTareas;
    @FXML private ListView<Empleado> listaEmpleados;
    @FXML private Label etiquetaTareaSeleccionada;
    @FXML private Label etiquetaEmpleadoSeleccionado;
    @FXML private Label etiquetaTareaFinal;
    @FXML private Label etiquetaEmpleadoFinal;
    @FXML private Label etiquetaMensaje;
    
    private OperacionRepositorio operacionRepositorio;
    private EmpleadoServicio empleadoServicio;
    private Operacion tareaSeleccionada;
    private Empleado empleadoSeleccionado;
    
    public GestionTareasEmpleadosControlador() {
        this.operacionRepositorio = new OperacionRepositorio();
        this.empleadoServicio = new EmpleadoServicio();
    }
    
    @FXML
    public void initialize() {
        cargarTareas();
        cargarEmpleados();
        configurarListeners();
    }
    
    private void cargarTareas() {
        try {
            List<Operacion> tareas = operacionRepositorio.obtenerTodasLasOperaciones();
            listaTareas.getItems().clear();
            listaTareas.getItems().addAll(tareas);
            
            // Configurar cómo mostrar las tareas en el ListView
            listaTareas.setCellFactory(lv -> new ListCell<Operacion>() {
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
                etiquetaMensaje.setText("No hay tareas disponibles. Crea algunas tareas primero.");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarEmpleados() {
        try {
            List<Empleado> empleados = empleadoServicio.obtenerTodosLosEmpleados();
            listaEmpleados.getItems().clear();
            listaEmpleados.getItems().addAll(empleados);
            
            // Configurar cómo mostrar los empleados en el ListView
            listaEmpleados.setCellFactory(lv -> new ListCell<Empleado>() {
                @Override
                protected void updateItem(Empleado empleado, boolean empty) {
                    super.updateItem(empleado, empty);
                    if (empty || empleado == null) {
                        setText(null);
                    } else {
                        setText("ID: " + empleado.getId() + " - " + empleado.getNombre());
                    }
                }
            });
            
            if (empleados.isEmpty()) {
                etiquetaMensaje.setText(etiquetaMensaje.getText() + "\nNo hay empleados disponibles. Crea algunos empleados primero.");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void configurarListeners() {
        // Listener para selección de tareas
        listaTareas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    tareaSeleccionada = newValue;
                    etiquetaTareaSeleccionada.setText("✓ Seleccionada: " + newValue.getNombre());
                    etiquetaTareaFinal.setText(newValue.getNombre());
                    actualizarMensajeAsignacion();
                }
            });
        
        // Listener para selección de empleados
        listaEmpleados.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    empleadoSeleccionado = newValue;
                    etiquetaEmpleadoSeleccionado.setText("✓ Seleccionado: " + newValue.getNombre());
                    etiquetaEmpleadoFinal.setText(newValue.getNombre());
                    actualizarMensajeAsignacion();
                }
            });
    }
    
    private void actualizarMensajeAsignacion() {
        if (tareaSeleccionada != null && empleadoSeleccionado != null) {
            etiquetaMensaje.setText("✅ Listo para asignar: '" + tareaSeleccionada.getNombre() + 
                              "' a '" + empleadoSeleccionado.getNombre() + "'");
            etiquetaMensaje.setStyle("-fx-text-fill: green;");
        }
    }
    
    @FXML
    private void manejarGuardarAsignacion() {
        if (tareaSeleccionada == null || empleadoSeleccionado == null) {
            mostrarError("❌ Debe seleccionar tanto una tarea como un empleado");
            return;
        }
        
        try {
            // Guardar la asignación en la base de datos
            boolean exito = guardarAsignacionEnBD(tareaSeleccionada, empleadoSeleccionado);
            
            if (exito) {
                mostrarExito("✅ Asignación guardada exitosamente!\n" +
                            "Tarea: " + tareaSeleccionada.getNombre() + "\n" +
                            "Empleado: " + empleadoSeleccionado.getNombre());
                manejarLimpiarSeleccion();
            } else {
                mostrarError("❌ Error al guardar la asignación en la base de datos");
            }
            
        } catch (Exception e) {
            mostrarError("❌ Error al guardar asignación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean guardarAsignacionEnBD(Operacion tarea, Empleado empleado) {
        // Por ahora, simulamos que se guardó correctamente
        // En una implementación real, aquí guardarías en la tabla 'asignaciones'
        System.out.println("GUARDANDO ASIGNACIÓN:");
        System.out.println("Tarea ID: " + tarea.getId() + " - " + tarea.getNombre());
        System.out.println("Empleado ID: " + empleado.getId() + " - " + empleado.getNombre());
        System.out.println("Fecha: " + java.time.LocalDateTime.now());
        
        // TODO: Implementar el guardado real en la base de datos
        // Necesitarías crear una tabla 'asignaciones' y un repositorio para ella
        
        return true; // Simulación exitosa
    }
    
    @FXML
    private void manejarLimpiarSeleccion() {
        listaTareas.getSelectionModel().clearSelection();
        listaEmpleados.getSelectionModel().clearSelection();
        tareaSeleccionada = null;
        empleadoSeleccionado = null;
        etiquetaTareaSeleccionada.setText("");
        etiquetaEmpleadoSeleccionado.setText("");
        etiquetaTareaFinal.setText("Ninguna seleccionada");
        etiquetaEmpleadoFinal.setText("Ninguno seleccionado");
        etiquetaMensaje.setText("");
    }
    
    @FXML
    private void manejarVolverAlPanelPrincipal() {
        try {
            Stage stageActual = (Stage) listaTareas.getScene().getWindow();
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
        }
    }
    
    private void mostrarError(String mensaje) {
        etiquetaMensaje.setText(mensaje);
        etiquetaMensaje.setStyle("-fx-text-fill: red;");
    }
    
    private void mostrarExito(String mensaje) {
        etiquetaMensaje.setText(mensaje);
        etiquetaMensaje.setStyle("-fx-text-fill: green;");
    }
}