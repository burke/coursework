//**************************************************************
// CLASS: Savings
//
// REMARKS:
// Specifics for a basic savings account. Other than being able to
// identify this for output purposes as a savings account,
// the generic superclass takes care of pretty much everything..

// Externals: Account (superclass)
// ***************************************************************

class Savings extends Account {

	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with ID - this lets us create a specific account
	//object to look for in a list of accounts
	Savings(int newID) {super(newID);}

	//account with a null constructor ultimately generates
	// (thru superclass - Account) a new account number
	//using the bank branch's scheme for generating valid accounts
	Savings() {
		super();}

	//***********************************************************
	//getTypeCode
	//
	//return a character code for the account type.  This is not
	//used internally since we already have specific subtypes, but
	//it is used for output purposes (account toString).
	//***********************************************************
	public char getTypeCode() { return 'S';}

	//no deposit/withdrawal methods here, superclass can take care
	//of everything.

	} // end class Savings
