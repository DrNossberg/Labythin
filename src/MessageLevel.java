/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Enumeration of the differents level of importance of message of the project
**      FATAL      :
**                  only during exception or event that cause the programm to stop
**      IMPORTANT  :
**                  display all the time, no matteer the option
**      INFO       :
**                  only display when --verbose is activated
**      DEBUG      :
**                  mode reserved to dev to help debug the programm
*/

enum MessageLevel {
    FATAL,
    IMPORTANT,
    INFO,
    DEBUG
}
