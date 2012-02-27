//-----------------------------------------
// INSTRUCTOR: Hossain Pourreza
// ASSIGNMENT: assignment 3, QUESTION: 1
// 
// REMARKS: Checks a file for spelling errors.
//   I didn't have time to get the hash table working.
//   And it keep track of incorrect words; just prints a
//   line for each error.
//-----------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "dictionary.h"

#define LINELEN 85

int main()
{
  initDictionary("words.txt");

  char line[LINELEN];
  int linenum = 0;
  char *word;
  char delims[] = "1234567890&+/.,?!:;/\()\"*-$\\ \t\n";
  char *cp;
  
  while (++linenum && fgets(line, LINELEN, stdin) != NULL)
  {
    word = strtok( line, delims );
    while( word != NULL ) {

      //lowercase
      for (cp=word; *cp; ++cp) {
        *cp = (char) tolower(*cp);
      }

      if (!lookupDictionary(word)) {
        printf( "word \"%s\" not found in dictionary on line %d.\n", word, linenum );
      }

      word = strtok( NULL, delims );
    }
  }

  return 0;
}


