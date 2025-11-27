import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckBalanceController {

    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField accountNumberField;
    @FXML private Button checkBalanceButton;
    @FXML private Button backButton;
    @FXML private Label balanceLabel;

    @FXML
    private void initialize() {
        accountTypeComboBox.getItems().addAll("Savings", "Checking", "Investment");
        balanceLabel.setText("Balance will be displayed here");
    }

    @FXML
    private void handleCheckBalance() {
        String accountType = accountTypeComboBox.getValue();
        String accountNumber = accountNumberField.getText();
        
        if (accountType == null || accountNumber.isEmpty()) {
            balanceLabel.setText("Please select account type and enter account number");
            return;
        }
        
        // Simulate balance check
        double balance = Math.random() * 10000;
        balanceLabel.setText(String.format("Account %s (%s) Balance: $%.2f", 
            accountNumber, accountType, balance));
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
}