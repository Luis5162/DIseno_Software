package com.autorefacsys.controlador;

import com.autorefacsys.servicio.AuthServicio;
import com.autorefacsys.repositorio.UsuarioRepositorio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginControlador {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private Button btnLogin;
    @FXML private Label lblMensaje;
    
    private AuthServicio authServicio;
    
    public LoginControlador() {
        this.authServicio = new AuthServicio(new UsuarioRepositorio());
    }
    
    @FXML
    public void initialize() {
        txtContraseña.setOnAction(e -> manejarLogin());
    }
    
    @FXML
    private void manejarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contraseña = txtContraseña.getText();
        
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarError("Por favor, completa todos los campos");
            return;
        }
        
        if (authServicio.autenticar(usuario, contraseña)) {
            mostrarPanelPrincipal();
        } else {
            mostrarError("Credenciales incorrectas. Intenta nuevamente.");
            limpiarCampos();
        }
    }
    
    private void mostrarPanelPrincipal() {
        try {
            Stage stageActual = (Stage) btnLogin.getScene().getWindow();
            stageActual.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autorefacsys/vista/panel_principal.fxml"));
            Parent root = loader.load();
            
            Stage mainStage = new Stage();
            mainStage.setTitle("AutoRefacSys - Bienvenido " + authServicio.getUsuarioLogueado().getUsuario());
            mainStage.setScene(new Scene(root, 1000, 700));
            mainStage.show();
            
        } catch (Exception e) {
            mostrarError("Error al cargar la aplicación: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
    
    private void limpiarCampos() {
        txtContraseña.clear();
        txtUsuario.requestFocus();
    }
}