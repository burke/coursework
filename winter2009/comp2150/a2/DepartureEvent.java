// CLASS: DepartureEvent
//
// REMARKS: Models the departure of a person from an event-based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public abstract class DepartureEvent extends Event {

  Checkout checkout;
  
  //------------------------------------------------------
  // DepartureEvent
  //
  // PURPOSE: Constructs a new DpartureEvent.
  // PARAMETERS:
  //     time: time at which person departs
  //     actor: person leaving
  //     checkout: checkout person is leaving from
  // EXTERNAL REFERENCES:  requires Person, Checkout
  //------------------------------------------------------
  public DepartureEvent(int time, Person actor, Checkout checkout) {
    super(time,actor);
    this.checkout = checkout;
  }

}