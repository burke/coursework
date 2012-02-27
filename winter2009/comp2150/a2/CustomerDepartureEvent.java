// CLASS: CustomerDepartureEvent
//
// REMARKS: Models the departure of a customer from an event-based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// CustomerDepartureEvent:
//   if |Q| > 0:
//     T Q.front -> StartCheckoutEvent @ t+1

public class CustomerDepartureEvent extends DepartureEvent {

  //------------------------------------------------------
  // CustomerDepartureEvent
  //
  // PURPOSE: Constructs a new CustomerDepartureEvent
  // PARAMETERS:
  //     time: time at which event occurs
  //     customer: customer departing
  //     checkout: checkout customer is departing from
  // EXTERNAL REFERENCES:  requires Customer, Checkout
  //------------------------------------------------------
   public CustomerDepartureEvent(int time, Customer customer, Checkout checkout) {
    super(time,customer,checkout);
  }
  
  //------------------------------------------------------
  // action
  //
  // PURPOSE: called when this event "happens". If the line behind them is
  //     not empty, the customer will trigger a StartCheckoutEvent on the front.
  // EXTERNAL REFERENCES: requires StartCheckoutEvent, Customer
  //------------------------------------------------------
  public void action() {
    Customer customer = (Customer) this.getActor();

    checkout.setCheckingOut((Customer) null);
    
    if (checkout.getLength() > 0) {
      Event event = new StartCheckoutEvent(time+1, checkout.firstInLine(), this.checkout);
      this.store.getEventList().addEvent(event);
    }

    checkout.setCheckingOut(checkout.firstInLine());
    this.checkout.moveLine();

    System.out.printf("Time %d: Customer %d departs (spent %d units waiting)\n",
                      time,
                      customer.getId(),
                      0);
  }
  
}