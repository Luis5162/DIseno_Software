package repositorio;

import conexion.DatabaseConnection;
import java.sql.*;

public class AsignacionRepositorio {
    
    public boolean guardarAsignacion(int empleadoId, int tareaId) {
        // Usamos los valores por defecto de la tabla: fecha_asignacion = CURRENT_TIMESTAMP, estado = 'PENDIENTE'
        String sql = "INSERT INTO empleado_tarea (empleado_id, tarea_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empleadoId);
            pstmt.setInt(2, tareaId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar asignación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean existeAsignacion(int empleadoId, int tareaId) {
        String sql = "SELECT COUNT(*) FROM empleado_tarea WHERE empleado_id = ? AND tarea_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empleadoId);
            pstmt.setInt(2, tareaId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar asignación: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    // Método adicional para obtener información de las asignaciones
    public void mostrarAsignaciones() {
        String sql = "SELECT et.id, e.nombre as empleado, t.nombre as tarea, et.fecha_asignacion, et.estado " +
                     "FROM empleado_tarea et " +
                     "JOIN empleado e ON et.empleado_id = e.id " +
                     "JOIN tareas t ON et.tarea_id = t.id " +
                     "ORDER BY et.fecha_asignacion DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("=== ASIGNACIONES EN LA BASE DE DATOS ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 " | Empleado: " + rs.getString("empleado") +
                                 " | Tarea: " + rs.getString("tarea") +
                                 " | Fecha: " + rs.getTimestamp("fecha_asignacion") +
                                 " | Estado: " + rs.getString("estado"));
            }
            System.out.println("========================================");
            
        } catch (SQLException e) {
            System.err.println("Error al obtener asignaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
}