package mx.edu.uacm.is.autorefacsystem2;

/**
 *
 * @author Iker
 */
public class Empleado {
    
    protected String idEmpleado;
    protected String nombre;
    protected String puesto; 

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
    

