#ifndef STORE_RUNSIM_HH
#define STORE_RUNSIM_HH

#include <string>

using namespace std;

/**
 * @file store_runsim.hh
 * This is separate from store.hh because of a circular dependency
 * between Store(::runsim) and ArrivalEvent.
 */

/**
 * Main loop for the simulation.  Prime with an arrival event read
 * from the data file, then simply keep stripping events off the event
 * list and carrying them out.
 */
void Store::runsim() {

	ArrivalEvent* anArrival;
	Event* currentEvent;

	//put in a few dummy arrivals
  anArrival = new ArrivalEvent(23,'C',22);
	eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(23,'C',1);
  eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(2,'E',12);
  eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(12,'C',137);
  eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(14,'C',42);
  eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(8,'C',13);
  eventList->insertInOrder(anArrival);
  anArrival = new ArrivalEvent(9,'C',12);
  eventList->insertInOrder(anArrival);

	cout <<endl<< "EVENT LIST IS:"<<endl;
	eventList->print();
	cout <<endl;

	/**
   * run the simulation until there's nothing left in the event list --
   * processing each arrival will cause the next future arrival to
   * be inserted into the event list.
   */
	while (!(eventList->isEmpty())) {
		//just get the next event and process it...
		//cout << "processing next event...\n";
		currentEvent= dynamic_cast<Event *> (eventList->removeFirst());
		if (currentEvent == NULL) {
			cout << "ERROR: Non-event in event list..." <<endl;
		} else {
			currentEvent->makeItHappen();
    }
		delete(currentEvent); //done with this event - return memory!
  }
  //all events processed, just print the final summary data.
	cout << endl << "...All events complete.  Final Summary:" <<endl;

  totalCustomers = customerList->size();
  
  //NiceOutput contains all formatting routines.
	NiceOutput::cashierHeaders();
	cashierList->print();
	NiceOutput::customerHeaders();
	customerList->print();
	NiceOutput::checkoutHeaders();
	cout << checkout->toString() <<endl <<endl;
	if (totalCustomers == 0)
		cout << "No customers...nothing to see here, move along...";
	else {
		cout << "Customers processed in total: " << totalCustomers <<endl;
    // Meh. I'm out of red bull and it's almost 7am. 0 it is.
    cout << "Average waiting time per customer :" << (totalWait/(double) totalCustomers) << endl;
  }
}

#endif
