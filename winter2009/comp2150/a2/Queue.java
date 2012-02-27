// CLASS: Queue
//
// REMARKS: Manages a Queue of customers
//
// Input: None
//
// Output: None
//
//-----------------------------------------
public class Queue {

  Node front;
  
  //------------------------------------------------------
  // Queue
  //
  // PURPOSE: Construct a new Queue
  //------------------------------------------------------
  public Queue() {
    front = null;
  }

  //------------------------------------------------------
  // enqueue
  //
  // PURPOSE: Add a new object to the end of this Queue
  // PARAMETERS:  obj: the object to add
  //------------------------------------------------------
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

  //------------------------------------------------------
  // getFirst
  //
  // PURPOSE: return the object at the front of the queue
  //------------------------------------------------------
  public Object getFirst() {
    if (front == null) {
      return null;
    }
    return front.getData();
  }

  //------------------------------------------------------
  // dequeue
  //
  // PURPOSE: Remove the front element from the Queue and return its data
  //------------------------------------------------------
  public Object dequeue() {
    if (front == null) {
      return null;
    }
    Node ret = front;
    front = front.getNext();
    return ret.getData();
  }

  //------------------------------------------------------
  // size
  //
  // PURPOSE: Return the number of elements in the queue.
  //------------------------------------------------------
  public int size() {
    Node curr;
    int size=0;
    for (curr=front; curr!=null; curr=curr.getNext()) {
      ++size;
    }
    return size;
  }

  // CLASS: Node
  //
  // REMARKS: Encapsulates an object for storage in a Queue
  //
  // Input: None
  //
  // Output: None
  //
  //-----------------------------------------
  private class Node {
    Object data;
    Node next;

    //------------------------------------------------------
    // Node
    //
    // PURPOSE: constructs a new Node
    //------------------------------------------------------
    public Node(Object data, Node next) {
      this.data = data;
      this.next = next;
    }
    
    //------------------------------------------------------
    // getData
    //
    // PURPOSE: returns the Data of this Node
    //------------------------------------------------------
    public Object getData() {
      return this.data;
    }

    //------------------------------------------------------
    // getNext
    //
    // PURPOSE: returns the next Node in this sequence
    //------------------------------------------------------
    public Node getNext() {
      return this.next;
    }

    //------------------------------------------------------
    // setNext
    //
    // PURPOSE: changes the next element in this sequence to node
    // PARAMETERS:  node: the new successor to this Node
    //------------------------------------------------------
    public void setNext(Node node) {
      this.next = node;
    }
  }
}