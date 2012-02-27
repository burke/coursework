#pragma once

// Parent class to InteriorNode and LeafNode. Implements a node in a Shallow Tree.
class Node {
private:
  static int count; // used to track number of nodes accessed in search methods

public:
  
  Node() {
    //TODO
  }

  ~Node() {
    //TODO
  }

  // accessors for search tracking
  static void zeroCount()      { Node::count = 0;    }
  static void incrementCount() { Node::count++;      }
  static int  getCount()        { return Node::count; }

  // Insert a node into the subtree at this node
  virtual Node* insert(Entry<StudentRecord>* entry) = 0;
  
  virtual bool isInteriorNode() { return false; }
  virtual bool isLeafNode()     { return false; }

  // Search methods
  virtual Entry<StudentRecord>* leafSearch(int key) {}
  virtual Entry<StudentRecord>* treeSearch(int key) {}

  virtual int numberOfLeafNodes()     = 0;
  virtual int numberOfInteriorNodes() = 0;
  virtual int depth()                 = 0;
  
};

int Node::count = 0;
