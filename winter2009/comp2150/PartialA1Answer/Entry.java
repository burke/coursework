//-----------------------------------------
// CLASS: Entry
//
// REMARKS:
// This class stores a key-link pair, so that a number of these may be used together as data in
// node for a shallow tree.  The link is stored as an object so that it can potentially be used
// for a number of different purposes.
//
// INPUT: None.
//
// OUTPUT: None.
//-----------------------------------------

class Entry{
	private Object		link;		//Student record for LEAF nodes, Node for INTERNAL nodes
	private int			key;

    //basic constructor and accesssors

	public Entry(Object link,int key){
	//Purpose: initialization constructor
		this.link = link;
		this.key = key;
	}//Entry

	public Object getLink(){
	//Purpose: access method
		return link;
	}//getLink

	public int getKey(){
	//Purpose: access method
		return key;
	}//getKey

    //------------------------------------------------------
    // toString
    //
    // PURPOSE:   return data in the form of a string for formatted output
    //
    // PARAMETERS: None
    //------------------------------------------------------
	public  String toString(){
		return "("+link+", "+key+")";
	}//toString

    //------------------------------------------------------
    // swapLink
    //
    // PURPOSE:   supply a new link to be swapped with the one i currently have...
    // sets this Entry's link to the supplied argument and returns the old value.
    //
    // PARAMETERS: link to swap
    //------------------------------------------------------
	public Node swapLink(Node link){
		Node		currLink=(Node)(this.link);
		this.link = link;
		return currLink;
	}//swapLink

    //------------------------------------------------------
    // swapEntryLinks
    //
    // PURPOSE:   supply an entry and swap its link with that of the current
    //            Entry.  Since we access Entries via pointer, we can set this
    //            directly rather than returning a value as above.
    //
    // PARAMETERS: Entry to obtain data from
    //------------------------------------------------------
	public void swapEntryLinks(Entry entry){
		Node		currLink=(Node)(this.link);
		this.link = entry.link;
		entry.link = currLink;
	}//swapEntryLinks
}//Entry

