//***********************************************************
// CLASS: List
//
// REMARKS:
// A simple generic List class that can store any kind
// of ListItem, represented using a linked list of nodes.
// we want to be able to search for an item in the list, so we
// need to ensure ListItem has a method to compare for equality.
// there's also a method to get the item stored at a specific position
// in the list; hardly the most efficient operation to process in a
// linked list, but necessary in some spots.
//
// INPUT: None
//
// OUTPUT: Printing the list is possible - sends a generic print
// message to the nodes comprising the list.
//
// Externals: requires Node
//***********************************************************
//

class List {

	private Node head; 	//just a ptr to the head of the list

	//very basic constructor, just make an empty list.
	List() {head=null;}

	//simple accessor: get the head of the list
	protected Node getHead() {return head;}

	//***********************************************************
	//find
	//
	//is the specific item supplied in the list?  We shuffle along
	//to each item comparing (i.e. a linear search).  Comparison
	//is done by polymorphic isEqual() to the List's data items
	//in order to have this customizable.
	//***********************************************************
	public ListItem find(ListItem newData) {
		//internal search, provides node
	  Node curr=head; //start at the top
	  boolean done = false; //assume it won't be found
	  while (curr != null)
	  	  //is the new piece of data the same as that in this node?
		  if (curr.getData().isEqual(newData))
			  return curr.getData();
		  else //shuffle along...
		      curr=curr.getNext();
	   //if we got here not found...
	   return null;
	   }

	//***********************************************************
	//ithItem
	//
	//get the Ith item in the list, where the first item is i=0.
	//this is not the most efficient operation in a linked list
	//compared to other collections of items, but it's necessary
	//sometimes.  Here we use it so that we can do things like put
	//the strings of all the account names together.  We either
	//have to write a special method here (or in a subclass of List)
	//to deal with just that, or let the application have access to
	//the list's internals.  So here we allow the user to get all
	//the items one by one, so that they can be processed individually.
	//Since it's only used for accounts, no biggie.  If we were
	//keeping customers with it, though, likely a problem.  This is
	//also where smalltalk is nice, since we could write a method
	//here that would take any message (like "gimme the toString of your
	//ID field" dynamically) so that we could put a single iterate-
	//over-a-list method here instead of this!
	//***********************************************************

	public ListItem ithItem(int i) {
		//get the ith item (i>=0) in the list; if i>list size, return null
		ListItem returnVal = null; //assume there is no Ith item.
		Node curr=head;  //start @ the top
		int counter=0; //at the 0th item

		//move over to the proper item; stop when we get there
		//or if we run out of list first...
		while ((counter < i) && (curr !=null)) {
			curr=curr.getNext();
			counter++;}
		//if we stopped because the counter is in the right
		//spot, this is the ith item & we want to return it
		if (curr != null)
			returnVal = curr.getData();
		return returnVal;
	}

	//***********************************************************
	//insert
	//
	//insert a new ListItem into the list; easily done, we can make
	//a new node and specify the "next" node to it, so we just
	//supply the head of the list to that in order to insert at
	//front.
	//***********************************************************
	public void insert(ListItem item) {
		//insert node in list
		Node newNode = new Node(item,head);
		head = newNode;
		}

	//***********************************************************
	//removeFirst
	//
	//remove the first item from the list.  If there isn't one,
	//just return null, otherwise extract data from the first
	//node.
	//***********************************************************
	public ListItem removeFirst() {
		if (head == null)
			return null;
		else {
			Node toReturn = head;
			if (head != null) head=head.getNext();
			return toReturn.getData();}
	}

	//***********************************************************
	//print
	//
	//cause the contents of the list to be printed
    //by sending a print message to all nodes.  Also prints the
    //size of the list.
	//***********************************************************

	public void print() {
		Node curr=head; //start at the top
		int count=0; //current size of list
		//is there anything in the list?
		if (curr==null)
		    System.out.println("No Items");
		else {
		    //just follow from node to node, sending print
			while (curr != null) {
				count++;  //saw one more
				System.out.println(curr.toString());
				System.out.println();
				curr=curr.getNext(); //shuffle along
				} //while
			System.out.println(count+" entries in total"+"\n");
		} //else
	}

	//***********************************************************
	//isEmpty
	//
	//boolean result: does the list have anything in it?
	//***********************************************************
	public boolean isEmpty() {
		if (head == null)
			return true;
		else
			return false;
	}
}