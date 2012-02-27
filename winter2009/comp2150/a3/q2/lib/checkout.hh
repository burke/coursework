#ifndef CHECKOUT_HH
#define CHECKOUT_HH

#include <string>

using namespace std;

/**
 * A Checkout in the store simulation
 */
class Checkout {

private:
  Queue<Customer>* line; ///< A Queue of people waiting to check out.
  Cashier* cashier;      ///< The cashier currently checking people out.
  Customer* checkingOut;  ///< The customer currently checking out.
  int totalCustomers; ///< Total number of customers to use this checkout.
  int totalItems; ///< Total number of items processed at this checkout
  int endShift; ///< Time employee ends working 
  int startShift; ///< Time employee starts working
  
public:

  /** Construct a new Checkout */
  Checkout() {
    line = new Queue<Customer>;
  }

  /**
   * Set the cashier of this checkout.
   * When a cashier enters the store, the need to find a checkout to start working at.
   * @param cashier the Cashier object to set as this->cashier.
   * @param time The current time
   */
  void setCashier(Cashier* cashier, int time) {
    if (cashier == NULL)
      this->endShift = time;
    else
      this->startShift = time;

    this->cashier = cashier;
  }

  /** Simple accessor for cashier. */
  Cashier* getCashier() {
    return this->cashier;
  }
  
  /**
   * Add a customer to the checkout. Returns true if they have to wait in line,
   * false if there is no line and they can begin checkout immediately.
   * @param customer The customer lining up.
   */
  bool addCustomer(Customer* customer) {
    if (this->checkingOut == NULL) {
      this->checkingOut = customer;
      return false;
    } else {
      this->line->add(customer);
      return true;
    }
  }

  /**
   * Let the cashier object know about the customer for stats purposes.
   * @param customer the Customer being checkout out.
   */
  void ringThrough(Customer* customer) {
    totalCustomers++;
    totalItems += customer->getNumItems();
    this->cashier->serveCustomer(customer);
  }

  /** The number of customers processed at this checkout */
  int getTotalCustomers() {
    return this->totalCustomers;
  }

  /** The number of items processed at this checkout */
  int getTotalItems() {
    return this->totalItems;
  }

  /** The number of time units for which this checkout had a cashier */
  int timeStaffed() {
    return endShift - startShift;
  }
  
  /**
   * Start the customer at the head of the line checking out, remove
   * them from the list, and return them.
   */
  Customer* moveLineAhead() {
    Customer* next = NULL;
    if (! this->line->isEmpty()) {
      next = this->line->poll();
      this->checkingOut = next;
    }
    return next;
  }
  
  /**
   * Return a summary of statistics for this Checkout over the course of the simulation.
   */
  string toString() {
    return "";
  }
  
};

#endif
