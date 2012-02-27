all: soopastore doc

debug:
	g++ -g -o soopastore main.cc

soopastore:
	g++ -o soopastore main.cc

doc:
	doxygen doxygen-config >/dev/null

clean:
	rm -rf *.o *.gch html latex soopastore
