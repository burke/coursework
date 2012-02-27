#ifndef NICE_OUTPUT_HH
#define NICE_OUTPUT_HH

#include <string>

using namespace std;

/**
 * Gathers together routines for nice printing that really don't
 * belong with other objects.  All of these are static, so there will
 * never be any instances of this class.
 *
 * @output formatted text (mainly right-justified) on the system output stream.
*/
class NiceOutput {
	//Just a couple of functions for some nicer text output
	//so that we can right align things...
public:

	//methods for aligning and printing out headers
	static string rightAlign(string aString,int size);
	static void cashierHeaders();
	static void checkoutHeaders();
	static void customerHeaders();

};

/**
 * right align a string in a given text field witdh simply done by padding with blanks.
 * @param aString A string to format
 * @param size The width to fit it in
 */
string NiceOutput::rightAlign(string aString,int size) {
  string space = " ";
  while ((int) (aString.length()) < size) {
    aString = aString+space;
  }
  return aString;
}

/**
 * Print a formatted display of cashier stats
 */
void NiceOutput::cashierHeaders() {
  OrderedList<Cashier>* cashierList = Store::getCashierList();
  Cashier* curr;
  cout << "Cashier Information:" << endl
       << endl
       << "ID      Start    Time        Customers   Items"  << endl
       << "Number  Time     Worked      Served      Packed" << endl
       << "-----------------------------------------------" << endl;
  while ((curr = cashierList->removeFirst()) != NULL) {
    cout.flags(ios::left);
    cout.width(7);
    cout << curr->getId() << " ";
    cout.width(8);
    cout << curr->getStartTime() << " ";
    cout.width(11);
    cout << curr->timeWorked() << " ";
    cout.width(11);
    cout << curr->getCustomersServed() << " ";
    cout << curr->getItemsPacked() << endl;
  }
  
}

/**
 * Print a formatted display of customer stats
 */
void NiceOutput::customerHeaders() {
  OrderedList<Customer>* customerList = Store::getCustomerList();
  Customer* curr;

  cout << "Customer Information:" << endl
       << endl
       << "ID      Start    Grocery  Waiting" << endl
       << "Number  Time     Items    Time" << endl
       << "---------------------------------" << endl;
  while ((curr = customerList->removeFirst()) != NULL) {
    cout.flags(ios::left);
    cout.width(7);
    cout << curr->getId() << " ";
    cout.width(8);
    cout << curr->getStartTime() << " ";
    cout.width(8);
    cout << curr->getNumItems() << " ";
    cout << curr->timeWaiting() << endl;
  }
  
}

/**
 * Print a formatted display of checkout stats
 */
void NiceOutput::checkoutHeaders() {
  Checkout* checkout = Store::getCheckout();

  cout << "Checkout Information:" << endl
       << endl
       << "Time     Total       Total" << endl 
       << "Staffed  Customers   Items" << endl 
       << "--------------------------" << endl;
  cout.flags(ios::left);
  cout.width(9);
  cout << checkout->timeStaffed();
  cout.width(12);
  cout << checkout->getTotalCustomers();
  cout << checkout->getTotalItems();

}


#endif
