// Author:     Burke Libbey
// Assignment: Comp 2160 Assignment 4
// Modified:   <2009-07-31 19:47:38 CDT>

#include <iostream>
#include "mystring.h"

using namespace std;

// Just a simple test. Create two strings, concatenate them, and print the result.
int
main()
{
  MyString * string1 = new MyString("The quick brown fox ");
  MyString * string2 = new MyString("jumped over the lazy dog.");
  
  MyString string3 = *string1 + *string2;
  
  string3.print();
  
  return 0;
}
