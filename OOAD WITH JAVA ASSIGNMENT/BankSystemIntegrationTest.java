// BankSystemIntegrationTest.java
public class BankSystemIntegrationTest {
    
    @Test
    public void testCompleteCustomerWorkflow() {
        System.out.println("=== COMPLETE CUSTOMER WORKFLOW TEST ===");
        
        try {
            // Step 1: Employee creates customer
            System.out.println("1. Creating customer...");
            String customerName = "Integration Test User";
            String customerId = "1001";
            
            // Simulate customer creation through DAO
            Customer customer = new Individual(
                Integer.parseInt(customerId), 
                customerName, 
                "123 Test St", 
                "555-0123", 
                35, "Female", "Developer"
            );
            
            boolean customerCreated = CustomerDAO.createCustomer(customer, "testuser", "testpass");
            assertTrue("Customer should be created successfully", customerCreated);
            System.out.println("✅ Customer created: " + customerId);
            
            // Step 2: Create account for customer
            System.out.println("2. Creating savings account...");
            Account savingsAccount = new SavingsAccount(2001, 1000.0, 0.02);
            boolean accountCreated = AccountDAO.createAccount(savingsAccount, Integer.parseInt(customerId));
            assertTrue("Account should be created successfully", accountCreated);
            System.out.println("✅ Account created: 2001");
            
            // Step 3: Perform transactions
            System.out.println("3. Performing deposit...");
            double initialBalance = savingsAccount.getBalance();
            savingsAccount.deposit(500.0);
            boolean balanceUpdated = AccountDAO.updateAccountBalance(2001, savingsAccount.getBalance());
            assertTrue("Balance should be updated", balanceUpdated);
            System.out.println("✅ Deposit completed: $" + initialBalance + " → $" + savingsAccount.getBalance());
            
            // Step 4: Perform withdrawal
            System.out.println("4. Performing withdrawal...");
            boolean withdrawSuccess = savingsAccount.withdraw(200.0);
            assertTrue("Withdrawal should succeed", withdrawSuccess);
            balanceUpdated = AccountDAO.updateAccountBalance(2001, savingsAccount.getBalance());
            assertTrue("Balance should be updated after withdrawal", balanceUpdated);
            System.out.println("✅ Withdrawal completed: Balance = $" + savingsAccount.getBalance());
            
            // Step 5: Verify final state
            Account verifiedAccount = AccountDAO.getAccountByNumber(2001);
            assertNotNull("Account should be retrievable from database", verifiedAccount);
            assertEquals("Balance should match", 1300.0, verifiedAccount.getBalance(), 0.01);
            System.out.println("✅ Final verification passed");
            
            System.out.println("🎉 COMPLETE WORKFLOW TEST PASSED!");
            
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        }
    }
    
    @Test 
    public void testEmployeeToCustomerFlow() {
        System.out.println("=== EMPLOYEE TO CUSTOMER FLOW TEST ===");
        
        try {
            // Step 1: Employee login
            System.out.println("1. Employee login...");
            boolean employeeAuth = EmployeeDAO.verifyEmployeeLogin("admin", "admin123");
            assertTrue("Employee should authenticate successfully", employeeAuth);
            System.out.println("✅ Employee authenticated");
            
            // Step 2: Create new customer (simulating employee action)
            System.out.println("2. Employee creates customer...");
            Customer newCustomer = new Individual(1002, "Flow Test User", 
                "456 Test Ave", "555-0456", 28, "Male", "Analyst");
            
            boolean customerCreated = CustomerDAO.createCustomer(newCustomer, "flowuser", "flowpass123");
            assertTrue("Customer should be created", customerCreated);
            System.out.println("✅ Customer created by employee");
            
            // Step 3: Customer login with generated credentials
            System.out.println("3. Customer login...");
            boolean customerAuth = CustomerDAO.verifyCustomerLogin("flowuser", "flowpass123");
            assertTrue("Customer should login with provided credentials", customerAuth);
            System.out.println("✅ Customer authenticated");
            
            // Step 4: Customer performs banking operations
            System.out.println("4. Customer banking operations...");
            Account customerAccount = new CheckAccount(2002, 2000.0, 1000.0);
            boolean accountCreated = AccountDAO.createAccount(customerAccount, 1002);
            assertTrue("Account should be created for customer", accountCreated);
            
            // Test overdraft protection
            boolean largeWithdraw = customerAccount.withdraw(2500.0); // Within overdraft limit
            assertTrue("Withdrawal within overdraft should succeed", largeWithdraw);
            System.out.println("✅ Customer transactions completed");
            
            System.out.println("🎉 EMPLOYEE-CUSTOMER FLOW TEST PASSED!");
            
        } catch (Exception e) {
            fail("Employee-customer flow test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testErrorScenarios() {
        System.out.println("=== ERROR SCENARIOS TEST ===");
        
        try {
            // Test invalid login
            System.out.println("1. Testing invalid credentials...");
            boolean invalidAuth = EmployeeDAO.verifyEmployeeLogin("wrong", "wrong");
            assertFalse("Invalid credentials should fail", invalidAuth);
            System.out.println("✅ Invalid login handled correctly");
            
            // Test account not found
            System.out.println("2. Testing non-existent account...");
            Account missingAccount = AccountDAO.getAccountByNumber(999999);
            assertNull("Non-existent account should return null", missingAccount);
            System.out.println("✅ Missing account handled correctly");
            
            // Test insufficient funds
            System.out.println("3. Testing insufficient funds...");
            Account testAccount = new SavingsAccount(3001, 100.0, 0.02);
            boolean insufficientWithdraw = testAccount.withdraw(200.0);
            assertFalse("Withdrawal beyond balance should fail", insufficientWithdraw);
            System.out.println("✅ Insufficient funds handled correctly");
            
            System.out.println("🎉 ERROR SCENARIOS TEST PASSED!");
            
        } catch (Exception e) {
            fail("Error scenarios test failed: " + e.getMessage());
        }
    }
}