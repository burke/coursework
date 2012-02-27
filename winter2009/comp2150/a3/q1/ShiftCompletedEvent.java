/**
 * Models the situation where a cashier is done their shift and wants to leave.
 *
 * <pre>
 * ShiftCompletedEvent:
 *   T Q.each -> ChangeLineUp @ t+1
 *   T self -> CashierDepartureEvent @ f()
 * </pre>
 */
public class ShiftCompletedEvent extends Event {

  Checkout checkout; ///< The checkout from which the cashier is attempting to leave.
  
  /**
   * Construct a new ShiftCompletedEvent
   * @param time the time at which this event occurs
   * @param employee the employee whose shift is complete
   * @param checkout the checkout the employee is currently working
   * @externals Cashier, Checkout
   */
  public ShiftCompletedEvent(int time, Cashier employee, Checkout checkout) {
    super(time, employee);
    this.checkout = checkout;
  }

  /**
   * The method called when this event "happens". For each person
   * standing in line at this checkout, send them a ChangeLineUpEvent,
   * then trigger an CashierDepartureEvent as soon as the current
   * customer is gone.
   * @externals Cashier, ChangeLineUpEvent, CashierDepartureEvent, Customer
   */
  public void action() {
    Customer customer;
    Event event;
    Cashier employee = (Cashier) this.getActor();
    int checkoutTime;
    int startTime;
    
    checkout.setOpen(false);
    
    for (customer = checkout.moveLine(); customer != null; customer = checkout.moveLine()) {
      event = new ChangeLineUpEvent(time+1,customer);
      SoopaStore.getEventList().insertInOrder(event);
    }

    
    
    customer = checkout.getCheckingOut();
    if (customer != null) {
      checkoutTime = employee.unitsForItems(customer.getNumItems());
      if(customer.getStartedCheckout() == -1) {
        startTime = time+1;
      } else {
        startTime = customer.getStartedCheckout();
      }
      event = new CashierDepartureEvent(startTime+checkoutTime+1, employee, checkout);
      SoopaStore.getEventList().insertInOrder(event);
      System.out.printf("Time %d: Cashier %d closes checkout %d (cust. %d will be completed)\n",
                        time,
                        employee.getId(),
                        checkout.getIndex(),
                        customer.getId());
    } else {
      event = new CashierDepartureEvent(time+1, employee, checkout);
      SoopaStore.getEventList().insertInOrder(event);
      System.out.printf("Time %d: Cashier %d closes checkout %d (no remaining customers in line)\n",
                        time,
                        employee.getId(),
                        checkout.getIndex());
    }

                      
                      
  }
  
}