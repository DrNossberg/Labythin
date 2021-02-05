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
    UNDEFINED(''),
    WALL('#'),
    WALL_UNVISITED('"'),
    WALL_VISITED('_'),
    PATH('.');
    PATH_UNVISITED('/'),
    PATH_VISITED('o');

    private char state;

    MazeElement(char state) {
        this.state = state;
    }

    public char getChar() {
        return this.state;
    }
}
