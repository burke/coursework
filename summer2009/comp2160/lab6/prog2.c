/* Example program for time profiling. */


#include <stdio.h>
#include <stdlib.h>

struct AList {
    int             first;
    struct AList *  rest;
};
typedef  struct AList *  List;

List  data;

List make_empty()
{
    return NULL;
}

List make_nonempty(int first, List rest)
{
    List  list = (List) malloc(sizeof(struct AList));
    if (list == NULL) {
	fprintf(stderr,"Couldn't allocate cons-cell.\n");
	exit(1);
    }

    list->first = first;
    list->rest  = rest;

    return list;
}

/* Returning an int, instead of bool, so that we can use the
 * non-C99 compliant cc. */
int is_empty(List list)
{
    return (list == NULL);
}

List make_list_helper(unsigned int size, List accum)
{
    if (size == 1)
	return make_nonempty(size,accum);
    else
	return make_list_helper(size-1,make_nonempty(size,accum));
}

List make_list(unsigned int size)
{
    return make_list_helper(size,make_empty());
}

List append_lists(List list1, List list2)
{
    List  return_list;

    if (is_empty(list1))
	return list2;
    else {
	return_list = make_nonempty(list1->first,
				    append_lists(list1->rest,list2));
	free(list1);
	return return_list;
    }
}

unsigned int get_num()
{
    unsigned int  n;
    scanf("%u",&n);
    return n;
}

/* Do interesting stuff to get some data. */
void get_data()
{
    unsigned int  size;

    data = make_empty();

    printf("To create some data, enter a sequence of positive numbers.\n");
    printf("Terminate the list with a zero.\n");
    printf("Use at least moderately large numbers to create enough data for good profiling.\n");
    while ((size=get_num()) != 0)
	data = append_lists(data,make_list(size));
}

List insert_list(int n, List list)
{
    List  return_list;

    if (is_empty(list))
	return make_nonempty(n,make_empty());
    else if (n <= list->first)
	return make_nonempty(n,list);
    else {
	return_list = make_nonempty(list->first,insert_list(n,list->rest));
	free(list);
	return return_list;
    }
}

List sort_list(List list)
{
    List  return_list;

    if (is_empty(list))
	return list;
    else {
	return_list = insert_list(list->first,sort_list(list->rest));
	free(list);
	return return_list;
    }
}

void sort_data()
{
    data = sort_list(data);
}

void
print_list(List list)
{
  List curr;

  for (curr = list; !is_empty(curr); curr = curr->rest)
  {
    printf("%d ",list->first);
  }
  printf("\n");
}

/* Print out whatever data we have. */
void print_data()
{
    printf("The data:\n");
    print_list(data);
}


int main()
{
    get_data();
    print_data();

    /* Do something interesting on the data. */
    sort_data();

    print_data();

    return 0;
}
