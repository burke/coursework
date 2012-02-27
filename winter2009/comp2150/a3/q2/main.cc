//--------------------------------------------------------
// NAME		        : Burke Libbey
// STUDENT NUMBER : 6840752
// COURSE		      : COMP 2150 Object Orientation
// INSTRUCTOR	    : Anderson
// ASSIGNMENT	    : 3, Question 2
//
// REMARKS: A simplified version of the SoopaStore simulation
//   from assignment 2. 
//
// INPUT: None. All input is hard-coded.
//
// OUTPUT: Prints a formatted data summary of the simulation

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <math.h>

#include "lib/node.hh"
#include "lib/queue.hh"
#include "lib/ordered_list.hh"

#include "lib/person.hh"
#include "lib/customer.hh"
#include "lib/cashier.hh"

#include "lib/checkout.hh"

#include "lib/event.hh"

#include "lib/store.hh"

#include "lib/departure_event.hh"
#include "lib/cashier_departure_event.hh"
#include "lib/customer_departure_event.hh"
#include "lib/arrival_event.hh"

#include "lib/nice_output.hh"
#include "lib/store_runsim.hh"

using namespace std;

int main(void) {
	cout << endl << "Welcome to Mini SoopaStore..." <<endl;
	Store::runsim();
	cout << endl << "End of Processing" << endl;

  return 0;
}

