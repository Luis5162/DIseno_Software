package servicio;

import modelo.Empleado;
import repositorio.EmpleadoRepositorio;
import java.util.List;

public class EmpleadoServicio {
    private EmpleadoRepositorio empleadoRepositorio;
    
    public EmpleadoServicio() {
        this.empleadoRepositorio = new EmpleadoRepositorio();
    }
    
    public boolean crearEmpleado(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        Empleado empleado = new Empleado(nombre.trim());
        return empleadoRepositorio.guardarEmpleado(empleado);
    }
    
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepositorio.obtenerTodosLosEmpleados();
    }
    
    public boolean eliminarEmpleado(int id) {
        return empleadoRepositorio.eliminarEmpleado(id);
    }
}