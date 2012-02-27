// CLASS: CheckoutEvent
//
// REMARKS: Models the situation where a customer is having their items checkout out
//   by a cashier.
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// CheckoutEvent:
//   deQ self from Q
//   T self -> CustomerDepartureEvent @ t+ceil(items/speed)

public class CheckoutEvent extends Event {

  Checkout checkout;

  //------------------------------------------------------
  // CheckoutEvent
  //
  // PURPOSE: Construct a new CheckoutEvent
  // PARAMETERS:
  //     time: the time at which this event will occur
  //     customer: the customer checking out
  //     checkout: the checkout they're checking out at.
  // EXTERNAL REFERENCES:  require Customer, Checkout
  //------------------------------------------------------
  public CheckoutEvent(int time, Customer customer, Checkout checkout) {
    super(time, customer);
    this.checkout = checkout;
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: called when this event "happens". The customer removes themself
  //     from the line and triggers a DepartureEvent for the time at which
  //     they will be done checking out.
  //------------------------------------------------------
  public void action() {
    int currentTime = this.getTime();
    Customer customer = (Customer) this.getActor();
    customer.setStartedCheckout(time);
    Employee cashier = this.checkout.getCashier();
    int speed = cashier.getSpeed();
    int checkoutTime = cashier.unitsForItems(customer.getNumItems());
    Event triggered = new CustomerDepartureEvent(currentTime+checkoutTime-1,customer,checkout);
    SoopaStore store = SoopaStore.getInstance();
    store.getEventList().addEvent(triggered);
  }
  
}