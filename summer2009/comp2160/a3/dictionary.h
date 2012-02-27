#ifndef DICTIONARY_H
#define DICTIONARY_H

#include <stdbool.h>

void initDictionary(char *fileName);
void destroyDictionary(void);
bool lookupDictionary(char *word);

#endif
