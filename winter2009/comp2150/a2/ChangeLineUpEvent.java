// CLASS: ChangeLineUpEvent
//
// REMARKS: Models the situation where a cashier closes a checkout while there are
//   customers in line. These customers must repeat the arrival process
//   to find a new checkout.
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// ChangeLineUpEvent:
//   choose Line
//   if Line.empty?:
//     T self -> StartCheckoutEvent @ t+1
//   else:
//     self -> Q

public class ChangeLineUpEvent extends Event {

  //------------------------------------------------------
  // ChangeLineUpEvent
  //
  // PURPOSE: Construct a new ChangeLineUpEvent
  // PARAMETERS:
  //     time: the time at which this event will occur.
  //     customer: the customer that will have to move
  // EXTERNAL REFERENCES:  requires Customer
  //------------------------------------------------------
  public ChangeLineUpEvent(int time, Customer customer) {
    super(time, customer);
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: called when this event "happens". The customer should choose a new
  //     new line, and start checking out if nobody is ahead of them.
  // EXTERNAL REFERENCES:  requires Checkout, Customer, StartCheckoutEvent
  //------------------------------------------------------
  public void action() {
    Checkout checkout = store.preferableCheckout();
    Customer customer = (Customer) this.getActor();
    if (checkout.getLength() == 0) {
      // fire a StartCheckoutEvent at the next time unit.
      Event event = new StartCheckoutEvent(time+1, customer, checkout);
      checkout.setCheckingOut(customer);
      store.getEventList().addEvent(event);
    } else {
      // append self to queue. (stand in line)
      checkout.getInLine(customer);
    }

    System.out.printf("Time %d: Customer %d moves to checkout %d\n",
                      time,
                      customer.getId(),
                      checkout.getIndex());
    
  }
  
}