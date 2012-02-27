#include <stdbool.h>
#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include "hashtable.h"
#include "dictionary.h"

void initDictionary(char *fileName) {
  FILE *fp;
  char word[80];
  char *cp;
  
  fp = fopen("words.txt", "rt");

  hashtable_init();
  
  while (fscanf(fp, "%s\n", word) != EOF)
  {
    //lowercase.
    for (cp=word; *cp; ++cp) {
      *cp = (char) tolower(*cp);
    }
    hashtable_insert(word);
  }
}

void destroyDictionary(void) {

}

bool lookupDictionary(char *word) {
  return (hashtable_lookup(word) != NULL);
}

