// CLASS: Event
//
// REMARKS: Model an event in an event-based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public abstract class Event {
  int time;
  Person actor;
  SoopaStore store;
  
  //------------------------------------------------------
  // Event
  //
  // PURPOSE: Construct a new Event
  // PARAMETERS:
  //     time: the time at which the event occurs
  //     actor: the person this event involves
  // EXTERNAL REFERENCES:  requires Person
  //------------------------------------------------------
  public Event(int time, Person actor) {
    this.time  = time;
    this.actor = actor;
    this.store = SoopaStore.getInstance();
  }

  //------------------------------------------------------
  // getTime
  //
  // PURPOSE: Returns the time at which this event occurs
  //------------------------------------------------------
  public int getTime() {
    return this.time;
  }

  //------------------------------------------------------
  // getActor
  //
  // PURPOSE: Return the Person this event involves
  //------------------------------------------------------
  public Person getActor() {
    return this.actor;
  }

  //------------------------------------------------------
  // action
  //
  // PURPOSE: Method called when this event "happens".
  //------------------------------------------------------
  abstract void action();
  
}