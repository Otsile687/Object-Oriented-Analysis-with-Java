import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepositController {

    @FXML
    private TextField depositAmountField;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField accountNumberField;

    @FXML
    private Button confirmDepositButton;

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
    private void handleConfirmDeposit() {
        String amount = depositAmountField.getText();
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();

        // Add deposit logic here
        System.out.println("Depositing $" + amount + " to account: " + accountNumber + " Type: " + accountType);

        // Clear fields after deposit
        depositAmountField.clear();
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
    private void handleWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Withdraw.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) withdrawButton.getScene().getWindow();
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
}