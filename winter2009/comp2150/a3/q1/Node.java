/**
 * Implements a Node for use in List-based data structures.
 */
public class Node {
  private Object data;    ///< Contents of the node
  private Node next; ///< Pointer to the subsequent node in the parent data structure
  
  public Node() {
    this.next = null;
    this.data = null;
  }

  /**
   * Construct a new Node with given data and successor Node.
   * @param data The contents of the node
   * @param next pointer to successor, or NULL if none.
   */
  public Node(Object data, Node next) {
    this.data = data;
    this.next = next;
  }

  /**
   * Sets the next pointer to change the next Node in this sequence.
   * @param next The node to point to
   */
  public void setNext(Node next) {
    this.next = next;
  }

  /**
   * Sets the data of this Node to the input.
   * @param data The new data to use
   */
  public void setData(Object data) {
    this.data = data;
  }

  /**
   * Get the next Node in this sequence
   */
  public Node getNext() {
    return this.next;
  }

  /**
   * @brief Get the contents of this Node. 
   */
  public Object getData() {
    return this.data;
  }

}
