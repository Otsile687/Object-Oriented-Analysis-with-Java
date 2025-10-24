import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private void initialize() {
        // Initialize account types
        accountTypeComboBox.getItems().addAll("Savings", "Checking", "Investment");
    }

    @FXML
    private void handleConfirmWithdraw() {
        String amount = withdrawAmountField.getText();
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();

        // Add withdrawal logic here
        System.out.println("Withdrawing $" + amount + " from account: " + accountNumber + " Type: " + accountType);

        // Clear fields after withdrawal
        withdrawAmountField.clear();
        accountNumberField.clear();
        accountTypeComboBox.setValue(null);
    }

    @FXML
    private void handleCheckBalance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckBalance.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) checkBalanceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) depositButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTransactionHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionHistory.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) transactionHistoryButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BankLogIN.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmWithdraw() {
        try {
            double amount = Double.parseDouble(withdrawAmountField.getText());
            String accountType = accountTypeComboBox.getValue();
            String accountNumber = accountNumberField.getText();

            // Connect to bank business logic
            Account account = findAccount(accountNumber, accountType);
            if (account != null && account.withdraw(amount)) {
                System.out.println("Withdrawal successful!");
                clearFields();
            } else {
                System.out.println("Withdrawal failed - insufficient funds or invalid account");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount");
        }
    }
}