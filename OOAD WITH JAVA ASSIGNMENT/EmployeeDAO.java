import java.sql.*;

public class EmployeeDAO {
    
    // Verify employee login
    public static boolean verifyEmployeeLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM employees WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            System.err.println("Error verifying employee login: " + e.getMessage());
            return false;
        }
    }
    
    // Create new employee
    public static boolean createEmployee(int employeeId, String name, String position, String username, String password) {
        String sql = "INSERT INTO employees (employee_id, name, position, username, password) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, name);
            pstmt.setString(3, position);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating employee: " + e.getMessage());
            return false;
        }
    }
}