// File:     architecture.c
// Author:   Burke Libbey
// Modified: <2009-10-08 15:03:37 CDT>

#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include "architecture.h"

cpu_t *
makeCpu()
{
  // initialize a 'fresh' cpu.
  cpu_t *cpu = calloc(1,          sizeof(struct cpu));
  cpu->code  = malloc(CODESPACE * sizeof(word_t));
  cpu->data  = malloc(DATASPACE * sizeof(word_t));

  // This is kind of ugly. We know a word is 2 bytes, so init. 2*DATASPACE.
  memset(cpu->code, 0xFF, CODESPACE * 2); 
  memset(cpu->data, 0xFF, DATASPACE * 2); 
  
  return cpu;
}
