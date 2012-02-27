/**
 * (abstract) represents a person arriving at the supermarket.
 */
public abstract class ArrivalEvent extends Event {

  /**
   * Construct a new ArrivalEvent.
   * @param arrival the time at which the target will arrive
   * @param person the person that will arrive
   * @externals Person
   */
  public ArrivalEvent(int arrival, Person person) {
    super(arrival,person);
  }

}