// CLASS: EmployeeDepartureEvent
//
// REMARKS: Models the departure of an employee in a simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
class EmployeeDepartureEvent extends DepartureEvent {

  //------------------------------------------------------
  // EmployeeDepartureEvent
  //
  // PURPOSE: Construct a new EmployeeDepartureEvent
  // PARAMETERS:
  //     time: time at which the event occurs
  //     employee: employee leaving
  //     checkout: checkout employee is leaving from
  // EXTERNAL REFERENCES:  requires Employee, Checkout
  //------------------------------------------------------
  public EmployeeDepartureEvent(int time, Employee employee, Checkout checkout) {
    super(time, employee, checkout);
  }
  
  //------------------------------------------------------
  // action
  //
  // PURPOSE: method called when event "happens". Remove self from Checkout.
  // EXTERNAL REFERENCES:  requires Employee
  //------------------------------------------------------
  public void action() {
    Employee employee = (Employee) this.getActor();
    checkout.setCashier(null);
    System.out.printf("Time %d: Cashier %d leaves checkout %d (worked %d time units)\n",
                      time,
                      employee.getId(),
                      checkout.getIndex(),
                      time-employee.getArrival());
  }

}