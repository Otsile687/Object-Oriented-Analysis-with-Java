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

public class DeleteCustomerController {

    @FXML private TextField customerIdField;
    @FXML private TextField customerNameField;
    @FXML private Button verifyButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private Label customerInfoLabel;
    @FXML private Label messageLabel;

    private Customer verifiedCustomer;
    private boolean customerVerified = false;

    @FXML
    private void initialize() {
        System.out.println("DeleteCustomerController initialized!");
        messageLabel.setText("Enter customer ID and name to verify");
        deleteButton.setDisable(true);
    }

    @FXML
    private void handleVerifyCustomer() {
        String customerId = customerIdField.getText().trim();
        String customerName = customerNameField.getText().trim();

        if (customerId.isEmpty() || customerName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both customer ID and name");
            return;
        }

        try {
            int id = Integer.parseInt(customerId);
            
            // Verify customer exists in database
            verifiedCustomer = CustomerDAO.getCustomerById(id);
            
            if (verifiedCustomer != null && verifiedCustomer.getName().equalsIgnoreCase(customerName)) {
                customerVerified = true;
                customerInfoLabel.setText(String.format(
                    "Customer Verified: %s (ID: %d)\nAddress: %s\nPhone: %s",
                    verifiedCustomer.getName(), verifiedCustomer.getCustomerID(),
                    verifiedCustomer.getAddress(), verifiedCustomer.getPhoneNumber()
                ));
                customerInfoLabel.setStyle("-fx-text-fill: #006400;");
                
                messageLabel.setText("✅ Customer verified successfully. You can now delete.");
                messageLabel.setStyle("-fx-text-fill: #006400; -fx-font-weight: bold;");
                
                deleteButton.setDisable(false);
            } else {
                customerVerified = false;
                customerInfoLabel.setText("❌ Customer verification failed. Please check ID and name.");
                customerInfoLabel.setStyle("-fx-text-fill: #cc0000;");
                
                messageLabel.setText("❌ Customer not found or name doesn't match");
                messageLabel.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
                
                deleteButton.setDisable(true);
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid numeric customer ID");
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        if (!customerVerified || verifiedCustomer == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please verify customer first");
            return;
        }

        // Show confirmation dialog
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Customer Deletion");
        confirmation.setHeaderText("PERMANENT CUSTOMER DELETION");
        confirmation.setContentText(String.format(
            "You are about to PERMANENTLY delete:\n\n" +
            "Customer: %s\n" +
            "ID: %d\n" +
            "Address: %s\n\n" +
            "⚠️  THIS ACTION CANNOT BE UNDONE! ⚠️\n\n" +
            "All customer data and accounts will be lost.\n\n" +
            "Are you absolutely sure you want to proceed?",
            verifiedCustomer.getName(), verifiedCustomer.getCustomerID(), verifiedCustomer.getAddress()
        ));

        confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        
        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            // Delete customer from database
            boolean success = CustomerDAO.deleteCustomer(verifiedCustomer.getCustomerID());
            
            if (success) {
                showSuccessMessage();
                resetForm();
            } else {
                showErrorMessage("Failed to delete customer. Customer may have active accounts.");
            }
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
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                "Cannot return to employee menu: " + e.getMessage());
        }
    }

    private void showSuccessMessage() {
        String successMessage = String.format(
            "✅ Customer deleted successfully!\n\n" +
            "Customer: %s\n" +
            "ID: %d\n\n" +
            "All customer data has been permanently removed from the system.",
            verifiedCustomer.getName(), verifiedCustomer.getCustomerID()
        );
        
        messageLabel.setStyle("-fx-text-fill: #006400; -fx-font-weight: bold;");
        messageLabel.setText(successMessage);
        
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Customer Deleted");
        success.setHeaderText("Deletion Successful");
        success.setContentText(successMessage);
        success.showAndWait();
    }

    private void showErrorMessage(String message) {
        messageLabel.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
        messageLabel.setText(message);
        
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Deletion Failed");
        error.setHeaderText("Customer Deletion Failed");
        error.setContentText(message);
        error.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetForm() {
        customerIdField.clear();
        customerNameField.clear();
        customerInfoLabel.setText("");
        messageLabel.setText("Enter customer ID and name to verify");
        messageLabel.setStyle("-fx-text-fill: black;");
        deleteButton.setDisable(true);
        customerVerified = false;
        verifiedCustomer = null;
    }
}