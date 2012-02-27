#ifndef CASHIER_DEPARTURE_EVENT_HH
#define CASHIER_DEPARTURE_EVENT_HH

/**
 * Models the departure of a cashier from the simulation
 */
class CashierDepartureEvent : public DepartureEvent {
private:
  Cashier* cashier;
  Checkout* checkout;
  
public:

  /** Constructs a new CashierDepartureEvent */
  CashierDepartureEvent(int time, Cashier* cashier, Checkout* checkout) : DepartureEvent(time) {
    this->cashier = cashier;
    this->actor = cashier;
    this->checkout = checkout;
  }

  void makeItHappen() {
    cashier->setQuitTime(this->time);
    checkout->setCashier(NULL, time);
    
    cout << "Time " << this->time << "  : Cashier " << this->cashier->getId()
         << " leaves checkout (worked " << cashier->timeWorked() << " time units)" << endl;

    Store::getCashierList()->insertInOrder(this->cashier);

  }
  
};

#endif
