import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Simulates a supermarket. 
 *
 * @input Should be instantiated with a number of checkouts to
 * simulate and an input file to read from
 *
 * @output Prints a summary of most events as they happen and some
 * basic stats at the end.
 */
public class SoopaStore {

  private static Checkout    checkouts[]; ///< Array of checkouts
  private static int         numCheckouts; ///< Number of checkouts this store has
  private static OrderedList events; ///< Sorted linked list of upcoming events
  private static Scanner     inputScanner; ///< Used to parse file input
  private static OrderedList cashierList; ///< List of departed cashiers
  private static int         mostRecentTime;
  
  /**
   * The Main loop essentially. Read in an event, then as long as the
   * event list has more events to process, process them.
   */
  public static void startSimulation() {
    readNextArrival();
    while (! events.isEmpty()) {
      processNextEvent();
    }
  }

  public static int currentTime() {
    if (! events.isEmpty()) {
      Event evt = (Event) events.peekFirst();
      return evt.getTime();
    } else {
      return mostRecentTime;
    }
  }

  /** Pull an event off the EventList and call action() on it to run it. */
  public static void processNextEvent() {
    Event evt = (Event) events.removeFirst();
    mostRecentTime = evt.getTime();
    evt.action();
  }
  
  /** Return this store's event list. */
  public static OrderedList getEventList() {
    return events;
  }

  /**
   * Return the first checkout that doesn't have a cashier, so
   * employees can figure out where to go
   */
  public static Checkout firstUnmannedCheckout() {
    Checkout first = null;
    for (int i=0; i<numCheckouts; ++i) {
      if (!checkouts[i].hasCashier()) {
        first = checkouts[i];
        break;
      }
    }
    return first;
  }
  
  /** Return the emptiest checkout, so customers can figure out where to go */
  public static Checkout preferableCheckout() {
    Checkout pref=null;
    int min = -1; // A value that will never be returned naturally.
                  // We'll overwrite this regardless of the result of getLength().
    int length;
    for (int i=0; i<numCheckouts; ++i) {
      if (checkouts[i].isOpen()) {
        length = checkouts[i].getLength();
        if (min == -1 || length < min) {
          min = length;
          pref = checkouts[i];
        }
      }
    }
    return pref;    
  }
  
  /** true if the input isn't all used up. Are there more people coming? */
  private static boolean hasMoreArrivals() {
    // Logically, hasNextLine makes more sense, but if there's a blank
    // line at the end of the file, hasNextLine will cause errors.
    // We're guaranteed lines start with an integer, so this works better.
    return (inputScanner.hasNextInt());
  }


  /**
   * Pull the next arrival from input, create an event for it, and
   * stick it on the EventList
   */
  public static void readNextArrival() {
    String personType;
    int arrivalTime, employeeNumber, quittingTime, numItems;

    if (hasMoreArrivals()) {
      arrivalTime = inputScanner.nextInt();
      personType  = inputScanner.next();
      if (personType.equals("E")) {
        // This is an employee.
        employeeNumber = inputScanner.nextInt();
        quittingTime   = inputScanner.nextInt();
        addCashierArrivalEvent(arrivalTime,employeeNumber,quittingTime);
      } else {
        // This is a customer.
        numItems = inputScanner.nextInt();
        addCustomerArrivalEvent(arrivalTime,numItems);
      }
    }

  }

  /**
   * Create an ArrivalEvent for an employee record that was pulled from input
   * @param arrivalTime the time at which the person arrives
   * @param employeeNumber id of employee
   * @param quittingTime time at which employee's shift is over
   */
  public static void addCashierArrivalEvent(int arrivalTime, int employeeNumber, int quittingTime) {
    Cashier employee = new Cashier(employeeNumber, quittingTime, arrivalTime);
    CashierArrivalEvent event = new CashierArrivalEvent(arrivalTime, employee);
    events.insertInOrder(event);
  }

  /**
   * Create an ArrivalEvent for a customer record that was pulled from input
   * @param arrivalTime the time at which the person arrives
   * @param numItems the number of items this customer is purchasing
   */
  public static void addCustomerArrivalEvent(int arrivalTime, int numItems) {
    Customer customer = new Customer(numItems, arrivalTime);
    CustomerArrivalEvent event = new CustomerArrivalEvent(arrivalTime, customer);
    events.insertInOrder(event);
  }

  /**
   * Start the simulation by calling SoopaStore.
   * @param args not like we use it.
   */
  public static void main(String args[]) {
    String infile;
    Scanner sc;

    numCheckouts = 8;
    events       = new OrderedList();
    cashierList  = new OrderedList();
    
    // Get input filename.
    sc = new Scanner(System.in);
    System.out.println("Please enter input file name:");
    infile = sc.next();
    sc.close();

    checkouts = new Checkout[numCheckouts];
    for (int i=0; i<numCheckouts; ++i) {
      checkouts[i] = new Checkout(i);
    }
    try {
      inputScanner = new Scanner(new File(infile));
    } catch (FileNotFoundException fnfe) {
      System.out.println(fnfe);
      System.exit(1);
    }
    
    System.out.println("Welcome to SoopaStore!\n");
    
    startSimulation();

    System.out.println("\n...All events complete.  Final Summary:");

    System.out.println("\nCashier Information:\n");
    System.out.println(
      "      ID    Start     Quit        Time    Time    Speed   Customers    Items\n"+
      "  Number     Time     Time      Worked    Idle               Served   Packed\n"+
      "----------------------------------------------------------------------------");
    System.out.printf("%8s %8s %8s %11s %8s %7s %11s %8s\n",
                      0,0,0,0,0,0,0,0);
    

    System.out.println("\nCheckout Information:\n");
    System.out.println(
      "Checkout     Time    Total       Total    Total\n"+
      "  Number  Staffed  Workers   Customers    Items\n"+
      "-----------------------------------------------" );
    for (int i=0; i<numCheckouts; ++i) {
      System.out.printf("%8s %8s %8s %11s %8s\n",
                        i,
                        SoopaStore.checkouts[i].getTimeStaffed(),
                        SoopaStore.checkouts[i].getNumCashiers(),
                        SoopaStore.checkouts[i].getNumCustomers(),
                        SoopaStore.checkouts[i].getTotalItems());
    }
    
    System.out.println("\nCustomers processed in total: ");
    System.out.println("Average waiting time per customer : ");
    
    
    System.out.println("End of Processing");
  }
  
}