#ifndef DEPARTURE_EVENT_HH
#define DEPARTURE_EVENT_HH

/**
 * Models the departure of a customer or cashier from the simulation
 */
class DepartureEvent : public Event {

public:
  /** Constructs a new DepartureEvent */
  DepartureEvent(int time) : Event(time) {
  }

  /**
   * DepartureEvent doesn't need print(), since DepartureEvents
   * cannot exist when the initial event list is printed.
   */
  void print() { return; }
  
};

#endif
