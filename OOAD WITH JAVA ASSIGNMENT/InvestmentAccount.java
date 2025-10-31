public class InvestmentAccount extends Account {
    private double returnRate;

    public InvestmentAccount(int accountNumber, double balance, double returnRate) {
        super(accountNumber, balance);
        this.returnRate = returnRate;
    }

    public double calculateReturns() {
        return balance * returnRate;
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