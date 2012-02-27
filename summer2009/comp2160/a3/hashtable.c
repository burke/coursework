/* This is a simplified hashtable that doesn't store values, since we
 only really care that a key exists, not any particular data association.*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "hashtable.h"

#define HASHSIZE 101

static node* Hashtable[HASHSIZE];

void
hashtable_init()
{
  int i;
  for (i=0; i < HASHSIZE; ++i)
    Hashtable[i] = NULL;
}

unsigned int /* Hash index. */
hash(char *s)
{
	unsigned hashVal;
	for(hashVal = 0;*s != '\0';s++)
		hashVal = *s + 31 * hashVal;
	return hashVal % HASHSIZE;
}

node* /* Does the key exist in this table? */
hashtable_lookup(char* name)
{
  printf("looking up %s\n",name);
  unsigned int hash_ix;
  node* node;
  hash_ix = hash(name);
  for(node = Hashtable[hash_ix]; node != NULL; node = node->next)
  {
    printf("AAA");
    if (!strcmp(name, node->key))
    {
      printf("FOUND IT %s\n",node->key);
      return node;
    }
  }
  return NULL;
}

void
hashtable_insert(char* key) {
  unsigned int hash_index;
  node *node, *newnode;
  node = hashtable_lookup(key);
  if (node == NULL)
  {
    printf("I1\n");
    hash_index = hash(key);
    node = malloc(sizeof(node));
    node->key = strdup(key);
    node->next = NULL;
    Hashtable[hash_index] = node;
  }
  else
  {
    printf("I2\n");
    hash_index = hash(key);
    newnode = malloc(sizeof(node));
    newnode->key = strdup(key);
    newnode->next = NULL;
    node->next = newnode;
    Hashtable[hash_index] = newnode;
  }
}
