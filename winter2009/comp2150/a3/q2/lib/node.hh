#ifndef NODE_HH
#define NODE_HH

/**
 * Implements a Node for use in sorted List-based data structures.
 *
 * Expects class T to define primarySortingKey and secondarySortingKey
 * methods. If a secondary key is not desired, return a constant
 * value.
 *
 * Can be used for data structures that don't support or require sorting --
 * just don't call compare or other sorting methods.
 */
template <class T>
class Node {
private:
  T* data;    ///< Contents of the node
  Node* next; ///< Pointer to the subsequent node in the parent data structure
  
public:

  Node() {
    this->next = NULL;
    this->data = NULL;
  }

  /**
   * Construct a new Node with given data and successor Node.
   * @param data The contents of the node
   * @param next pointer to successor, or NULL if none.
   */
  Node(T* data, Node<T>* next) {
    this->data = data;
    this->next = next;
  }

  ~Node() {
    delete(this->data);
  }

  /**
   * Compare this Node to another.
   *
   * Use the primarySortingKey, then secondarySortingKey methods.
   *
   * Returns -1 if this<other, 0 if this==other, and 1 if this>other.
   *
   * @param other The node to compare this node to
   */ 
  int compare(Node* other) {
    T* td = this->getData();
    T* od = other->getData();

    if (td->primarySortingKey() < od->primarySortingKey()) {
      return -1;
    } else if (td->primarySortingKey() > od->primarySortingKey()) {
      return 1;
    } else { // Primary keys are equal
      if (td->secondarySortingKey() < od->secondarySortingKey()) {
        return -1;
      } else if (td->secondarySortingKey() > od->secondarySortingKey()) {
        return 1;
      } else { // Both keys are equal
        return 0;
      }
    }
  }

  /**
   * Returns true if this node should come before other in a sorted list.
   * Note that this implementation will place this before other if both sorting keys are equal.
   * @param other The node to compare this node to
   */
  int comesBefore(Node* other) {
    return (compare(other) < 1);
  }
  
  /**
   * Sets the next pointer to change the next Node in this sequence.
   * @param next The node to point to
   */
  void setNext(Node* next) {
    this->next = next;
  }

  /**
   * Sets the data of this Node to the input.
   * @param data The new data to use
   */
  void setData(T* data) {
    this->data = data;
  }

  /**
   * Get the next Node in this sequence
   */
  Node* getNext() {
    return this->next;
  }

  /**
   * @brief Get the contents of this Node. 
   */
  T* getData() {
    return this->data;
  }

};

#endif
