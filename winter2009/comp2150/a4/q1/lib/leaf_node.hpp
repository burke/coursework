#pragma once

using namespace std;

// Implements a leaf node in a shallow tree. All leaf nodes will be at the same depth.
class LeafNode: public Node {
private:
  static int            size;    // Number of elements contained in a LeafNode
  Entry<StudentRecord> *entries; // the elements.

public:

  bool isLeafNode() { return true; }

  static void setSize(int newSize) {
    size = newSize;
  }

  static int getSize() {
    return size;
  }
  
  LeafNode() {
    this->entries = new Entry<StudentRecord> [LeafNode::getSize()];
  }

  ~LeafNode() {
    //TODO
  }

  // Return the number of LeafNodes in the subtree including and under this node.
  // One by definition. (ie. this node).
  int numberOfLeafNodes() {
    return 1;
  }

  // Return the number of InteriorNodes in the subtree including and under this node.
  // Zero by definition.
  int numberOfInteriorNodes() {
    return 0;
  }

  // Return the depth of the subtree including and under this node.
  // One by definition. (ie. this node).
  int depth() {
    return 1;
  }

  // Given a key, iterate through the leaves of this tree until the key is found,
  // or return null if it's not.
  Entry<StudentRecord>* leafSearch(int key) {
    //TODO
  }

  // Search this subtree for a record identified by key. Recurses down the tree
  // until it finds a leaf node.
  // Return null if it's not in this node.
  Entry<StudentRecord>* treeSearch(int key) {
    //TODO
  }

  // Insert an entry into this node. Split if necessary.
  Node* insert(Entry<StudentRecord>* record) {
    //TODO
  }
  
};

int LeafNode::size = 5;
