package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña; // Solo para admin
    
    // Constructor para empleados (solo nombre)
    public Usuario(String nombre) {
        this.nombre = nombre;
    }
    
    // Constructor para admin (con credenciales)
    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }
    
    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContraseña() { return contraseña; }
    
    // Método para validar credenciales (solo admin)
    public boolean validarCredenciales(String contraseña) {
        return this.contraseña != null && this.contraseña.equals(contraseña);
    }
}