JLIBS= Jama-1.0.3.jar
JFLAGS= -cp
JC= javac

all: 
	$(JC) $(JFLAGS) $(JLIBS) main.java

clean: 
	rm -rf transition.csv degrees.csv
