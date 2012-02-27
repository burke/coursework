/**
 * A sorted linked list.
 * 
 * Manages a sorted linked list of arbitrary data type. 
 * 
 * @output    Can print a representation of the list's contents
 * @externals Node
 */
class OrderedList {
  private Node front; ///< The head node of this list

  public OrderedList() {
    this.front = null;
  }

  /**
   * Inserts an item into the correct (sorted) position in the list (uses helper method)
   * @param data The item to be inserted
   */
  public void insertInOrder(SortableObject data) {

    Node newNode = new Node(data, null);
    
    /// If the list is empty, set front to a new node containing this data.
    if (this.front == null) {
      this.front = newNode;
    }
    /// If there is data, but this item should come before front, reset front to this item.
    else if (data.lessThan((SortableObject) front.getData())) {
      newNode.setNext(this.front);
      this.front = newNode;
    }
    /// Otherwise, begin iterating through the list to find the correct insertion index.
    else {
      this.insertInOrder(data, this.front);
    }
  }
  
  /**
   * Inserts an item into the correct (sorted) position in the list (recursive helper method)
   * @param data The data to be inserted
   * @param curr The current Node in the sequence being compared against
   */
  public void insertInOrder(SortableObject data, Node curr) {
    SortableObject otherData;
    Node newNode;
    
    /// If we've reached the end of the list, add the Node to the end.
    if (curr.getNext() == null) {
      newNode = new Node(data, null);
      curr.setNext(newNode);
    } else {
      otherData = (SortableObject) curr.getNext().getData();
      /// If the Node-to-insert comes before curr's successor, insert.
      if (data.lessThan(otherData)) {
        newNode = new Node(data, null);
        newNode.setNext(curr.getNext());
        curr.setNext(newNode);
      }
      /// Otherwise, Recurse with curr = curr.getNext()
      else {
        insertInOrder(data, curr.getNext());
      }
    }
  }

  public SortableObject peekFirst() {
    return (SortableObject) front.getData();
  }
  
  /**
   * Determines whether or not the list contains more data.
   */
  public Boolean isEmpty() {
    return (this.front == null);
  }

  /** Return the size of this list */
  public int size() {
    Node curr;
    int count;
    if (isEmpty())
      count = 0;
    else {
      count = 1;
      for (curr = this.front; curr.getNext() != null; curr = curr.getNext()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Removes and returns the element at the front of the list
   */
  public SortableObject removeFirst() {
    Node nextNode; 
    // We could either throw an error if or return null if the list is empty.
    // I think returning null makes sense in this case.
    if (this.isEmpty()) {
      return null;
    } else {
      nextNode = this.front;
      this.front = this.front.getNext();
      return (SortableObject) nextNode.getData();
    }
  }

}