import java.io.*;
import java.util.*;

//**************************************************************
// CLASS: BankServer
//
// REMARKS:
// Handles all the server-side details of our bank system.  Creates
// a single bank branch, takes string commands from a client and
// processes them.
//
// There are a number of things that could be improved here if you want some
// extra practice.
// 1 - Interfaces - This uses an abstract class ListItem to deal with
// comparison in lists; You could use a set of interfaces much like the ordered/
// printable list example we saw in class.
//
// 2 - A Hierarchy of commands, much like the hierarchy of events in the
// simulator you implemented.
//
// 2 - A vector of arguments from commands - this command parser
// is longer than one that assumed parsing a vector of commands would
// be. If you changed it to that, in which case you could
// parse everything out in one parse routine and then simply look at
// the arguments one by one in each of the command-processing routines
// to get the data that was needed..
//
// 3 - more sophisticated error-reporting
// methodologies, to pass a string to a single error-generating
// routine rather than returning them as they were found as is done
// in the code here.
//
// Externals: BankBranch, Customer, Account (and subclasses)
// ***************************************************************

public class BankServer {

	// This class implements the server half of the client-server link.

	private BankBranch testBranch; //a single BankBranch for testing

	// Constructor
	BankServer() {testBranch = new BankBranch();}

	//***********************************************************
	//processLine
	//
	// The principal server method. Accept one String, process it,
	// and return a response String to the client.  Each command
	// begins with a code and then has a series of arguments
	// following it.  Again, you could have used a vector to store
	// these, which would have separated the command parsing/processing
	// a little more.
	//***********************************************************
	public String processLine(String commandLine) {

		char code; // The code (first character) from commandLine
		String response; //the response to send back to the server

		// Get the first character of the line (the action code)
		try {code = commandLine.charAt(0);}
		//if we go out of bounds there is no command here (no
		//legitimate input line)
		catch (StringIndexOutOfBoundsException e) {code='?';}

		// Process the code by calling the proper method
		// each of these generates an appropriate response to
		// the client.  Not much choice other than using a switch
		// because we're doing this straight from an input line...
		// we have to look up the code and see what the client wants...
		switch (code) {
			case 'C': //create new customer
				response = handleC(commandLine);
				break;
			case 'S': //search for a customer
				response = handleS(commandLine);
				break;
			case 'N': //create a new account
				response = handleN(commandLine);
				break;
			case 'F': //find an account
				response = handleF(commandLine);
				break;
			case 'W': //withdraw from an account
				response = handleW(commandLine);
				break;
			case 'D': //deposit to an account
				response = handleD(commandLine);
				break;
			default: //no valid command
				response = "Command Not Found";
			} // end of switch
		return response;
	} // end of processLine method


	//********************************************************
	// lookUpCustomerAccount
	//
	// many of the things our server has to do involve transactions
	// on accounts - for that, we are given a speficied customer and
	// an account number, and then we have to do something with that
	// account.  This code does a double lookup - search for customer
	// given customer number, and then search for account given the
	// account number assuming the customer was found.  It returns the
	// object as an Account if found, or null if either of the searches
	// was unsuccessful.
	//********************************************************
	private Account lookUpCustomerAccount(int custID,int accountID) {
		Customer cust = testBranch.findCustomer(new Customer(custID,""));
		if (cust==null)  //not found
			return null;
		else  //search for the account & return it or null if not found
			return cust.findAccount(accountID);
		}

	//********************************************************
	// handleC - create a new customer
	//
	// parses and error checks data, then creates customer and
	// adds to the branch's customer list.  Returns new customer's
	// value as a string.
	//
	//             Input line format:  C /name/address/
	//********************************************************

	private String handleC(String line) {
		line = line.substring(1).trim(); // delete C and outer blanks
		StringTokenizer pieces = new StringTokenizer(line,"/");
		String name,address; //customer data items
		Customer newCust; //the new customer we're makin...
		try { //parse customer data
			name = pieces.nextToken();
			address = pieces.nextToken();
			}
		catch (NoSuchElementException e) {
			return "Error : name/address unparseable";
			}
		//all is well, make the customer and add it in, return
		//its value as a string.
		newCust = new Customer(name,address);
		testBranch.addCustomer(newCust);
		return newCust.toString();
		} // end of handleC method

	//********************************************************
	// handleS - search for a customer
	//
	// allows a customer list to be searched.  Parses data, then
	// creates a customer object with fields to match those we're
	// looking for based on the arguments supplied.  Returns a
	// not found message or the customer as a string.
	//
	//  Input line format:  S /id-or-0/name (possibly empty)/
	//********************************************************

	private String handleS(String line) {
		//positions of delimiters
		int firstSlash = line.indexOf('/');
		int secondSlash = line.indexOf('/',firstSlash+1);
		int thirdSlash = line.indexOf('/',secondSlash+1);
		String name,IDString; //parsed name, ID as a string
		int keyID; //id as an integer
		Customer foundCust; //the found customer

		try { //parse customer information
			IDString = line.substring(firstSlash+1,secondSlash);
			keyID = Integer.parseInt(IDString);
			name = line.substring(secondSlash+1,thirdSlash);
			}
		catch (StringIndexOutOfBoundsException e) {
			return "Error - bad delimeters or missing data items";
			}
		catch (NumberFormatException e) {
			return "Error - Invalid numeric data on command line";
			}
	 	//error check to ensure we're not leaving all wildcards - i.e.
	 	//ID of 0 and blank name is not allowed...
		if ((keyID==0) && ((name.trim()).length()==0)) {
			return "Error - name and account number cannot both be unspecified";}
		else {
	 		//a legitimate attempt to search - make a customer object
	 		//& search for it
			foundCust = testBranch.findCustomer(new Customer(keyID,name));
		//if this returned null, not found...
		if (foundCust==null)
			return "Not Found";
		else
			return foundCust.toString();
		} // end of handleS method
	}

	//********************************************************
	// handleN - create a new account
	//
	// creates a new account for a specific customer (and of a
	// specific type).  Parses data, then attempts to find the
	// customer to attach the account to.  If it's a legitimate
	// customer, we create the account and add it on, and return
	// it as a string...otherwise we return a message indicating
	// our lack of success.
	//             Input line format:  N type customer-id
	//********************************************************

	private String handleN(String line) {
		char typeCode = line.charAt(2); //the type of account to create

		Account newAccount=null; //the new account...

		//parse the customer Id
		int custID = Integer.parseInt(line.substring(3).trim());
		//check for a zero account number - 0's a wildcard so we can't
		//search for it specifically
		if (custID == 0) {
			return "Error - No Such Customer";}
		else {
			//search for the customer based on number...
			Customer whichCust = testBranch.findCustomer(new Customer(custID,""));
			if (whichCust != null) {
				//found the customer...now, have to make the appropriate
				//type of account...this is where it
				//would be nice to be able to generate and run a dynamic message
				//like you can do in smalltalk...
				switch (typeCode) {
					case 'C':
						newAccount = new Chequing();
						break;
					case 'S':
						newAccount = new Savings();
						break;
					case 'L':
						newAccount = new LockedIn();
						break;
					case 'M':
						newAccount = new MutualFund();
						break;
					default:
						System.out.println("New Account: Invalid Type Requested");
				} // end of switch
				//now just add 'er to the customer's data.
				whichCust.addAccount(newAccount);
				return newAccount.toString();}
			else
				return "Error - No Such Customer";
		}} // end of handleN method

	//********************************************************
	// handleF - find an account
	//
	// find a specific account for a specific customer.
	// need to parse the data, then create a customer obj. to match
	// against the cust list, and then assuming we find the customer,
	// do the same to the account for that customer's account
	// list.  If found, returns the account
	// as a string...otherwise we return a message indicating
	// our lack of success.
	//             Input line format:  F customer-id account-id
	//********************************************************

	private String handleF(String line) {
		line = line.substring(1).trim(); // delete F and outer blanks
		StringTokenizer pieces = new StringTokenizer(line);
		//parse the customer ID and account ID
		int custID; int accountID;
		try {
			custID = Integer.parseInt(pieces.nextToken());
			accountID = Integer.parseInt(pieces.nextToken());}
		catch(NoSuchElementException E) {
			 return("Error");}
		catch(NumberFormatException E) {
			 return("Error - Invalid numeric data on command line");}
		//find the customer account and return as a string, or not found
		Account account = lookUpCustomerAccount(custID,accountID);
		if (account==null)
			return "Not Found";
		else
			return account.toString();
		} // end of handleF method

	//********************************************************
	// handleW - withdraw from an account
	//
	// withdraw an amount from a specific customer's account.
	// parse the data from the command, then look for the specified
	// customer's account, error check the data and actually perform
	// the withdrawal.
	//      Input line format:  W customer-id account-id amount
	//********************************************************

	private String handleW(String line) {
		line = line.substring(1).trim(); // delete W and outer blanks
		StringTokenizer pieces = new StringTokenizer(line);

		int custID; int accountID; long amount; //data parsed from command line

		//parse the data
		try {custID = Integer.parseInt(pieces.nextToken());
			 accountID = Integer.parseInt(pieces.nextToken());
			 amount = Long.parseLong(pieces.nextToken());}
	    catch(NoSuchElementException E) {
			 return("Error - Not enough arguments supplied to command");}
		catch(NumberFormatException E) {
			 return("Error - Invalid numeric data on command line");}

		//if the data was parseable, look up the account
		Account theAccount = lookUpCustomerAccount(custID,accountID);
		if (theAccount==null) //not found
			return "Error - No such account for specified customer";
		else
			//make sure we have a good data value for amount
			if (amount < 0)
				return "Error - Cannot withdraw negative amount";
			else { //attempt the withdrawal!
				String result=theAccount.withdrawal(amount);
				if (result.compareTo("")==0)
					return theAccount.toString();
				else //it's an error message...
					return result;
				} //else

		} // end of handleW method


	//********************************************************
	// handleD - deposit to an account
	//
	// deposit an amount to a specific customer's account.
	// parse the data from the command, then look for the specified
	// customer's account, error check the data and actually perform
	// the deposit.
	//      Input line format:  D customer-id account-id amount
	//********************************************************
	private String handleD(String line) {
		line = line.substring(1).trim(); // delete D and outer blanks
		StringTokenizer pieces = new StringTokenizer(line);
		int custID; int accountID; long amount; //data parsed from command line

		//parse the data
		try {
			custID = Integer.parseInt(pieces.nextToken());
			accountID = Integer.parseInt(pieces.nextToken());
			amount = Long.parseLong(pieces.nextToken());}
		catch(NoSuchElementException E) {
			return("Error - Not enough arguments supplied to command");}
		catch(NumberFormatException E) {
			return("Error - Invalid numeric data on command line");}

		//if the data was parseable, look up the account
		Account theAccount = lookUpCustomerAccount(custID,accountID);
		if (theAccount==null) //not found
			return "Error - No such account for specified customer";
		else
			if (amount < 0)
				return "Error - Cannot deposit negative amount";
			else {//attempt the deposit!
				String result=theAccount.deposit(amount);
				if (result.compareTo("")==0)
					return theAccount.toString();
				else //error message!
					return result;
				} //else
		} // end of handleD method

	//**********************************************************
 	//processLines
	// The principal method. Read a sequence of lines from the data
	// file, process them, and send responses to System.out.
	// Need to modify this to communicate with a real client.
	// Validation of data is called up in each of the command
	// handlers below, and is ultimately handled by a method in
	// the object(s) requiring validation
	//***********************************************************
	private void processLines(BufferedReader dataIn) {
	    //read a file and process individual lines of input

		boolean done = false;  // Set to true when a "Q" code is found.
		char code; // The code (first character) from input_line
		String inputLine,response; // the current input line and the response

		try {
			inputLine = dataIn.readLine();}
			catch (IOException ie) {
			System.out.println("Error reading:"+ie.getMessage());
			return;}
		while ((inputLine != null) && !done) {
			//now we have the input line
			System.out.println("input:"+inputLine);
			code = inputLine.charAt(0);
			// Process the code by calling the proper method
			switch (code) {
				case 'C':
					response = handleC(inputLine);
					break;
				case 'S':
					response = handleS(inputLine);
					break;
				case 'N':
					response = handleN(inputLine);
					break;
				case 'F':
					response = handleF(inputLine);
					break;
				case 'W':
					response = handleW(inputLine);
					break;
				case 'D':
					response = handleD(inputLine);
					break;
				case 'Q':
					System.out.println("Server shutting down normally.");
					done = true;
					response = ""; // make the compiler happy
					break;
				default:
					System.out.println("Unknown code found:"+code);
					response = "Not Found";
				} // end of switch
			if (!done) System.out.println("response:"+response+"\n");
			try {
				inputLine = dataIn.readLine();}
				catch (IOException ie) {
				System.out.println("Error reading:"+ie.getMessage());
			return;}
		} // end while
	} // end of ProcessLines method



	//********************************************************
	//********************************************************
	//
	//                    M   A   I   N
	//
	//Just open the file, call the server, and then print
	//the necessary summary data.
	//********************************************************
	//********************************************************

	public static void main(String[] args){
		//open file - return if there's an error.
		BankServer theServer = new BankServer();
		FileReader inFile;
		BufferedReader dataIn = null;

		try {
			//make stream, connect output file!
		  inFile = new FileReader("input.txt");
		  dataIn = new BufferedReader(inFile);
					} //try
		   catch (IOException ie) {
			 System.out.println("Error opening data file:"+ie.getMessage());
			 System.out.println("Cannot Continue");
			 System.exit(0);
			 } //catch

		//process the file, print summary data
		System.out.println("****Welcome to BBB: The Big Bailout Bank****");
		System.out.println();
		theServer.processLines(dataIn);
		System.out.println("***End of Processing***");
	} // end of main method

} // BankServer
