/**
 * Models the departure of a person from an event-based simulation
 */
public abstract class DepartureEvent extends Event {

  Checkout checkout; ///< The specific checkout a Person is leaving from.
  
  /**
   * Constructs a new DpartureEvent.
   *
   * @param time time at which person departs
   * @param actor person leaving
   * @param checkout checkout person is leaving from
   * @externals Person, Checkout
   */
  public DepartureEvent(int time, Person actor, Checkout checkout) {
    super(time,actor);
    this.checkout = checkout;
  }

}