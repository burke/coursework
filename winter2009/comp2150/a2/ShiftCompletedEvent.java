// CLASS: ShiftCompletedEvent
//
// REMARKS: Models the situation where a cashier is done their shift and wants to leave.
//
// Input: None
//
// Output: None
//
//-----------------------------------------

// ShiftCompletedEvent:
//   T Q.each -> ChangeLineUp @ t+1
//   T self -> EmployeeDepartureEvent @ f()

public class ShiftCompletedEvent extends Event {

  Checkout checkout;
  
  //------------------------------------------------------
  // ShiftCompletedEvent
  //
  // PURPOSE: Construct a new ShiftCompletedEvent
  // PARAMETERS:
  //     time: the time at which this event occurs
  //     employee: the employee whose shift is complete
  //     checkout: the checkout the employee is currently working
  // EXTERNAL RESOURCES: requires Employee, Checkout
  //------------------------------------------------------
  public ShiftCompletedEvent(int time, Employee employee, Checkout checkout) {
    super(time, employee);
    this.checkout = checkout;
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: The method called when this event "happens". For each person
  //     standing in line at this checkout, send them a ChangeLineUpEvent,
  //     then trigger an EmployeeDepartureEvent as soon as the current customer is gone.
  // EXTERNAL RESOURCES: requires Employee, ChangeLineUpEvent, EmployeeDepartureEvent, Customer
  //------------------------------------------------------
  public void action() {
    Customer customer;
    Event event;
    Employee employee = (Employee) this.getActor();
    int checkoutTime;
    int startTime;
    
    checkout.setOpen(false);
    
    for (customer = checkout.moveLine(); customer != null; customer = checkout.moveLine()) {
      event = new ChangeLineUpEvent(time+1,customer);
      store.getEventList().addEvent(event);
    }

    
    
    customer = checkout.getCheckingOut();
    if (customer != null) {
      checkoutTime = employee.unitsForItems(customer.getNumItems());
      if(customer.getStartedCheckout() == -1) {
        startTime = time+1;
      } else {
        startTime = customer.getStartedCheckout();
      }
      event = new EmployeeDepartureEvent(startTime+checkoutTime+1, employee, checkout);
      store.getEventList().addEvent(event);
      System.out.printf("Time %d: Cashier %d closes checkout %d (cust. %d will be completed)\n",
                        time,
                        employee.getId(),
                        checkout.getIndex(),
                        customer.getId());
    } else {
      event = new EmployeeDepartureEvent(time+1, employee, checkout);
      store.getEventList().addEvent(event);
      System.out.printf("Time %d: Cashier %d closes checkout %d (no remaining customers in line)\n",
                        time,
                        employee.getId(),
                        checkout.getIndex());
    }

                      
                      
  }
  
}