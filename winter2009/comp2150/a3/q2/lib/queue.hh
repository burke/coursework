#ifndef QUEUE_HH
#define QUEUE_HH

#include <string>

using namespace std;

/**
 * Implements a generic queue.
 */
template <class T>
class Queue {
private:
  Node<T>* head; ///< Initial element in the queue.
  
public:

  /** Construct a new Queue. */
  Queue() {
    this->head = NULL;
  }

  /**
   * Adds an item to the back of the queue.
   * @param data The data to add to the queue.
   */
  void add(T* data) {
    Node<T>* newNode = new Node<T>(data, NULL);
    Node<T>* curr;
    if (this->head == NULL) {
      this->head = newNode;
    } else {
      curr = this->head;
      while (curr->getNext() != NULL) {
        curr = curr->getNext();
      }
      curr->setNext(newNode);
    }
  }

  /**
   * Removes and returns the first item in the queue.
   */
  T* poll() {
    T* data = this->head->getData();
    this->head = this->head->getNext();
    return data;
  }

  /**
   * Returns, but does not remove, the first item in the queue.
   */
  T* peek() {
    return this->head->getData();
  }

  /**
   * Is there any data in this queue?
   */
  bool isEmpty() {
    return (this->head == NULL);
  }
  
};

#endif
