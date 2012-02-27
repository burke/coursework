// CLASS: Person
//
// REMARKS: Model a person in an event based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public abstract class Person {
  int arrival;
  int id;

  //------------------------------------------------------
  // getId
  //
  // PURPOSE: Return the ID of the person
  //------------------------------------------------------
  public int getId() {
    return this.id;
  }

  //------------------------------------------------------
  // getArrival
  //
  // PURPOSE: return the time at which this person arrived
  //------------------------------------------------------
  public int getArrival() {
    return this.arrival;
  }
  
}