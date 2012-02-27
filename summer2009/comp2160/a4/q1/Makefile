CC=gcc
CFLAGS=-Wall -O3

OBJ=q1.o mymalloc.o
HDR=mymalloc.h
TARGET=q1

$(TARGET):$(OBJ) $(HDR)
	$(CC) $(CFLAGS) $(OBJ) -o $(TARGET) 

%.o:%.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f *.o
	rm -f $(TARGET)
