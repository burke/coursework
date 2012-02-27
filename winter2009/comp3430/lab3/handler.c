/*
  COMP 3430 Operating Systems
  handler.c
  This process sets up a signal handler for the SIGUSR1 signal
*/

#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

int main ( void ) {
  int i ;
  
  void catch_signal(int);

  printf("This is process %d looping forever\n", (int)getpid());
  
  signal(SIGQUIT,catch_signal);
  
  for (i=0;;i++) {
    printf("%d\n",i); sleep(1); //sleep 1 second
  }
  
} // end main

void catch_signal(int the_signal) {
  signal(SIGQUIT,exit);
  printf("A witty message\n");
} // end catch_signal
