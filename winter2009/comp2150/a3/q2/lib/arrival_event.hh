#ifndef ARRIVAL_EVENT_HH
#define ARRIVAL_EVENT_HH

/**
 * Models the arrival of a person in an event-driven simulation.
 */
class ArrivalEvent : public Event {

public:
  /**
   * Constructs a new arrival event.
   *
   * @param arrivalTime The time at which this person will arrive
   *
   * @param personType A single character ('E' or 'C') indicating
   * whether the person is an Employee or a Customer, respectively.
   *
   * @param auxData If the person is a customer, represents the number
   *   of groceries they have. If they're a cashier, represents their
   *   employee ID.
   */
  ArrivalEvent(int arrivalTime, char personType, int auxData) : Event(arrivalTime) {
    if (personType == 'C') {
      this->actor = new Customer(auxData, arrivalTime);
    } else {
      this->actor = new Cashier(auxData, arrivalTime);
    }
  }

  /**
   * Run this event. 
   */
  void makeItHappen() {
    bool queued;
    Customer* customer;
    Event* newEvent;
    int departureTime;
    Checkout* checkout;
    Cashier* cashier;
    
    checkout = Store::getCheckout();
      
    // Print time info.
    cout << "Time " << this->time << "  : ";

    // If This event is a _customer_ arrival...
    if (this->actor->isCustomer()) {
      customer = dynamic_cast<Customer *>(this->actor);
      if (customer == NULL) {
        cout << "How the fuck did that happen?";
      } else {
        // addCustomer returns bool => did the customer have to stand in line?
        queued = Store::getCheckout()->addCustomer(customer);

        cout << "Customer  " << this->actor->getId() << " arrives ("
             << customer->getNumItems() << " items, enters checkout & "
             << (queued ? "waits" : "gets served")
             << ")" << endl;

        // If the customer is checking out, make a DepartureEvent
        if (! queued) {
          departureTime = time + checkout->getCashier()->timeForItems(customer->getNumItems());
          newEvent = new CustomerDepartureEvent(departureTime, customer, Store::getCheckout());
          Store::getEventList()->insertInOrder(newEvent);
        } else { //customer is standing in line.
          customer->startWaiting(time);
        }
      }
    } else { // cashier
      cashier = dynamic_cast<Cashier *>(this->actor);
      if (cashier) {
        checkout->setCashier(cashier, time);
        cout << "Cashier " << this->actor->getId() << " arrives & starts work" << endl;
      }
    }

  }

  void print() {
    string type = (this->actor->isCustomer()) ? "Customer" : "Cashier";
    cout << "Arrival of " << type << " " << this->actor->getId() << " at time " << this->time << endl;
  }
  
};

#endif
