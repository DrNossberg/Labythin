##
## IUT Nancy-Charlemagne, 2021
## Projet :
##    Labythin
## Author :
##    Erin      Bernardoni
##    Antoine   Orion
## File description :
##    Compile the picocli librairy
##

# NAME		:= Labythin //Labyrinthe
NAME		:= picocli-4.6.1.jar
JC 			:= javac
EXEC		:= jar
FILE_TYPE	:= java
OBJ_TYPE	:= class
OUTPUT_DIR	:= .
SRC_DIR 	:= .
OBJ_DIR		:= .
SRC 		:= $(wildcard $(SRC_DIR)/*.$(FILE_TYPE))
OBJ			:= $(SRC:$(SRC_DIR)/%.$(FILE_TYPE)=%.$(OBJ_TYPE))
LIBS		:= $(LIB_DIR)/

all		: $(NAME)

$(NAME)	: $(OBJ)
	$(EXEC) -cvf $(NAME) ./picocli/*.$(OBJ_TYPE)


%.$(OBJ_TYPE) : $(SRC_DIR)/%.$(FILE_TYPE)
	$(JC) -d $(OBJ_DIR)  $^

clean	:
	rm -rf *.$(OBJ_TYPE)

fclean	: clean
	rm	$(NAME)

re		: fclean all

.PHONY	: all clean fclean re

# verbose mode. use as $ make vrb=1 
$(vrb).SILENT: