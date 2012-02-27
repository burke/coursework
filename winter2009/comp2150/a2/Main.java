//-----------------------------------------
// NAME		: Burke Libbey
// STUDENT NUMBER	: 6840752
// COURSE		: COMP 2150
// INSTRUCTOR	: John Anderson
// ASSIGNMENT	: assignment #2
// QUESTION	: question #2 
// 
// REMARKS: This is a simple even-based simulation. It reads in a list of
//   events for a supermarket and processes them until there are no events left.
//
// INPUT: When run, this program will ask for the name of an input file to use.
//   It should contain a number of arrival events, one per line.
//
// OUTPUT: The program will simulate all relevant events and print a summary
//   of the steps taken.
//
//-----------------------------------------

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

  public static final int numCheckouts = 8;
  public static SoopaStore store;
  
  //------------------------------------------------------
  // main
  //
  // PURPOSE: Start the simulation by calling SoopaStore.
  // PARAMETERS:  args: not like we use it.
  //------------------------------------------------------
   public static void main(String args[]) {
    String infile;
    Scanner sc;

    // Get input filename.
    sc = new Scanner(System.in);
    System.out.println("Please enter input file name:");
    infile = sc.next();
    sc.close();

    System.out.println("Welcome to SoopaStore!");
    System.out.println();
    
    store = SoopaStore.getInstance(numCheckouts, infile);

    store.startSimulation();

    System.out.println("End of Processing");
  }

}