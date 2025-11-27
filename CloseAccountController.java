import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseAccountController {

    @FXML private TextField accountNumberField;
    @FXML private Button closeAccountButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        System.out.println("CloseAccountController initialized!");
        messageLabel.setText("Enter account number to close account");
    }

    @FXML
    private void handleCloseAccount() {
        String accountNumber = accountNumberField.getText().trim();
        
        if (accountNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter an account number");
            return;
        }
        
        try {
            int accountNum = Integer.parseInt(accountNumber);
            
            // Verify account exists
            if (!accountExists(accountNum)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Account not found or already closed");
                return;
            }
            
            // Show confirmation dialog
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Account Closure");
            confirmation.setHeaderText("PERMANENT ACCOUNT CLOSURE");
            confirmation.setContentText(
                "You are about to PERMANENTLY close account: " + accountNumber + "\n\n" +
                "⚠️  THIS ACTION CANNOT BE UNDONE! ⚠️\n\n" +
                "Are you absolutely sure you want to proceed?"
            );
            
            confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            
            if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                // Close the account
                boolean success = closeAccount(accountNum);
                
                if (success) {
                    messageLabel.setText("✅ Account " + accountNumber + " closed successfully!");
                    messageLabel.setStyle("-fx-text-fill: #006400; -fx-font-weight: bold;");
                    accountNumberField.clear();
                    
                    // Show success alert
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Account Closed");
                    successAlert.setHeaderText("Account Closure Successful");
                    successAlert.setContentText("Account " + accountNumber + " has been permanently closed.");
                    successAlert.showAndWait();
                } else {
                    messageLabel.setText("❌ Failed to close account " + accountNumber);
                    messageLabel.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
                }
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid account number");
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
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                "Cannot return to employee menu: " + e.getMessage());
        }
    }

    private boolean accountExists(int accountNumber) {
        // Check if account exists in database
        String sql = "SELECT COUNT(*) FROM accounts WHERE account_number = ? AND is_active = true";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            System.err.println("Error checking account existence: " + e.getMessage());
            return false;
        }
    }
    
    private boolean closeAccount(int accountNumber) {
        // Close account in database
        String sql = "UPDATE accounts SET is_active = false WHERE account_number = ?";
        
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Account " + accountNumber + " closed successfully");
                return true;
            } else {
                System.out.println("Failed to close account " + accountNumber);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error closing account: " + e.getMessage());
            return false;
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