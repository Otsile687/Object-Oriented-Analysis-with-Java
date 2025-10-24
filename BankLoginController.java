import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BankLoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button customerLoginButton;

    @FXML
    private Button employeeLoginButton;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        // Initialization code if needed
    }

    @FXML
    private void handleCustomerLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();

        // Add authentication logic here
        System.out.println("Customer Login Attempt - UserID: " + userId);

        // If login successful, navigate to customer menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu2.o.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) customerLoginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEmployeeLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();

        // Add authentication logic here
        System.out.println("Employee Login Attempt - UserID: " + userId);

        // If login successful, navigate to employee menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMenu2.0.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) employeeLoginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}