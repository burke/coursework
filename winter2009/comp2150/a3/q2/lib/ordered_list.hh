#ifndef ORDERED_LIST_HH
#define ORDERED_LIST_HH

#include <string>

using namespace std;

/**
 * A sorted linked list.
 * 
 * Manages a sorted linked list of arbitrary data type. 
 * 
 * Expects class T to define primarySortingKey and secondarySortingKey
 * methods. If a secondary key is not desired, return a constant
 * value. Also expects a print() method.
 *
 * @output    Can print a representation of the list's contents
 * @externals Node
 */
template <class T>
class OrderedList {
private:
  Node<T>* front; ///< The head node of this list

public:

  OrderedList() {
    this->front = NULL;
  }

  /**
   * Inserts an item into the correct (sorted) position in the list (uses helper method)
   * @param data The item to be inserted
   */
  bool insertInOrder(T* data) {
    Node<T>* newNode = new Node<T>(data, NULL);

    /// If the list is empty, set front to a new node containing this data.
    if (this->front == NULL) {
      this->front = newNode;
      return false;
    }
    /// If there is data, but this item should come before front, reset front to this item.
    else if (newNode->comesBefore(front)) {
      newNode->setNext(this->front);
      this->front = newNode;
    }
    /// Otherwise, begin iterating through the list to find the correct insertion index.
    else {
      return this->insertInOrder(newNode, this->front);
    }
  }

  /**
   * Inserts an item into the correct (sorted) position in the list (recursive helper method)
   * @param newNode The Node to be inserted
   * @param curr The current Node in the sequence being compared against
   */
  bool insertInOrder(Node<T>* newNode, Node<T>* curr) {
    /// If we've reached the end of the list, add the Node to the end.
    if (curr->getNext() == NULL) {
      curr->setNext(newNode);
      return false;
    }
    /// If the Node-to-insert comes before curr's successor, insert.
    else if (newNode->comesBefore(curr->getNext())) {
      newNode->setNext(curr->getNext());
      curr->setNext(newNode);
      return false;
    }
    /// Otherwise, Recurse with curr = curr->getNext()
    else {
      // I'm guessing C++ doesn't have automatic TCO. Oh well.
      return insertInOrder(newNode, curr->getNext());
    }
  }

  
  /**
   * Determines whether or not the list contains more data.
   */
  bool isEmpty() {
    return (this->front == NULL);
  }

  /** Return the size of this list */
  int size() {
    Node<T>* curr;
    int count;
    if (isEmpty())
      count = 0;
    else {
      count = 1;
      for (curr = this->front; curr->getNext() != NULL; curr = curr->getNext()) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Removes and returns the element at the front of the list
   */
  T* removeFirst() {
    Node<T>* nextNode; 
    // We could either throw an error if or return NULL if the list is empty.
    // I think returning NULL makes sense in this case.
    if (this->isEmpty()) {
      return NULL;
    } else {
      nextNode = this->front;
      this->front = this->front->getNext();
      return nextNode->getData();
    }
  }

  /**
   * Prints a representation of this list to stdout.
   * @warning TODO
   */
  void print() {
    Node<T>* curr;
    for (curr = this->front; curr != NULL; curr = curr->getNext()) {
      curr->getData()->print();
    }
  }
};


#endif
