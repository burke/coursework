/**
 * Models the departure of an employee in a simulation
 */
class CashierDepartureEvent extends DepartureEvent {

  /**
   * Construct a new CashierDepartureEvent
   *
   * @param time time at which the event occurs
   * @param employee employee leaving
   * @param checkout checkout employee is leaving from
   * @externals Cashier, Checkout
   */
  public CashierDepartureEvent(int time, Cashier employee, Checkout checkout) {
    super(time, employee, checkout);
  }
  
  /**
   * method called when event "happens". Remove self from Checkout.
   * @externals Cashier
   */
  public void action() {
    Cashier employee = (Cashier) this.getActor();
    checkout.setCashier(null);
    System.out.printf("Time %d: Cashier %d leaves checkout %d (worked %d time units)\n",
                      time,
                      employee.getId(),
                      checkout.getIndex(),
                      time-employee.getArrival());
  }
  
}