package com.autorefacsys.modelo;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioTrabajo {
    private DayOfWeek diaSemana;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    
    public HorarioTrabajo(DayOfWeek diaSemana, LocalTime horaEntrada, LocalTime horaSalida) {
        this.diaSemana = diaSemana;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    
    // Getters
    public DayOfWeek getDiaSemana() { return diaSemana; }
    public LocalTime getHoraEntrada() { return horaEntrada; }
    public LocalTime getHoraSalida() { return horaSalida; }
}