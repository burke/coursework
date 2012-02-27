import java.util.Random;

/**
 * Models a customer in an event-based store simulation
 */
public class Customer extends Person {
  int numItems; ///< Number of grocery items this customer has
  int startedCheckout = -1; ///< Time at which customer started checking out, or -1 if they haven't yet.
  static int baseId = 42; ///< Initial Customer ID. It'll increment from here.
  static Random x = new Random(1000); ///< Random sequence for generating customer patience
  int patience;
  
  int timeSpentWaiting = 0;
  int startedWaiting = -1;
  
  /**
   * Constructs a new Customer object
   * @param numItems the number of items this customer is purchasing
   * @param arrival the time at which this customer arrived
   */
   public Customer(int numItems, int arrival) {
    this.setId();
    this.setPatience();
    this.numItems = numItems;
    this.arrival = arrival;
  }

  public void startWaiting(int time) {
    if (this.startedWaiting == -1)
      this.startedWaiting = time+1;
  }

  public void stopWaiting(int time) {
    if (this.startedWaiting != -1) {
      this.timeSpentWaiting += (time - startedWaiting);
      this.startedWaiting = -1;
    }
  }

  public int totalTimeWaiting() {
    return this.timeSpentWaiting;
  }
  

  /**
   * Set the speed of this employee based on a predefined "random"
   * sequence (see static Random x above)
   */
  private void setPatience() {
    patience = x.nextInt(3)+1;
  }

  public int getPatience() {
    return patience;
  }
  
  /** return the time at which this customer started checking out */
   public int getStartedCheckout() {
    return this.startedCheckout;
  }

  /**
   * Set the time at which this customer will start checking out for future reference.
   * @param time the time at which customer starts checking out.
   */
   public void setStartedCheckout(int time) {
    this.startedCheckout = time;
  }
  
  /** set an incremental ID starting at 42 (see static int baseId) */
  private void setId() {
    id = baseId++;
  }
  
  /** return the number of items this customer is purchasing */
   public int getNumItems() {
    return numItems;
  }
}
