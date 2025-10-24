import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class CustomerMenuController {

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
    private ListView<String> transactionListView;

    @FXML
    private void initialize() {
        // Initialize transaction history with sample data
        transactionListView.getItems().addAll(
                "2024-01-15: Deposit - $500.00",
                "2024-01-10: Withdrawal - $100.00",
                "2024-01-05: Deposit - $200.00"
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