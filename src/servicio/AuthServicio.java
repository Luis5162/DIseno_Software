package com.autorefacsys.servicio;

import com.autorefacsys.modelo.Usuario;
import com.autorefacsys.repositorio.UsuarioRepositorio;

public class AuthServicio {
    private UsuarioRepositorio usuarioRepositorio;
    private Usuario usuarioLogueado;
    
    public AuthServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    
    public boolean autenticar(String usuario, String contraseña) {
        Usuario usuarioEncontrado = usuarioRepositorio.buscarPorUsuario(usuario);
        if (usuarioEncontrado != null && usuarioEncontrado.validarCredenciales(usuario, contraseña)) {
            this.usuarioLogueado = usuarioEncontrado;
            return true;
        }
        return false;
    }
    
    public void cerrarSesion() {
        this.usuarioLogueado = null;
    }
    
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    public boolean isAutenticado() {
        return usuarioLogueado != null;
    }
}