/*******************************************************************************
*
* UseSimpleThread.java
*
*
*******************************************************************************/
public class UseSimpleThread {

	public static void main(String args[]) {

		// Creating two SimpleThread objects:
		SimpleThread first = new SimpleThread("Sun Microsystems","Ed Zander",300);
		SimpleThread second = new SimpleThread("Microsoft","Bill Gates",500);
		System.out.println("Press <ENTER> when you have had enough . . .\n");

		// Start the two threads, this calls the run method of SimpleThread:
		first.start();
		second.start();

		// Wait for the <Enter> key to be pressed and terminate the program. By default
		// this will also close any running threads.
		try {
			System.in.read();
		}
		catch(java.io.IOException e) {
			System.out.println("The following exception occurred: " + e);
		}
			System.out.println("Ending UseSimpleThread.");
	} // main()
} // UseSimpleThread