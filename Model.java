/**
 * Model represents all the content and functionality of the app such as: <br>
 * keeping track of the information shown in the display and the interaction with the bank <br>
 * executing commands provided by the controller <br>
 * telling the view to update when something changes
 * 
 */

public class Model 
{
    // the ATM model is always in one of three states - waiting for an account number, 
    // waiting for a password, or logged in and processing account requests. 
    // We use string values to represent each state:
    // (the word 'final' tells Java we won't ever change the values of these variables)
    final String ACCOUNT_NO = "account_no";
    final String PASSWORD = "password";
    final String LOGGED_IN = "logged_in";

    // variables representing the ATM model
    String state = ACCOUNT_NO;      // the state it is currently in
    int  number = 0;                // current number displayed in GUI (as a number, not a string)
    Bank  bank = null;              // The ATM talks to a bank, represented by the Bank object.
    int accNumber = -1;             // Account number typed in
    int accPasswd = -1;             // Password typed in
    // These three are what are shown on the View display
    String title = "Bank ATM";      // The contents of the title message
    String display1 = null;         // The contents of the Message 1 box (a single line)
    String display2 = null;         // The contents of the Message 2 box (may be multiple lines)

    // The other parts of the model-view-controller setup
    public View view;
    public Controller controller;

    
    /**
     * model constrctor
     * @param b a bank object representing the bank we want to talk to
     */
    public Model(Bank b)
    {
        Debug.trace("Model::<constructor>");          
        bank = b;
    }


    /**
     * initialising or resetting the ATM
     * @param message the message to be displayed on display1 
     * sets state to ACCOUNT_NO, number to 0 and displays messages in the 2 displays
     */
    public void initialise(String message) {
        setState(ACCOUNT_NO);
        number = 0;
        display1 = message; 
        display2 =  "Enter your account number\n" +
        "Followed by \"Ent\"";
    }


    /**
     * This method changes the state and prints a debug message
     * @param newState the new state we want to change to
     */
    public void setState(String newState) 
    {
        if ( !state.equals(newState) ) 
        {
            String oldState = state;
            state = newState;
            Debug.trace("Model::setState: changed state from "+ oldState + " to " + newState);
        }
    }

    // These methods are called by the Controller to change the Model
    // when particular buttons are pressed on the GUI
    
    
    /**
     * processes a number key press
     * @param label the number pressed
     */
    public void processNumber(String label)
    {
        // a little magic to turn the first char of the label into an int
        // and update the number variable with it
        char c = label.charAt(0);
        number = number * 10 + c-'0';           // Build number 
        // show the new number in the display
        display1 = "" + number;
        display();  // update the GUI
    }

    
    /**
     * processes the clear button by ressetting the number and display string
     */
    public void processClear()
    {
        // clear the number stored in the model
        number = 0;
        display1 = "";
        display();  // update the GUI
    }

    /**
     * processes the enter button from account number to passowrd to logged in and back to account number by using a stitch case statement to go 
     * through all possible states` 
     */
    public void processEnter()
    {
        // Enter was pressed - what we do depends what state the ATM is already in
        switch ( state )
        {
            case ACCOUNT_NO:
                // we were waiting for a complete account number - save the number we have
                // reset the tyed in number to 0 and change to the state where we are expecting 
                // a password
                Debug.trace( "Model:: processEnter number:" + number );
                accNumber = number;
                number = 0;
                setState(PASSWORD);
                display1 = "";
                display2 = "Now enter your password\n" +
                "Followed by \"Ent\"";
                break;
            case PASSWORD:
                // we were waiting for a password - save the number we have as the password
                // and then cotnact the bank with accumber and accPasswd to try and login to
                // an account
                accPasswd = number;
                number = 0;
                display1 = "";
                // now check the account/password combination. If it's ok go into the LOGGED_IN
                // state, otherwise go back to the start (by re-initialsing)
                if ( bank.login(accNumber, accPasswd) )
                {
                    setState(LOGGED_IN);
                    display2 = "Accepted\n" +
                    "Now enter the transaction you require";
                } else {
  
                    initialise("Unknown account/password");
            
                }
                break;
            case LOGGED_IN:     
            default: 
                // do nothing in any other state (ie logged in)
        }  
        display();  // update the GUI
    }


    
    /**
     * processes the withdraw button <br> 
     * checks if user is logged in <br>
     * if user is logged in run bank.withdraw(number) and display the outcome
     */
    public void processWithdraw()
    {
        if (state.equals(LOGGED_IN) ) {            
            if ( bank.withdraw( number ) )
            {
                display2 =   "Withdrawn: " + number;
            } else {
                display2 =   "You do not have sufficient funds";
            }
            number = 0;
            display1 = "";           
        } else {
            initialise("You are not logged in");
        }
        display();  // update the GUI
    }

    
    /**
     * processes the deposit button <br> 
     * checks if user is logged in <br>
     * if user is logged in run bank.deposit(number) and display the outcome
     */
    public void processDeposit()
    {
        if (state.equals(LOGGED_IN) ) {
            bank.deposit( number );
            display1 = "";
            display2 = "Deposited: " + number;
            number = 0;
        } else {
            initialise("You are not logged in");
        }
        display();  // update the GUI
    }


    /**
     * process balance button <br>
     * checks user is logged in then runs bank.getBalance() and dispalys it to the user in the GUI
     */
    public void processBalance()
    {
        if (state.equals(LOGGED_IN) ) {
            number = 0;
            display2 = "Your balance is: " + bank.getBalance();
        } else {
            initialise("You are not logged in");
        }
        display();  // update the GUI
    }

    /**
     * proccesses the finish button <br>
     * is user is logged in, log the user out and reset all values back to the beginning
     */
    public void processFinish()
    {
        if (state.equals(LOGGED_IN) ) {
            // go back to the log in state
            setState(ACCOUNT_NO);
            number = 0;
            display2 = "Welcome: Enter your account number";
            bank.logout();
        } else {
            initialise("You are not logged in");
        }
        display();  // update the GUI
    }
    
    /**
     * processes the new pin button <br>
     * if the user is logged in, changes the pin and changes the display
     */
    public void processNewPin (){
        if (state.equals(LOGGED_IN) ) {
            if(number!=0){
                int newPin = bank.changePin(number);
                number = 0; 
                display2 = "Your new pin is " + newPin; 
            }
            else{
                display2 = "Please enter a valid pin before pressing the New Pin Button";
            }
        } else {
            initialise("You are not logged in");
        }
        display();
    }

    /**
     * Catch all for any unknown button presses <br>
     * resets the GUI and sends out an error message
     */
    public void processUnknownKey(String action)
    {
        // unknown button, or invalid for this state - reset everything
        Debug.trace("Model::processUnknownKey: unknown button \"" + action + "\", re-initialising");
        // go back to initial state
        initialise("Invalid command");
        display();
    }
    
        /**
     * Catch all for any unknown button presses <br>
     * resets the GUI and sends out an error message
     */
    public void processStatement()
    {
        if (state.equals(LOGGED_IN) ) {
            String Statement = bank.getStatement();
            display2 = Statement;
        } else {
            initialise("You are not logged in");
        }
        display();
    }



    /**
     * model talks to the view to update the gui
     */
    public void display()
    {
        Debug.trace("Model::display");
        view.update();
    }
}
