CC=gcc
CFLAGS=-O2 -g -Wall -fnested-functions

VPATH = src
OBJDIR = build

TARGET=sim

OBJ = instructions.o architecture.o sim.o
HDR = instructions.h architecture.h bitmacros.h 

OBJECTS = $(addprefix $(OBJDIR)/, $(OBJ))

$(TARGET):$(OBJECTS) $(HDR) mkobjdir
	$(CC) $(CFLAGS) $(OBJECTS) -o $(TARGET)

$(OBJDIR)/%.o: %.c mkobjdir
	$(CC) $(CFLAGS) -c $< -o $@

mkobjdir:
	test -d $(OBJDIR) || mkdir -p $(OBJDIR)

clean:
	rm -rf $(OBJDIR)
	rm -f $(TARGET)