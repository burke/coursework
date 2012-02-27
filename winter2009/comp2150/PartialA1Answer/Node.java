//-----------------------------------------
// CLASS: Node
//
// REMARKS:  A Node for a shallow tree.
//		Each node consists primarily of a collection (array) of pointer-index pairs (Entries) and a marker
//      indicating how many of these are in use.  Whether the pointers in these Entries point to
//      other Nodes or to data records is the main thing that distinguishes interior from leaf nodes.  One spare entry
//      is provided as well, for convenience so that data can be inserted and the node split later, by the parent, if it is overfull.
//      Otherwise you'd have to deal with the split here, where you could create a new node but not easily link it to
//      the parent.
//
//      In addition to these Entries, a node requires one more pointer.  This is used for different purposes
//      depending on whether the node is an interior or leaf node.  For interior nodes, each index separates the
//      subtree of values less than the index from those that are >= (i.e. two pointers).  Since each Entry contains
//      one of these, the next Entry's pointer points to the data values that are >= the current Entry's index, and
//      this means we need one more pointer at the end for the rightmost subtree.
//      For leaf nodes, we need a link to point to the next leaf in the tree, to be able to process items
//      sequentially without a tree traversal.  The pointer in both of these situations points to another Node,
//      and so we use the same data item.
//
//		You were told to use a SINGLE node class for this assignment, and so this requires an
//      internal tag so that you know whether it is used as an interior or leaf node.  It also requires dealing
//      with the fact that interior and leaf nodes can have different sizes.  Here a class-level method is
//      created that sets constants appropriately, and this method must be called before loading a tree.
//
//      This class contains the expected operations for tree nodes, including recursive operations to search the tree by
//      moving from node to node, to search linearly across the leaves, and to insert items.  Inserting items involves moving
//      down the tree as any insert must, and then inserting the data item in a leaf, potentially splitting a leaf and
//      interior nodes all the way up.  There are also methods for converting to a string and counting nodes and depth
//      as the assignment requests.  To support all these, there is a suite of
//      constructors that are used to create new interior and leaf nodes depending on the situation in the tree (e.g.
//      create a new leaf for a new tree vs. create an interior node to deal with a split).
//
// INPUT: None.
//
// OUTPUT: None.
//-----------------------------------------
class Node{

	//some constants - since nodes are used for >1 purpose, we need some defined values
	//for node types...
	private static final int LEAF=0; //leaf node
	private static final int INTERNAL=1; //internal node

	// maximum Entry sizes in types of nodes...
	private static int MAX_INTERNAL_SIZE; //maximum number of entries in an internal node
	private static int MAX_LEAF_SIZE; //maximum number of entries in a leaf node

	//Instance Members
	private int type; //INTERNAL or LEAF node - see constant values above
	private int	size; //number of keys in the node so far (# Entries used)
	private int	maxSize; //maximum number of entries for this type of node
	private Entry[]	entry; //list of MAXSIZE+1 entries (one extra so we don't have to split here)
	private Node link; //LEAF: link to next leaf,
	                   //INTERNAL: subtree equal or greater than last key value

    //------------------------------------------------------
    // General Constructor
    //
    // PURPOSE:   supply the type, size, the Entry list (the data), and whatever value you want
    // for the additional link, do basic
    // error checking and initialize Node.
    //
    // PARAMETERS: See above
    //------------------------------------------------------
	public Node(int type,int size,Entry[] entry,Node link){
		//Error checking: need to check that we have a valid type, size, etc.


	}//Node

    //------------------------------------------------------
    // Initial Leaf Constructor
    //
    // PURPOSE:   initialization constructor for first node of tree,
    //            which must be a LEAF node
    //
    // PARAMETERS: One Entry
    //------------------------------------------------------
	public Node(Entry entry){


	}//Node

    //------------------------------------------------------
    // Root Constructor
    //
    // PURPOSE: constructor for new tree root after a split of the root.
    //          which must be an INTERNAL node
    //
    // PARAMETERS: links, key
    //------------------------------------------------------
	public Node(Object leftLink,int key,Node rightLink){


	}//Node

    //------------------------------------------------------
    // setSizes
    //
    // PURPOSE:  set the sizes for the node.  Note that this MUST be called
    // by tree before using any nodes
    //
    // PARAMETERS: m (leaf), n (internal)
    //------------------------------------------------------
	public static void setSizes(int m,int n){


	}//setSizes

    //------------------------------------------------------
    // getLink
    //
    // PURPOSE:  basic accessor for the link field
    //
    // PARAMETERS: none
    //------------------------------------------------------
	public Node getLink(){
		return link;
	}//getLink

    //------------------------------------------------------
    // splitIndex
    //
    // PURPOSE:  return the index of the position in the Entries where the
    // split should occur.
    //
    // PARAMETERS: none
    //------------------------------------------------------
	public int splitIndex(){
		return maxSize/2;
	}//splitIndex

    //------------------------------------------------------
    // isFull
    //
    // PURPOSE:  is this node full? i.e. does it hold all the keys it can already.
    //
    // PARAMETERS: none
    //------------------------------------------------------
	private boolean isFull(){
		return size==maxSize;
	}//isFull

    //------------------------------------------------------
    // toString
    //
    // PURPOSE:  convert a subtree to a string recursively...
    //
    // PARAMETERS: none
    //------------------------------------------------------
	public String toString(){

	}//toString


    //------------------------------------------------------
    // depth
    //
    // PURPOSE:  return the depth of the subtree from this node down, recursively
    //
    // PARAMETERS: none
    //------------------------------------------------------
	public int depth(){

	}//depth


    //------------------------------------------------------
    // numberOfInteriorNodes
    //
    // PURPOSE:  return the number of internal nodes from this node down in the tree, recursively
    //
    // PARAMETERS: none
    //------------------------------------------------------
	public int numberOfInteriorNodes(){


	}//numberOfNodes


    //------------------------------------------------------
    // treeSearch
    //
    // PURPOSE:  search the tree from this node down for the record with the given key.
    // this is done by looking for the branch to follow from this node, and
    // then issuing a recursive call to that node.
    //
    // PARAMETERS: key to search for
    //------------------------------------------------------
	public StudentRecord treeSearch(int searchKey){



	}//treeSearch

    //------------------------------------------------------
    // leafSearch
    //
    // PURPOSE:  a simple linear search along the leaves - this is only meant to work
    // if the current node is actually a leaf node, it doesn't start at the top of the tree!
    //
    // PARAMETERS: key to search for
    //------------------------------------------------------
	public StudentRecord leafSearch(int searchKey){



	}//leafSearch


    //------------------------------------------------------
    // insert
    //
    // PURPOSE:  Insert a new Entry somewhere in the subtree formed
    // by the current Node - i.e. find the correct node and insert it there.
    // We return null if a child didn't split, or the splitting entry if it did.  This works by starting at the root,
    // looking through the keys to see which child the data should be in, and working down the
    // tree recursively until we hit the leaf where it belongs.  The data is then inserted in that leaf, and if that
    // leaf is overfull, we split that node, possibly continuing to split as we return upward.
    //
    // PARAMETERS: None
    //------------------------------------------------------
	public Entry insert(Entry entry){

		boolean		full;  //is the tree full?
		int			i;     //index into entries in a node
		Entry		split; //entry to hold splitting key/link
		Node		child; //A Child Node

		if(type==Node.LEAF){ //this node is a LEAF - insert here
			full = isFull(); //need to know if the node is already full, so that we can split it afterward
			                 //(i.e. the insert will go in the extra Entry)

		//need more code here :)

		else { //INTERNAL - insert into one of the children - search the keys
		       //linearly to find the right one

			//need more code here!

			//now insert into that child
			split = child.insert(entry);
			//if the above insert into the subtree resulted in a split, we got an entry back, containing the splitting key and a pointer
			// to the new node that resulted.  Insert that into this node
			if(split!=null){
				full = isFull(); //again, need to know if it's full now, not after the insert
				insertIntoNode(split);
				if(full)
					return split();
				else
					return null; }
			else
				return null;
		}//if
	}//insert


    //------------------------------------------------------
    // insertIntoNode
    //
    // PURPOSE:  insert a key and link (an Entry) into the current node.  Remember that we
    // have an extra entry element, so that we can overfill a node and then split it later...
    //
    // PARAMETERS: Entry to insert
    //------------------------------------------------------
	private void insertIntoNode(Entry entry){

		int	i;		//current position in entry array
		Node swap;	//placeholder for swapping

		//we have a spare entry for the case where node is full, but just for an error check we should
		//verify we haven't used that and forgotten to split...
		if(size>=maxSize+1)
			System.out.println("ERROR - node is already over full - you missed a split somewhere!");
		else {
			//if all is well, do an ordered insert.  Shuffle entries down to find the spot and make room

            //more code here


			//check if the loop above stopped because we hit the start or found a value smaller than the entry
			//if instead we found an equal entry, it's an error
			if(i>0 && this.entry[i-1].getKey()==entry.getKey()){
				System.out.println("ERROR - attempt to insert duplicate entry "+entry.getKey());
				//abort the insert - we need to shuffle back everything we shuffled earlier
				for(int j=i;j<size;j++)
					this.entry[j] = this.entry[j+1];
				return;
			}//if
			//we're all set - do the insert
			this.entry[i] = entry;
			if(type==Node.INTERNAL){
				//this MUST be being done as the result of a split.  What we're putting in here is then not
			    //a "normal" entry, but a key-pointer pair where the key is a new index value, and the link is to
			    //a new node to the RIGHT, i.e. values >= the index.  Now that this is being treated like any other
			    //entry, we have to put this pointer value where it belongs and link this entry to the subtree that it should
			    //connect to in searching.  In either case, the spot where the new entry's link belongs, and the link that this
                 //entry needs, is to the right.  Think about it, if it's at the beginning, for example, the 2nd entry now points
                 //to everything that was less than all the keys in this node, and now we've put in a smaller key - and the new
                 //entry pointer points to everything greater than that key.
                 //there's also a special case if its the last thing in the node, since then we have to swap with our "extra" pointer
                 //and not that of the next Entry.


                 //more code here...

			}//if
			size++;  //node holds one more now...
		}//else
	}//insertIntoNode

    //------------------------------------------------------
    // split
    //
    // PURPOSE:  Split a node that is too large into two nodes, by choosing the middle element and
    // moving all keys (& their pointers) >= that to a new node, which will be to the right in
    // the tree.  Smaller stuff stays in the old (left) node.  We have to make a little correction after
    // doing this, since the extra right pointer in an interior node points to the subtree >= all its
    // other keys, and that's still in the left node where it no longer belongs.  Similarly, the next
    // pointer (the same physical data item) in a leaf node also has to be corrected.
    // We return the splitting key, since that will be needed in the parent, along with a pointer.  That pointer
    // will be a pointer to the new node - the old one is already linked to the tree properly.
    // An Entry is a convenient structure to return these in, holding the appropriate types, and we
    // use that here - however it's important to realize this is NOT a normal entry, since the pointer there
    // would be a pointer to the subtree/data < the key, not a pointer to the new Node.  The
    // caller has to deal with this appropriately.
    //
    // PARAMETERS: None
    //------------------------------------------------------
	private Entry split(){
		int			splitInd=splitIndex(); //find the spot to split
		int			rightNodeSize; //size of the new right node after copying data over to it
		Entry		splitEntry=entry[splitInd];  //the splitting entry
		Entry[]		rightEntry=new Entry[maxSize+1]; //entries the new (right) node should have
		Node		rightNode; //the new (right) node - same type as current node

		//prepare the entries for the new right node...

		//more code here

		//create the new right node with the entries we prepared...

	    //more code here

		//remove from the left node everything that we put in the new right node
		size = splitInd;

	//no matter what, the parent will need the splitting key for an index, and a pointer
	//to the new right node so it can link it up. return these as a new entry.
		return new Entry(rightNode,splitEntry.getKey());
	}//split


}//Node