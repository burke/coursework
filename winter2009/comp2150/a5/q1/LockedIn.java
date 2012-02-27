//**************************************************************
// CLASS: LockedIn
//
// REMARKS:
// Specifics for a locked-in savings account.  Basically, the
// addition of a service charge for a deposit and the restriction
// that we can't make any withdrawals.

// Externals: Account (superclass), BankBranch (requires info about'
// service charges that is specific to the branch level).
// ***************************************************************

class LockedIn extends Account {

	private int serviceCharge; //deposit service charge for this type

	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with ID - this lets us create a specific account
	//object to look for in a list of accounts
	LockedIn(int newID)  {super(newID);
       serviceCharge = BankBranch.DEPOSIT_MANAGEMENT_FEE;}

	//account with a null constructor ultimately generates
	// (thru superclass - Account) a new account number
	//using the bank branch's scheme for generating valid accounts
	LockedIn() {super();
		serviceCharge = BankBranch.DEPOSIT_MANAGEMENT_FEE;
		}

	//***********************************************************
	//getTypeCode
	//
	//return a character code for the account type.  This is not
	//used internally since we already have specific subtypes, but
	//it is used for output purposes (account toString).
	//***********************************************************
	public char getTypeCode() { return 'L';}

	//**********************************************************
	//deposit
	//
	//deposits for locked in accounts.  the amount to deposit is
	//a value in cents.  The only difference from a
	//basic deposit is that we have to be concerned with potentially
	//overdrawing the account when we have a deposit that isn't enough
	//to cover the service charge.  So this one will happily let you
	//drain the account by depositing less than the charge to deposit,
	//but won't actually let you take it under a - balance.  We
	//return a string indicating whether or not things succeeded
	//(containing an error message if things didn't go as planned).
	//***********************************************************
	public String deposit(long amount) {
		if ((getValue() + (amount-serviceCharge)) < 0)
			return "Error: insufficient funds to cover service charges";
		else
			return super.deposit(amount - serviceCharge);
		}

	//**********************************************************
	//withdrawal
	//
	//withdrawals for locked in account - easy, just disallow it
	//by returning an error message (amount to withdraw is in cents
	//but that's irrelevant, since we never use it).
	//***********************************************************
	public String withdrawal(long amount) {
		return "Error: cannot withdraw funds from a locked-in account";}

	//**********************************************************
	//getOtherInfo
	//
	//any other info associated with the account - for this one,
	//the service charge associated with depositing...
	//***********************************************************
	public String getOtherInfo() { return " "+serviceCharge;}
}