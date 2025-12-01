package controlador;

import modelo.Operacion;
import modelo.Empleado;
import repositorio.OperacionRepositorio;
import repositorio.AsignacionRepositorio;
import servicio.EmpleadoServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

public class GestionOperacionesControlador {
    
    @FXML private ListView<Operacion> listViewTareas;
    @FXML private ListView<Empleado> listViewEmpleados;
    @FXML private Label lblTareaSeleccionada;
    @FXML private Label lblEmpleadoSeleccionado;
    @FXML private Label lblTareaFinal;
    @FXML private Label lblEmpleadoFinal;
    @FXML private Label lblMensaje;
    
    private OperacionRepositorio operacionRepositorio;
    private AsignacionRepositorio asignacionRepositorio;
    private EmpleadoServicio empleadoServicio;
    private Operacion tareaSeleccionada;
    private Empleado empleadoSeleccionado;
    
    public GestionOperacionesControlador() {
        this.operacionRepositorio = new OperacionRepositorio();
        this.asignacionRepositorio = new AsignacionRepositorio();
        this.empleadoServicio = new EmpleadoServicio();
    }
    
    @FXML
    public void initialize() {
        // Configurar cómo se muestran los items en las listas
        configurarCellFactories();
        cargarTareas();
        cargarEmpleados();
        configurarListeners();
    }
    
    private void configurarCellFactories() {
        // Configurar cómo se muestran las tareas en el ListView
        listViewTareas.setCellFactory(lv -> new ListCell<Operacion>() {
            @Override
            protected void updateItem(Operacion tarea, boolean empty) {
                super.updateItem(tarea, empty);
                if (empty || tarea == null) {
                    setText(null);
                } else {
                    setText(tarea.getNombre() + " - " + tarea.getDescripcion());
                }
            }
        });
        
        // Configurar cómo se muestran los empleados en el ListView
        listViewEmpleados.setCellFactory(lv -> new ListCell<Empleado>() {
            @Override
            protected void updateItem(Empleado empleado, boolean empty) {
                super.updateItem(empleado, empty);
                if (empty || empleado == null) {
                    setText(null);
                } else {
                    setText(empleado.getNombre());
                }
            }
        });
    }
    
    private void cargarTareas() {
        try {
            List<Operacion> tareas = operacionRepositorio.obtenerTodasLasOperaciones();
            listViewTareas.getItems().clear();
            listViewTareas.getItems().addAll(tareas);
            
            System.out.println("Tareas cargadas: " + tareas.size());
            for (Operacion tarea : tareas) {
                System.out.println("Tarea - ID: " + tarea.getId() + ", Nombre: " + tarea.getNombre());
            }
            
            if (tareas.isEmpty()) {
                lblMensaje.setText("No hay tareas disponibles. Crea algunas tareas primero.");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarEmpleados() {
        try {
            List<Empleado> empleados = empleadoServicio.obtenerTodosLosEmpleados();
            listViewEmpleados.getItems().clear();
            listViewEmpleados.getItems().addAll(empleados);
            
            System.out.println("Empleados cargados: " + empleados.size());
            for (Empleado empleado : empleados) {
                System.out.println("Empleado - ID: " + empleado.getId() + ", Nombre: " + empleado.getNombre());
            }
            
            if (empleados.isEmpty()) {
                lblMensaje.setText(lblMensaje.getText() + "\nNo hay empleados disponibles. Crea algunos empleados primero.");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void configurarListeners() {
        // Listener para selección de tareas
        listViewTareas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    tareaSeleccionada = newValue;
                    lblTareaSeleccionada.setText("✓ Seleccionada: " + newValue.getNombre());
                    lblTareaFinal.setText(newValue.getNombre());
                    actualizarMensajeAsignacion();
                    System.out.println("Tarea seleccionada - ID: " + newValue.getId() + ", Nombre: " + newValue.getNombre());
                }
            });
        
        // Listener para selección de empleados
        listViewEmpleados.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    empleadoSeleccionado = newValue;
                    lblEmpleadoSeleccionado.setText("✓ Seleccionado: " + newValue.getNombre());
                    lblEmpleadoFinal.setText(newValue.getNombre());
                    actualizarMensajeAsignacion();
                    System.out.println("Empleado seleccionado - ID: " + newValue.getId() + ", Nombre: " + newValue.getNombre());
                }
            });
    }
    
    private void actualizarMensajeAsignacion() {
        if (tareaSeleccionada != null && empleadoSeleccionado != null) {
            lblMensaje.setText("✅ Listo para asignar: '" + tareaSeleccionada.getNombre() + 
                              "' a '" + empleadoSeleccionado.getNombre() + "'");
            lblMensaje.setStyle("-fx-text-fill: green;");
        }
    }
    
    @FXML
    private void guardarAsignacion() {
        if (tareaSeleccionada == null || empleadoSeleccionado == null) {
            mostrarError("❌ Debe seleccionar tanto una tarea como un empleado");
            return;
        }
        
        try {
            System.out.println("Intentando guardar asignación:");
            System.out.println("Empleado ID: " + empleadoSeleccionado.getId() + ", Nombre: " + empleadoSeleccionado.getNombre());
            System.out.println("Tarea ID: " + tareaSeleccionada.getId() + ", Nombre: " + tareaSeleccionada.getNombre());
            
            // Verificar si ya existe esta asignación
            if (asignacionRepositorio.existeAsignacion(empleadoSeleccionado.getId(), tareaSeleccionada.getId())) {
                mostrarError("⚠️ Esta asignación ya existe: '" + 
                           tareaSeleccionada.getNombre() + "' ya está asignada a '" + 
                           empleadoSeleccionado.getNombre() + "'");
                return;
            }
            
            // Guardar en la base de datos
            boolean exito = asignacionRepositorio.guardarAsignacion(
                empleadoSeleccionado.getId(), 
                tareaSeleccionada.getId()
            );
            
            if (exito) {
                mostrarExito("✅ Asignación guardada exitosamente!\n" +
                            "Tarea: " + tareaSeleccionada.getNombre() + "\n" +
                            "Empleado: " + empleadoSeleccionado.getNombre() + "\n" +
                            "Estado: PENDIENTE");
                
                // Mostrar todas las asignaciones en consola para verificar
                asignacionRepositorio.mostrarAsignaciones();
                
                limpiarSeleccion();
            } else {
                mostrarError("❌ Error al guardar la asignación en la base de datos");
            }
            
        } catch (Exception e) {
            mostrarError("❌ Error al guardar asignación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void limpiarSeleccion() {
        listViewTareas.getSelectionModel().clearSelection();
        listViewEmpleados.getSelectionModel().clearSelection();
        tareaSeleccionada = null;
        empleadoSeleccionado = null;
        lblTareaSeleccionada.setText("");
        lblEmpleadoSeleccionado.setText("");
        lblTareaFinal.setText("Ninguna seleccionada");
        lblEmpleadoFinal.setText("Ninguno seleccionado");
        lblMensaje.setText("");
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