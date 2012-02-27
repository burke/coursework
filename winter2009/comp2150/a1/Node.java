// CLASS: Node
//
// REMARKS: This class implements a Node in a ShallowTree. It can represent
//   both Leaf and Interior nodes.
//
// Input: None.
//
// Output: the toString method formats the subtree below this node as an s-expression.
//   ...and we have to use Java. /facepalm.
//
//-----------------------------------------
class Node
{
  private final int LEAF_TYPE = 1;
  private final int INTERIOR_TYPE = 0;

  private Node nodeRef; // Implements either nextLeaf or leftEdge, depending
                        // on whether this is interior or leaf.
  
  private int occupiedSlots = 0; // How many slots in the entries array are in use?
 
  private int dataType; // Is this a leaf or interior node?
  private Entry entries; // Content of node
  private int size; // Max. size of node.

  
  //------------------------------------------------------
  // Node
  //
  // PURPOSE:
  //     Constructs a new Node with the specified size and type.
  //
  // PARAMETERS:
  //   - size (int): The maximum elements in the node before it should split.
  //   - dataType (int): LEAF_TYPE or INTERIOR_TYPE. 
  //
  // EXTERNAL RESOURCES: Entry
  //------------------------------------------------------
  public Node(int size, int dataType)
  {
    this.dataType = dataType;
    this.size = size+1;
    this.entries = new Entry[size+1];
  }

  //------------------------------------------------------
  // setLeftEdge
  //
  // PURPOSE:
  //     Set the lowest child of an interior node.
  //
  // PARAMETERS:
  //   - node (Node): Which node should be referred to?
  //------------------------------------------------------
  public void setLeftEdge(Node node)
  {
    this.nodeRef=node;
  }

  //------------------------------------------------------
  // leftEdge
  //
  // PURPOSE:
  //     return the lowest child of this interior Node.
  //------------------------------------------------------
  public Node leftEdge()
  {
    return this.nodeRef;
  }
  
  //------------------------------------------------------
  // nextLeaf
  //
  // PURPOSE:
  //     Return the next leaf to the right of this leaf.
  //------------------------------------------------------
  public Node nextLeaf()
  {
    return this.nodeRef;
  }

  //------------------------------------------------------
  // setNextLeaf
  //
  // PURPOSE:
  //     Inform this leaf node about its closest neighbour to the right.
  //
  // PARAMETERS:
  //   - next (Node): which node should be referred to?
  //------------------------------------------------------
  public void setNextLeaf(Node next)
  {
    this.nodeRef = next;
  }

  
  //------------------------------------------------------
  // treeSearch
  //
  // PURPOSE:
  //     Search this subtree for a record identified by key. Recurses down the tree
  //     until it finds a leaf node.
  //
  // PARAMETERS:
  //   - key (int): search term
  //------------------------------------------------------
  public Entry treeSearch(int key)
  {
    if (this.dataType == this.INTERIOR_TYPE) {
      for (int i=this.occupiedSlots; i>0; --i) {
        if (key > this.entries[i-1].getKey()) {
          return ((Node) this.entries[i-1].getData()).treeSearch(key);
        }
      }
      //nothing higher matched; guess it's in the left edge node.
      return this.leftEdge().treeSearch(key);
    } else { //LEAF_TYPE
      for (int i=0; i<this.occupiedSlots; ++i) {
        if (key == this.entries[i].getKey()) {
          return (Entry) this.entries[i];
        }
      }
    }
    
    return null;
  }
  
  //------------------------------------------------------
  // getDataType
  //
  // PURPOSE:
  //     Is this a leaf node or an interior node?
  //------------------------------------------------------
  public int getDataType()
  {
    return this.dataType;
  }

  
  //------------------------------------------------------
  // countLeafNodes
  //
  // PURPOSE:
  //     Return the number of Leaf nodes in this subtree.
  //------------------------------------------------------
  public int countLeafNodes()
  {
    int count = 0; // number of leaf nodes found
    Node nextNode; // next node to examine.
    if (this.dataType == this.LEAF_TYPE) {
      count = 1;
    } else {//INTERIOR_TYPE
      count += this.leftEdge().countLeafNodes();
      for (int i=0; i<this.occupiedSlots; ++i) {
        nextNode = (Node) this.entries[i].getData();
        count += nextNode.countLeafNodes();
      }
    }
    return count;
  }

  
  //------------------------------------------------------
  // depth
  //
  // PURPOSE:
  //     Return the depth of the current subtree, that is, the path length from
  //     the root to a leaf.
  //------------------------------------------------------
  public int depth()
  {
    if(this.dataType == this.LEAF_TYPE) {
      return 1;
    } else {
      return 1+this.leftEdge().depth();
    }
  }

  
  //------------------------------------------------------
  // countInteriorNodes
  //
  // PURPOSE:
  //     How many interior nodes are in this subtree?
  //------------------------------------------------------
  public int countInteriorNodes()
  {
    int count = 0; // number of interior nodes found
    Node nextNode; // next node to examine
    if (this.dataType == this.INTERIOR_TYPE) {
      count += 1;
      count += this.leftEdge().countInteriorNodes();
      for (int i=0; i<this.occupiedSlots; ++i) {
        nextNode = (Node) this.entries[i].getData();
        count += nextNode.countInteriorNodes();
      }
    }
    return count;
  }
  
  //------------------------------------------------------
  // hasEmptySlot
  //
  // PURPOSE:
  //     Does this Node have room to accept additional elements?
  //------------------------------------------------------
  public boolean hasEmptySlot()
  {
    return (this.occupiedSlots < this.size);
  }

  //------------------------------------------------------
  // toString
  //
  // PURPOSE:
  //     Print the contents of the node in the form of (el1 el2 el3).
  //     Embed subnodes (recursively) as ((a1 a2 a3)b1(b1 b2 b3)).
  //------------------------------------------------------
  public String toString()
  {
    String output = "("; // We'll build up this output string later, then return it.
    Node child; // temporary storage for conversion of child nodes
    if (this.dataType == this.LEAF_TYPE)
    {
      // (defn toString [] (map :key entries)) :(
      for (int i=0; i<this.occupiedSlots; ++i) {
        output += this.entries[i].getKey();
        if (i != this.occupiedSlots-1) {
          // Space numbers with a " ", but don't terminate list with it.
          output += " ";
        }
      }
    } else { // Interior node.
      output += this.leftEdge().toString();
      for (int i=0; i<this.occupiedSlots; ++i) {
        output += this.entries[i].getKey();
        child = (Node) this.entries[i].getData();
        output += child.toString();
      }
    }
    output += ")";
    return output;
  }

  //------------------------------------------------------
  // insert
  //
  // PURPOSE:
  //     Insert an entry into this subtree. Recurse down the tree until
  //     the appropriate location is found.
  // PARAMETERS:
  //     - entry (Entry): The element to be inserted into the tree.
  // EXTERNAL REFERENCES:
  //     - Entry
  //------------------------------------------------------
  public Entry insert(Entry entry)
  {
    Entry retEntry=null; // The Entry created during a split to pass back up the stack.
    Node newNode; // new node created during split
    int midPoint; // cutoff for node split

    if (this.dataType == this.LEAF_TYPE) {
      // This is a leaf node. Just insert the entry at the appropriate index.
      // We'll figure out if we have to split it later.
      this.realInsert(entry);
    } else { //INTERIOR_TYPE
      // This is an interior node. Call insert on the appropriate subnode.
      // If we get a return value back, the subnode split. Insert it.

      if (entry.getKey() < this.entries[0].getKey()) {
        // If this belongs in the left edge node
        retEntry = this.leftEdge().insert(entry);
      } else {
        // search through for appropriate position

        for (int i=0; i<this.occupiedSlots; ++i) {
          // If entry's key is within the range of this node, insert into this node.
          if (this.entries[i+1]==null || entry.getKey() < this.entries[i+1].getKey() )
          {
            newNode = (Node) this.entries[i].getData();
            retEntry = newNode.insert(entry);
            break;
          }
        }
      }
      if (retEntry != null) {
        // The subnode split when we inserted. Insert the returned value into self.
        this.realInsert(retEntry);
      }
    }
    
    // If we've overfilled this node, we need to split it now.
    if (!this.hasEmptySlot()) {
      
      newNode = new Node(this.size-1, this.dataType);

      // Move the second half of the original node's entries to newNode
      midPoint = (int) this.entries.length / 2;
      for (int i=midPoint; i<this.entries.length; ++i) {
        newNode.realInsert(this.entries[i]);
        this.entries[i] = null;
        this.occupiedSlots--;
      }

      //This is horribly inefficient.
      // http://thedailywtf.com/Articles/The-Speedup-Loop.aspx
      // I'll call it intentional.
      this.relinkLeaves();
      newNode.relinkLeaves();
      this.highestLeaf().setNextLeaf(newNode.lowestLeaf());
      
      // Send the entry for the newly created node back up the stack for the
      //   parent to deal with
      return new Entry(newNode.getFirstKey(), newNode);
    } else {
      return null;
    }
  }

  
  //------------------------------------------------------
  // relinkLeaves
  //
  // PURPOSE:
  //     Recurse through the entire subtree and manually reapply every
  //     nextLeaf link. This is a really stupid way to do it, but I've been
  //     up all night and I'm not in the mood to optimize. :P
  //------------------------------------------------------
  private void relinkLeaves()
  {
    Node nodeA, nodeB; // from, to
    if (this.dataType == this.INTERIOR_TYPE) {
      if (this.leftEdge().dataType == this.INTERIOR_TYPE) {
        //recurse through the whole damn tree. This almost hurts.
        // I really should do this better.
        this.leftEdge().relinkLeaves();
        for (int i=0; i<this.occupiedSlots; ++i) {
          ((Node) this.entries[i].getData()).relinkLeaves();
        }
      } else {
        for (int i=0; i<this.occupiedSlots - 1; ++i) {
          nodeA = (Node) this.entries[i].getData();
          nodeB = (Node) this.entries[i+1].getData();
          // link 'em.
          nodeA.setNextLeaf(nodeB);
        }
      }
    }
  }
  
  
  //------------------------------------------------------
  // highestLeaf
  //
  // PURPOSE:
  //     Return the highest-valued leaf in this subree.
  //------------------------------------------------------
  public Node highestLeaf()
  {
    Node next;
    //basically, just climb down the right side of the tree until we hit a leaf
    if (this.dataType == this.LEAF_TYPE) {
      return this;
    } else {
      next = (Node) this.entries[this.occupiedSlots-1].getData();
      return next.highestLeaf();      
    }
  }

  
  //------------------------------------------------------
  // lowestLeaf
  //
  // PURPOSE:
  //     Return the lowest-valued leaf in this subtree.
  //------------------------------------------------------
  public Node lowestLeaf()
  {
    //climb down the left side of the tree until we hit a leaf.
    if (this.dataType == this.LEAF_TYPE) {
      return this;
    } else {
      return this.leftEdge().lowestLeaf();
    }
  }

  
  //------------------------------------------------------
  // leafSearch
  //
  // PURPOSE:
  //     Given a key, iterate through the leaves of this tree until the key is found,
  //     or return null if it's not.
  //------------------------------------------------------
  public Entry leafSearch(int key) {
    Node curr = this.lowestLeaf(); // Start at the left edge of the tree
    
    while (curr != null) {
      // Work our way across to the right
      for (int i=0; i<curr.occupiedSlots; ++i) {
        if (curr.entries[i].getKey() == key) {
          return (Entry) curr.entries[i];
        }
      }
      curr = curr.nextLeaf();
    }
    return null;
  }
  
  
  //------------------------------------------------------
  // getFirstKey
  //
  // PURPOSE:
  //     Return the key of the first Entry in this Node.
  //------------------------------------------------------
  public int getFirstKey()
  {
    if (this.dataType == this.INTERIOR_TYPE) {
      return this.leftEdge().getFirstKey();
    } else {
      return this.entries[0].getKey();
    }
  }
  
  //------------------------------------------------------
  // realInsert
  //
  // PURPOSE:
  //     Having previously established that this is the actual Node
  //     we need to insert an Entry into, go ahead and do that.
  //     Note that interior nodes have a leftEdge node, so they must
  //     be treated specially.
  // PARAMETERS:
  //     - entry (Entry): The element to be inserted into the node.
  // EXTERNAL REFERENCES:
  //     - Entry
  //------------------------------------------------------
  public void realInsert(Entry entry)
  {
    //WARNING: REALLY UGLY CODE. SORRY.
    boolean done=false;
    if (this.dataType == this.INTERIOR_TYPE
        && this.leftEdge() == null)
    {
      // This is a hack, but it's already 4am on the 23rd.
      // related: http://www.ted.com/index.php/talks/rives_on_4_a_m.html
      this.nodeRef = (Node) entry.getData();
      return;
    } else {
      if(this.occupiedSlots == 0) {
        this.entries[0] = entry;
      } else {
        // insert in sorted order. count from the back of the list.
        for(int i=this.occupiedSlots; i>0; --i)
        {
          // if we haven't reached the insertion point...
          if(this.entries[i-1].getKey() > entry.getKey()) {
            this.entries[i] = this.entries[i-1];
          } else if (this.entries[i-1].getKey() == entry.getKey()) {
            //Repeat! Just reject it.
            System.out.println("Repeat Key! Ignoring...");
            return;
          } else { //ok, here's the insertion point.
            this.entries[i] = entry;
            done = true;
            break;
          }
        }
        if (!done) {
          this.entries[0] = entry;
        }
      }
    }
    this.occupiedSlots++;

  }
  
  //------------------------------------------------------
  // middleKey
  // 
  // PURPOSE:
  //     Returns the key of the entry which will become the first
  //     entry in the new node created on split of this node.
  //------------------------------------------------------
  private int middleKey()
  {
    int index = this.entries.length / 2;
    return this.entries[index].getKey();
  }

}
