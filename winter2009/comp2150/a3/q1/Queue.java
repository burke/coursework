/**
 * Manages a Queue of customers
 */
public class Queue {

  Node front; ///< The front of the queue.
  
  /** Construct a new Queue */
  public Queue() {
    front = null;
  }

  /**
   * Add a new object to the end of this Queue 
   * @param obj the object to add
   */
  public void enqueue(Object obj) {
    Node curr;
    Node newNode = new Node(obj, null);
    
    if (front == null) {
      front = newNode;
    } else {
      curr = front;
      while (curr.getNext() != null) {
        curr = curr.getNext();
      }
      curr.setNext(newNode);
    }
  }

  /** return the object at the front of the queue */
  public Object getFirst() {
    if (front == null) {
      return null;
    }
    return front.getData();
  }

  /** Remove the front element from the Queue and return its data */
  public Object dequeue() {
    if (front == null) {
      return null;
    }
    Node ret = front;
    front = front.getNext();
    return ret.getData();
  }

  /** Return the number of elements in the queue. */
  public int size() {
    Node curr;
    int size=0;
    for (curr=front; curr!=null; curr=curr.getNext()) {
      ++size;
    }
    return size;
  }

}