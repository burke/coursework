/******************************************************************************
* MyInterface2.java
*
* This class illustrates the use of implementing threads using the runnable
* interface and sychronized block.
*
*******************************************************************************/
public class MyInterface2 implements Runnable {

	// instance variables
	private String company;
	private String president;
	private long delay;

	// constructor
	public MyInterface2(String company, String president, long delay) {
		this.company = company;
		this.president = president;
		this.delay = delay;
	}

	// run() method is called whenever we start our thread all your code to be
	// executed concurrently should be in run() or called from run() we don't call
	// run() directly but we'll call start() and then start() will call run()
	public void run() {
		try{
			while(true) {
				//create a sychronized block to indicate that the following
				//three lines of code should be executed togather
				synchronized(System.out){
					System.out.print(president + "\t: ");
					Thread.sleep(delay);
					System.out.println(company);
				}
			}

		}
		catch(InterruptedException e) {
			System.out.println("Exception: " + company + ", " + president + ": " + e);
		}
	} // run()
} // MyInterface
