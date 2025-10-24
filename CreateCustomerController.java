import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCustomerController {

    @FXML
    private ComboBox<String> customerTypeComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField occupationField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private void initialize() {
        // Initialize customer types
        customerTypeComboBox.getItems().addAll("Individual", "Company");
    }

    @FXML
    private void handleRegister() {
        // Collect customer data
        String customerType = customerTypeComboBox.getValue();
        String name = nameField.getText();
        String id = idField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        String occupation = occupationField.getText();

        // Add customer registration logic here
        System.out.println("Registering new " + customerType + " customer: " + name);

        // Create customer object based on type
        if (customerType.equals("Individual")) {
            // Create Individual customer
            Individual individual = new Individual(Integer.parseInt(id), name, address, phoneNumber,
                    Integer.parseInt(age), gender, occupation);
            System.out.println("Individual customer created: " + individual);
        } else if (customerType.equals("Company")) {
            // Create Company customer
            Company company = new Company(Integer.parseInt(id), name, address, phoneNumber,
                    Integer.parseInt(age), gender, occupation);
            System.out.println("Company customer created: " + company);
        }

        // Clear fields after registration
        clearFields();
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

    private void clearFields() {
        customerTypeComboBox.setValue(null);
        nameField.clear();
        idField.clear();
        phoneNumberField.clear();
        addressField.clear();
        ageField.clear();
        genderField.clear();
        occupationField.clear();
    }
}