#pragma once

// Implements an interior node in a shallow tree. Contains several pointers
// to either LeafNodes or InteriorNodes
class InteriorNode : protected Node {
private:
  static int size; // Number of elements container in an InteriorNode
  Entry<Node> *entries; // the elements.
  
public:
  bool isInteriorNode() { return true; }

  static void setSize(int newSize) { size = newSize; }

  // Constructor
  InteriorNode() {
    //TODO
  }

  // Destructor
  ~InteriorNode() {
    //TODO
  }

  // Return the number of InteriorNodes in the subtree including and under this node.
  int numberOfInteriorNodes() {
    //TODO
    return 1 + 0;
  }
  
  // Return the number of LeafNodes in the subtree including and under this node.
  int numberOfLeafNodes() {
    //TODO
    return 0;
  }

  // Return the depth of the subtree including and under this node.
  int depth() {
    //TODO
    // 1 + first->depth();
    return 1 + 0;
  }

  // Given a key, iterate through the leaves of this tree until the key is found,
  // or return null if it's not.
  // Since this is an interior node, just descend down the left edge
  Entry<StudentRecord>* leafSearch(int key) {
    //Node::getCount();
    //Node::zeroCount();
    return NULL;
  }

  // Search this subtree for a record identified by key. Recurses down the tree
  // until it finds a leaf node.
  // Search all subtrees.
  Entry<StudentRecord>* treeSearch(int key) {
    Node::getCount();
    Node::zeroCount();
    return NULL;
  }

  // Insert an entry into the appropriate subtree. Split if necessary.
  Node* insert(Entry<StudentRecord>* entry) {
    //TODO
  }
  
};

int InteriorNode::size = 5;

