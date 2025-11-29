package com.autorefacsys.modelo;

import java.time.LocalTime;

public class Tarea {
    private String id;
    private String nombre;
    private String descripcion;
    private Rol rolRequerido;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    public Tarea(String nombre, String descripcion, Rol rolRequerido) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rolRequerido = rolRequerido;
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Rol getRolRequerido() { return rolRequerido; }
}