// CLASS: name...
//
// REMARKS: What is the purpose of this class? describe what it does?
//
// Input: What is the input?
//
// Output: What is the output?
//
//-----------------------------------------
public class Checkout {

  Queue customerLine;
  Employee cashier;
  int index;
  Customer checkingOut;
  boolean open;
  
  //------------------------------------------------------
  // Checkout
  //
  // PURPOSE: Construct a new Checkout
  // PARAMETERS: index: this checkout's number (think of the numbered signs)
  // EXTERNAL REFERENCES:  requires Queue
  //------------------------------------------------------
  public Checkout(int index) {
    customerLine = new Queue();
    this.index = index;
    this.open = false;
  }

  //------------------------------------------------------
  // isOpen
  //
  // PURPOSE: returns a boolean indicating whether or not the checkout is Open
  //------------------------------------------------------
  public boolean isOpen() {
    return open;
  }

  //------------------------------------------------------
  // setOpen
  //
  // PURPOSE: change the openness state of the checkout
  // PARAMETERS:  open: boolean value -- should checkout be open?
  //------------------------------------------------------
  public void setOpen(boolean open) {
    this.open = open;
  }

  //------------------------------------------------------
  // setCheckingOut
  //
  // PURPOSE: Allow a customer to check out.
  // PARAMETERS:  customer: the Customer at the head of the line that should check out.
  // EXTERNAL REFERENCES:  requires Customer
  //------------------------------------------------------
  public void setCheckingOut(Customer customer) {
    this.checkingOut = customer;
  }

  //------------------------------------------------------
  // getCheckingOut
  //
  // PURPOSE: Returns the customer that is currently checking out at this checkout.
  //------------------------------------------------------
 
  public Customer getCheckingOut() {
    return this.checkingOut;
  }

  //------------------------------------------------------
  // getIndex
  //
  // PURPOSE: returns the number of this checkout (think numbered signs)
  //------------------------------------------------------
  public int getIndex() {
    return this.index;
  }

  //------------------------------------------------------
  // getLength
  //
  // PURPOSE: Return the number of people waiting in line at this checkout.
  //------------------------------------------------------
  public int getLength() {
    int length = 0;
    if (this.checkingOut != null) {
      length = 1;
    }
    return customerLine.size() + length;
  }

  //------------------------------------------------------
  // getInLine
  //
  // PURPOSE: Put a customer at the back of the line at this checkout.
  // PARAMETERS:  customer: the Customer object to put in line
  // EXTERNAL REFERENCES:  requires Customer
  //------------------------------------------------------
  public void getInLine(Customer customer) {
    customerLine.enqueue(customer);
  }

  //------------------------------------------------------
  // firstInLine
  //
  // PURPOSE: returns the Customer at the front of the line.
  //------------------------------------------------------
  public Customer firstInLine() {
    return (Customer) customerLine.getFirst();
  }

  //------------------------------------------------------
  // moveLine
  //
  // PURPOSE: Move the line ahead. Remove the front person from the list, as
  //     they've already started checking out.
  // EXTERNAL REFERENCES:  requires Customer
  //------------------------------------------------------
  public Customer moveLine() {
    return (Customer) customerLine.dequeue();
  }

  //------------------------------------------------------
  // setCashier
  //
  // PURPOSE: Called when a new cashier comes to this checkout.
  // PARAMETERS:  cashier: the Employee that will work this checkout.
  // EXTERNAL REFERENCES:  requires Employee
  //------------------------------------------------------
  public void setCashier(Employee cashier) {
    this.cashier = cashier;
  }

  //------------------------------------------------------
  // getCashier
  //
  // PURPOSE: returns the Employee currently working this checkout
  //------------------------------------------------------
  
  public Employee getCashier() {
    return this.cashier;
  }
  //------------------------------------------------------
  // hasCashier
  //
  // PURPOSE: Returns a boolean value indicating whether or not there is
  //     currently an employee working this checkout.
  //------------------------------------------------------
  
  public boolean hasCashier() {
    return (this.cashier != null);
  }
  
}
