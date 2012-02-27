#include <stdio.h>
#include <stdlib.h>

int
main(int argc, char *argv[])
{
  int i, j=890, pid;

  for (i=0; i<3; ++i) {
    printf("Parent process before fork: uid=%d, pid=%d\n",
           getuid(),
           getpid());
    pid=fork();
    if (pid!=0) {
      /* parent process executed here. */
      printf("Parent process after the fork: uid=%d, pid=%d\n",
             getuid(),
             getpid());
    } else {
      /* Child process executed here. */
      printf("Child process after the fork: uid=%d, pid=%d\n",
             getuid(),
             getpid());
      printf("j = %d\n", j);
      sleep(1);
      printf("Child process exiting, uid=%d, pid=%d\n",
             getuid(),
             getpid());
      exit(0);
    }
    j++;
  }
  printf("Parent process exiting, uid=%d, pid=%d\n",
         getuid(),
         getpid());

}

