/**
 *  Calls different methods based on what button was pressed
 */
public class Controller
{
    public Model model;
    public View  view;

    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public Controller()
    {
        Debug.trace("Controller::<constructor>");
    }

    /**
     * Called by the View to srespond to a user interface event, controller decides what to do using
     * a switch statement then calls a method in the Model
     */
    public void process( String action )
    {
        Debug.trace("Controller::process: action = " + action);
        switch (action) {
        case "1" : case "2" : case "3" : case "4" : case "5" :
        case "6" : case "7" : case "8" : case "9" : case "0" : 
            model.processNumber(action);
            break;
        case "CLR":
            model.processClear();
            break;
        case "Ent":
            model.processEnter();
            break;
        case "W/D":
            model.processWithdraw();
            break; 
        case "Dep":
            model.processDeposit();
            break;
        case "Bal":
            model.processBalance();
            break; 
        case "Fin":
            model.processFinish();
            break;
        case "NPin":
            model.processNewPin();
            break;
        default:
            model.processUnknownKey(action);
            break;
        }    
    }

}
