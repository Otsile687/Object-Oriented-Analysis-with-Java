import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeMenuController {

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button openAccountButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button closeAccountButton;

    @FXML
    private Button customerListButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<String> customerListView;

    @FXML
    private TextField searchCustomerField;

    @FXML
    private void initialize() {
        // Initialize with sample customer data
        customerListView.getItems().addAll(
                "John Doe - ID: 001 - Accounts: 2",
                "Jane Smith - ID: 002 - Accounts: 1",
                "Bob Johnson - ID: 003 - Accounts: 3"
        );
    }

    @FXML
    private void handleAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenAccount.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) openAccountButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        // Handle delete customer logic
        String selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            System.out.println("Deleting customer: " + selectedCustomer);
            customerListView.getItems().remove(selectedCustomer);
        }
    }

    @FXML
    private void handleCloseAccount() {
        // Handle close account logic
        System.out.println("Close account functionality");
    }

    @FXML
    private void handleCustomerList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerAndAccountManagement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) customerListButton.getScene().getWindow();
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

    @FXML
    private void handleSearchCustomer() {
        String searchTerm = searchCustomerField.getText().toLowerCase();
        // Add search logic here
        System.out.println("Searching for customer: " + searchTerm);

        // Filter list based on search term
        customerListView.getItems().clear();
        customerListView.getItems().addAll(
                "John Doe - ID: 001 - Accounts: 2",
                "Jane Smith - ID: 002 - Accounts: 1",
                "Bob Johnson - ID: 003 - Accounts: 3"
        );
    }
}