#include <iostream>
#include "mystring.h"

// default constructor. Pretty much does nothing.
MyString::MyString()
{
}
  
// Initialize the string with the contents of the supplied C string.
MyString::MyString(char *rhs)
{
  int i;
  for (size = 0; rhs[size]; ++size);  // find size
  data = (char *)malloc(size * sizeof(char)); // alocate req. space
  for (i=0; i<size; ++i) data[i] = rhs[i];
}

// Free the contents, if any.
MyString::~MyString()
{
  free(data);
}

/*
 * Having a utility class print seems weird, but sure.
 * Print the string, terminated by a newline, to stdio.
 */
void
MyString::print()
{
  std::cout << data << std::endl;
}

// Return the nth character
char
MyString::charAt(int index)
{
  return (index >= size) ? NULL : data[index];
}

// Concatenate two string, returning a new one.
MyString
MyString::operator+(const MyString& rhs)
{
  MyString newString;
  int i;
  
  newString.size = size + rhs.size;
  newString.data = (char *)malloc(newString.size * sizeof(char));

  // Copy data into new string, first this string, then the rhs.
  // Start reading in the RHS on the null character of this string.
  for (i=0; (newString.data[i] = data[i]); ++i);
  for (i=0; (newString.data[i + size] = rhs.data[i]); ++i);

  return newString;
}

