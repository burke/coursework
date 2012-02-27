// CLASS: EmployeeArrivalEvent
//
// REMARKS: Models the arrival of an employee in a simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// EmployeeArrivalEvent: 
//   (open first available till)
//   T self -> ShiftCompletedEvent @ quittingTime

public class EmployeeArrivalEvent extends ArrivalEvent {

  Checkout checkout;
  
  //------------------------------------------------------
  // EmployeeArrivalEvent
  //
  // PURPOSE: Construct a new EmployeeArrivalEvent
  // PARAMETERS:
  //     arrival: time at which employee arrives
  //     employee: employee arriving
  // EXTERNAL REFERENCES:  requires Employee
  //------------------------------------------------------
   public EmployeeArrivalEvent(int arrival, Employee employee) {
    super(arrival, employee);
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: method called when this event "happens". Find the first
  //     available checkout, then trigger a ShiftCompletedEvent at quittingTime.
  // EXTERNAL REFERENCES:  requires Employee, ShiftCompletedEvent
  //------------------------------------------------------
   public void action() {
    store.readNextArrival();
    Employee employee = (Employee)this.getActor();
    checkout = store.firstUnmannedCheckout();
    checkout.setCashier(employee);
    checkout.setOpen(true);
    int quittingTime = employee.getQuittingTime();
    Event event = new ShiftCompletedEvent(quittingTime, employee, checkout);
    store.getEventList().addEvent(event);
    
    System.out.printf("Time %d: Cashier %d arrives (Speed %d, quitting time %d, checkout %d)\n",
                      time,
                      employee.getId(),
                      employee.getSpeed(),
                      employee.getQuittingTime(),
                      checkout.getIndex());
  }
  
}