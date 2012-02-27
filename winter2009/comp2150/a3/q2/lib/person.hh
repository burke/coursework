#ifndef PERSON_HH
#define PERSON_HH

/**
 * Represents a generic person. Abstract class.
 */
class Person { 

private:
  int id; ///< Employee ID or arbitrary customer ID

protected:
  int startTime; ///< Starting time 
  
public:

  /** Is this object of type Customer? */
  virtual bool isCustomer() { return false; }

  /** Is this object of type Cashier? */
  virtual bool isCashier()  { return false; }

  /** Construct a new Person */
  Person(int id, int time) {
    this->id = id;
    this->startTime = time;
  }

  /** Simple accessor for id */
  int getId() {
    return this->id;
  }

  /** Simple accessor for startTime */
  int getStartTime() {
    return this->startTime;
  }

  /**
   * OrderedList uses this method to sort People.
   */
  int primarySortingKey() {
    return this->id;
  }

  /** IDs are unique, therefore we don't need a secondary key. */
  int secondarySortingKey() {
    return 0;
  }
  
  /**
   * Required by OrderedList. Do nothing.
   */ 
  void print() {
    return;
  }
  
};

#endif
