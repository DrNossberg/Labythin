# Labythin

```bash
'##::::::::::'###::::'########::'##:::'##:'########:'##::::'##:'####:'##::: ##:
 ##:::::::::'## ##::: ##.... ##:. ##:'##::... ##..:: ##:::: ##:. ##:: ###:: ##:
 ##::::::::'##:. ##:: ##:::: ##::. ####:::::: ##:::: ##:::: ##:: ##:: ####: ##:
 ##:::::::'##:::. ##: ########::::. ##::::::: ##:::: #########:: ##:: ## ## ##:
 ##::::::: #########: ##.... ##:::: ##::::::: ##:::: ##.... ##:: ##:: ##. ####:
 ##::::::: ##.... ##: ##:::: ##:::: ##::::::: ##:::: ##:::: ##:: ##:: ##:. ###:
 ########: ##:::: ##: ########::::: ##::::::: ##:::: ##:::: ##:'####: ##::. ##:
........::..:::::..::........::::::..::::::::..:::::..:::::..::....::..::::..::
```

## Description:
Create mazes recursively using the backtraking algorithme or iteratively using different possible algorithms.
    
## Usage:
```bash
    Labythin [-chmptvV] [-a[=<mode>]] [-s[=<step>]] [-o=FILE] width height
```

### Parameters:
* width                  width  of the maze to create. default : 30
* height                 height of the maze to create. default : 30

### Options:
*    -a, --algorithm[=<mode>]   Algorithm to use to generate the maze with.
                             If specified, the maze will be generated
                               iteratively, otherwise it will be done
                               recursively
                             values : NONE, RECURSIVE_BACKTRACKER, PRISM,
                               OLDEST, FIFTY_FITY
                             default : NONE
*    -s, --step[=<step>]        will display the maze at every step of the maze's
                               creation
*    -v, --verbose              verbose mode of display
*    -c, --color                color the output, makes it look fabulous
*    -o, --output=FILE          file to output the maze to
*    -p, --pretty-print         display wall as wall with special characters
*    -t, --time                 time took to generate the maze
*    -m, --memory               display the allocated memory/memory used by the
                               program
*    -h, --help                 Show this help message and exit.
*    -V, --version              Print version information and exit.


## Compilation: 
You may wanna use Makefile to compile the dependency or the program itself, but it's not mandatory.  
```bash
java -cp bin:lib/picocli-4.6.1.jar Labythin -h
```
This should execute the Labythin and print the help page.

## Credits:
This program use the picocli librairy to parse the command line and write on the terminal
[![picocli](https://img.shields.io/badge/picocli-4.6.1-green.svg)](https://github.com/remkop/picocli)

## Authors:
BERNARDONI Erin
ORION Antoine
