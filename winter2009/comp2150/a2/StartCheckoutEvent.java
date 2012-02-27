// CLASS: StartCheckoutEvent
//
// REMARKS: Models the situation where a customer is preparing to check out, ie.
//   unloading groceries.
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// StartCheckoutEvent:
//   T self -> CheckoutEvent @ t+1
  
public class StartCheckoutEvent extends Event {

  Checkout checkout;
  
  //------------------------------------------------------
  // StartCheckoutEvent
  //
  // PURPOSE: Construct a new startCheckoutEvent
  // PARAMETERS:
  //     time: time at which this event occurs
  //     customer: customer checking out
  //     checkout: checkout customer is checking out at.
  //------------------------------------------------------
  public StartCheckoutEvent(int time, Customer customer, Checkout checkout) {
    super(time, customer);
    this.checkout = checkout;
  }
  
  //------------------------------------------------------
  // action
  //
  // PURPOSE: The method called when this event "happens".
  //     Just trigger a CheckoutEvent at time t+1
  // EXTERNAL REFERENCES: requires Event, SoopaStore, Customer, CheckoutEvent
  //------------------------------------------------------
  public void action() {
    int currentTime = this.getTime();
    Event triggered = new CheckoutEvent(currentTime+1, (Customer) this.getActor(), this.checkout);
    SoopaStore store = SoopaStore.getInstance();
    store.getEventList().addEvent(triggered);

    Customer customer = (Customer) this.getActor();

    checkout.setCheckingOut(customer);
    
    System.out.printf("Time %d: Customer %d starts checkout (#%d, speed %d, will take %d units)\n",
                      time,
                      customer.getId(),
                      checkout.getIndex(),
                      checkout.getCashier().getSpeed(),
                      checkout.getCashier().unitsForItems(customer.getNumItems()));
                      
  }

}