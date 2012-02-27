/**
 * Model an event in an event-based simulation
 */
public abstract class Event extends SortableObject {
  int time; ///< Time at which this event occurs
  Person actor; ///< Employee or Customer this event refers to
  
  /**
   * Construct a new Event
   *
   * @param time the time at which the event occurs
   * @param actor the person this event involves
   * @externals Person
   */
  public Event(int time, Person actor) {
    this.time  = time;
    this.actor = actor;
  }

  /**
   * Compare this event to another using the event time
   * @param other The event to compare against
   */
  public int compareTo(SortableObject other) {
    Event event = (Event) other;
    if (this.getTime() < event.getTime())
      return -1;
    else if (this.getTime() > event.getTime())
      return 1;
    else
      return this.getActor().compareTo(event.getActor());
  }
  
  /** Returns the time at which this event occurs */
  public int getTime() {
    return this.time;
  }

  /** Return the Person this event involves */
  public Person getActor() {
    return this.actor;
  }

  /** Method called when this event "happens". */
  abstract void action();
  
}