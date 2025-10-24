public class InvestmentAccount extends Account implements IInterestCalculator {
    private String investmentType;
    private int investmentTerm;
    private Transaction lastTransaction;

    public InvestmentAccount(int accountNumber, double balance, String investmentType, int investmentTerm) {
        super(accountNumber, balance);
        this.investmentType = investmentType;
        this.investmentTerm = investmentTerm;
    }

    @Override
    public double calculateMonthlyInterest(double amount) {
        return amount * 0.05; // 5% monthly interest
    }

    public double calculateReturns() {
        return calculateMonthlyInterest(balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
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
        return (int)(Math.random() * 100000);
    }

    public String getInvestmentType() { return investmentType; }
    public int getInvestmentTerm() { return investmentTerm; }
}