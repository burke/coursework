/**
 * Represents a single Checkout in a Store simulation. 
 */
public class Checkout {

  Queue customerLine; ///< Line of customers waiting to checko out here
  Cashier cashier; ///< Cashier currently serving customers at this checkout
  int index; ///< Index into checkouts[] array
  Customer checkingOut; ///< Currently checking-out customer
  boolean open; ///< True if there's nobody checking out or standing in line here.
  int timeStaffed;
  int staffedSince = -1;
  int numCashiers = 0;
  int numCustomers = 0;
  int totalItems = 0;
  /**
   * Construct a new Checkout
   * @param index this checkout's number (think of the numbered signs)
   * @externals Queue
   */
  public Checkout(int index) {
    customerLine = new Queue();
    this.index = index;
    this.open = false;
  }

  /** returns a boolean indicating whether or not the checkout is Open */
  public boolean isOpen() {
    return open;
  }

  public int getTotalItems() {
    return totalItems;
  }
  
  public int getNumCustomers() {
    return numCustomers;
  }
  
  public int getNumCashiers() {
    return numCashiers;
  }
  
  public int getTimeStaffed() {
    return timeStaffed;
  }
  
  /**
   * change the openness state of the checkout
   * @param open boolean value -- should checkout be open?
   */
  public void setOpen(boolean open) {
    this.open = open;
  }

  /**
   * Allow a customer to check out.
   * @param customer the Customer at the head of the line that should check out.
   * @externals Customer
   */
  public void setCheckingOut(Customer customer) {
    if (customer != null) {
      if (checkingOut == null || checkingOut.getId() != customer.getId())
      numCustomers++;
      totalItems += customer.getNumItems();
    }
    this.checkingOut = customer;
  }

  /** Returns the customer that is currently checking out at this checkout. */
  public Customer getCheckingOut() {
    return this.checkingOut;
  }

  /** returns the number of this checkout (think numbered signs) */
  public int getIndex() {
    return this.index;
  }

  /** Return the number of people waiting in line at this checkout. */
  public int getLength() {
    int length = 0;
    if (this.checkingOut != null) {
      length = 1;
    }
    return customerLine.size() + length;
  }


  /**
   * Put a customer at the back of the line at this checkout.
   * @param customer the Customer object to put in line
   * @externals Customer
   */
  public void getInLine(Customer customer) {
    customerLine.enqueue(customer);
  }

  /** returns the Customer at the front of the line. */
  public Customer firstInLine() {
    return (Customer) customerLine.getFirst();
  }

  /**
   * Move the line ahead. Remove the front person from the list, as
   * they've already started checking out.
   *
   * @externals Customer
   */
  public Customer moveLine() {
    return (Customer) customerLine.dequeue();
  }

  /**
   * Called when a new cashier comes to this checkout.
   * @param cashier the Cashier that will work this checkout.
   * @externals Cashier
   */
  public void setCashier(Cashier cashier) {
    if (cashier == null) {
      if (staffedSince != -1) {
        timeStaffed += (SoopaStore.currentTime() - staffedSince);
        staffedSince = -1;
      }
    } else {
      numCashiers++;
      if (staffedSince == -1) {
        staffedSince = SoopaStore.currentTime();
      }
    }
    this.cashier = cashier;
  }

  /** returns the Cashier currently working this checkout */
  public Cashier getCashier() {
    return this.cashier;
  }

  /**
   * Returns a boolean value indicating whether or not there is
   * currently an employee working this checkout.
   */
  public boolean hasCashier() {
    return (this.cashier != null);
  }
  
}
