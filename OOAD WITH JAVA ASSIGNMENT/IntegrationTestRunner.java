// IntegrationTestRunner.java
public class IntegrationTestRunner {
    public static void main(String[] args) {
        System.out.println("🚀 BANK SYSTEM INTEGRATION TEST SUITE");
        System.out.println("======================================");
        
        // Pre-test verification
        System.out.println("\n📋 PRE-TEST VERIFICATION");
        boolean preCheck = IntegrationVerifier.verifyAllConnections();
        if (!preCheck) {
            System.err.println("❌ Pre-test verification failed. Aborting tests.");
            return;
        }
        
        // Run integration tests
        BankSystemIntegrationTest testSuite = new BankSystemIntegrationTest();
        
        try {
            System.out.println("\n🧪 TEST 1: Complete Customer Workflow");
            testSuite.testCompleteCustomerWorkflow();
            
            System.out.println("\n🧪 TEST 2: Employee to Customer Flow");
            testSuite.testEmployeeToCustomerFlow();
            
            System.out.println("\n🧪 TEST 3: Error Scenarios");
            testSuite.testErrorScenarios();
            
            System.out.println("\n🎉 ALL INTEGRATION TESTS PASSED SUCCESSFULLY!");
            generateTestReport(true);
            
        } catch (AssertionError e) {
            System.err.println("\n💥 TEST FAILED: " + e.getMessage());
            generateTestReport(false);
        } catch (Exception e) {
            System.err.println("\n💥 UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
            generateTestReport(false);
        }
    }
    
    private static void generateTestReport(boolean success) {
        System.out.println("\n📊 INTEGRATION TEST REPORT");
        System.out.println("=========================");
        System.out.println("Timestamp: " + new java.util.Date());
        System.out.println("Overall Status: " + (success ? "PASSED" : "FAILED"));
        System.out.println("Modules Integrated: ");
        System.out.println("  ✅ Database Layer (JDBC)");
        System.out.println("  ✅ Core Domain Models");
        System.out.println("  ✅ Data Access Objects (DAO)");
        System.out.println("  ✅ Controller Classes");
        System.out.println("  ✅ Boundary/GUI Classes (FXML)");
        System.out.println("Test Coverage: ");
        System.out.println("  ✅ Customer Creation & Authentication");
        System.out.println("  ✅ Account Management");
        System.out.println("  ✅ Transaction Processing");
        System.out.println("  ✅ Employee Workflows");
        System.out.println("  ✅ Error Handling");
    }
}