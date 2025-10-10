public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("FirstBank", "Gaborone");

        SavingsAccount savings = new SavingsAccount(1001, 5000.0, 0.05);
        CheckAccount check = new CheckAccount(1002, 2000.0, 500.0);
        InvestmentAccount invest = new InvestmentAccount(1003, 10000.0, "Stocks", 3);

        bank.openAccount(savings);
        bank.processDeposit(savings, 1000);
        bank.applyInterest(savings);

        bank.openAccount(check);
        bank.processWithdrawal(check, 2200);

        bank.openAccount(invest);
        bank.applyReturns(invest);

        System.out.println("💰 Final Savings Balance: " + savings.getBalance());
        System.out.println("💰 Final Check Balance: " + check.getBalance());
        System.out.println("💰 Final Investment Balance: " + invest.getBalance());
    }
}
