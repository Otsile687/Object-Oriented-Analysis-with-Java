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

public class WithdrawController {

    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;
    @FXML private Button withdrawButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        System.out.println("WithdrawController initialized!");
        
        // Only include Checking and Investment accounts for regular withdrawals
        // Savings accounts can only be withdrawn when closed (handled separately)
        accountTypeComboBox.getItems().addAll("Checking", "Investment");
        
        messageLabel.setText("Enter withdrawal details above\nNote: Savings accounts can only be withdrawn when closed");
    }

    @FXML
    private void handleWithdraw() {
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();
        String amountText = amountField.getText();
        
        // Validate inputs
        if (accountType == null || accountNumber.isEmpty() || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }
        
        // Double-check that Savings account is not selected (shouldn't be possible from UI)
        if ("Savings".equals(accountType)) {
            showAlert(Alert.AlertType.ERROR, "Error", 
                "Savings accounts cannot be used for regular withdrawals.\n\n" +
                "To withdraw from a Savings account, you must close the account.\n" +
                "This can be done through the 'Close Account' option in the Employee menu.");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountText);
            
            if (amount <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Amount must be greater than 0");
                return;
            }
            
            // Additional validation for specific account types
            if ("Investment".equals(accountType) && amount > 10000) {
                showAlert(Alert.AlertType.ERROR, "Error", "Investment account withdrawal limit: $10,000 per transaction");
                return;
            }
            
            if ("Checking".equals(accountType) && amount > 5000) {
                showAlert(Alert.AlertType.ERROR, "Error", "Checking account withdrawal limit: $5,000 per transaction");
                return;
            }
            
            // Simulate withdrawal process
            boolean success = processWithdrawal(accountType, accountNumber, amount);
            
            if (success) {
                messageLabel.setText(String.format(
                    "Withdrawal Successful!\n" +
                    "Account: %s (%s)\n" +
                    "Amount: $%.2f\n" +
                    "Transaction completed successfully",
                    accountNumber, accountType, amount
                ));
                amountField.clear();
            } else {
                messageLabel.setText("Withdrawal Failed: Insufficient funds or invalid account");
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount");
        }
    }

    @FXML
    private void handleBack() {
        try {
            System.out.println("Returning to customer menu...");
            Parent root = FXMLLoader.load(getClass().getResource("CustomerMenu.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error returning to customer menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean processWithdrawal(String accountType, String accountNumber, double amount) {
        // Simulate account validation and withdrawal
        System.out.println("Processing withdrawal:");
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Amount: $" + amount);
        
        // Different success rates based on account type
        double successRate;
        switch (accountType) {
            case "Checking":
                successRate = 0.8; // 80% success for checking
                break;
            case "Investment":
                successRate = 0.7; // 70% success for investment
                break;
            default:
                successRate = 0.5; // 50% for any other type
        }
        
        boolean success = Math.random() > (1 - successRate);
        
        if (success) {
            System.out.println("Withdrawal approved for " + accountType + " account");
        } else {
            System.out.println("Withdrawal denied - insufficient funds or restrictions");
        }
        
        return success;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}