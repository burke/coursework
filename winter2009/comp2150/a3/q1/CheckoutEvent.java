/**
 * Models the situation where a customer is having their items checkout out by a cashier.
 *
 * <pre>
 * CheckoutEvent:
 *  deQ self from Q
 *  T self -> CustomerDepartureEvent @ t+ceil(items/speed)
 * </pre>
 */
public class CheckoutEvent extends Event {

  Checkout checkout; ///< The specific checkout that this event refers to.

  /**
   * Construct a new CheckoutEvent
   * @param time the time at which this event will occur
   * @param customer the customer checking out
   * @param checkout the checkout they're checking out at.
   * @externals Customer, Checkout
   */
  public CheckoutEvent(int time, Customer customer, Checkout checkout) {
    super(time, customer);
    this.checkout = checkout;
  }

  /**
   * called when this event "happens". The customer removes themself
   * from the line and triggers a DepartureEvent for the time at which
   * they will be done checking out.
   */
  public void action() {
    int currentTime = this.getTime();
    Customer customer = (Customer) this.getActor();
    customer.setStartedCheckout(time);
    Cashier cashier = this.checkout.getCashier();
    int speed = cashier.getSpeed();
    int checkoutTime = cashier.unitsForItems(customer.getNumItems());
    Event triggered = new CustomerDepartureEvent(currentTime+checkoutTime-1,customer,checkout);
    SoopaStore.getEventList().insertInOrder(triggered);
  }
  
}