import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    // Create a new customer
    public static boolean createCustomer(Customer customer, String username, String password) {
        String sql = "INSERT INTO customers (customer_id, name, address, phone_number, age, gender, occupation, customer_type, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customer.getCustomerID());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setInt(5, customer.getAge());
            pstmt.setString(6, customer.getGender());
            pstmt.setString(7, customer.getOccupation());
            pstmt.setString(8, customer instanceof Individual ? "Individual" : "Company");
            pstmt.setString(9, username);
            pstmt.setString(10, password);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            return false;
        }
    }
    
    // Get customer by ID
    public static Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createCustomerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer: " + e.getMessage());
        }
        
        return null;
    }
    
    // Get all customers
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        
        try (Connection conn = DatabaseSetup.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(createCustomerFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all customers: " + e.getMessage());
        }
        
        return customers;
    }
    
    // Update customer
    public static boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, phone_number = ?, age = ?, gender = ?, occupation = ? WHERE customer_id = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setInt(4, customer.getAge());
            pstmt.setString(5, customer.getGender());
            pstmt.setString(6, customer.getOccupation());
            pstmt.setInt(7, customer.getCustomerID());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }
    
    // Delete customer
    public static boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
    
    // Verify customer login
    public static boolean verifyCustomerLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM customers WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            System.err.println("Error verifying customer login: " + e.getMessage());
            return false;
        }
    }
    
    // Check if username exists
    public static boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM customers WHERE username = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            return false;
        }
    }
    
    private static Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        int customerId = rs.getInt("customer_id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phone_number");
        int age = rs.getInt("age");
        String gender = rs.getString("gender");
        String occupation = rs.getString("occupation");
        String customerType = rs.getString("customer_type");
        
        if ("Individual".equals(customerType)) {
            return new Individual(customerId, name, address, phoneNumber, age, gender, occupation);
        } else {
            return new Company(customerId, name, address, phoneNumber, age, gender, occupation);
        }
    }
}