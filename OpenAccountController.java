import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpenAccountController {

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField accountHolderField;

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField initialDepositField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Initialize account types
        accountTypeComboBox.getItems().addAll("Savings", "Checking", "Investment");
    }

    @FXML
    private void handleRegister() {
        String accountType = accountTypeComboBox.getValue();
        String customerId = customerIdField.getText();
        String accountHolder = accountHolderField.getText();
        String accountNumber = accountNumberField.getText();
        String initialDeposit = initialDepositField.getText();

        // Add account opening logic here
        System.out.println("Opening new account:");
        System.out.println("Account Type: " + accountType);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Initial Deposit: " + initialDeposit);

        // Validate inputs
        if (validateInputs()) {
            // Create account object based on type
            try {
                double depositAmount = Double.parseDouble(initialDeposit);
                int accNumber = Integer.parseInt(accountNumber);

                switch (accountType) {
                    case "Savings":
                        SavingsAccount savings = new SavingsAccount(accNumber, depositAmount, 0.05);
                        System.out.println("Savings account created: " + savings.getAccountNumber());
                        break;
                    case "Checking":
                        CheckAccount checking = new CheckAccount(accNumber, depositAmount, 0.02);
                        System.out.println("Checking account created: " + checking.getAccountNumber());
                        break;
                    case "Investment":
                        InvestmentAccount investment = new InvestmentAccount(accNumber, depositAmount, "Stocks", 3);
                        System.out.println("Investment account created: " + investment.getAccountNumber());
                        break;
                }

                // Clear fields after successful registration
                clearFields();
                System.out.println("Account opened successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers for account number and initial deposit.");
            }
        } else {
            System.out.println("Please fill all required fields correctly.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMenu2.0.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        return accountTypeComboBox.getValue() != null &&
                !customerIdField.getText().isEmpty() &&
                !accountHolderField.getText().isEmpty() &&
                !accountNumberField.getText().isEmpty() &&
                !initialDepositField.getText().isEmpty();
    }

    private void clearFields() {
        accountTypeComboBox.setValue(null);
        customerIdField.clear();
        accountHolderField.clear();
        accountNumberField.clear();
        initialDepositField.clear();
    }
}