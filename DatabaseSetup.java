import java.sql.*;

public class DatabaseSetup {
    private static final String URL = "jdbc:sqlite:bank_system.db";
    
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                createTables(conn);
                System.out.println("Database initialized successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    private static void createTables(Connection conn) {
        String[] createTableSQLs = {
            // Customers table
            """
            CREATE TABLE IF NOT EXISTS customers (
                customer_id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                address TEXT NOT NULL,
                phone_number TEXT NOT NULL,
                age INTEGER NOT NULL,
                gender TEXT NOT NULL,
                occupation TEXT NOT NULL,
                customer_type TEXT NOT NULL,
                username TEXT UNIQUE,
                password TEXT
            )
            """,
            
            // Accounts table
            """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number INTEGER PRIMARY KEY,
                customer_id INTEGER NOT NULL,
                account_type TEXT NOT NULL,
                balance REAL NOT NULL DEFAULT 0.0,
                interest_rate REAL,
                overdraft_limit REAL,
                return_rate REAL,
                is_active BOOLEAN DEFAULT true,
                created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
            )
            """,
            
            // Transactions table
            """
            CREATE TABLE IF NOT EXISTS transactions (
                transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
                account_number INTEGER NOT NULL,
                transaction_type TEXT NOT NULL,
                amount REAL NOT NULL,
                transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                description TEXT,
                FOREIGN KEY (account_number) REFERENCES accounts(account_number)
            )
            """,
            
            // Employees table
            """
            CREATE TABLE IF NOT EXISTS employees (
                employee_id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                position TEXT NOT NULL,
                username TEXT UNIQUE,
                password TEXT
            )
            """
        };
        
        try (Statement stmt = conn.createStatement()) {
            for (String sql : createTableSQLs) {
                stmt.execute(sql);
            }
            
            // Insert default employee
            insertDefaultEmployee(conn);
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    private static void insertDefaultEmployee(Connection conn) {
        String sql = "INSERT OR IGNORE INTO employees (employee_id, name, position, username, password) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "System Administrator");
            pstmt.setString(3, "Manager");
            pstmt.setString(4, "admin");
            pstmt.setString(5, "admin123");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting default employee: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}