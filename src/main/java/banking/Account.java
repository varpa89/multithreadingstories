package banking;

import java.util.logging.Logger;

/**
 * @author varpa89
 * @since 18.11.2016
 */
public class Account {
    private Logger logger = Logger.getLogger(Account.class.getName());

    private int balance;
    private final Integer id;

    public Account(Integer id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void debit(int amount) {
        this.balance = this.balance - amount;
    }

    public void credit(int amount) {
        this.balance = this.balance + amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public Integer getId() {
        return this.id;
    }

    private void logBalance() {
        //if(this.balance < 0){
            logger.info(Thread.currentThread().getName() + " " + this.id + " account balance is " + this.balance);
        //}
    }
}
