CC=g++
CFLAGS=-g

OBJ=mystring.o q2.o
HDR=mystring.h
TARGET=A4Q2

$(TARGET):$(OBJ) $(HDR)
	$(CC) $(CFLAGS) $(OBJ) -o $(TARGET)

%.o:%.cpp
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f *.o
	rm -f $(TARGET)