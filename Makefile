JC= javac
JVM= java
all:
	$(JC) main.java

Bott:
		$(JVM) main Bott.csv 0.1 n null

Coleman:
		$(JVM) main Coleman.csv 0.1 n null

a1Bott:
		$(JVM) main Bott.csv 1 n null

a1Coleman:
		$(JVM) main Coleman.csv 1 n null

clean:
	rm -rf transition.csv degrees.csv main.class
