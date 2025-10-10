public class Transaction implements ITransaction {
    private int transactionID;
    private String date;
    private double amount;

    public Transaction(int transactionID, String date, double amount) {
        this.transactionID = transactionID;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        System.out.println("✅ Interest transaction " + transactionID + " executed on " + date + " for amount " + amount);
    }

    @Override
    public void cancel() {
        System.out.println("❌ Transaction " + transactionID + " canceled.");
    }
}
