// CLASS: ArrivalEvent
//
// REMARKS: (abstract) represents a person arriving at the supermarket.
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public abstract class ArrivalEvent extends Event {

  //------------------------------------------------------
  // ArrivalEvent
  //
  // PURPOSE:  Construct a new ArrivalEvent
  // PARAMETERS:
  //     arrival: the time at which the target will arrive
  //     person: the person that will arrive
  // EXTERNAL REFERENCES:  requires Person
  //------------------------------------------------------
  public ArrivalEvent(int arrival, Person person) {
    super(arrival,person);
  }

}