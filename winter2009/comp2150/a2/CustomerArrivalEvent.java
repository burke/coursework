// CLASS: CustomerArrivalEvent
//
// REMARKS: Models the arrival of a customer in an event-based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// CustomerArrivalEvent:
//   choose Line
//   if Line.empty?:
//     T self -> StartCheckoutEvent @ t+1
//   else:
//     self -> Q

public class CustomerArrivalEvent extends ArrivalEvent {

  //------------------------------------------------------
  // CustomerArrivalEvent
  //
  // PURPOSE: Construct a new CustomerArrivalEvent
  // PARAMETERS:
  //     arrival: the time at which the customer arrives
  //     customer: the customer arriving
  // EXTERNAL REFERENCES:  requires Customer
  //------------------------------------------------------
   public CustomerArrivalEvent(int arrival, Customer customer) {
    super(arrival, customer);
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: called when this event "happens". The customer first chooses
  //     the "best" checkout line, then either starts checking out or waits in line.
  // EXTERNAL REFERENCES: requires Checkout, Customer, StartCheckoutEvent
  //------------------------------------------------------
  public void action() {
    store.readNextArrival();

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

    System.out.printf("Time %d: Customer %d arrives (%d items, enters line for Checkout %d)\n",
                      time,
                      customer.getId(),
                      customer.getNumItems(),
                      checkout.getIndex());
  }
  
}