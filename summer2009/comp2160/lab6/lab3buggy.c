#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#define DIM 15
#define WORDNUM	18 /* number of words in the list */
#define WORD_LEN 20



typedef struct{
	char name[WORD_LEN];
	int over;
	int down;

} Word;

void readWords(void);
int getRowWord(int row,int *col,char *buf,int len);
int getColWord(int col,int *row,char *buf,int len);
void fillRowCoordinate(char *buf,int row,int col);
void fillColCoordinate(char *buf,int row,int col);
void printList(void);

Word wordList[WORDNUM]; /* you should fill this list with words and their coardinates */

/*                                                            1
                          1   2   3   4   5   6   7   8   9   0   1   2   3   4   5  */
char puzzle[DIM][DIM]={ {'E','U','N','I','T','N','O','C','+','+','+','+','+','+','+'},  /*  1 */
					    {'X','+','+','+','+','+','+','+','+','+','+','+','A','+','+'},  /*  2 */
					    {'I','+','+','+','+','+','+','T','+','P','R','I','N','T','F'},  /*  3 */
					    {'T','+','+','+','+','+','+','U','+','+','+','+','S','+','L'},  /*  4 */
					    {'+','W','H','I','T','E','S','P','A','C','E','+','I','+','O'},  /*  5 */
					    {'T','+','+','+','+','+','+','+','+','+','+','+','+','+','A'},  /*  6 */
					    {'S','+','N','O','I','S','S','E','R','P','X','E','+','+','T'},  /*  7 */
					    {'E','+','+','+','+','+','+','+','+','+','+','+','+','+','+'},  /*  8 */
					    {'T','N','E','M','E','R','C','N','I','+','P','O','W','E','R'},  /*  9 */
					    {'+','+','+','A','+','+','+','+','+','+','+','+','+','+','E'},  /* 10 */
					    {'E','R','O','C','+','I','+','E','L','B','U','O','D','+','T'},  /* 11 */
					    {'+','+','+','R','+','N','+','+','+','+','+','L','L','U','N'},  /* 12 */
					    {'+','+','+','O','+','T','L','U','A','F','E','D','+','+','I'},  /* 13 */
					    {'+','+','+','S','+','+','+','+','+','+','+','+','+','+','O'},  /* 14 */
                        {'+','+','+','+','+','S','Y','M','B','O','L','I','C','+','P'}}; /* 15 */

int main()
{
	int i,j;
	int row;
	int col;
	char word[WORD_LEN];
	
	readWords();
	for(i = 0;i < DIM;i++)
		while(getRowWord(i,&col,word,WORD_LEN) > 0)
			fillRowCoordinate(word,i,col);
	for(j = 0;j < DIM;j++)
		while(getColWord(j,&row,word,WORD_LEN) > 0)
			fillColCoordinate(word,row,j);

	printList();
	
   return EXIT_SUCCESS;
}

void readWords(void)
{
	FILE *ifp;
	char word[WORD_LEN];
	int i = 0;
	
	ifp = fopen("list.txt","r");
  assert(ifp != NULL);
	if(ifp == NULL)
	{
		printf("Cannot open list.txt\n");
		exit(1);
	}
	while((fscanf(ifp,"%s",word) != EOF) && i < WORDNUM)
	{
		strcpy(wordList[i].name,word);
		wordList[i].over = 0;
		wordList[i].down = 0;
		i++;
	}
	fclose(ifp);
}

int getRowWord(int row, int *col,char *buf,int len)
{
	static int j = 0;
	int i = 0;

	
	while(puzzle[row][j] == '+' && j < DIM)
		j++;
	
	if(j == DIM)
	{
		j = 0;
		return 0;
	}
	
	*col = j;
	
	while((puzzle[row][j] != '+') && j < DIM && i < len)
	{
		buf[i] = puzzle[row][j];
		i++;
		j++;
	}
	buf[i] = '\0';
	return i;
}
int getColWord(int col,int *row,char *buf,int len)
{
	static int i = 0;
	int j = 0;

	while(puzzle[i][col] == '+' && i < DIM)
		i++;
	
	if(i == DIM)
	{
		i = 0;
		return 0;
	}
		
	*row = i;
	
	while(puzzle[i][col] != '+' && i < DIM && j < len)
	{
		buf[j] = puzzle[i][col];
		i++;
		j++;
	}
	buf[j] = '\0';
	return j;
}

void fillRowCoordinate(char *buf,int row,int col)
{
	int i = 0;
	int j = strlen(buf) - 1;
	char reverse[WORD_LEN];
	int found = 0;

  assert(buf != NULL);
  
	while(buf[i])
		reverse[i++] = buf[j--];
	reverse[i] = '\0';
	
	for(i = 0;i < WORDNUM && !found;i++)
	{
		if(strcmp(wordList[i].name,buf) == 0)
		{
			wordList[i].over = col + 1;
			wordList[i].down = row + 1;
			found = 1;
		}
		else if(strcmp(wordList[i].name,reverse) == 0)
		{
			wordList[i].over = -(col + strlen(buf));
			wordList[i].down = row + 1;
			found = 1;
		}
	}
}

void fillColCoordinate(char *buf,int row,int col)
{
	int i = 0;
	int j = strlen(buf) - 1;
	char reverse[WORD_LEN];
	int found = 0;

  assert(buf != NULL);
  
	while(buf[i])
		reverse[i++] = buf[j--];
	reverse[i] = '\0';
	
	for(i = 0;i < WORDNUM && !found;i++)
	{
		if(strcmp(wordList[i].name,buf) == 0)
		{
			wordList[i].over = col + 1;
			wordList[i].down = row + 1;
			found = 1;
		}
		else if(strcmp(wordList[i].name,reverse) == 0)
		{
			wordList[i].over = col + 1;
			wordList[i].down = -(row + strlen(buf));
			found = 1;
		}
	}
}
void printList(void)
{
	int i = 0;
	for(i = 0;i < WORDNUM;i++)
	{
		printf("%s(%d,%d)\n",wordList[i].name,wordList[i].over,
			wordList[i].down);
	}
}
