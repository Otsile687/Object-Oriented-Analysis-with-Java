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

public class DepositController {

    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;
    @FXML private Button depositButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        accountTypeComboBox.getItems().addAll("Savings", "Checking", "Investment");
        messageLabel.setText("Enter deposit details above");
    }

    @FXML
    private void handleDeposit() {
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();
        String amountText = amountField.getText();
        
        if (accountType == null || accountNumber.isEmpty() || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Amount must be positive");
                return;
            }
            
            messageLabel.setText(String.format("Deposited $%.2f to account %s (%s)", 
                amount, accountNumber, accountType));
            
            // Clear fields
            amountField.clear();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
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