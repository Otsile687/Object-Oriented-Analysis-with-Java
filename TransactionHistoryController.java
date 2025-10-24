import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionHistoryController {

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private TextField accountNumberField;

    @FXML
    private Button confirmButton;

    @FXML
    private ListView<String> transactionListView;

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

        // Initialize with sample transaction data
        transactionListView.getItems().addAll(
                "2024-01-20: Deposit - $1,000.00",
                "2024-01-18: Withdrawal - $500.00",
                "2024-01-15: Interest - $25.50",
                "2024-01-10: Deposit - $2,000.00",
                "2024-01-05: Withdrawal - $300.00"
        );
    }

    @FXML
    private void handleConfirm() {
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();

        // Add transaction history loading logic here
        System.out.println("Loading transaction history for account: " + accountNumber + " Type: " + accountType);

        // Refresh transaction list based on account
        transactionListView.getItems().clear();
        transactionListView.getItems().addAll(
                "2024-01-20: Deposit - $1,000.00",
                "2024-01-18: Withdrawal - $500.00",
                "2024-01-15: Interest - $25.50",
                "2024-01-10: Deposit - $2,000.00",
                "2024-01-05: Withdrawal - $300.00"
        );
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