/*
  COMP 3430 Assignment 3
  Burke Libbey (6840762)

  This code has a lot of problems.
  It doesn't really work at all.

  Too much to do this weekend. If you see this comment,
  I guess I didn't get around to submitting a finished version.

  To compile: gcc -lpthread q3.c
*/

#include <ctype.h>
#include <errno.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define MAX_THREADS 10
#define BUF_SIZE 6
#define INPUT_SIZE 81
#define DELIMS " \t\n"
#define SHUTDOWN "ZZshutdownZZ"

#define TRUE 1
#define FALSE 0
#define BLOCKING 0     // blocking send/receive

// Had to subtract 10000, since 40752 > 2^16
#define MYPORT 12345   // unique value to create key
#define FIRST_MSG 0    // receive first message in queue
#define DATA_T 1       // type code for a data message
#define SECOND_T 2
#define PERMS 0644     // (octal) owner may R/W, others R only
#define MSG_SIZE 100   // max. size of message

int ret_code;

// monitor variables
char *buffer[BUF_SIZE];
int in = 0;
int out = 0;
int count = 0;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

// the message buffer record
typedef struct msgbuf{
  long mtype;	// message type (must be > 0)
  char the_message[MSG_SIZE];	// the message
}Message, *Messageptr;

// message queue parameters
key_t myKey;	// unique key
int queueId;	// message queue identifier
int flags;
size_t msgsize = sizeof(Message);


// Counters for consumer threads. We'll use unsigned
// long ints to minimize likelihood of overflow.
typedef unsigned long ulong;
ulong char_count[MAX_THREADS];
ulong word_count[MAX_THREADS];

// monitor functions for buffer manipulation

/*
  append - monitor function for the producer

  Since there is only 1 producer, we can test
  the blocking condition in an if statement.
*/
void append(char *item){
  
  Messageptr message1;
  char msg[MSG_SIZE];

  message1 = (Messageptr)malloc(msgsize);
  message1->mtype = SECOND_T; 
  strncpy(message1->the_message, item, MSG_SIZE-1);
  ret_code = msgsnd(queueId, message1, msgsize, BLOCKING);
  count++;

}// append

/*
  take - monitor function for the consumers

  Because there are multiple consumers, we need
  to check the blocking condition in a while
  loop, since the condition may be changed
  between the time a consumer receives a signal
  and the time it actually executes.
*/
char *take(int tid){
  Messageptr message1;

  message1 = (Messageptr)malloc(msgsize);
  ret_code = msgrcv(queueId, message1, msgsize, FIRST_MSG, BLOCKING);
  printf("Thread %d read message type %d %s", tid,
         message1->mtype, message1->the_message);
  return message1->the_message;
}// take

// utility function for counting tokens
int token_count(char *string, char *delims){
  int count = 0;
  // get rid of leading delimiters
  while(*string != '\0' && string == strpbrk(string, delims))
    string++;
  while(*string != '\0'){
    count++;
    // jump to the next delimiter, if present
    string = strpbrk(string, delims);
    while(*string != '\0' && string == strpbrk(string, delims))
      string++;
  }// while
  return count;
}// token_count

void *consumer_thread(void *arg){

  char buffer[INPUT_SIZE];
  int i = (int)arg;
  int count;
  char *input_line = take(i);
  while(strcmp(input_line, SHUTDOWN) != 0){
    char_count[i] += strlen(input_line);
    count = token_count(input_line, DELIMS);
    word_count[i] += count;
    free(input_line);
    input_line = take(i);
  }
  pthread_exit(NULL);
} // end consumer_thread

int main (int argc, char * argv[]){

  pthread_t tids[MAX_THREADS];
  char line[INPUT_SIZE];
  int num_threads, rc, t, tnum;
  FILE *inputfile;
  ulong word_total = 0;
  ulong char_total = 0;

  myKey = ftok(".", MYPORT);
  // create the message queue
  // IPC_CREAT - create a queue with the given key
  //             if it does not already exist.
  // IPC_EXCL - return error if queue with the given
  //            key already exists.
  // PERMS - specify permissions for queue.
  flags = IPC_CREAT | IPC_EXCL | PERMS;
  queueId = msgget(myKey, flags);
  if(queueId < 0){
    printf("Error creating message queue: %d\n", queueId);
    exit(0);
  }


   
  // argument checking
  if(argc < 3){
    printf("Usage: %s num_threads <input file>\n", argv[0]);
    exit(0);
  }else
    num_threads = atoi(argv[1]);

  // initialize count array for consumers
  for(t = 0; t < num_threads; t++)
    word_count[t] = 0;

  // open the input file
  inputfile = fopen(argv[2], "r");
  if(inputfile == NULL){
    printf("Unable to open %s\n", argv[2]);
    exit(0);
  }

  // Start the consumer threads
  for(t=0; t < num_threads; t++){
    tnum = t;
    pthread_create(&tids[t], NULL, consumer_thread, (void *)tnum);
  }

  // read the input file
  fgets(line, INPUT_SIZE, inputfile);
  while(!feof(inputfile)){
    append(strdup(line));
    fgets(line, INPUT_SIZE, inputfile);
  }
  fclose(inputfile);

  // Send the shutdown message to the consumer threads
  for(t=0; t < num_threads; t++)
    append(SHUTDOWN);

  // Wait for the consumer threads to finish
  for(t=0; t < num_threads; t++)
    pthread_join(tids[t],NULL);
   
  // print the results
  printf("\tchar\tword\n");
  printf("thread\tcount\tcount\n");
  for(t=0; t < num_threads; t++){
    printf("%d\t%lu\t%lu\n",t, char_count[t], word_count[t]);
    word_total += word_count[t];
    char_total += char_count[t];
  }
  printf("total\t%lu\t%lu\n", char_total, word_total);
  msgctl(queueId, IPC_RMID, NULL);

  printf("End of processing\n");
  pthread_exit(NULL);
} // end main
