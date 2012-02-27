import java.util.Random;

/**
 * Models an employee in an event-based simulation
 */
public class Cashier extends Person {
  int quittingTime; ///< Time employee's shift ends.
  int speed; ///< Number of items per time unit employee is capable of processing
  static Random x = new Random(1000); ///< Random sequence for generating cashier speeds

  /**
   *Construct a new Cashier
   * @param employeeNumber the ID number of the Cashier
   * @param quittingTime the time at which this employee expects to end their shift
   * @param arrivalTime ths time at which this employee will arrive
   */
  public Cashier(int employeeNumber, int quittingTime, int arrivalTime) {
    this.id = employeeNumber;
    this.quittingTime = quittingTime;
    this.arrival = arrivalTime;
    setSpeed();
  }
  
  /**
   * Return the number of time units this employee will take to process n items
   * @param items the number of itmems to process
   */
  public int unitsForItems(int items) {
    return (int)Math.ceil((double)items / (double)speed);
  }
  
  /** return the time at which this employee expects to leave */
  public int getQuittingTime() {
    return this.quittingTime;
  }

  /**
   * Set the speed of this employee based on a predefined "random"
   * sequence (see static Random x above)
   */
  private void setSpeed() {
    speed = x.nextInt(10)+1;
  }

  /** return the speed of this employee */
  public int getSpeed() {
    return this.speed;
  }
  
}