public class Bank {
    private String bankName;
    private String address;

    public Bank(String bankName, String address) {
        this.bankName = bankName;
        this.address = address;
    }

    public void openAccount(Account account) {
        System.out.println(" Account " + account.getAccountNumber() + " opened.");
    }

    public void closeAccount(Account account) {
        System.out.println(" Account " + account.getAccountNumber() + " closed.");
    }

    public void processDeposit(Account account, double amount) {
        account.deposit(amount);
        System.out.println(" Deposited " + amount + " to account " + account.getAccountNumber());
    }

    public void processWithdrawal(Account account, double amount) {
        boolean success = account.withdraw(amount);
        if (success) {
            System.out.println(" Withdrew " + amount + " from account " + account.getAccountNumber());
        } else {
            System.out.println(" Withdrawal failed for account " + account.getAccountNumber());
        }
    }

    public void applyInterest(SavingsAccount account) {
        double interest = account.calculateInterest();
        account.applyInterest();
        System.out.println("Applied interest of " + interest + " to account " + account.getAccountNumber());
    }

    public void applyReturns(InvestmentAccount account) {
        double returns = account.calculateReturns();
        account.deposit(returns);
        System.out.println("Applied returns of " + returns + " to investment account " + account.getAccountNumber());
    }
}
