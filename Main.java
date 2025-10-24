import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the login FXML file
            Parent root = FXMLLoader.load(getClass().getResource("BankLogIN.fxml"));

            // Create the scene
            Scene scene = new Scene(root, 700, 500);

            // Set up the primary stage
            primaryStage.setTitle("OTS Bank System - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

            System.out.println("OTS Bank System Started Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error starting Bank System: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting OTS Bank System...");
        launch(args);
    }
}