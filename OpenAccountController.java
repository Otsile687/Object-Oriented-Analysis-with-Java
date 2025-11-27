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

public class OpenAccountController {

    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField customerIdField;
    @FXML private TextField accountNumberField;
    @FXML private TextField interestRateField;
    @FXML private TextField overdraftField;
    @FXML private TextField returnRateField;
    @FXML private Button createAccountButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        accountTypeComboBox.getItems().addAll("Savings Account", "Checking Account", "Investment Account");
        
        // Add listener to show/hide relevant fields based on account type
        accountTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFieldsForAccountType(newValue);
        });
        
        messageLabel.setText("Select account type and enter details");
    }
    
    private void updateFieldsForAccountType(String accountType) {
        // Reset all fields first
        interestRateField.setDisable(true);
        interestRateField.clear();
        overdraftField.setDisable(true);
        overdraftField.clear();
        returnRateField.setDisable(true);
        returnRateField.clear();
        
        // Enable relevant fields based on account type
        if ("Savings Account".equals(accountType)) {
            interestRateField.setDisable(false);
            interestRateField.setPromptText("Enter interest rate (e.g., 0.025 for 2.5%)");
        } else if ("Checking Account".equals(accountType)) {
            overdraftField.setDisable(false);
            overdraftField.setPromptText("Enter overdraft limit");
        } else if ("Investment Account".equals(accountType)) {
            returnRateField.setDisable(false);
            returnRateField.setPromptText("Enter expected return rate");
        }
    }

    @FXML
    private void handleCreateAccount() {
        String accountType = accountTypeComboBox.getValue();
        String customerId = customerIdField.getText();
        String accountNumber = accountNumberField.getText();
        
        // Initial balance is always 0
        double initialBalance = 0.00;
        
        if (accountType == null || customerId.isEmpty() || accountNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
            return;
        }
        
        try {
            int customerIdInt = Integer.parseInt(customerId);
            int accountNumberInt = Integer.parseInt(accountNumber);
            
            // Validate account doesn't already exist
            if (accountExists(accountNumberInt)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Account number already exists");
                return;
            }
            
            // Create account based on type with 0 initial balance
            boolean success = false;
            String accountDetails = "";
            
            switch (accountType) {
                case "Savings Account":
                    double interestRate = 0.02; // Default 2%
                    if (!interestRateField.getText().isEmpty()) {
                        interestRate = Double.parseDouble(interestRateField.getText());
                    }
                    success = createSavingsAccount(accountNumberInt, customerIdInt, initialBalance, interestRate);
                    accountDetails = String.format("Savings Account created with %.3f%% interest", interestRate * 100);
                    break;
                    
                case "Checking Account":
                    double overdraftLimit = 100.00; // Default $100
                    if (!overdraftField.getText().isEmpty()) {
                        overdraftLimit = Double.parseDouble(overdraftField.getText());
                    }
                    success = createCheckingAccount(accountNumberInt, customerIdInt, initialBalance, overdraftLimit);
                    accountDetails = String.format("Checking Account created with $%.2f overdraft", overdraftLimit);
                    break;
                    
                case "Investment Account":
                    double returnRate = 0.05; // Default 5%
                    if (!returnRateField.getText().isEmpty()) {
                        returnRate = Double.parseDouble(returnRateField.getText());
                    }
                    success = createInvestmentAccount(accountNumberInt, customerIdInt, initialBalance, returnRate);
                    accountDetails = String.format("Investment Account created with %.1f%% expected return", returnRate * 100);
                    break;
            }
            
            if (success) {
                messageLabel.setText(String.format(
                    "✅ Account Created Successfully!\n\n" +
                    "Account Number: %s\n" +
                    "Customer ID: %s\n" +
                    "Account Type: %s\n" +
                    "Initial Balance: $%.2f\n\n" +
                    "%s\n\n" +
                    "Account is ready for deposits.",
                    accountNumber, customerId, accountType, initialBalance, accountDetails
                ));
                
                // Clear fields
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numbers for ID and account number");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean accountExists(int accountNumber) {
        // Check if account already exists in database
        // This would typically query your database
        // For now, return false (simulating no duplicate)
        return false;
    }
    
    private boolean createSavingsAccount(int accountNumber, int customerId, double balance, double interestRate) {
        // Create savings account in database with 0 balance
        System.out.println("Creating Savings Account:");
        System.out.println("Account: " + accountNumber);
        System.out.println("Customer: " + customerId);
        System.out.println("Balance: $" + balance);
        System.out.println("Interest Rate: " + (interestRate * 100) + "%");
        
        // Simulate database operation
        // In real implementation, call AccountDAO.createAccount()
        return true;
    }
    
    private boolean createCheckingAccount(int accountNumber, int customerId, double balance, double overdraftLimit) {
        // Create checking account in database with 0 balance
        System.out.println("Creating Checking Account:");
        System.out.println("Account: " + accountNumber);
        System.out.println("Customer: " + customerId);
        System.out.println("Balance: $" + balance);
        System.out.println("Overdraft Limit: $" + overdraftLimit);
        
        // Simulate database operation
        return true;
    }
    
    private boolean createInvestmentAccount(int accountNumber, int customerId, double balance, double returnRate) {
        // Create investment account in database with 0 balance
        System.out.println("Creating Investment Account:");
        System.out.println("Account: " + accountNumber);
        System.out.println("Customer: " + customerId);
        System.out.println("Balance: $" + balance);
        System.out.println("Return Rate: " + (returnRate * 100) + "%");
        
        // Simulate database operation
        return true;
    }

    private void clearFields() {
        customerIdField.clear();
        accountNumberField.clear();
        interestRateField.clear();
        overdraftField.clear();
        returnRateField.clear();
        accountTypeComboBox.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}