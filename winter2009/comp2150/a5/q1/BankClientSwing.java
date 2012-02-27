//-----------------------------------------
// NAME		: Burke Libbey
// STUDENT NUMBER	: 6840752
// COURSE		: COMP 2150
// INSTRUCTOR	: John Anderson
// ASSIGNMENT	: assignment 5
// QUESTION	: question 1  
// 
// REMARKS: A simple Swing app to manage transactions for a hypothetical bank.
//
// INPUT: GUI.
//
// OUTPUT: GUI.
//
//-----------------------------------------

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;

/************class BankClientSwing***********************/

//This class contains the main() method, which simply
//creates and opens a ClientWindow.

public class BankClientSwing {
  public static void main(String[] args) {
    ClientWindow testWindow = new ClientWindow();
    testWindow.setVisible(true);
  } //end main()
}
//------------end class BankClient--------------------


/************class ClientWindow***********************/

//This class defines a window which will present a user
//interface for communicating with the BankServer class
//The major portions of the interface are divided into
//two panels - a CustomerInterface which displays all
//information related to the customer, and an
//AccountInterface which displays the information about
//a particular account. A quit button and two text
//fields which show the communication between the
//BankClient and the BankServer are also provided at the
//bottom of the window.

class ClientWindow extends JFrame implements WindowListener {

  private LabelledField commandPanel, responsePanel;
  private JPanel quitPanel;
  private QuitButton quitButton;
  private CustomerInterface customerInterface;
  private AccountInterface accountInterface;
  
  //The bank server to which commands will be sent,
  //and from which responses will come.
  private BankServer myServer;
  
  // the ClientWindow constructor
  public ClientWindow() {

    myServer = new BankServer();
    this.setTitle("Welcome to the Big Bailout Bank");
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    //bind the window listener!
    addWindowListener(this);

    // TOP THIRD OF SCREEN /////////////////////////
    customerInterface = new CustomerInterface(this);

    // MIDDLE THIRD OF SCREEN /////////////////////////
    accountInterface = new AccountInterface(this);
    
    // BOTTOM THIRD OF SCREEN ////////////////////////
    commandPanel = new LabelledField("Request:",30,false);
    responsePanel = new LabelledField("Response:",30,false);

    quitPanel = new JPanel();
    quitPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    quitButton = new QuitButton("Quit");
    quitButton.addActionListener(quitButton);
    quitPanel.add(quitButton);
    
    this.add(customerInterface);
    this.add(accountInterface);
    this.add(commandPanel);
    this.add(responsePanel);
    this.add(quitPanel);

    //lay the contents out
    pack();

    //Center the window in the context of the screen size (how cool is that)
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height - 25) / 2);
  } //end ClientWindow() constructor

  public void actionPerformed   ( ActionEvent e ) {}
  public void windowDeactivated ( WindowEvent e ) {}
  public void windowActivated   ( WindowEvent e ) {}
  public void windowDeiconified ( WindowEvent e ) {}
  public void windowIconified   ( WindowEvent e ) {}
  public void windowClosing     ( WindowEvent e ) {}
  public void windowOpened      ( WindowEvent e ) {}

  // clientInterface needs a way to clear the accountInterface
  public void clear() {
    accountInterface.clear();
  }

  // Re-grab all the customer data from the server
  public void reloadCustomer() {
    customerInterface.find(getCustomerId(),"");
  }

  // Just quit.
  public void windowClosed(WindowEvent e) {
    System.exit(0);
  }

  // Typically called on selection of an account. Update all the account fields
  // to show some information about this account
  void setActiveAccount(int custId, int acctId) {
    doAccountCommand("F "+custId+" "+acctId);
  }

  //The doCustomerCommand method will accept a string and send it to
  //the server (after echoing it to the commandPanel). The response
  //from the server will handled by the processCustString method.
  void doCustomerCommand(String s) {
    String reply;
    commandPanel.setText(s);
    responsePanel.setText(reply = myServer.processLine(s));
    if ((reply.compareTo("Not Found") != 0)
        && ((reply.substring(0,5)).compareTo("Error") != 0))
      processCustString(reply);
  } //end doCustomerCommand(String)


  //The processCustString method will accept a string of information
  //about a customer (which came as a response from the server) in the
  //format:
  //       IDNum /Name/Address/ NumAccts AcctID1 AcctID2 ...
  //It will parse this string, and send the appropriate messages to
  //cause this information to be displayed.
  void processCustString(String custData) {
    custData = custData.trim(); //discard excess blanks
    Scanner fields = new Scanner(custData);

    int id,numAccts;
    String info;
    String[] accounts;
    
    try {
      id = fields.nextInt();
      
      customerInterface.setId(id);

      fields.next(); // just eat that. the next couple lines deal with it differently.
      
      // Now lets grab that data we discarded just by splitting the whole string on /
      customerInterface.setName(    custData.split("\\/")[1] );
      customerInterface.setAddress( custData.split("\\/")[2] );
      
      numAccts = fields.nextInt();
      
      accounts = new String[numAccts];
      for (int i=0; i<numAccts; ++i) {
        accounts[i] = fields.next();
      }

      customerInterface.setAccounts(accounts);
      
    } catch (InputMismatchException ex) {
      System.out.println("Server Response Format Error!");
    }

    //you need to manage the list of accounts for a customer too
    //This is where the JList comes in.

  } //end method processCustString(String)

  // Return the ID of the customer we're currently looking at.
  public int getCustomerId() {
    return customerInterface.getId();
  }

  //The doAccountCommand method will accept a string and send it to
  //the server (after echoing it to the commandPanel). The response
  //from the server will handled by the processAcctString method. It
  //will return a boolean value indicating whether or not a valid
  //response was received. ("Not Found" is the invalid response.)
  boolean doAccountCommand(String command) {
    String reply;
    commandPanel.setText(command);
    responsePanel.setText(reply = myServer.processLine(command));
    if ((reply.compareTo("Not Found") != 0) &&
        ((reply.substring(0,5)).compareTo("Error") != 0)) {
      processAcctString(reply);
      return true;
    }
    else return false;
  } //end method doAccountCommand


  //The processAcctString method will accept a string of information
  //about an account (which came as a response from the server) in the
  //format:
  //       TypeCode AcctID Balance Value OtherInfo
  //It will parse this string, and send the appropriate messages to
  //cause this information to be displayed. The interpretation of
  //the last few fields will depend on the TypeCode.
  void processAcctString(String acctData) {
    String ignored; //A place to put strings that are not needed
    String code, acctId, type, data1, data2, data3;
    acctData = acctData.trim(); //remove excess blanks

    Scanner fields = new Scanner(acctData);
    code = fields.next();
    acctId = fields.next();

    data1 = fields.next();
    data2 = fields.next();

    accountInterface.setNumber(acctId);
    accountInterface.setBalance(data1);

    accountInterface.setServiceCharge("N/A");
    accountInterface.setShares("N/A");
    accountInterface.setSharePrice("N/A");
    
    // This is ugly, but java's poor support for hashmaps drains my will to live.
    if (code.equals("C")) {
      accountInterface.setType("Chequing");
      accountInterface.setServiceCharge(fields.next());
    } else if (code.equals("S")) {
      accountInterface.setType("Savings");
    } else if (code.equals("M")) {
      accountInterface.setType("Mutual Fund");
    } else if (code.equals("L")) {
      accountInterface.setType("Locked In");
      accountInterface.setServiceCharge(fields.next());
    }
    
  } //end method processAcctString

}
//------------end class BankClient--------------------

// The button to exit the program. 
class QuitButton extends JButton implements ActionListener {

  public QuitButton(String label) {
    super(label);
  }

  // Button was clicked. Just exit.
  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }

}


/***************class LabelledField******************/

/* This class implements a TextField with an attached
   Label within a small Panel. The panel uses a
   left-justified FlowLayout so that the label and the
   text field remain close together at all times. */

class LabelledField extends JPanel {

  // instance variables
  private JLabel theLabel;
  private JTextField theField;

  //the constructor, which requires the label, and the
  //number of characters expected in the text field.
  //the text field is initially empty.
  public LabelledField(String labelText,int fieldSize) {
    setLayout(new FlowLayout(FlowLayout.LEFT,0,0)); // 0,0 = No extra space in the panel
    add(theLabel = new JLabel(labelText,JLabel.RIGHT));
    add(theField = new JTextField(fieldSize));
  } //end LabelledField constructor

  //as above but with the option of having the field be editable or not
  public LabelledField(String labelText,int fieldSize, boolean editable) {
    setLayout(new FlowLayout(FlowLayout.LEFT,0,0)); // 0,0 = No extra space in the panel
    add(theLabel = new JLabel(labelText,JLabel.RIGHT));
    add(theField = new JTextField(fieldSize));
    theField.setEditable(editable);
  } //end LabelledField constructor

  //the following methods simply call the TextField method of the same name
  public void setText(String s) {theField.setText(s);}
  public String getText() {return theField.getText();}

} //end class LabelledField

// CustomerInterface is just the top third of the clientWindow screen. It contains
// customerId, name, address, account list, and customer management buttons
class CustomerInterface extends JPanel implements ActionListener, ListSelectionListener {

  private JPanel buttonsPanel, infoPanel, accountsPanel;
  private LabelledField idField, nameField, addressField;
  private JList accountsList;
  private JButton clearButton, findButton, newButton;

  private ClientWindow parent;

  // Build the UI for this part of the app.
  public CustomerInterface(ClientWindow parent) {
    super();

    this.parent = parent;

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
    clearButton = new JButton("Clear");
    clearButton.setActionCommand("clear");
    clearButton.addActionListener(this);
    buttonsPanel.add(clearButton);
    findButton = new JButton("Find");
    findButton.setActionCommand("find");
    findButton.addActionListener(this);
    buttonsPanel.add(findButton);
    newButton = new JButton("New");
    newButton.setActionCommand("new");
    newButton.addActionListener(this);
    buttonsPanel.add(newButton);
    this.add(buttonsPanel);

    infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    idField = new LabelledField("Customer ID Number:", 12);
    nameField = new LabelledField("Name:", 30);
    addressField = new LabelledField("Address:", 30);
    infoPanel.add(idField);
    infoPanel.add(nameField);
    infoPanel.add(addressField);
    this.add(infoPanel);

    accountsPanel = new JPanel();
    accountsPanel.setLayout(new BoxLayout(accountsPanel, BoxLayout.Y_AXIS));
    accountsPanel.add(new JLabel("Accounts"));
    accountsList = new JList();
    accountsList.addListSelectionListener(this);
    accountsPanel.add(new JScrollPane(accountsList));
    this.add(accountsPanel);
    
  }

  //Clear() method - blank out fields.
  public void clear() {
    idField.setText("");
    setName("");
    setAddress("");
    setAccounts(new String[] {});
    parent.clear();
  }

  // Return the current Account ID.
  public int getId() {
    try {
      return Integer.parseInt(idField.getText());
    } catch (NumberFormatException ex) {
      return 0;
    }
  }

  // simple textField accessors
  public void setId     (   int id     ) {      idField.setText(""+id);   }
  public void setName   (String name   ) {    nameField.setText(name);    }
  public void setAddress(String address) { addressField.setText(address); }

  // Set the accounts list to show the new set of accounts
  public void clearAccounts()                { setAccounts(new String[] {});       }
  public void setAccounts(String[] accounts) { accountsList.setListData(accounts); }

  // Query the server to find a given customer
  public void find(int id, String name) {
    parent.doCustomerCommand("S /"+id+"/"+name+"/");
  }

  // Create a customer with a specified name and address.
  private void create(String name, String address) {
    parent.doCustomerCommand("C /"+name+"/"+address+"/");
  }

  // Depending on whether the user clicked "clear", "find", or "new",
  // perform one of the actions defined in the above methods.
  public void actionPerformed(ActionEvent e) {
    String name = nameField.getText();
    int id;
    try {
      id = Integer.parseInt(idField.getText());
    } catch (NumberFormatException ex) {
      id = 0;
    }
    String address = addressField.getText();
    if (e.getActionCommand().equals("clear")) {
      clear();
    } else if (e.getActionCommand().equals("find")) {
      find(id,name);
    } else if (e.getActionCommand().equals("new")) {
      create(name,address);
    } else {
      // do nothing?
    }

  }

  // The currently selected account changed. Update the active account, refresh the
  // fields in AccountInterface.
  public void valueChanged(ListSelectionEvent e) {
    int acctId;
    if (accountsList.getSelectedValue() != null) {
      acctId = Integer.parseInt(accountsList.getSelectedValue().toString());
      parent.setActiveAccount(parent.getCustomerId(), acctId);
    }
  }

} //end class CustomerInterface



/***************class AccountInterface******************/

/* This class implements the middle panel of the
   Bank Client, containing all of the necessary components for working
   with accounts. */

class AccountInterface extends JPanel implements ActionListener {

  private JPanel newAccountPanel, transactionControlPanel, transactionActionPanel, statusPanel;
  private LabelledField typeField,numberField,balanceField,sharesField,sharePriceField,serviceChargeField,amountField;
  private AccountTypeComboBox typeComboBox;
  private JButton withdrawalButton, depositButton;

  ClientWindow parent;

  // Build this part of the UI. Contained in a JPanel.
  public AccountInterface(ClientWindow parent) {
    super();

    this.parent = parent;
    
    Border blackline = BorderFactory.createLineBorder(Color.black);

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    newAccountPanel = new JPanel();
    newAccountPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    newAccountPanel.add(new JLabel("Create new account:"));
    typeComboBox = new AccountTypeComboBox(new String[] {"Savings","Chequing","Locked In","Mutual Fund"},parent);
    typeComboBox.addPopupMenuListener(typeComboBox);
    newAccountPanel.add(typeComboBox);
    this.add(newAccountPanel);
    
    transactionControlPanel = new JPanel();
    transactionControlPanel.setLayout(new BoxLayout(transactionControlPanel, BoxLayout.Y_AXIS));
    transactionControlPanel.setBorder(BorderFactory.createTitledBorder(blackline,"Transaction Control"));
    amountField = new LabelledField("Amount:",10);
    transactionControlPanel.add(amountField);
    transactionActionPanel = new JPanel();
    transactionActionPanel.setLayout(new BoxLayout(transactionActionPanel, BoxLayout.X_AXIS));
    withdrawalButton = new JButton("Withdrawal");
    withdrawalButton.setActionCommand("withdrawal");
    withdrawalButton.addActionListener(this);
    transactionActionPanel.add(withdrawalButton);
    depositButton = new JButton("Deposit");
    depositButton.setActionCommand("deposit");
    depositButton.addActionListener(this);
    transactionActionPanel.add(depositButton);
    transactionControlPanel.add(transactionActionPanel);
    this.add(transactionControlPanel);

    statusPanel = new JPanel();
    statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
    statusPanel.setBorder(BorderFactory.createTitledBorder(blackline,"Account Status"));
    typeField = new LabelledField("Account Type:",15,false);
    numberField = new LabelledField("Account Number:",15,false);
    balanceField = new LabelledField("Balance:",15,false);
    sharesField = new LabelledField("Shares:",15,false);
    sharePriceField = new LabelledField("Share Price:",15,false);
    serviceChargeField = new LabelledField("Service Charge:",15,false);
    statusPanel.add(typeField);
    statusPanel.add(numberField);
    statusPanel.add(balanceField);
    statusPanel.add(sharesField);
    statusPanel.add(sharePriceField);
    statusPanel.add(serviceChargeField);
    this.add(statusPanel);

  } //end constructor

  // Empty out all the customer and account information in this and customerWindow.
  public void clear() {
    amountField.setText("");
    typeField.setText("");
    numberField.setText("");
    balanceField.setText("");
    sharesField.setText("");
    sharePriceField.setText("");
    serviceChargeField.setText("");
  }

  // Withdrawal or Deposit was clicked. Perform the appropriate action, converting
  // from "normal" currency format to cents.
  public void actionPerformed(ActionEvent e) {
    String acctId, amount;
    int custId;
    custId = parent.getCustomerId();
    acctId = numberField.getText();
    amount = amountField.getText();
    if (e.getActionCommand().equals("withdrawal")) {
      parent.doAccountCommand("W "+custId+" "+acctId+" "+human_to_cents(amount));
    } else if (e.getActionCommand().equals("deposit")) {
      parent.doAccountCommand("D "+custId+" "+acctId+" "+human_to_cents(amount));
    }
  }

  // simple textField accessors
  public void setType(String type) { typeField.setText(type); }
  public void setNumber(String number) { numberField.setText(number); }
  public void setBalance(String balance) { balanceField.setText(cents_to_human(balance)); }
  public void setShares(String shares) { sharesField.setText(shares); }
  public void setSharePrice(String sharePrice) { sharePriceField.setText(cents_to_human(sharePrice)); }
  public void setServiceCharge(String serviceCharge) { serviceChargeField.setText(cents_to_human(serviceCharge)); }
  

  //CentAmount converts the string typed by the user into the amount field
  //into a string giving the amount in cents. For example, "100" and "100.00"
  //both mean $100.00 and change into "10000" (cents). If any error occurs,
  //"0" is returned.
  private String human_to_cents(String amount) {
    String s;
    try {
      s = Long.toString(Math.round(100.0*(Double.valueOf(Double.parseDouble(amount)))));
    } catch(RuntimeException e) {s = "0";}
    return s;
  }

  // Convert a number of cents to a more readable dotted dollar format.
  private String cents_to_human(String cents) {
    if (cents.equals("N/A")) {
      return "N/A";
    }
    NumberFormat nf = new DecimalFormat("#0.00");
    return nf.format(Integer.parseInt(cents) / 100.0);
  }

} //end class AccountInterface

// Show a ComboBox of all the different account types. When one is clicked,
// automatically create a new account of that type.
class AccountTypeComboBox extends JComboBox implements PopupMenuListener {

  ClientWindow window;
  
  public AccountTypeComboBox(String[] data, ClientWindow window) {
    super(data);
    this.window = window;
  }

  public void popupMenuCanceled(PopupMenuEvent e) {}
  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

  // Popup menu probably clicked. Create a new item of type (selected)
  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    char type = this.getSelectedItem().toString().charAt(0);
    int customerId = window.getCustomerId();
    window.doAccountCommand("N "+type+" "+customerId);
    window.reloadCustomer();
  }
  
}