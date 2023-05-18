import java.util.*;

/**
 * BankAccount class
 * This class has instance variables for the account number, password and
 * balance, and methods
 * to withdraw, deposit, check balance etc.
 */
public class BankAccount {
    public int accNumber = 0;
    public int accPasswd = 0;
    public int balance = 0;
    public boolean overdraft = false;
    public int limit = 0;
    LinkedList<String> statement = new LinkedList<String>();
    int count = 0;

    public BankAccount() {
    }

    public BankAccount(int a, int p, int b, boolean o, int l) {
        accNumber = a;
        accPasswd = p;
        balance = b;
        overdraft = o;
        limit = l;
    }

    /**
     * withdraw money from the account. Return true if successful, or
     * false if the amount is negative, or less than the amount in the account
     */
    public boolean withdraw(int amount) {
        Debug.trace("BankAccount::withdraw: amount =" + amount);

        if (amount < 0 || ((balance < amount) && (overdraft == false))
                || (overdraft == true && ((balance - amount) <= limit))) {
            return false;
        } else {
            int prevBalance = balance;
            balance = balance - amount; // subtract amount from balance
            if (count > 4) {
                statement.removeFirst();
                count--;
            }
            count++;
            statement.add(new String("WITHDRAW: \n Balance before: " + prevBalance +
                    " \n Amount withdrawn: " + amount + " \n New balance: " + balance + "\n \n"));
            return true;
        }
    }

    /**
     * deposit the amount of money into the account. Return true if successful,
     * or false if the amount is negative
     */
    public boolean deposit(int amount) {
        Debug.trace("LocalBank::deposit: amount = " + amount);

        if (amount < 0) {
            return false;
        } else {
            int prevBalance = balance;
            balance = balance + amount; // add amount to balance
            if (count > 4) {
                statement.removeFirst();
                count--;
            }
            count++;
            statement.add(new String("DEPOSIT: \n Balance before: " + prevBalance +
                    " \n Amount deposited: " + amount + " \n New balance: " + balance + "\n \n"));
            return true;
        }
    }

    /**
     * Return the current balance in the account
     */
    public int getBalance() {
        Debug.trace("LocalBank::getBalance");

        return balance;
    }

    /**
     * Change the password and then return the new password
     */
    public int newPin(int number) {
        Debug.trace("LocalBank::newPin");
        accPasswd = number;
        return accPasswd;
    }

    /**
     * converts the stringBuilder variable to a string
     */
    public String statement() {
        Debug.trace("LocalBank::statement");
        String finalStatement = "Your statement is : \n" + statement;
        Debug.trace(finalStatement);
        return finalStatement;
    }
}
