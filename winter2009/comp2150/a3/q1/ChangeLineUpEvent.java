/**
 * Models the situation where a cashier closes a checkout while there
 * are customers in line. These customers must repeat the arrival
 * process to find a new checkout.
 *
 * <pre>
 * ChangeLineUpEvent:
 *   choose Line
 *   if Line.empty?:
 *     T self -> StartCheckoutEvent @ t+1
 *   else:
 *     self -> Q
 * </pre>
 */

public class ChangeLineUpEvent extends Event {

  /**
   * Construct a new ChangeLineUpEvent
   * @param time the time at which this event will occur.
   * @param customer the customer that will have to move
   * @externals Customer
   */
  public ChangeLineUpEvent(int time, Customer customer) {
    super(time, customer);
  }

  /**
   * called when this event "happens". The customer should choose a ne
   * line, and start checking out if nobody is ahead of them.
   * @externals Checkout, Customer, StartCheckoutEvent
   */
  public void action() {
    Checkout checkout = SoopaStore.preferableCheckout();
    Customer customer = (Customer) this.getActor();
    if (checkout.getLength() == 0) {
      // fire a StartCheckoutEvent at the next time unit.
      Event event = new StartCheckoutEvent(time+1, customer, checkout);
      checkout.setCheckingOut(customer);
      SoopaStore.getEventList().insertInOrder(event);
    } else {
      // append self to queue. (stand in line)
      customer.startWaiting(time);
      checkout.getInLine(customer);
    }

    System.out.printf("Time %d: Customer %d moves to checkout %d (forced)\n",
                      time,
                      customer.getId(),
                      checkout.getIndex());
    
  }
  
}