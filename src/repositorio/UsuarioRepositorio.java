package repositorio;

import modelo.Usuario;
import java.util.*;

public class UsuarioRepositorio {
    private Map<String, Usuario> usuariosAdmin = new HashMap<>();
    
    public UsuarioRepositorio() {
        inicializarUsuariosDemo();
    }
    
    private void inicializarUsuariosDemo() {
        // Solo el usuario admin para login - SIN ROL
        usuariosAdmin.put("admin", new Usuario("admin", "admin123"));
    }
    
    public Usuario buscarAdminPorUsuario(String usuario) {
        return usuariosAdmin.get(usuario);
    }
    
    public boolean validarCredenciales(String usuario, String contraseña) {
        Usuario admin = usuariosAdmin.get(usuario);
        return admin != null && "admin123".equals(contraseña);
    }
}