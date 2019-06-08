package files;

public class Main extends Timer
{
	public static void main( String[] args )
	{
		// create object for each MVC component
		Model theModel = new Model(); // creates from default constructor
		
		View theView = new View(); // creates from default constructor
		
		// need to create Control class constructor with params for setting the M and V global variables
		Control theController = new Control( theModel, theView ); 
		
		// set the timer object
		Timer theTimer = new Timer();
		
		theController.setTimer( theTimer );
		
		// need to create start method for the timer
		theTimer.start();
		
		// need to create entry point into program to run
		theController.run();
	}

}
