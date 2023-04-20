/**
* BankAccount class
* This class has instance variables for the account number, password and balance, and methods
* to withdraw, deposit, check balance etc.
*/
public class BankAccount
{
    public int accNumber = 0;
    public int accPasswd = 0;
    public int balance = 0;
    
    public BankAccount()
    {
    }
    
    public BankAccount(int a, int p, int b)
    {
        accNumber = a;
        accPasswd = p;
        balance = b;
    }
    
    /**
    * withdraw money from the account. Return true if successful, or 
    * false if the amount is negative, or less than the amount in the account
    */
    public boolean withdraw( int amount ) 
    { 
        Debug.trace( "BankAccount::withdraw: amount =" + amount ); 

        if (amount < 0 || balance < amount) {
            return false;
        } else {
            balance = balance - amount;  // subtract amount from balance
            return true; 
        }
    }
    
    /**
    * deposit the amount of money into the account. Return true if successful,
    * or false if the amount is negative 
    */
    public boolean deposit( int amount )
    { 
        Debug.trace( "LocalBank::deposit: amount = " + amount ); 

        if (amount < 0) {
            return false;
        } else {
            balance = balance + amount;  // add amount to balance
            return true; 
        }
    }
    /**
    * Return the current balance in the account
    */
    public int getBalance() 
    { 
        Debug.trace( "LocalBank::getBalance" ); 

        return balance;
    }
}
