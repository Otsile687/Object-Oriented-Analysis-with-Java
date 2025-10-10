public class InvestmentAccount extends Account implements IInterestCalculator {
    private Transaction lastTransaction;

    public InvestmentAccount(int accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public double calculateMonthlyInterest(double amount) {
        return amount * 0.05; // 5% monthly interest
    }

    public void applyMonthlyInterest(String date) {
        double interest = calculateMonthlyInterest(balance);
        deposit(interest);
        lastTransaction = new Transaction(generateTransactionID(), date, interest);
        lastTransaction.execute();
    }

    public Transaction getLastTransaction() {
        return lastTransaction;
    }

    private int generateTransactionID() {
        return (int)(Math.random() * 100000); // simple random ID
    }
}
