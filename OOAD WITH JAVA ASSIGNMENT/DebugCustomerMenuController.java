import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;

public class DebugCustomerMenuController {

    @FXML private Button checkBalanceButton;
    @FXML private Button withdrawButton;
    @FXML private Button depositButton;
    @FXML private Button transactionHistoryButton;
    @FXML private Button logoutButton;
    @FXML private ListView<String> transactionListView;

    @FXML
    private void initialize() {
        System.out.println("Customer Menu Controller initialized");
        transactionListView.getItems().addAll(
            "2024-01-15: Deposit - $500.00",
            "2024-01-10: Withdrawal - $100.00"
        );
    }

    @FXML
    private void handleWithdraw() {
        try {
            System.out.println("Withdraw button clicked!");
            
            // Check if file exists
            File fxmlFile = new File("Withdraw.fxml");
            System.out.println("Withdraw.fxml exists: " + fxmlFile.exists());
            System.out.println("Withdraw.fxml path: " + fxmlFile.getAbsolutePath());
            
            // Try to load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Withdraw.fxml"));
            System.out.println("FXMLLoader created for Withdraw.fxml");
            
            Parent root = loader.load();
            System.out.println("Withdraw.fxml loaded successfully!");
            
            Stage stage = (Stage) withdrawButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Switched to Withdraw scene successfully!");
            
        } catch (Exception e) {
            System.err.println("ERROR loading Withdraw.fxml: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot load withdraw form: " + e.getMessage());
        }
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
        System.out.println("Transaction history displayed");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BankLogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}