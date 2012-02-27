//**************************************************************
// CLASS: Node
//
// REMARKS:
// Basic Node class for a singly-linked data structure.  Generic in
// that data just has to be a subclass of ListItem.  Allows
// insertion into a list thru a constructor that takes the "next" node
// as an argument.

// Externals: ListItem
// ***************************************************************
class Node {

	private ListItem data; //data stored in the node
	private Node next;     //next node in a linked structure

	//constructor with data - just make a solitary node with
	//supplied data in it
	Node(ListItem newData) {
		data = newData; next=null;}

	//constructor with data and next - make a node with
	//the supplied node as the next one - this thus allows an
	//"insert-at-front" via a constructor.
	Node(ListItem newData, Node newNext) {
		data = newData;
		next = newNext;
		}

	//simple accessors - get the data & next node
	ListItem getData() {return data;}
	Node getNext() {return next;}
	public String toString() {return data.toString();}

	} // end class Node
