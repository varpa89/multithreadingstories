package banking;

import java.util.Random;

/**
 * @author varpa89
 * @since 18.11.2016
 */
public class DemonstrateDeadlock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 2;
    private static final int NUM_ITERATIONS = 10000;

    public static void main(String[] args) {
        final Random rnd = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i, 1000000000);
        }

        class TransferTread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAccount = rnd.nextInt(NUM_ACCOUNTS);
                    int toAccount = rnd.nextInt(NUM_ACCOUNTS);
                    int amount = rnd.nextInt(1000);
                    transferMoneySafely(accounts[fromAccount], accounts[toAccount], amount);
                }
            }

            public void transferMoneyDeadlock(Account fromAccount, Account toAccount, int amount) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        if (fromAccount.getBalance() < amount) {
                            throw new IllegalStateException();
                        } else {
                            fromAccount.debit(amount);
                            toAccount.credit(amount);
                        }
                    }
                }
            }

            public void transferMoneySafely(Account fromAccount, Account toAccount, int amount) {
                Object lock1 = fromAccount.getId() < toAccount.getId() ? fromAccount : toAccount;
                Object lock2 = fromAccount.getId() < toAccount.getId() ? toAccount : fromAccount;
                synchronized (lock1) {
                    synchronized (lock2) {
                        if (fromAccount.getBalance() < amount) {
                            throw new IllegalStateException();
                        } else {
                            fromAccount.debit(amount);
                            toAccount.credit(amount);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferTread().start();
        }
    }

}
