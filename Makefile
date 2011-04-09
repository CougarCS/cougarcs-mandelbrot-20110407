BIN:= bin
SRC:= src

$(BIN)/%.class: $(SRC)/%.java
	-@mkdir bin 2> /dev/null
	javac -d $(BIN) $<

all:	build

build:	bin/MandelApp.class

run:	build
	java -classpath $(BIN) MandelApp

clean:
	-@rm -Rf $(BIN)
