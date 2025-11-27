import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    
    // Create a new transaction
    public static boolean createTransaction(int accountNumber, String transactionType, double amount, String description) {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, description) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            pstmt.setString(2, transactionType);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, description);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating transaction: " + e.getMessage());
            return false;
        }
    }
    
    // Get transactions for an account
    public static List<Transaction> getTransactionsByAccount(int accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                transactions.add(createTransactionFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    // Get recent transactions (for dashboard)
    public static List<Transaction> getRecentTransactions(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                transactions.add(createTransactionFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting recent transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    private static Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {
        int transactionId = rs.getInt("transaction_id");
        int accountNumber = rs.getInt("account_number");
        String transactionType = rs.getString("transaction_type");
        double amount = rs.getDouble("amount");
        Timestamp transactionDate = rs.getTimestamp("transaction_date");
        String description = rs.getString("description");
        
        return new Transaction(transactionId, accountNumber, transactionType, amount, transactionDate, description);
    }
}

// Transaction class
class Transaction {
    private int transactionId;
    private int accountNumber;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    private String description;
    
    public Transaction(int transactionId, int accountNumber, String transactionType, double amount, Timestamp transactionDate, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }
    
    // Getters
    public int getTransactionId() { return transactionId; }
    public int getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public Timestamp getTransactionDate() { return transactionDate; }
    public String getDescription() { return description; }
    
    @Override
    public String toString() {
        return String.format("%s: %s - $%.2f - %s", 
            transactionDate.toString(), transactionType, amount, description);
    }
}