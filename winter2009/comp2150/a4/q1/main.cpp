
//--------------------------------------------------------
// NAME		      : Burke Libbey
// STUDENT NUMBER : 6840752
// COURSE		  : Comp 2150 Object Orientation
// INSTRUCTOR	  : Anderson
// ASSIGNMENT	  : 4
//
// REMARKS:
// This program tests out the Shallow Tree implemented for 2150 assignment 2.
// The version for this assignment is in C++ and uses inheritance.
//
// INPUT:
// Uses one file of student data.
//
// OUTPUT:
// prints statistics and search results from an example tree.
//
// External references: requires ShallowTree, StudentRecord.
//-----------------------------------------------------------

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include "lib/student_record.hpp"
#include "lib/entry.hpp"
#include "lib/node.hpp"
#include "lib/interior_node.hpp"
#include "lib/leaf_node.hpp"
#include "lib/shallow_tree.hpp"

using namespace std;

typedef ShallowTree* ShallowTreePtr;
typedef StudentRecord* StudentRecordPtr;

//------------------------------------------------------
// loadFromFile
//
// PURPOSE:   read data for studentrecords and insert them into the tree, one by one
// Parameters: Takes the file name as a string, and a pointer to an existing shallow tree instance.
//
// External References: Requires StudentRecord.
//------------------------------------------------------

static void buildTree (ShallowTreePtr treePtr,string fileName){

  int      studNumber;
  int      numRecords;
  string   line,token;
  string   familyName,givenName;
  ifstream fin;

//open the file up
  fin.open(fileName.c_str());
  if(fin==NULL){
    cout<<endl<<"ERROR - couldn't open file "<<fileName<<endl;
    return;
  }//if

//read header lines & the number of records
  getline(fin,line); //line with record count
  istringstream    sin(line);
  sin>>numRecords;

//read and insert each record
  for(int i=0;i<numRecords;i++){
    getline(fin,line);
    istringstream    sin(line);
    sin>>studNumber>>familyName>>givenName;
    treePtr->insert(new StudentRecord(studNumber,familyName+" "+givenName));
  }//for
  fin.close();
//print some stats

  cout<<"Depth of tree: "<<treePtr->depth()<<endl;
  cout<<"Number of leaf nodes in above tree: "<<treePtr->numberOfLeafNodes()<<endl;
  cout<<"Number of interior nodes in above tree: "<<treePtr->numberOfInteriorNodes()<<endl<<endl;

}//readFile

//------------------------------------------------------
// part1
//
// PURPOSE:   part 1 of the testing: a large tree is made but not printed.
//   Nodes are counted in single key searches
//------------------------------------------------------
static void test1(){

  const int      NUM_KEYS=3;
  int          key[]={10340,13192,11536}; //keys to search for
  int          numRecords; //number of student records
  const string      fileName="StudentRecLarge.txt"; //file of data
  StudentRecordPtr  searchRecordPtr; //record that was searched for
  ShallowTreePtr    treePtr=new ShallowTree(35,35);

  cout<<"\n\nPart 1"<<endl;
  cout<<"------"<<endl;

//load the tree
  buildTree(treePtr,fileName);

//test searches
  for(int i=0;i<NUM_KEYS;i++){
    cout<<"Search for key: "<<key[i]<<endl;
    searchRecordPtr = treePtr->treeSearch(key[i]);
    if(searchRecordPtr!=NULL)
      cout<<"  The record found by a search-from-root: "<<searchRecordPtr->toString()<<endl;
    else
      cout<<"  The record was not found by a search-from-root"<<endl;
    searchRecordPtr = treePtr->leafSearch(key[i]);
    if(searchRecordPtr!=NULL)
      cout<<"  The record found by a search-across-leaves: "<<searchRecordPtr->toString()<<endl;
    else
      cout<<"  The record was not found by a search-across-leaves"<<endl;
    cout<<endl;
  }//for

//free the memory we allocated
  delete treePtr;
}//part1

//------------------------------------------------------
// part2
//
// PURPOSE:   part 2 of the testing: a large tree is made but not printed.
//   Range of keys in searching is tested
//------------------------------------------------------
static void test2(){

  const int      NUM_KEYS=7;
  int          minKey[]={10,13192,   10,13990,11864,10999,11091}; //keys to search for
  int          maxKey[]={20,13200,10036,15000,11870,11035,11101}; //keys to search for
  int          numRecords; //number of student records
  const string      fileName="StudentRecLarge.txt"; //file of data
  ShallowTreePtr    treePtr=new ShallowTree(30,30);

  cout<<"\n\nPart 2"<<endl;
  cout<<"------"<<endl;

//load the tree
  buildTree(treePtr,fileName);

//test searches
  for(int i=0;i<NUM_KEYS;i++){
    cout<<"Range search from "<<minKey[i]<<" to "<<maxKey[i]<<endl;
    treePtr->output(minKey[i],maxKey[i]);
    cout<<endl;
  }//for

//free the memory we allocated
  delete treePtr;
}//part2

//------------------------------------------------------
// main
//
// PURPOSE:   the main routine.  Run two tests defined in two
//    separate routines.
// External References: Requires StudentRecord, ShallowTree.
//------------------------------------------------------
int main(){
  test1();
  test2();
  cout<<endl<<"End of processing"<<endl;
  return 0;
}//main
