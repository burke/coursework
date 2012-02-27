
/*******************************************************************************
*
* SimpleThread.java
*
* This class illustrates the use of implementing threads by subclassing the
* Thread class.
*
*******************************************************************************/
public class SimpleThread extends Thread {

	// instance variables
	private String company;
	private String president;
	private long delay;

	// constructor
	public SimpleThread(String company, String president, long delay) {
		this.company = company;
		this.president = president;
		this.delay = delay;
		// This is a method of the Thread class, used to indicate (if true) that the
		// thread is to run as a daemon.
		setDaemon(false);
	}

	// The run() method is called whenever we start our thread. All of the code
	// that is to be executed concurrently (in parallel) should be in run() or called
	// from run(). run() is not called directly but implicitly through start(), i.e.
	// start() will call run().
	public void run() {
		try{
			while(true) {
				System.out.print(president + "\t: ");
				sleep(delay);
				System.out.println(company);
			}
		}
		catch(InterruptedException e) {
			System.out.println("Exception: " + company + ", " + president + ":"+ e);
		}
	} // run()

} // SimpleThread