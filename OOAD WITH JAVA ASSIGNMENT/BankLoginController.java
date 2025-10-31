import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BankLoginController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;
    @FXML private Button customerLoginButton;
    @FXML private Button employeeLoginButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        System.out.println("Bank Login Controller initialized");
    }

    @FXML
    private void handleCustomerLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();
        
        if (CustomerCredentials.verifyCustomer(userId, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) customerLoginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Cannot load customer menu");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", 
                "Invalid customer credentials!\n\n" +
                "Employee Login: admin / admin123\n" +
                "Customer Login: Use credentials provided when account was created");
        }
    }

    @FXML
    private void handleEmployeeLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();
        
        if (isValidEmployee(userId, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) employeeLoginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Cannot load employee menu");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid employee credentials!");
        }
    }

    @FXML
    private void handleExit() {
        showAlert(Alert.AlertType.INFORMATION, "Exit", "Thank you for using OTS BANK SYSTEM!");
        Platform.exit();
    }

    private boolean isValidEmployee(String userId, String password) {
        return "admin".equals(userId) && "admin123".equals(password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}