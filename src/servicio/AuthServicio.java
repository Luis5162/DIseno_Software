package servicio;

import modelo.Usuario;
import repositorio.UsuarioRepositorio;

public class AuthServicio {
    private UsuarioRepositorio usuarioRepositorio;
    private Usuario usuarioLogueado;
    
    public AuthServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    
    public boolean autenticar(String usuario, String contraseña) {
        if (usuarioRepositorio.validarCredenciales(usuario, contraseña)) {
            this.usuarioLogueado = usuarioRepositorio.buscarAdminPorUsuario(usuario);
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