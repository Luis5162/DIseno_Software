package com.autorefacsys.controlador;

import com.autorefacsys.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PanelPrincipalControlador {
    @FXML private Label lblUsuario;
    
    private Usuario usuarioLogueado;
    
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        actualizarInterfazSegunRol();
    }
    
    private void actualizarInterfazSegunRol() {
        if (usuarioLogueado != null) {
            lblUsuario.setText("Bienvenido: " + usuarioLogueado.getUsuario() + " - " + usuarioLogueado.getRol().getNombre());
        }
    }
}