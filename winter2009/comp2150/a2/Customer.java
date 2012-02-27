// CLASS: Customer
//
// REMARKS: Models a customer in an event-based store simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public class Customer extends Person {
  int numItems;
  int startedCheckout = -1;
  static int baseId = 42;
  
  //------------------------------------------------------
  // Customer
  //
  // PURPOSE: Constructs a new Customer object
  // PARAMETERS:
  //     numItems: the number of items this customer is purchasing
  //     arrival: the time at which this customer arrived
  //------------------------------------------------------
   public Customer(int numItems, int arrival) {
    this.setId();
    this.numItems = numItems;
    this.arrival = arrival;
  }

  //------------------------------------------------------
  // getStartedCheckout
  //
  // PURPOSE: return the time at which this customer started checking out
  //------------------------------------------------------
   public int getStartedCheckout() {
    return this.startedCheckout;
  }

  //------------------------------------------------------
  // setStartedCheckout
  //
  // PURPOSE: Set the time at which this customer will start checking out for future reference.
  // PARAMETERS:  time: the time at which customer starts checking out.
  // EXTERNAL REFERENCES:  
  //------------------------------------------------------
   public void setStartedCheckout(int time) {
    this.startedCheckout = time;
  }
  
  //------------------------------------------------------
  // setId
  //
  // PURPOSE: set an incremental ID starting at 42 (see static int baseId)
  //------------------------------------------------------
  private void setId() {
    id = baseId++;
  }
  
  //------------------------------------------------------
  // getNumItems
  //
  // PURPOSE: return the number of items this customer is purchasing
  //------------------------------------------------------
   public int getNumItems() {
    return numItems;
  }
}
