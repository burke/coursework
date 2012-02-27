/** Model a person in an event based simulation */
public abstract class Person extends SortableObject {
  int arrival; ///< The time at which this person arrived
  int id; ///< This person's sequence ID.

  /** Return the ID of the person */
  public int getId() {
    return this.id;
  }

  /**
   * Compare this Person to another using the person ID
   * @param other The Person to compare against
   */
  public int compareTo(SortableObject other) {
    Person person = (Person) other;
    if (this.getId() < person.getId())
      return -1;
    else if (this.getId() > person.getId())
      return 1;
    else
      return 0;
  }
  
  /** return the time at which this person arrived */
  public int getArrival() {
    return this.arrival;
  }
  
}