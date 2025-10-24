import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerAndAccountManagementController {

    @FXML
    private TextField searchCustomerField;

    @FXML
    private TextField searchAccountField;

    @FXML
    private Button searchCustomerButton;

    @FXML
    private Button searchAccountButton;

    @FXML
    private ListView<String> customerListView;

    @FXML
    private ListView<String> accountListView;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Initialize with sample data
        customerListView.getItems().addAll("Customer 1 - ID: 001", "Customer 2 - ID: 002");
        accountListView.getItems().addAll("Account 1 - 123456", "Account 2 - 789012");
    }

    @FXML
    private void handleSearchCustomer() {
        String searchTerm = searchCustomerField.getText();
        // Add customer search logic here
        System.out.println("Searching for customer: " + searchTerm);
    }

    @FXML
    private void handleSearchAccount() {
        String searchTerm = searchAccountField.getText();
        // Add account search logic here
        System.out.println("Searching for account: " + searchTerm);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeMenu2.0.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}