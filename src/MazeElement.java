/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Enumeration of the possible states of a node in the maze
*/


public enum MazeElement {
    UNDEFINED('\0'),
    WALL('#'),
    WALL_UNVISITED('#'),
    WALL_VISITED('_'),
    PATH(' '),
    PATH_UNVISITED('o'),
    PATH_VISITED('+');

    private char state;

    MazeElement(char state) {
        this.state = state;
    }

    public char getChar() {
        return this.state;
    }
}
