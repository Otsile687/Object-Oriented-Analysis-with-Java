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

    @FXML private TextField customerIdField;
    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField initialDepositField;
    @FXML private TextField accountNumberField;
    @FXML private Button openAccountButton;
    @FXML private Button generateNumberButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    // Minimum deposit requirements
    private static final double MIN_SAVINGS_DEPOSIT = 50.0;
    private static final double MIN_INVESTMENT_DEPOSIT = 500.0;
    private static final double MIN_CHECKING_DEPOSIT = 25.0;

    @FXML
    private void initialize() {
        // Initialize account types with withdrawal information
        accountTypeComboBox.getItems().addAll(
            "Savings Account", 
            "Checking Account", 
            "Investment Account"
        );
        messageLabel.setText("Enter account details above");
        generateAccountNumber();
        
        // Add listener to account type selection to update minimum deposit info
        accountTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateMinimumDepositMessage(newValue);
        });
    }

    @FXML
    private void handleGenerateNumber() {
        generateAccountNumber();
        messageLabel.setText("New account number generated");
    }

    @FXML
    private void handleOpenAccount() {
        String customerId = customerIdField.getText();
        String accountType = accountTypeComboBox.getValue();
        String depositText = initialDepositField.getText();
        String accountNumber = accountNumberField.getText();
        
        if (customerId.isEmpty() || accountType == null || depositText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
            return;
        }
        
        try {
            double initialDeposit = Double.parseDouble(depositText);
            
            // Validate minimum deposit requirements
            if (!validateMinimumDeposit(accountType, initialDeposit)) {
                return; // Validation failed, alert already shown
            }
            
            if (initialDeposit < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Initial deposit cannot be negative");
                return;
            }
            
            // Create account based on type
            String accountDetails = createAccount(accountType, accountNumber, initialDeposit);
            
            // Add withdrawal restrictions information
            String withdrawalInfo = "";
            if (accountType.equals("Savings Account")) {
                withdrawalInfo = "\n\n⚠️ Withdrawal Restriction: This account cannot be used for withdrawals.";
            } else if (accountType.equals("Checking Account")) {
                withdrawalInfo = "\n\n💳 Withdrawal Limit: $5,000 per transaction";
            } else if (accountType.equals("Investment Account")) {
                withdrawalInfo = "\n\n📈 Withdrawal Limit: $10,000 per transaction";
            }
            
            messageLabel.setText(String.format(
                "Account opened successfully!\n" +
                "Customer ID: %s\n" +
                "Account Number: %s\n" +
                "Account Type: %s\n" +
                "Initial Deposit: $%.2f\n\n%s%s",
                customerId, accountNumber, accountType, initialDeposit, accountDetails, withdrawalInfo
            ));
            
            // Clear fields for next entry
            customerIdField.clear();
            accountTypeComboBox.setValue(null);
            initialDepositField.clear();
            generateAccountNumber();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount for initial deposit");
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
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot return to employee menu: " + e.getMessage());
        }
    }

    private void generateAccountNumber() {
        int accountNumber = 10000000 + (int)(Math.random() * 90000000);
        accountNumberField.setText(String.valueOf(accountNumber));
    }

    private String createAccount(String accountType, String accountNumber, double initialDeposit) {
        int accNumber = Integer.parseInt(accountNumber);
        
        switch (accountType) {
            case "Savings Account":
                SavingsAccount savings = new SavingsAccount(accNumber, initialDeposit, 0.02);
                return String.format("Savings Account created with 2%% interest rate");
                
            case "Checking Account":
                CheckAccount checking = new CheckAccount(accNumber, initialDeposit, 500.0);
                return String.format("Checking Account created with $500 overdraft limit");
                
            case "Investment Account":
                InvestmentAccount investment = new InvestmentAccount(accNumber, initialDeposit, 0.05);
                return String.format("Investment Account created with 5%% return rate");
                
            default:
                return "Account created";
        }
    }

    private boolean validateMinimumDeposit(String accountType, double deposit) {
        double minDeposit = 0;
        String accountName = "";
        
        switch (accountType) {
            case "Savings Account":
                minDeposit = MIN_SAVINGS_DEPOSIT;
                accountName = "Savings";
                break;
            case "Investment Account":
                minDeposit = MIN_INVESTMENT_DEPOSIT;
                accountName = "Investment";
                break;
            case "Checking Account":
                minDeposit = MIN_CHECKING_DEPOSIT;
                accountName = "Checking";
                break;
        }
        
        if (deposit < minDeposit) {
            showAlert(Alert.AlertType.ERROR, "Insufficient Deposit", 
                String.format("Minimum deposit for %s Account is $%.2f\n\n" +
                            "You entered: $%.2f\n" +
                            "Required: $%.2f\n\n" +
                            "Please increase your initial deposit to open this account.",
                            accountName, minDeposit, deposit, minDeposit));
            return false;
        }
        
        return true;
    }

    private void updateMinimumDepositMessage(String accountType) {
        if (accountType == null) {
            messageLabel.setText("Enter account details above");
            return;
        }
        
        double minDeposit = 0;
        switch (accountType) {
            case "Savings Account":
                minDeposit = MIN_SAVINGS_DEPOSIT;
                break;
            case "Investment Account":
                minDeposit = MIN_INVESTMENT_DEPOSIT;
                break;
            case "Checking Account":
                minDeposit = MIN_CHECKING_DEPOSIT;
                break;
        }
        
        messageLabel.setText(String.format(
            "Minimum deposit for %s: $%.2f\nEnter account details above",
            accountType, minDeposit
        ));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}