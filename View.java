
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;

/**
 * Creates and manages the GUI,displays the current state of the Model (title, output1 and output2) and handles user input from buttons
 */
class View
{
    /**
     * Height of window pixels
     */
    int H = 559;
    /**
     * Width of window pixels
     */
    int W = 565; 

    // variables for components of the user interface
    /**
     * Title area (not the window title)
     */
    Label      title; 
    /**
     * Message area, where numbers appear
     */
    TextField  message; 
    /**
     *  Reply area where results or information is shown
     */
    TextArea   reply; 
    /**
     * scrollbars around the TextArea object
     */
    ScrollPane scrollPane;
    /**
     * main layout grid
     */
    GridPane   grid; 
    /**
     * tiled area for buttons
     */
    TilePane   buttonPane;

    // The other parts of the model-view-controller setup
    
    public Model model;
    public Controller controller;

    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View()
    {
        Debug.trace("View::<constructor>");
    }
    
    /**
     * Start the GUI up <br>
     * called from Main<br>
     * important to create controls etc here and not in the constructor as we need things to be initialised in the correct order
     */
    public void start(Stage window)
    {
        Debug.trace("View::start");

        // create the user interface component objects
        // The ATM is a vertical grid of four components -
        // label, two text boxes, and a tiled panel
        // of buttons

        // layout objects
        grid = new GridPane();
        grid.setId("Layout");           // assign an id to be used in css file
        buttonPane = new TilePane();
        buttonPane.setId("Buttons");    // assign an id to be used in css file

        // controls
        title  = new Label();  // Message bar at the top for the title
        title.setId("title");
        grid.add( title, 0, 0);         // Add to GUI at the top       

        message  = new TextField(); 
        message.setId("message") ;// text field for numbers and error messages 
        message.setEditable(false);     // Read only (user can't type in)
        grid.add( message, 0, 1);       // Add to GUI on second row                      

        reply  = new TextArea();        // multi-line text area for instructions
        reply.setId("reply");
        reply.setEditable(false);       // Read only (user can't type in)
        scrollPane  = new ScrollPane(); // create a scrolling window
        scrollPane.setContent( reply ); // put the text area 'inside' the scrolling window
        grid.add( scrollPane, 0, 2);    // add the scrolling window to GUI on third row

        // Buttons - these are laid out on a tiled pane, then
        // the whole pane is added to the main grid as the fourth row

        // Button labels - empty strings are for blank spaces
        // The number of button per row should match what is set in 
        // the css file
        String labels[][] = {
                {"7",    "8",  "9",  "",  "Dep",  "NPin"},
                {"4",    "5",  "6",  "",  "W/D",  ""},
                {"1",    "2",  "3",  "",  "Bal",  "Fin"},
                {"CLR",  "0",  "",   "",  "",     "Ent"} };

        // loop through the array, making a Button object for each label 
        // (and an empty text label for each blank space) and adding them to the buttonPane
        // The number of button per row is set in the css file, not the array.
        for ( String[] row: labels ) {
            for (String label: row) {
                if ( label.length() >= 1 ) {
                    // non-empty string - make a button
                    Button b = new Button( label );        
                    b.setOnAction( this::buttonClicked ); // set the method to call when pressed
                    buttonPane.getChildren().add( b );    // and add to tiled pane
                } else {
                    // empty string - add an empty text element as a spacer
                    buttonPane.getChildren().add( new Text() ); 
    
                }
            }
        }
        grid.add(buttonPane,0,3); // add the tiled pane of buttons to the grid

        // add the complete GUI to the window and display it
        Scene scene = new Scene(grid, W, H);   
        scene.getStylesheets().add("atm.css"); // tell the app to use our css file
        window.setScene(scene);
        window.show();
    }

    // This is how the View talks to the Controller
    // This method is called when a button is pressed
    // It fetches the label on the button and passes it to the controller's process method
    /**
     * Fetched the label on the button and passes it to the controller's proccess method <br>
     * this is how the view talks to the controller
     */
    public void buttonClicked(ActionEvent event) {
        // this line asks the event to provide the actual Button object that was clicked
        Button b = ((Button) event.getSource());
        if ( controller != null )
        {          
            String label = b.getText();   // get the button label
            Debug.trace( "View::buttonClicked: label = "+ label );
            // Try setting a breakpoint here
            controller.process( label );  // Pass it to the controller's process method
        }
    }

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It fetches th title, display1 and display2 variables from the model
    // and displays them in the GUI
    /**
     * Called by the model whenever the model changes, fethches the title, dispaly 1&2 and displays them
     */
    public void update()
    {        
        if (model != null) {
            Debug.trace( "View::update" );
            String message1 = model.title;        // get the new title from the model
            title.setText(message1);              // set the message text to be the title
            String message2 = model.display1;     // get the new message1 from the model
            message.setText( message2 );          // add it as text of GUI control output1
            String message3 = model.display2;     // get the new message2 from the model
            reply.setText( message3 );            // add it as text of GUI control output2
        }
    }
}
