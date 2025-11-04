package com.autorefacsys.repositorio;

import com.autorefacsys.modelo.Rol;
import java.util.*;

public class RolRepositorio {
    private Map<String, Rol> roles = new HashMap<>();
    
    public RolRepositorio() {
        // Roles predefinidos
        roles.put("Administrador", new Rol("Administrador"));
        roles.put("Vendedor", new Rol("Vendedor"));
        roles.put("Almacenista", new Rol("Almacenista"));
    }
    
    public Rol buscarPorNombre(String nombre) {
        return roles.get(nombre);
    }
    
    public List<Rol> obtenerTodos() {
        return new ArrayList<>(roles.values());
    }
}