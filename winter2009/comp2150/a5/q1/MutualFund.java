//**************************************************************
// CLASS: MutualFund
//
// REMARKS:
// Specifics for a mutual fund investment account.  Basically, the
// concept of balance changes from cents to shares, and we have to
// alter deposits and withdrawals to deal with converting between
// the two.  We also have to change the concept of value to converting
// from shares as well.

// Externals: Account (superclass), BankBranch (requires info about'
// share prices that is specific to the branch level).
// ***************************************************************

class MutualFund extends Account {

	private int sharePrice; //the price for a whole share of the
						    //fund.

	//***********************************************************
 	//Constructors
	//***********************************************************

	//constructor with ID - this lets us create a specific account
	//object to look for in a list of accounts
	MutualFund(int newID) {super(newID); sharePrice = BankBranch.SHARE_PRICE;}

	//account with a null constructor ultimately generates
	// (thru superclass - Account) a new account number
	//using the bank branch's scheme for generating valid accounts
	MutualFund() {super();
			sharePrice = BankBranch.SHARE_PRICE;
		}

	//**********************************************************
 	//getValue
 	//
	//Value for a mutual fund is the cash value of its shares.
	//since we're storing 1000ths of shares, we reduce the magnitude
	//by this factor as well as multiplying by the share price to
	//give us cold, hard, cash.
	//***********************************************************
	long getValue() { return getBalance()*sharePrice/1000;}

	//**********************************************************
	//getOtherInfo
	//
	//any other info associated with the account - for this one,
	//the price associated with the shares of the fund
	//***********************************************************
	public String getOtherInfo() { return " "+sharePrice;}

	//***********************************************************
	//getTypeCode
	//
	//return a character code for the account type.  This is not
	//used internally since we already have specific subtypes, but
	//it is used for output purposes (account toString).
	//***********************************************************
	public char getTypeCode() { return 'M';}

	//**********************************************************
	//withdrawal
	//
	//withdrawals for mutual funds.  What's different here from
	//the superclass is that the fund's balance is interpreted as
	//1/1000ths of shares, and we're actually withdrawing cash.  So,
	//what we do is actually attempt to sell enough shares to cover
	//amount we withdraw.  We figure that out by converting shares->
	//cash, and then we have an amount in shares that we need.  That's
	//passed to the superclass to attempt a withdrawal (which may or
	//may not succeed).  We're given back a message (string) from the
	//superclass indicating whether all went well or not, and pass that
	//back to the caller.
	//***********************************************************
	public String withdrawal(long amount) {
		amount = Math.round((1000*amount) / (double)sharePrice); //convert to 1/1000th shares
		return super.withdrawal(amount);
		}

	//**********************************************************
	//deposit
	//
	//deposits for mutual funds.  What's different here from
	//the superclass is that the fund's balance is interpreted as
	//1/1000ths of shares, and we're actually depositing cash.  So,
	//what we do is actually attempt to buy enough shares to cover
	//the deposit value.  We figure that out by converting shares->
	//cash, and then we have an amount in shares that we need.  That's
	//passed to the superclass to attempt a deposit.  We're given back
	//a message (string) from the superclass indicating whether all
	//went well or not, and pass that back to the caller.
	//***********************************************************
	public String deposit(long amount) {
		amount = Math.round((1000*amount) / (double)sharePrice); //convert to 1/1000th shares
		return super.deposit(amount);
		}
}