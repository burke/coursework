#ifndef CASHIER_HH
#define CASHIER_HH

#include <math.h>

/**
 * Represents a customer in an event-driven simulation of a store.
 */
class Cashier : public Person {
private:

  int quitTime; ///< Quitting time for employee
  int speed; ///< Number of items per time unit this cashier is capable of processing
  int customersServed; ///< Number of customers served in total.
  int itemsPacked; ///< Number of items packed in total
  
public:

  /**
   * Construct a new cashier object.
   * @param id the Employee ID of the cashier
   * @param time the Time this cashier arrived at 
   */
  Cashier(int id, int time) : Person(id, time) {
    this->speed = 4;
  }

  /** Is this object of type Cashier? */
  bool isCashier() { return true; }

  /**
   * The number of time units it will take this cashier
   * to ring through a given number of items.
   * @param numItems The number of items to ring through.
   */
  int timeForItems(int numItems) {
    return ceil((double)numItems / (double)this->speed);
  }

  /** Return the number of items packed */
  int getItemsPacked() {
    return this->itemsPacked;
  }
  
  /** The number of customers served in total by this cashier */
  int getCustomersServed() {
    return this->customersServed;
  }

  /** Increment the counter for served customers.
   * @param customer The customer being served
   */
  void serveCustomer(Customer* customer) {
    this->customersServed++;
    this->itemsPacked += customer->getNumItems();
  }
  
  /** Set this Cashier's quitting Time */
  void setQuitTime(int quitTime) {
    this->quitTime = quitTime;
  }

  /** Simple accessor for quitTime */
  int getQuitTime() {
    return this->quitTime;
  }
  
  /**
   * Number of time units worked by this cashier.
   */
  int timeWorked() {
    return quitTime - startTime;
  }
  
};

#endif
