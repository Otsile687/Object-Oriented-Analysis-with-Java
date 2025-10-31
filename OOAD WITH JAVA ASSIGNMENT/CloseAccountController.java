import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;

public class CloseAccountController {

    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField accountNumberField;
    @FXML private TextField customerIdField;
    @FXML private Button closeAccountButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    // Track closed accounts to prevent multiple closures
    private static Set<String> closedAccounts = new HashSet<>();

    @FXML
    private void initialize() {
        System.out.println("CloseAccountController initialized!");
        
        // Include all account types for closure
        accountTypeComboBox.getItems().addAll("Savings", "Checking", "Investment");
        
        messageLabel.setText("Enter account details to close account\nNote: Savings accounts can only be withdrawn when closed");
    }

    @FXML
    private void handleCloseAccount() {
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();
        String customerId = customerIdField.getText();
        
        // Validate inputs
        if (accountType == null || accountNumber.isEmpty() || customerId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }
        
        // Check if account is already closed
        String accountKey = accountType + "-" + accountNumber;
        if (closedAccounts.contains(accountKey)) {
            showAlert(Alert.AlertType.ERROR, "Error", 
                "This account has already been closed and withdrawn.\n\n" +
                "Account: " + accountNumber + " (" + accountType + ")\n" +
                "Once closed, an account cannot be used again.");
            return;
        }
        
        try {
            // Simulate account closure and final withdrawal
            boolean success = processAccountClosure(accountType, accountNumber, customerId);
            
            if (success) {
                // Mark account as closed
                closedAccounts.add(accountKey);
                
                // Calculate final balance with interest/returns if applicable
                double finalBalance = calculateFinalBalance(accountType, accountNumber);
                
                messageLabel.setText(String.format(
                    "Account Closed Successfully!\n\n" +
                    "Account Details:\n" +
                    "Type: %s Account\n" +
                    "Number: %s\n" +
                    "Customer ID: %s\n\n" +
                    "Final Withdrawal: $%.2f\n\n" +
                    "Account has been permanently closed.\n" +
                    "No further transactions are allowed.",
                    accountType, accountNumber, customerId, finalBalance
                ));
                
                // Clear fields for next operation
                accountNumberField.clear();
                customerIdField.clear();
                accountTypeComboBox.setValue(null);
                
            } else {
                messageLabel.setText("Account closure failed: Account not found or has balance issues");
            }
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error processing account closure: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            System.out.println("Returning to employee menu...");
            Parent root = FXMLLoader.load(getClass().getResource("EmployeeMenu.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error returning to employee menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean processAccountClosure(String accountType, String accountNumber, String customerId) {
        System.out.println("Processing account closure:");
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Customer ID: " + customerId);
        
        // Simulate account validation
        // In real system, this would check if account exists, belongs to customer, etc.
        boolean isValidAccount = Math.random() > 0.2; // 80% success rate for demo
        
        if (isValidAccount) {
            System.out.println("Account closure approved for " + accountType + " account " + accountNumber);
            return true;
        } else {
            System.out.println("Account closure denied - validation failed");
            return false;
        }
    }

    private double calculateFinalBalance(String accountType, String accountNumber) {
        // Simulate calculating final balance with interest/returns
        double baseBalance = 500 + (Math.random() * 5000); // Random balance between $500 and $5500
        
        switch (accountType) {
            case "Savings":
                // Add 2% interest for savings
                return baseBalance * 1.02;
            case "Investment":
                // Add 5% returns for investment
                return baseBalance * 1.05;
            case "Checking":
                // No additional interest for checking
                return baseBalance;
            default:
                return baseBalance;
        }
    }

    // Method to check if an account is already closed (for other parts of the system)
    public static boolean isAccountClosed(String accountType, String accountNumber) {
        String accountKey = accountType + "-" + accountNumber;
        return closedAccounts.contains(accountKey);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}