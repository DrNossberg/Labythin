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

@Command(name = "Labythin",
        sortOptions = false,
        headerHeading = "%n@|bold,underline Name:|@%n" + //+ 
                "@|white '|@@|white ##|@@|green ::::::::::|@@|white '|@@|white ###|@@|green ::::|@@|white '|@@|white ########|@@|green ::|@@|white '|@@|white ##|@@|green :::|@@|white '|@@|white ##|@@|green :|@@|white '|@@|white ########|@@|green :|@@|white '|@@|white ##|@@|green ::::|@@|white '|@@|white ##|@@|green :|@@|white '|@@|white ####|@@|green :|@@|white '|@@|white ##|@@|green :::|@@|green  |@@|white ##|@@|green :%n|@@|green  |@@|white ##|@@|green :::::::::|@@|white '|@@|white ##|@@|green  |@@|white ##|@@|green :::|@@|green  |@@|white ##|@@|green ....|@@|green  |@@|white ##|@@|green :|@@|green .|@@|green  |@@|white ##|@@|green :|@@|white '|@@|white ##|@@|green ::|@@|green ...|@@|green  |@@|white ##|@@|green ..|@@|green ::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green :|@@|green .|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ###|@@|green ::|@@|green  |@@|white ##|@@|green :%n|@@|green  |@@|white ##|@@|green ::::::::|@@|white '|@@|white ##|@@|green :|@@|green .|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::|@@|green .|@@|green  |@@|white ####|@@|green ::::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ####|@@|green :|@@|green  |@@|white ##|@@|green :%n|@@|green  |@@|white ##|@@|green :::::::|@@|white '|@@|white ##|@@|green :::|@@|green .|@@|green  |@@|white ##|@@|green :|@@|green  |@@|white ########|@@|green ::::|@@|green .|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white #########|@@|green ::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green  |@@|white ##|@@|green  |@@|white ##|@@|green :%n|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white #########|@@|green :|@@|green  |@@|white ##|@@|green ....|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ....|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green .|@@|green  |@@|white ####|@@|green :%n|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white ##|@@|green ....|@@|green  |@@|white ##|@@|green :|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green ::|@@|green  |@@|white ##|@@|green :|@@|green .|@@|green  |@@|white ###|@@|green :%n|@@|green  |@@|white ########|@@|green :|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green :|@@|green  |@@|white ########|@@|green :::::|@@|green  |@@|white ##|@@|green :::::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green ::::|@@|green  |@@|white ##|@@|green :|@@|white '|@@|white ####|@@|green :|@@|green  |@@|white ##|@@|green ::|@@|green .|@@|green  |@@|white ##|@@|green :%n|@@|green ........|@@|green ::|@@|green ..|@@|green :::::|@@|green ..|@@|green ::|@@|green ........|@@|green ::::::|@@|green ..|@@|green ::::::::|@@|green ..|@@|green :::::|@@|green ..|@@|green :::::|@@|green ..|@@|green ::|@@|green ....|@@|green ::|@@|green ..|@@|green ::::|@@|green ..|@@|green ::%n|@",
        header = "%n%n@|bold,underline Usage:|@%nCreate mazes using the growing tree algorithme.",
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description:|@%n",
        description = "Add a proper desc%n",
        parameterListHeading = "@|bold,underline Parameters:|@%n",
        optionListHeading = "%n@|bold,underline Options:|@%n",
        footerHeading="%n",
        footer = "by BERNARDONI Erin and ORION Antoine%n")
class Parser {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;
    @Parameters(paramLabel = "width", description = "width  of the maze to create. default : ${DEFAULT-VALUE}")
    double width = 50;
    @Parameters(paramLabel = "height", description = "height of the maze to create. default : ${DEFAULT-VALUE}")
    double height = 50;
    @Option(names = {"-v", "--verbose"}, description = "verbose mode of display")
    boolean verbose;
    @Option(names = {"-c", "--color"}, description = "color the output, makes it look fabulous")
    boolean color;
    @Option(names = {"-f", "--file"}, description = "file to read the maze from", paramLabel =  "FILE")
    File f_intput;
    @Option(names = {"-o", "--output"}, description = "file to output the maze to", paramLabel = "FILE")
    File f_output;
}
