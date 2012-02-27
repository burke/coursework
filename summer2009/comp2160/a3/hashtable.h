#ifndef HASHTABLE_H
#define HASHTABLE_H

typedef struct __node
{
  char *key;
  struct __node *next;
} node;

unsigned int hash(char *s);
void hashtable_init();
node* hashtable_lookup(char* name);
void hashtable_insert(char* key);

#endif
