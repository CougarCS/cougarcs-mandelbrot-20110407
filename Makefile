BIN:= bin
SRC:= src

$(BIN)/%.class: $(SRC)/%.java
	-@mkdir -p $(BIN) 2> /dev/null
	javac -d $(BIN) $<

all:	build

build:	$(BIN)/MandelApp.class

run:	build
	java -classpath $(BIN) MandelApp

clean:
	-@rm -Rf $(BIN)
