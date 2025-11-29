package com.autorefacsys.modelo;

public class Rol {
    private String id;
    private String nombre;
    
    public Rol(String nombre) {
        this.nombre = nombre;
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}