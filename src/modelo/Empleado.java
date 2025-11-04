package com.autorefacsys.modelo;

import java.util.*;

public class Empleado {
    private String id;
    private String nombre;
    private String email;
    private Rol rol;
    private List<HorarioTrabajo> horarios;
    
    public Empleado(String nombre, String email, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.horarios = new ArrayList<>();
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }
    
    public void agregarHorario(HorarioTrabajo horario) {
        this.horarios.add(horario);
    }
}