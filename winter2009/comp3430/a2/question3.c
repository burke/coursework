/* question3.c
 * COMP 3430 Operating Systems
 * Burke Libbey
 *
 * A simple producer/consumer example
 *
 * It doesn't really work. I ran out of time, but I have all the
 * thread stuff working the way it should. The main thing missing is the
 * string tokenizing. I figure it's worth most of the marks?
 *
 * To compile: gcc -lpthread question3.c
 */

#define MAX_THREADS 10
#define MAX_LINES   8
#define LINE_LENGTH 80
#define DELIMS      " \t\n"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <pthread.h>

int             total_chars = 0;
int             total_words = 0;
char           *lines[MAX_LINES];
pthread_mutex_t lines_mutex;
bool            producer_finished = false;

char *take() {
  int   i;
  char *ret = "";
  
  if (*lines[0] != '\0') {
    pthread_mutex_lock(&lines_mutex);
    for (i=MAX_LINES; i>0; --i) {
      if (*lines[i-1] != '\0') {
        //printf(">> taken at %d: %s", i-1, lines[i-1]);
        ret = strdup(lines[i-1]);
        *lines[i-1] = '\0';
        break;
      }
    }
    pthread_mutex_unlock(&lines_mutex);
    return ret;
  }
}

void append(const char *buffer) {
  int i;
  bool ok = false;

  while (!ok) {
    for (i=0; i<MAX_LINES; ++i) {
      if (*lines[i] == '\0')
        ok = true;
    }
  }
  pthread_mutex_lock(&lines_mutex);
  for (i=0; i<MAX_LINES; ++i) {
    if (*lines[i] == '\0') {
      strcat(lines[i], buffer);
      //printf(">> appended at %d\n", i);
      break;
    }
  }
  pthread_mutex_unlock(&lines_mutex);
}

void *consumer() {
  char *buffer;
  int   thread_chars = 0;
  int   thread_words = 0;
  bool  finished     = false;
  char **save;
  
  buffer = "X"; // hack. oh well.
  
  while (buffer && (*buffer != '\0' || !producer_finished)) {
    if (buffer = take()) {
      thread_chars += strlen(buffer);
      
      free(buffer);
    }
  } 
    
  total_chars += thread_chars;
  total_words += thread_words;
}

int main(int argc, char *argv[]) {
  char       buffer[LINE_LENGTH + 1];
  pthread_t  threads[MAX_THREADS];
  FILE      *fp;
  char      *infile;
  int        i;
  int        num_threads;

  if (argc < 3) {
    printf("usage: %s num_threads input_file\n", argv[0]);
    exit(1);
  }

  /* Create an array of strings */
  for (i=0; i<MAX_LINES; ++i) {
    lines[i] = calloc(LINE_LENGTH+1, sizeof(char));
  }
  
  num_threads = atoi(argv[1]);
  infile      = argv[2];

  pthread_mutex_init(&lines_mutex, NULL);
  
  /* Create the consumer threads */
  for (i=0; i<num_threads; ++i) {
    pthread_create(&threads[i], NULL, consumer, NULL);
  }

  /* Read input from the input file */
  fp = fopen(infile, "r");

  while (fgets(buffer, LINE_LENGTH+1, fp) != NULL) {
    append(buffer);
  }
  fclose(fp);

  producer_finished = true;
  
  /* Wait for the consumer threads to finish up */
  for (i=0; i<num_threads; ++i) {
    pthread_join(threads[i], NULL);
  }
  
  printf("Total characters: %d\nTotal words:      %d\n", \
         total_chars, total_words);
  
  pthread_exit(NULL);
  
}
