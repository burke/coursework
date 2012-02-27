/**
 * By inheriting from this class and implementing the compareTo method,
 * a class can be used in Sorted Lists.
 */
public abstract class SortableObject {

  /**
   * Compare this Node to another.
   *
   * Use the primarySortingKey, then secondarySortingKey methods.
   *
   * Returns -1 if this<other, 0 if this==other, and 1 if this>other.
   *
   * @param item The node to compare this node to
   */ 
  public abstract int compareTo(SortableObject item);

  /**
   * Is item greater than or equal to this?
   * @param item The item to compare against
   */
  public Boolean greaterThan(SortableObject item) {
    return (compareTo(item) > -1);
  }

  /**
   * Is item less than or equal to this?
   * @param item The item to compare against
   */
  public Boolean lessThan(SortableObject item) {
    return (compareTo(item) < 1);
  }
  
}