
//-----------------------------------------
// CLASS: ShallowTree
//
// REMARKS:
// This class implements an ordered shallow tree, with a given number of keys indexing the interior and leaf nodes
// (this number can be different for each type).  The tree supports access to date via the root,
// (i.e. a traditional ordered tree search) but also across the leaves (so that data can
// be accessed linearly in order as opposed to through a tree traversal).  To make the latter
// easier, we maintain immediate access to both the root and the first leaf.  Our tree supports
// only insert, search (both ways) and some statistical operations - there is no deleting.
//
// INPUT: None.
//
// OUTPUT: None, but the tree can be returned in string form for printing
//-----------------------------------------
class ShallowTree{

	private Node root; //root of the tree
	private Node leaf; //first leaf

//Constructor - supply M and N values for the tree

	public ShallowTree(int m,int n){
	//Purpose: initialization constructor
	//Input: m is max size of internal node, n is max size of leaf node
		root = null; leaf = null;
		Node.setSizes(m,n); //you MUST call setSizes before using any nodes - this sets the
		                    //constants for the maximum node sizes, otherwise they are unknown
	}//constructor

    //------------------------------------------------------
    // treeSearch
    //
    // PURPOSE:  A traditional ordered tree search - simply passes the search
    // on to the root node, which takes it recursively from there.
    //
    // PARAMETERS: The key to search for.  The found student record is returned.
    //------------------------------------------------------
	public StudentRecord treeSearch(int key){
		return root.treeSearch(key);
	}//treeSearch

    //------------------------------------------------------
    // treeSearch
    //
    // PURPOSE:  A linear search of the leaves - we simply pass the request for
    // a search on to the first leaf, which takes it from there.
    //
    // PARAMETERS: The key to search for.  The found student record is returned.
    //------------------------------------------------------
	public StudentRecord leafSearch(int key){
		return leaf.leafSearch(key);
	}//leafSearch

    //------------------------------------------------------
    // insert
    //
    // PURPOSE:  Insert a new record.  This needs to handle the case of inserting at the
    // root, and if we are not inserting at the root, the insert is simply passed on to the
    // root node, which takes it from there recursively.  We also need to handle the case of having an
    // overfull root after insertion (i.e. we inserted here and there was only the backup extra entry to use, or
    // nodes further down the tree split and pushed an entry up that would fill up the root).
    // In this case the root is split and a new root is created.  This latter
    // case is identified by getting a split key (and new node) back from the insert routine.
    //
    // PARAMETERS: The StudentRecord to insert.
    //------------------------------------------------------
	public void insert(StudentRecord studRec){
		int	key=studRec.getKey(); 			//the key from the record
		Entry entry=new Entry(studRec,key); //new Entry to go into a node
		Entry split;						//returned Entry if a split occurred below

		if(root==null){ //nothing in tree yet - just create the first node
			root = new Node(entry);
			leaf = root;
			}
		else { //insert in or below the root node
			split = root.insert(entry);
			if(split!=null) //overfull, need to create a new root
				root = new Node(root,split.getKey(),(Node)(split.getLink()));
		}//if
	}//insert

    //------------------------------------------------------
    // depth
    //
    // PURPOSE:  Return the depth of this tree: number of nodes from root->leaf inclusive.
    // returns 0 if the tree is null.
    //
    // PARAMETERS: None.
    //------------------------------------------------------
	public int depth(){
		if(root==null)
			return 0;
		else
			return root.depth();
	}//depth

    //------------------------------------------------------
    // numberOfInteriorNodes
    //
    // PURPOSE:  count the number of internal nodes.  If the tree is empty, it's 0, otherwise
    // it's the number below and including the root...
    //
    // PARAMETERS: None.
    //------------------------------------------------------
	public int numberOfInteriorNodes(){
		if (root == null)
			return 0;
		else
			return root.numberOfInteriorNodes();
	}//numberOfNodes()

    //------------------------------------------------------
    // numberOfLeafNodes
    //
    // PURPOSE:  count the number of leaf nodes.  We could do a traversal for this, but it
    // is much less intensive simply to run along the list of the leaf nodes counting
    // linearly (overhead of calls and returns!).
    //
    // PARAMETERS: None.
    //------------------------------------------------------
	public int numberOfLeafNodes(){



	}//numberOfNodes()

    //------------------------------------------------------
    // toString
    //
    // PURPOSE:  convert the tree to a string...just asks this of the root...
    //
    // PARAMETERS: None.
    //------------------------------------------------------
	public String toString(){
		return root.toString();
	}//toString
}//ShallowTree


