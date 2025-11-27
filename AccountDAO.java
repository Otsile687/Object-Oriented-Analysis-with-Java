import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    // Create a new account
    public static boolean createAccount(Account account, int customerId) {
        String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance, interest_rate, overdraft_limit, return_rate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, account.getAccountNumber());
            pstmt.setInt(2, customerId);
            pstmt.setString(3, getAccountType(account));
            pstmt.setDouble(4, account.getBalance());
            
            // Set account-specific parameters
            if (account instanceof SavingsAccount) {
                pstmt.setDouble(5, 0.02); // 2% interest rate
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
            } else if (account instanceof CheckAccount) {
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setDouble(6, ((CheckAccount) account).getOverdraftLimit());
                pstmt.setNull(7, Types.DOUBLE);
            } else if (account instanceof InvestmentAccount) {
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setDouble(7, 0.05); // 5% return rate
            } else {
                pstmt.setNull(5, Types.DOUBLE);
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
            }
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }
    
    // Get account by account number
    public static Account getAccountByNumber(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND is_active = true";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createAccountFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting account: " + e.getMessage());
        }
        
        return null;
    }
    
    // Get all accounts for a customer
    public static List<Account> getAccountsByCustomer(int customerId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ? AND is_active = true";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                accounts.add(createAccountFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer accounts: " + e.getMessage());
        }
        
        return accounts;
    }
    
    // Update account balance
    public static boolean updateAccountBalance(int accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, newBalance);
            pstmt.setInt(2, accountNumber);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating account balance: " + e.getMessage());
            return false;
        }
    }
    
    // Close account (soft delete)
    public static boolean closeAccount(int accountNumber) {
        String sql = "UPDATE accounts SET is_active = false WHERE account_number = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error closing account: " + e.getMessage());
            return false;
        }
    }
    
    // Check if account exists and is active
    public static boolean accountExists(int accountNumber) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE account_number = ? AND is_active = true";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            System.err.println("Error checking account existence: " + e.getMessage());
            return false;
        }
    }
    
    private static String getAccountType(Account account) {
        if (account instanceof SavingsAccount) return "Savings";
        if (account instanceof CheckAccount) return "Checking";
        if (account instanceof InvestmentAccount) return "Investment";
        return "General";
    }
    
    private static Account createAccountFromResultSet(ResultSet rs) throws SQLException {
        int accountNumber = rs.getInt("account_number");
        double balance = rs.getDouble("balance");
        String accountType = rs.getString("account_type");
        
        switch (accountType) {
            case "Savings":
                double interestRate = rs.getDouble("interest_rate");
                return new SavingsAccount(accountNumber, balance, interestRate);
            case "Checking":
                double overdraftLimit = rs.getDouble("overdraft_limit");
                return new CheckAccount(accountNumber, balance, overdraftLimit);
            case "Investment":
                double returnRate = rs.getDouble("return_rate");
                return new InvestmentAccount(accountNumber, balance, returnRate);
            default:
                return new GeneralAccount(accountNumber, balance);
        }
    }
}

// General account class for database purposes
class GeneralAccount extends Account {
    public GeneralAccount(int accountNumber, double balance) {
        super(accountNumber, balance);
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}