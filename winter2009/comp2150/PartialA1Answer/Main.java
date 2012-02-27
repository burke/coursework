
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

//--------------------------------------------------------
// NAME		      : Change This To Your Name
// STUDENT NUMBER : Your Student Number
// COURSE		  : Comp 2150 Object Orientation
// SECTION		  : Your Section
// INSTRUCTOR	  : Your Instructor
// ASSIGNMENT	  : 1
//
// REMARKS:
// This program tests out the Shallow Tree implemented for 2150 assignment 1, and provides
// the StudentRecord class that you need for data storage in your tree.
//
//
// INPUT:
// Uses two files of student data.
//
// OUTPUT:
// prints each tree after building it, as well as statistics and search results.
//
// External references: requires ShallowTree
//-----------------------------------------------------------

//-----------------------------------------
// CLASS: StudentRecord
//
// REMARKS:
// Data recorded for a student, which will be stored in our shallow tree, and
// accessors to this data.
//
// INPUT: None.
//
// OUTPUT: A toString method exists to return the contents of a record as a string, presumably
// for the purposes of printing, but no specific output routine exists.
// well as any nodes this one links to.
//-----------------------------------------
class StudentRecord{
   private int       number;   //student number
   private String    name;     //name of the student

	//a simple constructor and an accessor for the student number, which we will use as our key

   public StudentRecord(int number,String name){
   //Purpose: initialization constructor
      this.number = number;
      this.name = name;
   }//StudentRecord

   public int getKey(){
      return this.number;
   }//getKey

    //------------------------------------------------------
    // toString
    //
    // PURPOSE:   return the record in the form of a string
    //
    // PARAMETERS: None
    //------------------------------------------------------
   public String toString(){
      return ""+this.number+" "+this.name;
   }//toString

}//Class StudentRecord

//-----------------------------------------
// CLASS: Main
//
// REMARKS:
// Test out our tree
//
// INPUT: Two text files of student record data.
//
// OUTPUT: Prints the tree, results of searches, and summary data
//-----------------------------------------


public class Main{


    //------------------------------------------------------
    // readFile
    //
    // PURPOSE:   read the entire file of students into an array and return it.
    // Parameters: A string indicating the name of the file to open.  An array of StudentRecords
    // is returned.
    // External References: Requires StudentRecord.
    //------------------------------------------------------
    static StudentRecord[] readFile(String fileName){

      int                  numRecords;
      int                  studNumber;
      String               line,token;
      String               familyName,givenName;
      StringTokenizer      tokenizer;
      FileReader           freader=null;
      BufferedReader       fin;
      StudentRecord[]      retRecord=null;

	  //open the file up
      try{
         freader = new FileReader(fileName);
      }catch(FileNotFoundException err){
         System.out.println("ERROR - couldn't open file "+fileName);
         return null;
      }//try
      fin = new BufferedReader(freader);
      if(fin==null){
         System.out.println("ERROR - could not form the BufferedReader");
         return null;
      }//if
      try{
		 //read the number of records
         line = fin.readLine();
         numRecords = Integer.parseInt(line);
         //create an array and read all the records into it...
         retRecord = new StudentRecord[numRecords];
         for(int i=0;i<numRecords;i++){
            line = fin.readLine();
            tokenizer = new StringTokenizer(line);
            token = tokenizer.nextToken();
            studNumber = Integer.parseInt(token);
            familyName = tokenizer.nextToken(",");
            givenName = tokenizer.nextToken();
            retRecord[i] = new StudentRecord(studNumber,familyName+","+givenName);
         }//for
      }catch(IOException err){
         System.out.println("ERROR - problem in reading a line of the file "+fileName);
      }//try
      try{
         fin.close();
      }catch(IOException err){
         System.out.println("ERROR - file would not close");
         return null;
      }//try
      return retRecord;
   }//readFile


    //------------------------------------------------------
    // test
    //
    // PURPOSE:   test tree routines by supplying the sizes of interior and leaf nodes, a
    //            filename to read data from to build the tree, and an
    //            array of items for test searches.  A different header can be printed for any run.
    //
    //------------------------------------------------------
   private static void test(int m, int n, int[] keys, String fileName, String header, boolean printTree){

      StudentRecord     searchRecord; //record that was searched for
      StudentRecord[]   record=readFile(fileName); //the data to insert in the tree
      ShallowTree       tree=new ShallowTree(m,n);  //the tree itself
      StudentRecord 	lastRec;  //the last student record

      System.out.println("\n\n"+header);
      System.out.println("------");
      for(int i=0;i<record.length;i++){
        // System.out.println("\nInsert Student Number: "+record[i].getKey());
         tree.insert(record[i]);
      }//for
      if (printTree) {
      	System.out.println("Resulting tree:");
      	System.out.println("  "+tree.toString());
  		}
      System.out.println("Depth of tree: "+tree.depth());
      System.out.println("Number of leaf nodes in above tree: "+tree.numberOfLeafNodes());
      System.out.println("Number of interior nodes in above tree: "+tree.numberOfInteriorNodes());
      //test searches
      for(int i=0;i<keys.length;i++){
 	     searchRecord = tree.treeSearch(keys[i]);
 	     System.out.println("The record found by a search-from-root: "+searchRecord);
 	     searchRecord = tree.leafSearch(keys[i]);
	      System.out.println("The record found by a search-across-leaves: "+searchRecord);
	   }//for
   }//part1


    //------------------------------------------------------
    // main
    //
    // PURPOSE:   the main routine.  Run three series of tests defined in three
    // separate routines.
    // External References: Requires StudentRecord, ShallowTree.
    //------------------------------------------------------
   public static void main(String args[]){
      test(4,4,new int[]{66,62,71},"StudentRecSmall.txt","Sample From Assignment Listing",true);
      test(4,4,new int[]{47,2320,1},"students3.txt","Small Data",true);
      test(20,14,new int[]{1,2320,540,440,654,700},"students2.txt","Sequence Data",false);
      test(8,3,new int[]{30775,4043,2375,440,654,33137},"students1.txt","Random Data",false);
      test(30,30,new int[]{30775,4043,2375,440,654,33137},"students1.txt","Random Data Shallower",false);
      System.out.println("\nEnd of processing");
   }//main


}//Class Main

