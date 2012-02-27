/**
 * Models the situation where a customer is preparing to check out,
 * ie. unloading groceries.
 *
 * <pre>
 * StartCheckoutEvent:
 *   T self -> CheckoutEvent @ t+1
 * </pre>
 */
public class StartCheckoutEvent extends Event {

  Checkout checkout; ///< The checkout this customer is starting checking out at.
  
  /**
   * Construct a new startCheckoutEvent
   * @param time time at which this event occurs
   * @param customer customer checking out
   * @param checkout checkout customer is checking out at.
   */
  public StartCheckoutEvent(int time, Customer customer, Checkout checkout) {
    super(time, customer);
    this.checkout = checkout;
  }
  
  /**
   * The method called when this event "happens". Just trigger a CheckoutEvent at time t+1
   * @externals Event, SoopaStore, Customer, CheckoutEvent
   */
  public void action() {
    int currentTime = this.getTime();
    Event triggered = new CheckoutEvent(currentTime+1, (Customer) this.getActor(), this.checkout);
    SoopaStore.getEventList().insertInOrder(triggered);

    Customer customer = (Customer) this.getActor();

    customer.stopWaiting(time);
    checkout.setCheckingOut(customer);
    
    System.out.printf("Time %d: Customer %d starts checkout (#%d, speed %d, will take %d units)\n",
                      time,
                      customer.getId(),
                      checkout.getIndex(),
                      checkout.getCashier().getSpeed(),
                      checkout.getCashier().unitsForItems(customer.getNumItems()));
                      
  }

}