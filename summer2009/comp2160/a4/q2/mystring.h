// -*- mode: c++ -*-

// A very simplistic imitation of the standard C++ String class.
class MyString
{
public:
  int size;
  char *data;

  // default constructor. Pretty much does nothing.
  MyString();
  // Initialize the string with the contents of the supplied C string.
  MyString(char *rhs);
  // Free the contents, if any.
  ~MyString();

  // Dump the contents to stdio, \n-terminated.
  void print();
  // Return the nth character
  char charAt(int index);

  // Concatenate two string, returning a new one.
  MyString operator+(const MyString& rhs);
};

