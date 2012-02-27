// CLASS: SoopaStore
//
// REMARKS: Simulates a supermarket. 
//
// Input: Should be instantiated with a number of checkouts to simulate
//   and an input file to read from
//
// Output: Prints a summary of most events as they happen and some basic
//   stats at the end.
//
//-----------------------------------------
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SoopaStore {

  Checkout checkouts[];
  int numCheckouts;
  EventList events;
  Scanner inputScanner;

  private static SoopaStore instance;

  //------------------------------------------------------
  // getInstance/0
  //
  // PURPOSE: Return the singleton instace of SoopaStore
  //------------------------------------------------------
  public static synchronized SoopaStore getInstance() {
    return SoopaStore.getInstance(0,"");
  }

  //------------------------------------------------------
  // getInstance/2
  //
  // PURPOSE: Initialize SoopaStore and return the instance
  // PARAMETERS:
  //     numCheckouts: number of checkout lanes to simulate
  //     infile: input file to read data from
  //------------------------------------------------------
  public static synchronized SoopaStore getInstance(int numCheckouts, String infile) {
    if (instance == null) {
      instance = new SoopaStore(numCheckouts, infile);
    }
    return instance;
  }
  
  //------------------------------------------------------
  // SoopaStore
  //
  // PURPOSE: Private Constructor. Build a SoopaStore.
  //     numCheckouts: number of checkout lanes to simulate
  //     infile: input file to read data from
  //------------------------------------------------------
  private SoopaStore(int numCheckouts, String infile) {
    this.numCheckouts = numCheckouts;
    this.events       = new EventList();
    this.checkouts    = new Checkout[numCheckouts];
    for (int i=0; i<numCheckouts; ++i) {
      checkouts[i] = new Checkout(i);
    }
    try {
      this.inputScanner = new Scanner(new File(infile));
    } catch (FileNotFoundException fnfe) {
      System.out.println(fnfe);
      System.exit(1);
    }
    instance = this;
  }

  //------------------------------------------------------
  // startSimulation
  //
  // PURPOSE: The Main loop essentially. Read in an event, then as long
  //     as the event list has more events to process, process them.
  //------------------------------------------------------
  public void startSimulation() {
    this.readNextArrival();
    while (events.hasMoreEvents()) {
      this.processNextEvent();
    }
  }

  //------------------------------------------------------
  // processNextEvent
  //
  // PURPOSE: Pull an event off the EventList and call action() on it to run it.
  //------------------------------------------------------
  public void processNextEvent() {
    Event evt = events.popEvent();
    evt.action();
  }
  
  //------------------------------------------------------
  // getEventList
  //
  // PURPOSE: Return this store's event list.
  //------------------------------------------------------
  public EventList getEventList() {
    return this.events;
  }

  //------------------------------------------------------
  // firstUnmannedCheckout
  //
  // PURPOSE: Return the first checkout that doesn't have a cashier, so employees
  //     can figure out where to go
  //------------------------------------------------------
  public Checkout firstUnmannedCheckout() {
    Checkout first = null;
    for (int i=0; i<numCheckouts; ++i) {
      if (!checkouts[i].hasCashier()) {
        first = checkouts[i];
        break;
      }
    }
    return first;
  }
  
  //------------------------------------------------------
  // preferableCheckout
  //
  // PURPOSE: Return the emptiest checkout, so customers can figure out where to go
  //------------------------------------------------------
  public Checkout preferableCheckout() {
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
  
  //------------------------------------------------------
  // hasMoreArrivals
  //
  // PURPOSE: true if the input isn't all used up. Are there more people coming?
  //------------------------------------------------------
  private boolean hasMoreArrivals() {
    // Logically, hasNextLine makes more sense, but if there's a blank
    // line at the end of the file, hasNextLine will cause errors.
    // We're guaranteed lines start with an integer, so this works better.
    return (this.inputScanner.hasNextInt());
  }

  //------------------------------------------------------
  // readNextArrival
  //
  // PURPOSE: Pull the next arrival from input, create an event for it, and
  //     stick it on the EventList
  //------------------------------------------------------
  public void readNextArrival() {
    String personType;
    int arrivalTime, employeeNumber, quittingTime, numItems;

    if (this.hasMoreArrivals()) {
      arrivalTime = this.inputScanner.nextInt();
      personType  = this.inputScanner.next();
      if (personType.equals("E")) {
        // This is an employee.
        employeeNumber = this.inputScanner.nextInt();
        quittingTime   = this.inputScanner.nextInt();
        this.addEmployeeArrivalEvent(arrivalTime,employeeNumber,quittingTime);
      } else {
        // This is a customer.
        numItems = this.inputScanner.nextInt();
        this.addCustomerArrivalEvent(arrivalTime,numItems);
      }
    }

  }

  //------------------------------------------------------
  // addEmployeeArrivalEvent
  //
  // PURPOSE: Create an ArrivalEvent for an employee record that was pulled from input
  // PARAMETERS:
  //     arrivalTime: the time at which the person arrives
  //     employeeNumber: id of employee
  //     quittingTime: time at which employee's shift is over
  //------------------------------------------------------
  public void addEmployeeArrivalEvent(int arrivalTime, int employeeNumber, int quittingTime) {
    Employee employee = new Employee(employeeNumber, quittingTime, arrivalTime);
    EmployeeArrivalEvent event = new EmployeeArrivalEvent(arrivalTime, employee);
    this.events.addEvent(event);
  }

  //------------------------------------------------------
  // addCustomerArrivalEvent
  //
  // PURPOSE: Create an ArrivalEvent for a customer record that was pulled from input
  // PARAMETERS:
  //     arrivalTime: the time at which the person arrives
  //     numItems: the number of items this customer is purchasing
  //------------------------------------------------------
  public void addCustomerArrivalEvent(int arrivalTime, int numItems) {
    Customer customer = new Customer(numItems, arrivalTime);
    CustomerArrivalEvent event = new CustomerArrivalEvent(arrivalTime, customer);
    this.events.addEvent(event);
  }
  
}