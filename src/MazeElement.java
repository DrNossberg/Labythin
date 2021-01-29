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
    WALL_UNVISITED('"'),
    WALL_VISITED('_'),
    PATH_UNVISITED('#'),
    PATH_VISITED('.');

    private char state;

    MazeElement(char state) {
        this.state = state;
    }

    public char getState() {
        return this.state;
    }
}
