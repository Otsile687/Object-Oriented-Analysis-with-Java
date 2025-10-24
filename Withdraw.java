import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Withdraw {
    private int withdrawID;
    private double amount;
    private String accountType;
    private int accountNumber;
    private LocalDateTime timestamp;
    private String status; // "PENDING", "COMPLETED", "FAILED"
    private String transactionReference;

    // Constants for account types
    public static final String SAVINGS_ACCOUNT = "Savings";
    public static final String CHECKING_ACCOUNT = "Checking";
    public static final String INVESTMENT_ACCOUNT = "Investment";

    // Constructor
    public Withdraw(double amount, String accountType, int accountNumber) {
        this.withdrawID = generateWithdrawID();
        this.amount = amount;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.timestamp = LocalDateTime.now();
        this.status = "PENDING";
        this.transactionReference = generateTransactionReference();
    }

    // Getters and Setters
    public int getWithdrawID() {
        return withdrawID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    // Business Logic Methods
    public boolean validateWithdrawal() {
        if (amount <= 0) {
            return false;
        }

        if (accountType == null || accountType.trim().isEmpty()) {
            return false;
        }

        if (accountNumber <= 0) {
            return false;
        }

        // Validate account type
        if (!isValidAccountType(accountType)) {
            return false;
        }

        return true;
    }

    public boolean processWithdrawal(Account account) {
        if (account == null) {
            this.status = "FAILED";
            return false;
        }

        if (!validateWithdrawal()) {
            this.status = "FAILED";
            return false;
        }

        // Check if account number matches
        if (account.getAccountNumber() != this.accountNumber) {
            this.status = "FAILED";
            return false;
        }

        // Process withdrawal using account's withdraw method
        boolean success = account.withdraw(amount);

        if (success) {
            this.status = "COMPLETED";
            this.timestamp = LocalDateTime.now();
            return true;
        } else {
            this.status = "FAILED";
            return false;
        }
    }

    public boolean isValidAccountType(String type) {
        return type.equals(SAVINGS_ACCOUNT) ||
                type.equals(CHECKING_ACCOUNT) ||
                type.equals(INVESTMENT_ACCOUNT);
    }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public String getWithdrawalDetails() {
        return String.format(
                "Withdrawal ID: %d\n" +
                        "Amount: $%.2f\n" +
                        "Account Type: %s\n" +
                        "Account Number: %d\n" +
                        "Status: %s\n" +
                        "Transaction Reference: %s\n" +
                        "Timestamp: %s",
                withdrawID, amount, accountType, accountNumber,
                status, transactionReference, getFormattedTimestamp()
        );
    }

    // Utility Methods
    private int generateWithdrawID() {
        return (int)(System.currentTimeMillis() % 1000000);
    }

    private String generateTransactionReference() {
        return "WDR" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("Withdraw[ID: %d, Amount: $%.2f, Account: %s-%d, Status: %s]",
                withdrawID, amount, accountType, accountNumber, status);
    }

    // Static utility methods
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static String[] getSupportedAccountTypes() {
        return new String[]{SAVINGS_ACCOUNT, CHECKING_ACCOUNT, INVESTMENT_ACCOUNT};
    }
}


