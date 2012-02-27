#ifndef CUSTOMER_HH
#define CUSTOMER_HH

/**
 * Represents a customer in an event-driven simulation of a store.
 */
class Customer : public Person {
private:
  static int nextCustomerNum; ///< Integer ID to use for the next Customer introduced into the system
  int numItems;

  int startedWaiting;
  int finishedWaiting;
  
public:

  /**
   * Construct a new customer with a given number of items.
   * Increment nextCustomerNum while assigning an ID.
   * @param numItems The number of items this customer is purchasing
   * @param time the arrival time of this customer
   */
  Customer(int numItems, int time) : Person(nextCustomerNum++, time) {
    this->numItems = numItems;
    this->startedWaiting = -1;
  }

  /** Is this object of type Customer? */
  bool isCustomer() { return true; }

  /** Simple accessor for numItems */
  int getNumItems() {
    return this->numItems;
  }

  /** Set startedWaiting */
  void startWaiting(int time) {
    this->startedWaiting = time;
  }

  /** Set finishedWaiting */
  void finishWaiting(int time) {
    this->finishedWaiting = time;
  }

  /**
   * Calculate the total time this customer spent waiting in line.
   */
  int timeWaiting() {
    if (startedWaiting == -1)
      return 0;
    else 
      return finishedWaiting - startedWaiting;
  }
  
};

int Customer::nextCustomerNum = 42; // use 42 as the initial customer ID.

#endif
