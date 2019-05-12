all: clean build
debug: all run

build:
	javac src/*.java -d bin/
	jar cfe RaycastGame.jar Main -C textures/ . -C maps/ . -C bin/ . -C music/ .

clean:
	-rm bin/*.class
	-rm RaycastGame.jar

run:
	java -jar RaycastGame.jar
