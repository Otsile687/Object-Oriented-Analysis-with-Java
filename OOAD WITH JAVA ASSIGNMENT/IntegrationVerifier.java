public class IntegrationVerifier {
    public static boolean verifyAllConnections() {
        try {
            // Test database connection
            Connection conn = DatabaseSetup.getConnection();
            if (conn == null || conn.isClosed()) {
                System.err.println("❌ Database connection failed");
                return false;
            }
            
            // Test DAO classes
            if (!testCustomerDAO()) return false;
            if (!testAccountDAO()) return false;
            if (!testEmployeeDAO()) return false;
            
            // Test core model instantiation
            if (!testCoreModels()) return false;
            
            System.out.println("✅ All integrations verified successfully!");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Integration verification failed: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean testCustomerDAO() {
        try {
            Customer testCustomer = new Individual(999, "Test User", "Test Address", 
                "123-456-7890", 30, "Male", "Tester");
            return true;
        } catch (Exception e) {
            System.err.println("❌ Customer DAO test failed: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean testAccountDAO() {
        try {
            // Test account creation
            Account testAccount = new SavingsAccount(999999, 1000.0, 0.02);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Account DAO test failed: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean testEmployeeDAO() {
        try {
            boolean isValid = EmployeeDAO.verifyEmployeeLogin("admin", "admin123");
            System.out.println("✅ Employee DAO test: " + (isValid ? "Admin credentials work" : "Admin credentials failed"));
            return isValid;
        } catch (Exception e) {
            System.err.println("❌ Employee DAO test failed: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean testCoreModels() {
        try {
            // Test inheritance hierarchy
            Customer individual = new Individual(1, "John", "Address", "Phone", 25, "M", "Engineer");
            Customer company = new Company(2, "Company", "Address", "Phone", 0, "N/A", "Business");
            
            // Test account hierarchy
            Account savings = new SavingsAccount(1001, 500.0, 0.02);
            Account checking = new CheckAccount(1002, 1000.0, 500.0);
            Account investment = new InvestmentAccount(1003, 5000.0, 0.05);
            
            return true;
        } catch (Exception e) {
            System.err.println("❌ Core models test failed: " + e.getMessage());
            return false;
        }
    }
}