//**************************************************************
// CLASS: BankBranch
//
// REMARKS:
// Everything we need for Bank Branches.  Mostly just a collection
// of the constants necessary for a bank branch (if we had >1 branch
// many of these would become non-static).  The only real data a'
// branch maintains is a linked list of the customers in the branch,
// so we have a few simple methods for that.

// Externals: Customer, List
// ***************************************************************
class BankBranch {

	// The following variables and methods were made static so that
	// account and customer numbers would be globally unique, but is
	// is also OK to remove the "static" modifiers so that they are
	// only unique within a branch.

	//***********************************************************
 	//Static Members & Methods
	//***********************************************************

	static int NEXT_AVAIL_ACCT_ID = 1357992; //next available unused account number
	static int NEXT_AVAIL_CUST_ID = 2051300; //next available unused customer number
	static int STANDARD_SERVICE_CHARGE = 75; //default service charge
	static int DEPOSIT_MANAGEMENT_FEE = 200; //management fee for locked in accounts
	static int SHARE_PRICE = 1329; //default share price

	//***********************************************************
	//obtainNewAccountID
	//
	//simple scheme for obtaining the next valid account ID
	//Just 13 more than the previous one.
	//***********************************************************
	static int obtainNewAccountID() {
		int temp = NEXT_AVAIL_ACCT_ID;
		NEXT_AVAIL_ACCT_ID += 13;
		return temp;
		}

	//***********************************************************
	//obtainNewCustomerID
	//
	//simple scheme for obtaining the next valid customer ID
	//Just 21 more than the previous one.
	//***********************************************************
	static int obtainNewCustomerID() {
		int temp = NEXT_AVAIL_CUST_ID;
		NEXT_AVAIL_CUST_ID += 21;
		return temp;
		}

	//***********************************************************
 	//Instance Members & Methods
	//***********************************************************

	private List customerList; //list of customers for the branch

	//constructor  - just make a new list of customers...
	BankBranch() {customerList=new List();}

	//***********************************************************
	//addAccount
	//
	//take a supplied customer and add it to the list of customers
	//for this branch; easily done using a list routine.
	//***********************************************************
	public void addCustomer(Customer newCust) {
		customerList.insert(newCust);
		}

	//***********************************************************
	//findCustomer
	//
	//get a specific customer.  We accept a new customer object with
	//fields to look for, and search for it - basis for a successful
	//search is set by equalTo() in Customer...and will return null
	//if no match.
	//***********************************************************
	public Customer findCustomer(Customer key) {
		ListItem result=customerList.find(key);
		return (Customer) result;
		} //findCustomer

	} //end of class BankBranch
