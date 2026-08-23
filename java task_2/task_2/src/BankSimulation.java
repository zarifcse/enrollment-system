public class BankSimulation {

    static class BankAccount {
        private int balance = 1000;

        public synchronized void deposit(int amount) {
            int currentBalance = balance;
            try {
                Thread.sleep(1);
            } catch (InterruptedException _) {

            }
            balance = currentBalance + amount;
        }

        public synchronized void withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException _) {

                }
                balance = balance - amount;
            }
        }

        public synchronized int getBalance() {
            return balance;
        }
    }

    static class DepositTask implements Runnable {
        private final BankAccount account;

        public DepositTask(BankAccount account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                account.deposit(10);
            }
        }
    }

    static class WithdrawTask implements Runnable {
        private final BankAccount account;

        public WithdrawTask(BankAccount account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                account.withdraw(10);
            }
        }
    }

    public static void main(String[] args) {
        BankAccount sharedAccount = new BankAccount();

        System.out.println("Starting Balance: $" + sharedAccount.getBalance());

        Thread d1 = new Thread(new DepositTask(sharedAccount));
        Thread d2 = new Thread(new DepositTask(sharedAccount));
        Thread w1 = new Thread(new WithdrawTask(sharedAccount));
        Thread w2 = new Thread(new WithdrawTask(sharedAccount));

        d1.start();
        d2.start();
        w1.start();
        w2.start();

        try {
            d1.join();
            d2.join();
            w1.join();
            w2.join();
        } catch (InterruptedException _) {

        }

        System.out.println("Final Balance: $" + sharedAccount.getBalance());
    }
}