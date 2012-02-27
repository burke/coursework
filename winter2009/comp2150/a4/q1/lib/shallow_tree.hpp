#pragma once

#include "student_record.hpp"
#include "entry.hpp"
#include "node.hpp"
#include "leaf_node.hpp"
#include "interior_node.hpp"

// Implements a Shallow Tree. Every data element is stored at the same depth.
// Initialized with sizes for interior and leaf nodes.
class ShallowTree {
private:
  Node *root;
  Node *firstLeaf;

public:
  // constructor. Set values for size of leafnodes and interiornodes.
  ShallowTree(int leafSize, int interiorSize) {
    LeafNode::setSize(leafSize);
    InteriorNode::setSize(interiorSize);
    this->root         = dynamic_cast<Node *>(new LeafNode());
    this->firstLeaf    = this->root;
  }

  // Destructor.
  ~ShallowTree() {
    //TODO
  }

  // What is the depth of this tree?
  int depth() {
    return root->depth();
  }

  // How many leaf nodes does this tree contain?
  int numberOfLeafNodes() {
    return root->numberOfLeafNodes();
  }

  // How many interior nodes does this tree contain?
  int numberOfInteriorNodes() {
    return root->numberOfInteriorNodes();
  }

  // Insert a node into the tree.
  void insert(StudentRecord* record) {
    //TODO
  }

  // Find a data element in the tree by key (student number)
  // Search through each interior node until the element is found
  StudentRecord* treeSearch(int student_number) {
    Entry<StudentRecord>* result = root->treeSearch(student_number);

    if (result != NULL) {
      return result->getData();
    } else return NULL;
  }

  // Find a data element in the tree by key (student number)
  // Descend to the first leaf node, then iterate across until the element is found.
  StudentRecord* leafSearch(int student_number) {
    Entry<StudentRecord>* result = root->leafSearch(student_number);

    if (result != NULL) {
      return result->getData();
    } else return NULL;
  }

  // Output a range of data values, with keys between min and max.
  // Descend to the first leaf node, then iterate across.
  void output(int min, int max) {
    //TODO
  }
  
};
