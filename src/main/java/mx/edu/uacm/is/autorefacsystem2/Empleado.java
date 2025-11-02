package mx.edu.uacm.is.autorefacsystem2;

/**
 *
 * @author Iker
 */
public class Empleado {
    
    private final String idEmpleado;
    private final String nombre;
    private final String puesto; 

    public Empleado(String idEmpleado, String nombre, String puesto) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.puesto = puesto;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

   
}
    

