package com.autorefacsys.modelo;

import java.util.*;

public class Operacion {
    private String id;
    private String nombre;
    private String descripcion;
    private EstadoOperacion estado;
    private List<Tarea> tareas;
    
    public enum EstadoOperacion {
        NO_EJECUTADA, EN_EJECUCION, PAUSADA, DETENIDA, FINALIZADA
    }
    
    public Operacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = EstadoOperacion.NO_EJECUTADA;
        this.tareas = new ArrayList<>();
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public EstadoOperacion getEstado() { return estado; }
    public void setEstado(EstadoOperacion estado) { this.estado = estado; }
    
    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }
}