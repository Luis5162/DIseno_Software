package repositorio;

import modelo.Operacion;
import conexion.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperacionRepositorio {
    
    public boolean guardarOperacion(Operacion operacion) {
        String sql = "INSERT INTO tareas (nombre, descripcion) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, operacion.getNombre());
            pstmt.setString(2, operacion.getDescripcion());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        operacion.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar operación: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Operacion> obtenerTodasLasOperaciones() {
        List<Operacion> operaciones = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM tareas ORDER BY id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Operacion operacion = new Operacion(
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                operacion.setId(rs.getInt("id"));
                operaciones.add(operacion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener operaciones: " + e.getMessage());
            e.printStackTrace();
        }
        return operaciones;
    }
    
    public boolean eliminarOperacion(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar operación: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}