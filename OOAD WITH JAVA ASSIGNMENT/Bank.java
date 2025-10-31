public class Bank {
    private String bankName;
    private String address;

    public Bank(String bankName, String address) {
        this.bankName = bankName;
        this.address = address;
    }

    public void openAccount(Account account) {
        System.out.println("Account " + account.getAccountNumber() + " opened.");
    }

    public void processDeposit(Account account, double amount) {
        account.deposit(amount);
        System.out.println("Deposited " + amount + " to account " + account.getAccountNumber());
    }

    public void processWithdrawal(Account account, double amount) {
        boolean success = account.withdraw(amount);
        if (success) {
            System.out.println("Withdrew " + amount + " from account " + account.getAccountNumber());
        } else {
            System.out.println("Withdrawal failed for account " + account.getAccountNumber());
        }
    }
}