#ifndef CUSTOMER_DEPARTURE_EVENT_HH
#define CUSTOMER_DEPARTURE_EVENT_HH

/**
 * Models the departure of a customer from the simulation
 */
class CustomerDepartureEvent : public DepartureEvent {

private:
  Customer* customer; ///< The customer departing
  Checkout* checkout; ///< The checkout they're departing from.

public:

  /** Constructs a new CustomerDepartureEvent */
  CustomerDepartureEvent(int time, Customer* customer, Checkout* checkout) : DepartureEvent(time) {
    this->customer = customer;
    this->actor = customer;
    this->checkout = checkout;
  }
  
  void makeItHappen() {
    int departureTime;
    Customer* next = Store::getCheckout()->moveLineAhead();

    this->checkout->ringThrough(this->customer);
    
    /// If there are more customers in line, set up the next one in line
    if (next) {
      next->finishWaiting(time);
      departureTime = time + 1 + checkout->getCashier()->timeForItems(next->getNumItems());

      Event* newEvent = new CustomerDepartureEvent(departureTime, next, this->checkout);
      Store::getEventList()->insertInOrder(newEvent);
    }
    /// Otherwise, let the cashier go home.
    else {
      departureTime = time + 1;
      Event* newEvent = new CashierDepartureEvent(departureTime, checkout->getCashier(), checkout);
      Store::getEventList()->insertInOrder(newEvent);
    }
    
    cout << "Time " << this->time << "  : Customer  " << this->customer->getId()
         << " departs (spent " << this->customer->timeWaiting() << " units waiting)"
         << endl;

    Store::getCustomerList()->insertInOrder(this->customer);
  }

};

#endif
