package com.autorefacsys.repositorio;

import com.autorefacsys.modelo.Usuario;
import com.autorefacsys.modelo.Rol;
import java.util.*;

public class UsuarioRepositorio {
    private Map<String, Usuario> usuarios = new HashMap<>();
    
    public UsuarioRepositorio() {
        inicializarUsuariosDemo();
    }
    
    private void inicializarUsuariosDemo() {
        Rol rolAdmin = new Rol("Administrador");
        Rol rolVendedor = new Rol("Vendedor");
        Rol rolAlmacenista = new Rol("Almacenista");
        
        usuarios.put("admin", new Usuario("admin", "admin123", "admin@refaccionaria.com", rolAdmin));
        usuarios.put("vendedor1", new Usuario("vendedor1", "vendedor123", "vendedor1@refaccionaria.com", rolVendedor));
        usuarios.put("almacenista1", new Usuario("almacenista1", "almacen123", "almacen@refaccionaria.com", rolAlmacenista));
    }
    
    public Usuario buscarPorUsuario(String usuario) {
        return usuarios.get(usuario);
    }
    
    public Usuario guardar(Usuario usuario) {
        usuarios.put(usuario.getUsuario(), usuario);
        return usuario;
    }
}