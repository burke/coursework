#include <stdio.h>
#include <string.h>
#include <unistd.h>

#define BUFSIZE 81

int
main(int argc, char *argv[])
{
  int len, ret_code;
  char *cmd, buffer[BUFSIZE];

  /* Print a prompt and read a command from stdin */
  printf("> ");
  cmd = fgets(buffer, BUFSIZE, stdin);

  /* Did the user enter a command? */
  if (cmd != NULL) {
    /* Check for newline and overwrite with \0 */
    len = strlen(buffer);
    if (buffer[len-1] == '\n')
      buffer[len-1] = '\0';
    /* Execute the command. */
    ret_code = execl(cmd, cmd, NULL);
    if (ret_code != 0)
      printf("Error executing %s.\n", cmd);
  }
  printf("Done!\n");
}
