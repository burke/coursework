CC=gcc
CFLAGS=-Wall -g

OBJ=dictionary.o hashtable.o A3Q1.o 
HDR=dictionary.h hashtable.h
TARGET=A3Q1


$(TARGET):$(OBJ) $(HDR)
	$(CC) $(CFLAGS) $(OBJ) -o $(TARGET)

%.o:%.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f *.o
	rm -f $(TARGET)