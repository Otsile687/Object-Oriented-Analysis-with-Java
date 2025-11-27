import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomerCredentials {
    private static final String CREDENTIALS_FILE = "customer_credentials.txt";
    private static Map<String, String> credentials = new HashMap<>();
    private static Random random = new Random();

    static {
        loadCredentials();
    }

    // Generate a random password
    public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    // Create username from customer name
    public static String generateUsername(String customerName) {
        // Remove spaces and convert to lowercase
        String username = customerName.replaceAll("\\s+", "").toLowerCase();
        // Add random number if username already exists
        if (credentials.containsKey(username)) {
            username = username + (random.nextInt(900) + 100);
        }
        return username;
    }

    // Add new customer credentials
    public static void addCustomer(String customerName, String customerId) {
        String username = generateUsername(customerName);
        String password = generatePassword();
        
        credentials.put(username, password);
        saveCredentials();
        
        System.out.println("Customer Login Credentials Created:");
        System.out.println("Name: " + customerName);
        System.out.println("ID: " + customerId);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    // Verify customer login
    public static boolean verifyCustomer(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    // Get all customers (for display)
    public static Map<String, String> getAllCustomers() {
        return new HashMap<>(credentials);
    }

    // Save credentials to file
    private static void saveCredentials() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CREDENTIALS_FILE))) {
            for (Map.Entry<String, String> entry : credentials.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error saving credentials: " + e.getMessage());
        }
    }

    // Load credentials from file
    private static void loadCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading credentials: " + e.getMessage());
        }
    }
}