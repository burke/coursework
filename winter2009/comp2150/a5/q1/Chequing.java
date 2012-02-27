//**************************************************************
// CLASS: Chequing
//
// REMARKS:
// Specifics for a chequing account, over and above basic account
// stuff.

// Externals: Account (superclass), BankBranch (requires info about'
// service charges that is specific to the branch level).
// ***************************************************************

class Chequing extends Account {

	private int serviceCharge; //the service charge associated with
	                           //this account
	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with ID - this lets us create a specific account
	//object to look for in a list of accounts
	Chequing(int newID) {
		super(newID);
		serviceCharge = BankBranch.STANDARD_SERVICE_CHARGE;}

	//account with a null constructor ultimately generates
	// (thru superclass - Account) a new account number
	//using the bank branch's scheme for generating valid accounts
	Chequing() {
		super();
		serviceCharge = BankBranch.STANDARD_SERVICE_CHARGE;
		}

	//**********************************************************
	//getTypeCode
	//
	//return a character code for the account type.  This is not
	//used internally since we already have specific subtypes, but
	//it is used for output purposes (account toString).
	//***********************************************************
	public char getTypeCode() { return 'C';}

	//**********************************************************
	//getOtherInfo
	//
	//any other info associated with the account - for this one,
	//the service charge associated with writing cheques.
	//***********************************************************
	public String getOtherInfo() { return " "+serviceCharge;}

	//**********************************************************
	//withdrawal
	//
	//withdrawals for chequing accounts.  Takes an amount in cents.
	//Over and above the basic
	//need for the balance to cover the amount of the withdrawal,
	//we also require that the service charge be covered to, so
	//we check that.  We return an error message if that doesn't
	//work (a string); if it's ok, we return the message from the
	//superclass' master withdrawal method that actually does the
	//work.
	//***********************************************************
	public String withdrawal(long amount) {
		if ((getValue() - (amount+serviceCharge)) < 0)
			return "Error: insufficient funds";
		else
			return super.withdrawal(amount+serviceCharge);
		}

	//no deposit method here, superclass takes care of everything.

	} // end class Chequing