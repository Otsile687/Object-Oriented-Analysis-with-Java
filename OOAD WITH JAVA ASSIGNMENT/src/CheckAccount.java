public class CheckAccount extends Account implements IWithdrawalService {
    private double overdraftLimit;
    private double interestRate;

    public CheckAccount(int accountNumber, double balance, double overdraftLimit, double interestRate) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
        this.interestRate = interestRate;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getInterestRate() {
        return interestRate;
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
