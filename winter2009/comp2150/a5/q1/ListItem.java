//**************************************************************
// CLASS: ListItem
//
// REMARKS:
// Hierarchy for things that can appear in a list (this is thus an
// abstract class.  This forces subclasses to have a toString and a
// comparison for equality, the latter being so that the list can
// be searched.

// Externals: None.
// ***************************************************************

abstract class ListItem {
	//Abstract methods for equality (searching), and coercion to
	//an output string...
	public abstract String toString();
	public abstract boolean isEqual(ListItem newData);
	} //ListItem