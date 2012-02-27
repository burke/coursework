/**
 * Models the departure of a customer from an event-based simulation
 *
 * <pre>
 * CustomerDepartureEvent:
 *   if |Q| > 0:
 *     T Q.front -> StartCheckoutEvent @ t+1
 * </pre>
 */

public class CustomerDepartureEvent extends DepartureEvent {

  /**
   * Constructs a new CustomerDepartureEvent
   *
   * @param time time at which event occurs
   * @param customer customer departing
   * @param checkout: checkout customer is departing from
   * @externals Customer, Checkout
   */
  public CustomerDepartureEvent(int time, Customer customer, Checkout checkout) {
    super(time,customer,checkout);
  }
  
  /**
   * called when this event "happens". If the line behind them is not
   * empty, the customer will trigger a StartCheckoutEvent on the
   * front.
   * 
   * @externals StartCheckoutEvent, Customer
   */
  public void action() {
    Customer customer = (Customer) this.getActor();

    checkout.setCheckingOut((Customer) null);
    
    if (checkout.getLength() > 0) {
      Event event = new StartCheckoutEvent(time+1, checkout.firstInLine(), this.checkout);
      SoopaStore.getEventList().insertInOrder(event);
    }

    checkout.setCheckingOut(checkout.firstInLine());
    this.checkout.moveLine();

    System.out.printf("Time %d: Customer %d departs (spent %d units waiting)\n",
                      time,
                      customer.getId(),
                      customer.totalTimeWaiting());
  }
  
}