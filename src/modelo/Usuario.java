package com.autorefacsys.modelo;

public class Usuario {
    private String id;
    private String usuario;
    private String contraseña;
    private String email;
    private Rol rol;
    private boolean activo;
    
    public Usuario(String usuario, String contraseña, String email, Rol rol) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.email = email;
        this.rol = rol;
        this.activo = true;
    }
    
    // Getters y setters
    public String getUsuario() { return usuario; }
    public String getContraseña() { return contraseña; }
    public String getEmail() { return email; }
    public Rol getRol() { return rol; }
    public boolean isActivo() { return activo; }
    
    public boolean validarCredenciales(String usuario, String contraseña) {
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña) && this.activo;
    }
}