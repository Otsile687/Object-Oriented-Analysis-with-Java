import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WithdrawController {

    @FXML
    private TextField withdrawAmountField;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField accountNumberField;

    @FXML
    private Button confirmWithdrawButton;

    @FXML
    private Button checkBalanceButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button transactionHistoryButton;

    @FXML
    private Button logoutButton;

    // Reference to bank system (you'll need to inject this)
    private Bank bank;

    @FXML
    private void initialize() {
        // Initialize account types
        accountTypeComboBox.getItems().addAll(Withdraw.getSupportedAccountTypes());
        
        // Set up input validation
        setupValidation();
    }

    @FXML
    private void handleConfirmWithdraw() {
        try {
            // Get input values
            double amount = Double.parseDouble(withdrawAmountField.getText());
            String accountType = accountTypeComboBox.getValue();
            int accountNumber = Integer.parseInt(accountNumberField.getText());
            
            // Create withdrawal object
            Withdraw withdrawal = new Withdraw(amount, accountType, accountNumber);
            
            // Validate withdrawal
            if (!withdrawal.validateWithdrawal()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Withdrawal", 
                         "Please check your withdrawal details.", 
                         "Amount must be positive and all fields must be filled correctly.");
                return;
            }
            
            // Find the account (you'll need to implement this based on your Bank class)
            Account account = findAccount(accountNumber, accountType);
            if (account == null) {
                showAlert(Alert.AlertType.ERROR, "Account Not Found", 
                         "Account not found.", 
                         "Please check the account number and type.");
                return;
            }
            
            // Process withdrawal
            boolean success = withdrawal.processWithdrawal(account);
            
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Withdrawal Successful", 
                         "Withdrawal processed successfully.", 
                         withdrawal.getWithdrawalDetails());
                
                // Clear fields after successful withdrawal
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Withdrawal Failed", 
                         "Insufficient funds or withdrawal limit exceeded.", 
                         "Please check your account balance and try again.");
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", 
                     "Please enter valid numbers.", 
                     "Amount and Account Number must be valid numbers.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", 
                     "An error occurred during withdrawal.", 
                     e.getMessage());
        }
    }

    // Helper method to find account (you'll need to implement based on your Bank class)
    private Account findAccount(int accountNumber, String accountType) {
        // This is a placeholder - implement based on your Bank class structure
        // Example: return bank.findAccount(accountNumber, accountType);
        
        // For now, return a mock account for testing
        System.out.println("Looking for account: " + accountNumber + " Type: " + accountType);
        return new SavingsAccount(accountNumber, 1000.0, 0.05); // Mock account
    }

    private void setupValidation() {
        // Add input listeners for real-time validation
        withdrawAmountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                withdrawAmountField.setText(oldValue);
            }
        });
        
        accountNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                accountNumberField.setText(oldValue);
            }
        });
    }

    private void clearFields() {
        withdrawAmountField.clear();
        accountNumberField.clear();
        accountTypeComboBox.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Navigation methods (keep your existing navigation code)
    @FXML
    private void handleCheckBalance() {
        navigateTo("CheckBalance.fxml");
    }

    @FXML
    private void handleDeposit() {
        navigateTo("Deposit.fxml");
    }

    @FXML
    private void handleTransactionHistory() {
        navigateTo("TransactionHistory.fxml");
    }

    @FXML
    private void handleLogout() {
        navigateTo("BankLogIN.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Setter for bank reference
    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
