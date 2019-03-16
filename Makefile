build:
	javac src/*.java -d bin/
	jar cfe RaycastGame.jar Main -C bin/ .

clean:
	-rm bin/*.class
	-rm RaycastGame.jar

run:
	java -jar RaycastGame.jar
