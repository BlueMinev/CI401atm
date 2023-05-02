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
    StringBuilder statement = new StringBuilder();
    
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
            int prevBalance = balance;
            balance = balance - amount;  // subtract amount from balance
            statement.append("WITHDRAW: \n Balance before: "+ prevBalance + 
            " \n Amount withdrawn: "+ amount + " \n New balance: " + balance + "\n \n");
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
            int prevBalance = balance;
            balance = balance + amount;  // add amount to balance
            statement.append("DEPOSIT: \n Balance before: "+ prevBalance + 
            " \n Amount deposited: "+ amount + " \n New balance: " + balance + "\n \n");
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
    
    /**
     * Change the password and then return the new password
     */
    public int newPin(int number){
        Debug.trace( "LocalBank::newPin" );
        accPasswd = number;
        return accPasswd;
    }
    
        /**
     * Change the password and then return the new password
     */
    public String statement(){
        //do stuff
        Debug.trace( "LocalBank::statement" );
        String finalStatement = statement.toString();
        return finalStatement;
        
    }
}
