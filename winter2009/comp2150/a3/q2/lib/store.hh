#ifndef STORE_DECLARATIONS_HH
#define STORE_DECLARATIONS_HH

using namespace std;

/**
 * Uses infrastructure from all the other classes to put together a simulation.
 * All central data structures (checkouts, eventlist, cashier list, ...) are members of this class.
 *
 * If we knew more about friends we would be able to do a better job of this
 *   as far as encapsulation goes - but we don't yet!
 * 
 * @input     Reads the first item in from the input file; also opens the input file.
 * @output    Printing the list is possible (implemented by superclass).
 * @externals Requires all of the above classes.
*/
class Store {

private:
  static OrderedList<Cashier>*  cashierList;  ///< Sorted list of cashiers
  static OrderedList<Customer>* customerList; ///< Sorted list of customers
  static OrderedList<Event>*    eventList;    ///< Sorted list of upcoming events
  static Checkout* checkout; ///< This store's single checkout
  static int totalCustomers; ///< Total number of customers to use the store
  static int totalWait;      ///< Total time units spent waiting by all customers combined.
  
public:
  // Implemented in store_runsim.hh
	static void runsim();

  /** Get the current simulation's event list */
  static OrderedList<Event>* getEventList() {
    return eventList;
  }

  /** Simple accessor for checkout */
  static Checkout* getCheckout() {
    return checkout;
  }

  /** Get the departed customer list */
  static OrderedList<Customer>* getCustomerList() {
    return customerList;
  }

  /** Get the departed cashier list */
  static OrderedList<Cashier>* getCashierList() {
    return cashierList;
  }
  
};

OrderedList<Cashier>*  Store::cashierList    = new OrderedList<Cashier>();
OrderedList<Customer>* Store::customerList   = new OrderedList<Customer>();
OrderedList<Event>*    Store::eventList      = new OrderedList<Event>();
Checkout*              Store::checkout       = new Checkout();
int                    Store::totalCustomers = 0; //# customers processed in all
int                    Store::totalWait      = 0; //total wait by everybody

#endif
