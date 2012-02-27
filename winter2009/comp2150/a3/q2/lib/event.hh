#ifndef EVENT_HH
#define EVENT_HH

/**
 * Models an event in an event-driven simulation.
 * This is a generic parent class that more specific event types will inherit from.
 * It probably shouldn't be used directly.
 */
class Event {
protected:
  Person* actor; ///< The customer or cashier involved in this event.
  int time; ///< The time at which this event occurs
  
public:
  /** Print a one-line summary of this event. */
  virtual void print() = 0;

  /** Run this event. */
  virtual void makeItHappen() = 0;

  /**
   * Construct a new event.
   * @param time the Time at which to trigger this event.
   */
  Event(int time) {
    this->time = time;
  }

  /** The time at which this event occurs */
  int getTime() {
    return this->time;
  }
  
  /**
   * An integer key used to sort a list of Events
   */
  virtual int primarySortingKey() {
    return this->time;
  }

  /**
   * An integer key used to resolve conflicts when sorting by primarySortingKey.
   */
  virtual int secondarySortingKey() {
    return this->actor->getId();
  }
  
};


#endif
