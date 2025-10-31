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

public class CreateCustomerController {

    @FXML private ComboBox<String> customerTypeComboBox;
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField ageField;
    @FXML private TextField genderField;
    @FXML private TextField occupationField;
    @FXML private Button createButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        customerTypeComboBox.getItems().addAll("Individual", "Company");
        messageLabel.setText("Enter customer details above");
    }

    @FXML
    private void handleCreate() {
        String customerType = customerTypeComboBox.getValue();
        String name = nameField.getText();
        String id = idField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        String occupation = occupationField.getText();
        
        if (customerType == null || name.isEmpty() || id.isEmpty() || phone.isEmpty() || 
            address.isEmpty() || age.isEmpty() || gender.isEmpty() || occupation.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }
        
        try {
            int ageInt = Integer.parseInt(age);
            int idInt = Integer.parseInt(id);
            
            // Create customer based on type
            String customerInfo;
            if ("Individual".equals(customerType)) {
                Individual individual = new Individual(idInt, name, address, phone, ageInt, gender, occupation);
                customerInfo = individual.toString();
            } else {
                Company company = new Company(idInt, name, address, phone, ageInt, gender, occupation);
                customerInfo = company.toString();
            }
            
            // Generate login credentials for the customer
            CustomerCredentials.addCustomer(name, id);
            
            // Get the generated credentials
            String username = CustomerCredentials.generateUsername(name);
            String password = CustomerCredentials.generatePassword();
            
            messageLabel.setText(String.format(
                "Customer Created Successfully!\n\n" +
                "Name: %s\n" +
                "ID: %s\n" +
                "Type: %s\n\n" +
                "Login Credentials:\n" +
                "Username: %s\n" +
                "Password: %s\n\n" +
                "Please save these credentials for login.",
                name, id, customerType, username, password
            ));
            
            // Clear fields
            clearFields();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numbers for ID and Age");
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
        }
    }

    private void clearFields() {
        nameField.clear();
        idField.clear();
        phoneField.clear();
        addressField.clear();
        ageField.clear();
        genderField.clear();
        occupationField.clear();
        customerTypeComboBox.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}