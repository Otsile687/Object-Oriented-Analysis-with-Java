public class SavingsAccount extends Account implements IWithdrawalService {
    private double interestRate;

    public SavingsAccount(int accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    // ADD THESE METHODS FOR BANK OPERATIONS
    public double calculateInterest() {
        return balance * interestRate;
    }

    public void applyInterest() {
        double interest = calculateInterest();
        deposit(interest);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}