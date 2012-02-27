// CLASS: ShallowTree
//
// REMARKS: Implements a Shallow Tree. Nodes are automatically balanced to maintain
//    a consistent depth.
//
// Input: leafSize, interiorSize: sizes of each type of node.
//
// Output: the toString method formats the subtree below this node as an s-expression.
//
//-----------------------------------------
class ShallowTree
{
  private final int   LEAF_TYPE     = 1;
  private final int   INTERIOR_TYPE = 0;
  private Node        rootNode;
  private Node        firstLeaf;
  private int         leafSize;
  private int         interiorSize;


  //------------------------------------------------------
  // ShallowTree
  //
  // PURPOSE:
  //     Construct a new tree with the given Leaf and Interior node sizes.
  //
  // PARAMETERS:
  //   - leafSize (int): size of leaf Nodes
  //   - interiorSize (int): size of interior Nodes
  //------------------------------------------------------
  public ShallowTree(int leafSize, int interiorSize)
  {
    this.leafSize     = leafSize;     // m
    this.interiorSize = interiorSize; // n

    rootNode = new Node(this.leafSize, this.LEAF_TYPE);
  }


  //------------------------------------------------------
  // numberOfLeafNodes
  //
  // PURPOSE:
  //     Return the number of Leaf nodes in this tree
  //------------------------------------------------------
  public int numberOfLeafNodes()
  {
    return this.rootNode.countLeafNodes();
  }


  //------------------------------------------------------
  // numberOfInteriorNodes
  //
  // PURPOSE:
  //     How many interior nodes are in this tree?
  //------------------------------------------------------
  public int numberOfInteriorNodes()
  {
    return this.rootNode.countInteriorNodes();
  }


  //------------------------------------------------------
  // depth
  //
  // PURPOSE:
  //     Return the depth of the tree, that is, the path length from
  //     the root to a leaf.
  //------------------------------------------------------
  public int depth()
  {
    return this.rootNode.depth();
  }


  //------------------------------------------------------
  // toString
  //
  // PURPOSE:
  //     Convert the tree to a readable format, using s-expressions that make
  //     me wish I could do this assignment in lisp.
  //------------------------------------------------------
  public String toString()
  {
    return this.rootNode.toString();
  }


  //------------------------------------------------------
  // treeSearch
  //
  // PURPOSE:
  //     Search this tree for a record identified by key. Null if no match
  //
  // PARAMETERS:
  //   - key (int): search term
  //------------------------------------------------------
  public StudentRecord treeSearch(int key)
  {
    Entry result = this.rootNode.treeSearch(key);
    
    if (result != null) {
      return (StudentRecord) result.getData();
    } else return null;
  }


  //------------------------------------------------------
  // leafSearch
  //
  // PURPOSE:
  //     Search this tree for a record identified by key. Null if no match.
  //     uses a linear search across leaf nodes.
  //
  // PARAMETERS:
  //   - key (int): search term
  //------------------------------------------------------
  public StudentRecord leafSearch(int key)
  {
    Entry result = this.rootNode.leafSearch(key);
    
    if (result != null) {
      return (StudentRecord) result.getData();
    } else return null;
  }


  //------------------------------------------------------
  // insert
  //
  // PURPOSE:
  //     Insert a given studentrecord into the tree
  //
  // PARAMTERS:
  //   - record (StudentRecord): the record to insert
  //------------------------------------------------------
  public void insert(StudentRecord record)
  {
    // When we call insert on the rootNode, it may split itself, returning a new Entry.
    // If this happens, create a new rootNode with the previous rootNode and this
    // new Entry as elements.

    // This was written at 6am. Please don't shoot me.

    Entry entry = new Entry(record.getKey(), record);
    Entry retEntry = this.rootNode.insert(entry);
    if (retEntry != null) {
      Node newRoot = new Node(this.interiorSize, this.INTERIOR_TYPE);
      newRoot.setLeftEdge(this.rootNode);
      newRoot.realInsert(retEntry);
      Node retNode = (Node) retEntry.getData();
      
      rootNode.highestLeaf().setNextLeaf(retNode.lowestLeaf());
      
      this.rootNode = newRoot;
    }
  }

}