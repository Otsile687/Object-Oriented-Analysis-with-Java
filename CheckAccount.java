public class CheckAccount extends Account implements IWithdrawalService {
    private double overdraftLimit;

    // UPDATED CONSTRUCTOR (removed interestRate)
    public CheckAccount(int accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            return true;
        }
        return false;
    }
}