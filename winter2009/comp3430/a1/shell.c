/*
  shell.c
  COMP 3430 Assignment 1 Winter 2009
  Burke Libbey
  
  This program implements a simple shell.
  Features implemented:
  Searching the PATH environment variable
  Command-line arguments
  cd (change directory) command
  input/output redirection

*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <glob.h>

// program constants
#define BUFSIZE 81
#define MAXARGS 100

// define a Boolean type
#define TRUE 1
#define FALSE 0
typedef int BOOLEAN;

// a macro to compare two strings for equality
#define equals(s1,s2) strcmp((s1),(s2)) == 0

// global declarations for command line arguments
char buffer[BUFSIZE];		// the input buffer
char *cmdline_args[MAXARGS];

/*
  get_cmd
  Read a command from standard input.
  Remove any leading whitespace.
  Returns: 
  1) the entered command
  2) length of the command (in reference parm. len)
*/
char *get_cmd(int *len){
  char *cmd = NULL;
  printf("%% ");
  cmd = fgets(buffer, BUFSIZE, stdin);
  if(!feof(stdin)){
    // overwrite newline character with \0
    buffer[strlen(buffer) - 1] = '\0';
    // remove any pesky leading whitespace
    while(*cmd && (*cmd == ' ' || *cmd == '\t'))
      cmd++;
    *len = strlen(cmd);
  }
  return cmd;
}//get_cmd

int change_dir(char *line) {
  char *seg, *seg2, *brk;
  char *delims = " \t";
  char *buf = strdup(line);

  seg  = strtok(buf,delims); /* "cd " */
  seg  = strtok(NULL,delims); /* target dir */
  seg2 = strtok(NULL,delims); /* excess input? */
  if (seg2 != NULL)
    printf("Error: cd takes zero or one arguments.\n");
  else {
    if (seg == NULL)
      seg = getenv("HOME");
    chdir(seg);
  }
}

char *strip_input_redirection(char *cmd) {
  char mark = '<';
  char* idx;

  idx = strchr(cmd,mark);
  if(idx == NULL)
    return NULL;
  else {
    printf("%s\n\n\n",idx);//(char *)(&cmd + idx));
  }
}
char *strip_output_redirection(char *cmd) {
  return NULL;
}

int main(int argc, char *argv[]){

  int i, pid, idx, ret_code, len;
  char *the_cmd;		// pointer to a command
  char *args[MAXARGS];
  char *arg;
  char *delims = " \t";
  char *infile, *outfile;
  
  the_cmd = get_cmd(&len);
  while(!feof(stdin)) {
    if(len > 0){  // a non-empty command was entered
      if (!strncmp(the_cmd,"cd ",3) || (strlen(the_cmd)==2 && !strcmp(the_cmd,"cd"))) {
        change_dir(the_cmd); // If the command specified is "cd"
      } else {
        pid = fork();
        if(pid){	// parent process
          wait(NULL);
        }else{	// child process - execute the command
          infile = NULL;
          outfile = NULL;
          idx = 0;
          for(i=0;i<MAXARGS;++i)
            args[i]=" ";
          args[idx++] = strtok(the_cmd, delims);
          while (args[idx] != NULL) { //until strtok finishes
            args[idx] = strtok(NULL,delims);
            if(args[idx]==NULL) break;
            if (equals(args[idx-1],"<")) {
              printf("infile: %s\n",args[idx]);
              /* input redirection */
              infile = args[idx];
              args[idx] = " ";
              idx--; //back up, overwrite redirection params.
            } else if (equals(args[idx-1],">")) {
              /* output redirection */
              outfile = args[idx];
              args[idx] = " ";
              idx--;
            } else {
              idx++;
            }
          }
          
          if (infile != NULL) {
            freopen(infile, "r", stdin);
          }
          if (outfile != NULL) {
            freopen(outfile, "w", stdout);
          }
          ret_code = execvp(args[0], args);
          if(ret_code != 0){
            printf("error executing command.\n");
            exit(TRUE);	// indicate that cmd failed
          }
        }// end else
      }// cd
    }// if(len > 0)
    the_cmd = get_cmd(&len);
  }// while
  printf("\nEnd of processing\n");
  return 0;
} /* end main */
 
