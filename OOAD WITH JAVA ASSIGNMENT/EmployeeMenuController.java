import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Map;
import java.io.File;

public class EmployeeMenuController {

    @FXML private Button addCustomerButton;
    @FXML private Button openAccountButton;
    @FXML private Button closeAccountButton;
    @FXML private Button viewCustomersButton;
    @FXML private Button logoutButton;
    @FXML private ListView<String> customerListView;
    @FXML private TextField searchField;

    @FXML
    private void initialize() {
        System.out.println("Employee Menu Controller initialized");
        loadCustomerList();
    }

    @FXML
    private void handleCloseAccount() {
        try {
            System.out.println("Close Account button clicked!");
            
            // Check if file exists
            File fxmlFile = new File("CloseAccount.fxml");
            System.out.println("CloseAccount.fxml exists: " + fxmlFile.exists());
            System.out.println("CloseAccount.fxml path: " + fxmlFile.getAbsolutePath());
            
            // Check if controller class exists
            try {
                Class.forName("CloseAccountController");
                System.out.println("CloseAccountController class found!");
            } catch (ClassNotFoundException e) {
                System.err.println("CloseAccountController class not found: " + e.getMessage());
                throw new RuntimeException("CloseAccountController not compiled");
            }
            
            // Try to load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CloseAccount.fxml"));
            System.out.println("FXMLLoader created for CloseAccount.fxml");
            
            Parent root = loader.load();
            System.out.println("CloseAccount.fxml loaded successfully!");
            
            Stage stage = (Stage) closeAccountButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Switched to Close Account scene successfully!");
            
        } catch (Exception e) {
            System.err.println("ERROR loading CloseAccount.fxml: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", 
                "Cannot load close account form: " + e.getMessage() + 
                "\n\nPlease make sure:\n" +
                "1. CloseAccount.fxml exists in the same directory\n" +
                "2. CloseAccountController.java is compiled\n" +
                "3. No errors in the FXML file");
        }
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
    private void handleViewCustomers() {
        loadCustomerList();
        showAlert(Alert.AlertType.INFORMATION, "Customer List", 
            "Customer list updated with login credentials");
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

    private void loadCustomerList() {
        customerListView.getItems().clear();
        Map<String, String> customers = CustomerCredentials.getAllCustomers();
        
        if (customers.isEmpty()) {
            customerListView.getItems().add("No customers registered yet");
        } else {
            int count = 1;
            for (Map.Entry<String, String> entry : customers.entrySet()) {
                customerListView.getItems().add(
                    String.format("%d. Username: %s | Password: %s", 
                        count++, entry.getKey(), entry.getValue())
                );
            }
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