/**
 * Models the arrival of a customer in an event-based simulation
 *
 * <pre>
 * CustomerArrivalEvent:
 *   choose Line
 *   if Line.empty?:
 *     T self -> StartCheckoutEvent @ t+1
 *   else:
 *     self -> Q
 * </pre>
 */
public class CustomerArrivalEvent extends ArrivalEvent {

  /** Construct a new CustomerArrivalEvent
   * @param arrival the time at which the customer arrives
   * @param customer the customer arriving
   * @externals Customer
   */
  public CustomerArrivalEvent(int arrival, Customer customer) {
    super(arrival, customer);
  }

  /**
   * called when this event "happens". The customer first chooses the
   * "best" checkout line, then either starts checking out or waits in
   * line.
   * 
   * @externals requires Checkout, Customer, StartCheckoutEvent
   */
  public void action() {
    SoopaStore.readNextArrival();

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

    System.out.printf("Time %d: Customer %d arrives (%d items, Patience %d, checkout %d)\n",
                      time,
                      customer.getId(),
                      customer.getNumItems(),
                      customer.getPatience(),
                      checkout.getIndex());
  }
  
}