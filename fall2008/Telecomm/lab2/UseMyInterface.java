/*******************************************************************************
*
* UseMyInterface.java
*
*******************************************************************************/
public class UseMyInterface {

	public static void main(String args[]) {

		// Creating two MyInterface objects:
		Thread first = new Thread(new MyInterface("Sun Microsystems","EdZander",300));
		Thread second = new Thread(new MyInterface("Microsoft","Bill Gates",500));

		// Set the two MyInterface objects to run as daemons:
		first.setDaemon(true);
		second.setDaemon(true);
		System.out.println("Press <ENTER> when you have had enough . . .\n");

		// Start the two threads:
		first.start();
		second.start();

		// Wait for the <Enter> key to be pressed and terminate the program. By default
		// this will also close any running threads.
		try {
			System.in.read();
		}
		catch(java.io.IOException e){
			System.out.println("The following exception occurred: " + e);
		}
			System.out.println("Ending UseMyInterface");
	} // main()

} // UseMyInterface