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

NAME		:= Labythin
JC 			:= javac
EXEC		:= java
FILE_TYPE	:= java
OBJ_TYPE	:= class

OUTPUT_DIR	:= .
SRC_DIR 	:= src
OBJ_DIR		:= bin
LIB_DIR		:= lib

SRC			:=$(wildcard $(SRC_DIR)/*.$(FILE_TYPE))
OBJ			:= $(SRC:$(SRC_DIR)/%.$(FILE_TYPE)=$(OBJ_DIR)/%.$(OBJ_TYPE))

LFLAGS		:= $(LIB_DIR)/picocli-4.6.1.jar
CFLAGS		:= -cp $(SRC_DIR)
EXEFLAGS	:= -cp $(OBJ_DIR)

all		: $(NAME)

$(NAME)	: $(OBJ)
# 	$(EXEC) $(EXEFLAGS):$(LFLAGS) $(NAME)


$(OBJ_DIR)/%.$(OBJ_TYPE) : $(SRC_DIR)/%.$(FILE_TYPE)
	$(JC) $(CFLAGS):$(LFLAGS) -d $(OUTPUT_DIR)/$(shell dirname $@) $^

clean	:
	rm -f $(wildcard $(OBJ_DIR)/*.$(OBJ_TYPE))

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