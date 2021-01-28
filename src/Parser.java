/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Parser of the Labythin project 
*/

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;


class Parser {
    @Option(names = {"-h", "--help"}, usageHelp= true, description = "display this help and exit")
    boolean help;
    @Option(names = {"-x", "-w", "--width"},  description = "width of the maze to create. By default : ${DEFAULT-VALUE}") //paramLabel = "width", ?
    double width = 50;
    @Option(names = {"-y", "-h", "--height"},  description = "height of the maze to create. By default : ${DEFAULT-VALUE}") //paramLabel = "height", ?
    double height = 50;
    @Option(names = {"-v", "--verbose"}, description = "verbose mode of display")
    boolean verbose;
    @Option(names = {"-c", "--color"}, description = "color the output")
    boolean color;
    @Option(names = {"-f", "--file"}, description = "file to read the maze from")
    File f_intput;
    @Option(names = {"-o", "--output"}, description = "file to output the maze to")
    File f_output;
}
