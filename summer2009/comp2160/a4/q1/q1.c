#include <stdio.h>
#include <stdlib.h>

#include "mymalloc.h"

#ifdef __GNUC__
#define likely(x)   __builtin_expect((x),1)
#define unlikely(x) __builtin_expect((x),0)
#else
#define likely(x)   (x)
#define unlikely(x) (x)
#endif


int
main(void)
{
  printf("asdf\n");
  return (EXIT_SUCCESS);
}
