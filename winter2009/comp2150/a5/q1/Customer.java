//**************************************************************
// CLASS: Customer
//
// REMARKS:
// storage for the data concerning one customer for the bank.  Keeps
// basic personal identification info and a list of accounts.

// Externals: BankBranch (for appropriate customer #'s), List (for
// accounts)
// ***************************************************************
class Customer extends ListItem {

	//instance variables
	private int customerID;    //customer's ID number...
    private String customerName;  //full name
	private String customerAddress; //unrealistic one-line address...
	private List accounts; //list of accounts for this customer
	private int numAccounts; //the number of bank accounts this person holds

	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with no ID - used to create permanent customers for
	//a bank branch's customer list, and must generate account numbers
	//that are legitimate for the branch.
	Customer(String newName, String newAddress) {
			customerID = BankBranch.obtainNewCustomerID();
			customerName = newName;
			customerAddress = newAddress;
			accounts = new List();
			numAccounts = 0;}

	//constructors without ID - this lets us create a specific account
	//object to look for in a list of accounts, so that this object
	//can be compared to others.
		Customer(int newID, String newName) {
			customerID = newID;
			customerName = newName;
			customerAddress = "";
		}

	//basic accessors for the data members defined above...
	public int getID() {return customerID;}
	public String getName() {return customerName;}
	public String getAddress() {return customerAddress;}
	public List gatAccounts() {return accounts;}
	public int getNumAccounts() {return numAccounts;}

	//***********************************************************
	//addAccount
	//
	//take a supplied account and add it to the list of accounts for
	//this customer; easily done using a list routine.  We also
	//keep track of the number of accounts too...
	//***********************************************************
	public void addAccount(Account newAccount) {
		accounts.insert(newAccount);
		numAccounts++;
		}

	//***********************************************************
	//findAccount
	//
	//get a specific account.  We make a new account object with
	//the key (acct #) we want and  send that to our List search.  This
	//will give us back the Account, or null if not found.
	//***********************************************************
	public Account findAccount(int keyID) {
		//System.out.println("ACCOUNTS LIST:");
		//accounts.print();
		ListItem result = accounts.find(new Account(keyID));
		if (result == null)
			//not found
			return null;
		else if (result instanceof Account) //found
			return (Account) result;
		else { //bad data
			System.out.println("Error: searching for accounts in a non-account list");
			System.exit(1);
			return null; /*keep compiler happy*/
			}
		}

	//***********************************************************
	//isEqual
	//
	//comparison between this Customer and another.  If an ID of 0
	//was supplied on the customer to look for, that's indicative of
	//a "don't care" for that field.  Ditto if a null name was supplied.
	//so, either we have a valid name, a valid ID, or both together that
	//must be matched.
	//***********************************************************
	public boolean isEqual(ListItem newData) {
		boolean retValue=false; //assume !=
		if (newData instanceof Customer) {
			int compareID = ((Customer) newData).getID();
			String compareName = ((Customer) newData).getName();
			if (((compareID==0)||(compareID==customerID )) &&
				((compareName.length()==0)||(compareName.equals(customerName))))
					retValue = true;}
		else {
			System.out.println("Comparing Customer to non-customer data: severe errer");
			System.exit(1);}
		return retValue;
	}

	//***********************************************************
	//toString
	//
	//convert all customer info to a single string.  most of this
	//is trivial, but we have to supply each account and the number
	//of accounts.  This is done by requesting each account from
	//the list and converting it to a string individually.  The alternative
	//would be having a special list operation (really a special subclass
	//of list) just to do this internally, or to allow this method to
	//have access to list's internals so that we could go over it node-by-node.
	//this is inefficient from a linked list point of view, but not a
	//big deal because account lists will be short (and it's only inefficient
	//because you're stuck using a linked list here).
	//***********************************************************
	public String toString() {
		//first convert basic name and address
		String theString = customerID+" /"+customerName+"/"+customerAddress+"/ "+numAccounts;
		int count=0;
		//now add each account
		ListItem accountFromList;
		while (count < numAccounts) {
			accountFromList=accounts.ithItem(count);
			if (!(accountFromList instanceof Account)) {
				System.out.println("Non-account data in account list - severe error");
				System.exit(1);
			}
			else {
				count++;
				theString = theString+ " "+ ((Account) accountFromList).idString();
			}
		}//while
		return theString;
		}

	} // end class Customer