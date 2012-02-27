#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "mymalloc.h"


#define MEM_SIZE 1024 * 1024


typedef long Align; /* for alignment to long boundary */
union header { /* block header */
	struct {
		union header *ptr; /* next block if on free list */
		unsigned size; /* size of this block */
    bool free; /* is this block free? */
	} s;
	Align x; /* force alignment of blocks */
};

typedef union header Header;


static Header base; /* empty list to get started */


static Header *freeptr = NULL; /* start of free list */

/* malioc: general-purpose storage allocator */
void *my_malloc(unsigned nbytes)
{
	Header *p, *prevptr;
	unsigned nunits;
	nunits = (nbytes+sizeof(Header)-2)/sizeof(Header) + 1;
	
	if ((prevptr = freeptr) == NULL)
	{ /* no free list yet */
		base.s.ptr = (Header *)calloc(MEM_SIZE,sizeof(Header));
		base.s.size = 0;
		/* allocate memory from OS and then manage it */
		base.s.ptr->s.size = MEM_SIZE;
		base.s.ptr->s.ptr = &base;
		freeptr = prevptr = &base;
	}
	
	for (p = prevptr->s.ptr; ; prevptr = p, p = p->s.ptr)
	{
		if (p->s.size >= nunits) { /* big enough */
			if (p->s.size == nunits) /* exactly */
				prevptr->s.ptr = p->s.ptr;
			else
			{ /* allocate tail end */
				p->s.size -= nunits;
				p += p->s.size;
				p->s.size = nunits;
			}
			freeptr = prevptr;
			return (void *)(p+1);
		}
		if (p == freeptr) /* wrapped around free list */
			//if ((p = morecore(nunits)) == NULL)
				return NULL; /* none left */
	}
}


/* free: put block ap in free list */
void my_free(void *ap)
{
	Header *bp, *p;
	bp = (Header *)ap - 1; /* point to block header */
	//printf("bp->s.size %d\n",bp->s.size);
	for (p = freeptr; (bp <= p || bp >= p->s.ptr); p = p->s.ptr)
		if (p >= p->s.ptr && (bp > p || bp < p->s.ptr))
		{
			//printf("in the corner\n");
			break; /* freed block at start or end of arena */
		}
	if (bp + bp->s.size == p->s.ptr)
	{ /* join to upper nbr */
		bp->s.size += p->s.ptr->s.size;
		bp->s.ptr = p->s.ptr->s.ptr;
	} 
	else
		bp->s.ptr = p->s.ptr;
	if (p + p->s.size == bp)
	{ /* join to lower nbr */
		p->s.size += bp->s.size;
		p->s.ptr = bp->s.ptr;
	}
	 else
		p->s.ptr = bp;
	freeptr = p;
}
/*
int main()
{
	int *i = (int *) my_malloc(sizeof(int));
	
	*i = 10;
	printf("*i %d\n",*i);
	my_free(i);

	return 0;
}*/
