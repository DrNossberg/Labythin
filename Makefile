##
## IUT Nancy-Charlemagne, 2021
## Projet :
##    Labythin
## Author :
##    Erin      Bernardoni
##    Antoine   Orion
## File description :
##    Compile and execute the Labythin projet
##

# NAME		:= Labythin //Labyrinthe
NAME		:= Parser
JC 			:= javac
EXEC		:= java
FILE_TYPE	:= java
OBJ_TYPE	:= class
OUTPUT_DIR	:= .
SRC_DIR 	:= src
OBJ_DIR		:= bin
LIB_DIR		:= lib
SRC 		:= $(SRC_DIR)/Parser.java 
# SRC			:=$(wildcard $(SRC_DIR)/*.$(FILE_TYPE))
OBJ			:= $(SRC:$(SRC_DIR)/%.$(FILE_TYPE)=$(OBJ_DIR)/%.$(OBJ_TYPE))
LIBS		:= $(LIB_DIR)/picocli-4.6.1.jar
LFLAGS		:= -L

all		: $(NAME)

$(NAME)	: $(OBJ)
	$(EXEC) -cp $(LIBS):$(OBJ_DIR) $(NAME) -c
# 	$(EXEC) -cp $(LIBS):$(OBJ_DIR) $(NAME)


$(OBJ_DIR)/%.$(OBJ_TYPE) : $(SRC_DIR)/%.$(FILE_TYPE)
	$(JC) -cp $(LIBS) -d $(OUTPUT_DIR)/$(shell dirname $@) $^

clean	:
	rm -f $(OBJ)
	rm -f $(wildcard $(OBJ_DIR)/*.$(OBJ_TYPE))
	rmdir $(OBJ_DIR)

fclean	: clean
	rmdir $(OBJ_DIR)

re		: fclean all

lib:
	$(MAKE) -C $(LIB_DIR)

lib_clean:
	$(MAKE) -s -C $(LIB_DIR)/ clean

lib_fclean:
	$(MAKE) -s -C $(LIB_DIR)/ fclean


.PHONY	: all clean fclean re lib

# verbose mode. use as $ make vrb=1 
# $(vrb).SILENT: