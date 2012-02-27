/**
 * Models the arrival of an employee in a simulation
 *
 * <pre>
 * CashierArrivalEvent: 
 *   (open first available till)
 *   T self -> ShiftCompletedEvent @ quittingTime
 * </pre>
 */
public class CashierArrivalEvent extends ArrivalEvent {

  Checkout checkout; ///< The specific Checkout involved in this event.
  
  /**
   * Construct a new CashierArrivalEvent
   *
   * @param arrival time at which employee arrives
   * @param employee employee arriving
   * @externals Cashier
   */
  public CashierArrivalEvent(int arrival, Cashier employee) {
    super(arrival, employee);
  }

  /**
   * method called when this event "happens". Find the first available
   * checkout, then trigger a ShiftCompletedEvent at quittingTime.
   * @externals Cashier, ShiftCompletedEvent
   */
  public void action() {
    SoopaStore.readNextArrival();
    Cashier employee = (Cashier)this.getActor();
    checkout = SoopaStore.firstUnmannedCheckout();
    checkout.setCashier(employee);
    checkout.setOpen(true);
    int quittingTime = employee.getQuittingTime();
    Event event = new ShiftCompletedEvent(quittingTime, employee, checkout);
    SoopaStore.getEventList().insertInOrder(event);
    
    System.out.printf("Time %d: Cashier %d arrives (Speed %d, quitting time %d, checkout %d)\n",
                      time,
                      employee.getId(),
                      employee.getSpeed(),
                      employee.getQuittingTime(),
                      checkout.getIndex());
  }
  
}