/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Enumeration of the possible variations of the growing tree use to create the maze
**      RECURSIVE_BACKTRACKER :
**                     newest : use the newest discovered node (last added to activeSet)
**                              as the curent one
**      PRISM :
**                     random : use a random node in the  activeSet
**      OLDEST :
**                            : use the oldest node in the actveSet as the current one
**      FIFTY_FITY :
**                            : for each node:
**                                  50% chances to pick a random node in the active set,
**                                  50% chances to pick the last node added
**          
*/

public enum Mode {
    NONE,
    RECURSIVE_BACKTRACKER,
    PRISM,
    OLDEST,
    FIFTY_FITY,
}
