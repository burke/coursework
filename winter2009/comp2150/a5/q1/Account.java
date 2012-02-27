//**************************************************************
// CLASS: Account
//
// REMARKS:
// Hierarchy for account types.  Note that this is NOT abstract in
// my solution - that's because I actually DO want to make some
// generic account instances.  When I search a list, in order to be
// able to search by multiple fields easily and keep the number of
// arguments to a search consistent and allow the isEqual method to deal with any fields I want.
// I pass an actual account object to search for - since we know nothing
// of the account type when searching, this has to be a specific account
// but no specific type.  This also allows accounts to be treated similarly
// to customers.  We could also have made this abstract & made a DummyAccount
// subclass, but we'd have to make a dummy comparison and so on for
// that class then too.  I chose this route.  It's fine if you chose the
// other.  Note that there's no way to make a new generic account
// without supplying the account number - it wouldn't be successful if
// we tried to make a generic account that would actually be for
// inserting into a real list of accounts as opposed to for comparison
// purposes.

// Externals: ListItem
// ***************************************************************

class Account extends ListItem {

	//instance vatiables
	private int accountID;  //the ID of this account
	private long balance;   //balance - interpreted differently
							//depending on subclass.

	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with ID - this lets us create a specific account
	//object to look for in a list of accounts
	Account(int newID) {
		accountID = newID;
		balance = 0;}

	//account with a null constructor generates a new account number
	//using the bank branch's scheme for generating valid accounts
	Account() {
		accountID =BankBranch.obtainNewAccountID();
		balance=0;}

	//**********************************************************
 	//withdrawal
 	//
	//takes an amount (cents) to withdraw and does the one generic
	//check that applies to all accounts: you can't overdraw them.
	//returns an error message if this doesn't work, or a null string
	//if everything worked fine.  this is called from all subclasses
	//to actually perform the withdrawal - note it's not public!
	//***********************************************************
	protected String withdrawal(long amount) {
		if (balance < amount)
			return "Error: Insufficient funds";
		else
			balance -= amount;
			return "";
		}

	//**********************************************************
 	//deposit
 	//
	//takes an amount (cents) to deposit.  There's no errors to
	//check at this level, and since this is called from subclasses
	//only, any error checking there would already be done.  This
	//just does the deposit and returns a null string to make it
	//compatible with the scheme used by withdrawal above.
	//***********************************************************
	protected String deposit(long amount) {
		balance += amount;
		//if we've gotten up here to handle it, no possible further errors...
		return "";
		}

	//**********************************************************
 	//toString - takes all the components and returns them in a
 	//string for output purposes.  Includes some that are not stored
 	//(e.g. polymorphic getTypeCode(), getOtherInfo()).
	//***********************************************************
	public String toString() {
		String theString = getTypeCode()+" "+accountID+" "+balance;
		theString = theString+" "+getValue()+getOtherInfo();
		return theString;
		}

	//**********************************************************
 	//idString
 	//
	//return the string version of the account id
	//***********************************************************
	public String idString() {
		return ""+accountID;}

	//**********************************************************
 	//isEqual
 	//
	//comparison for accounts - we could make this customizable
	//to various subtypes by making it concrete below rather than
	//here, but all accounts
	//compare the same here, just by ID number.  This will take
	//two account objects and compare them with that - we shut down
	//with a severe error if we're ever asked to compare incompatible
	//types at runtime.
	//***********************************************************
	public boolean isEqual(ListItem newData) {
		if (newData instanceof Account)
			return ((Account) newData).getID()==accountID;
		else {
			System.out.println("Error: comparing an account with non-account data");
			System.exit(1);
			return false;}}

	//**********************************************************
 	//getValue
 	//
	//return the cash value of the account.  by default this is just
	//the balance, so we implement that here.  Subtypes doing something
	//else (e.g. mutual funds) must override this.
	//***********************************************************
	long getValue() { return balance;}

	//**********************************************************
	//getTypeCode
	//
	//return a character code for the account type.  This is not
	//used internally since we already have specific subtypes, but
	//it is used for output purposes (account toString). Since we can
	//make instances of Account for comparison, we need to return
	//a bogus one here.  Subtypes will override to appropriate values
	//***********************************************************
	char getTypeCode() { return '?';}

	//**********************************************************
	//getOtherInfo
	//
	//any other info associated with the account - by default,
	//nothing, so subclasses will override this.
	//***********************************************************
	String getOtherInfo() { return "";}

	//**********************************************************
 	//ACCESSORS
 	//
	//basic accessor methods for data members defined here.
	//***********************************************************
	int getID() {return accountID;}
	long getBalance() {return balance;}

	} // end class Account