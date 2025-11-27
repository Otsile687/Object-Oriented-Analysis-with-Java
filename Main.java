import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Starting OTS Bank System...");
            
            // Check if FXML file exists
            File fxmlFile = new File("BankLogin.fxml");
            if (!fxmlFile.exists()) {
                throw new RuntimeException("BankLogin.fxml not found in: " + System.getProperty("user.dir"));
            }
            System.out.println("FXML file found: " + fxmlFile.getAbsolutePath());
            
            // Load FXML
            Parent root = FXMLLoader.load(getClass().getResource("BankLogin.fxml"));
            
            primaryStage.setTitle("OTS BANK SYSTEM");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
            
            System.out.println("Application started successfully!");
            
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            
            // Show error window
            showErrorWindow(primaryStage, e.getMessage());
        }
    }
    
    private void showErrorWindow(Stage stage, String errorMessage) {
        try {
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label(
                "Error starting application:\n" + errorMessage + "\n\n" +
                "Please check:\n" +
                "1. BankLogin.fxml exists in the same directory\n" +
                "2. FXML file has valid XML syntax\n" +
                "3. All controller classes are compiled"
            );
            javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane();
            root.getChildren().add(errorLabel);
            Scene scene = new Scene(root, 500, 300);
            stage.setTitle("Error - OTS BANK SYSTEM");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Could even show error window: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}