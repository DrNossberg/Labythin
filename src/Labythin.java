/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Main file of the Labythin project
*/

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Optional;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryType;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import com.sun.management.OperatingSystemMXBean;

@Command(
        name = "Labythin",
        version =   {"Labythin 2021 2.0",
                    "Picocli " + picocli.CommandLine.VERSION,
                    "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
                    "OS: ${os.name} ${os.version} ${os.arch}"},
        mixinStandardHelpOptions = true,
        sortOptions = false,
        headerHeading = "%n@|bold,underline Name:|@%n" + //+ 
                "@|magenta '|@@|white ##|@@|magenta ::::::::::'|@@|white ###|@@|magenta ::::'|@@|white ########|@@|magenta ::'|@@|white ##|@@|magenta :::'|@@|white ##|@@|magenta :'|@@|white ########|@@|magenta :'|@@|white ##|@@|magenta ::::'|@@|white ##|@@|magenta :'|@@|white ####|@@|magenta :'|@@|white ##|@@|magenta :::|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::::'|@@|white ##|@@|black  |@@|white ##|@@|magenta :::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta :'|@@|white ##|@@|magenta ::...|@@|black  |@@|white ##|@@|magenta ..::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ###|@@|magenta ::|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta ::::::::'|@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::.|@@|black  |@@|white ####|@@|magenta ::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ####|@@|magenta :|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::'|@@|white ##|@@|magenta :::.|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ########|@@|magenta ::::.|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white #########|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|black  |@@|white ##|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white #########|@@|magenta :|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta .|@@|black  |@@|white ####|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ###|@@|magenta :%n|@@|black  |@@|white ########|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ########|@@|magenta :::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :'|@@|white ####|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::.|@@|black  |@@|white ##|@@|magenta :%n........::..:::::..::........::::::..::::::::..:::::..:::::..::....::..::::..::%n|@",
        header = "%n@|bold,underline Usage:|@",
        synopsisHeading = "",
        descriptionHeading = "%n@|bold,underline Description:|@%n",
        description = "Create mazes using the growing tree algorithm.%n",
        parameterListHeading = "@|bold,underline Parameters:|@%n",
        optionListHeading = "%n@|bold,underline Options:|@%n",
        footerHeading="%n@|bold,underline Authors:|@%n",
        footer = "BERNARDONI Erin%nORION Antoine")

class Labythin implements Runnable {
    private enum FILE_STATE {
        BAD_FILE, GOOD_FILE };
    private MazeGenerator generator;

    @Spec CommandSpec spec;

    @Parameters(paramLabel = "width",  defaultValue = "30", description = "width  of the maze to create. default : ${DEFAULT-VALUE}")
    int width;
    @Parameters(paramLabel = "height", defaultValue = "30", description = "height of the maze to create. default : ${DEFAULT-VALUE}")
    int height;
    @Option(names = {"-a", "--algorithm"}, arity = "0..1", defaultValue = "NONE", fallbackValue = "NONE",
        description = "Algorithm to use to generate the maze with.\nIf specified, the maze will be generated iteratively, otherwise it will be done recursively\n" +
        "values : ${COMPLETION-CANDIDATES}\n" +
        "default : ${DEFAULT-VALUE} ")
    Mode mode;
    @Option(names = {"-s", "--step"}, arity = "0..1", defaultValue = "0", fallbackValue = "1", description = "will display the maze at every step of the maze's creation")
    int step;
    @Option(names = {"-v", "--verbose"}, description = "verbose mode of display")
    boolean verbose;
    @Option(names = {"-c", "--color"}, description = "color the output, makes it look fabulous")
    boolean color;
    @Option(names = {"-o", "--output"}, description = "file to output the maze to", paramLabel = "FILE")
    File fOutput;
    @Option(names = {"-p", "--pretty-print"}, description = "display wall as wall with special characters")
    boolean prettyPrint;
    @Option(names = {"-t", "--time"}, description = "time took to generate the maze")
    boolean time;
    @Option(names = {"-m", "--memory"}, description = "display the allocated memory/memory used by the program")
    boolean memory;

    List<MemoryPoolMXBean> memoryPools 			= new ArrayList<MemoryPoolMXBean>(ManagementFactory.getMemoryPoolMXBeans());
    OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    RuntimeMXBean runtimeMXBean  = ManagementFactory.getRuntimeMXBean();
    int availableProcessors      = operatingSystemMXBean.getAvailableProcessors();
    long prevUpTime              = runtimeMXBean.getUptime();
    long prevProcessCpuTime      = operatingSystemMXBean.getProcessCpuTime();

    public static void main(String[] args) {
        System.exit(new CommandLine(new Labythin()).execute(args));
    }

    @Override
    public void run() {
        ParseResult parsedResult = spec.commandLine().getParseResult();
        double elapsedTime;
        Maze maze;

        check_args();
        try (Printer printer = new Printer(parsedResult)) {
            this.generator = new MazeGenerator(printer, width, height);
            printer.print(MessageLevel.INFO, toString());
            maze = this.generator.createMaze();
            this.generator.generate(maze, mode);
            printer.display(maze);
            if (time)
                printer.displayValue("Took %s ms to finish.",  String.format("%.2f", this.runtimeMXBean.getUptime() - this.prevUpTime));
            if (memory) {
                printer.displayValue("Average %s%% of CPU usage.", String.format("%.2f", this.cpuUsage()), "red");
                printer.displayValue("Used roughtly %s mo of memory.",  String.format("%.0f", this.memoryUsage() / 1024), "blue");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private double cpuUsage() {
     long processCpuTime     = this.operatingSystemMXBean.getProcessCpuTime();
     long elapsedCpu         = processCpuTime - this.prevProcessCpuTime;
     long elapsedTime        = runtimeMXBean.getUptime() - this.prevUpTime;

     return (Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors)));
    }

    private double memoryUsage() {
        long usedHeapMemoryAfterLastGC = 0;

        for (MemoryPoolMXBean memoryPool : this.memoryPools) {
            if (memoryPool.getType().equals(MemoryType.HEAP)) {
                MemoryUsage poolCollectionMemoryUsage = memoryPool.getCollectionUsage();
                usedHeapMemoryAfterLastGC += poolCollectionMemoryUsage.getUsed();
            }
        } 
        return (usedHeapMemoryAfterLastGC);
    }

    private void check_args() {
        if (width <= 0 || height <= 0)
            throw new ParameterException(spec.commandLine(),
                "Wrong parameter : Both dimention of the maze should be positiv numbers.");
        if (step < 0)
            throw new ParameterException(spec.commandLine(),
                "Wrong parameter : Step indent must be a positiv number.");
        if (fOutput != null && fOutput.exists() && !fOutput.canWrite())
            throw new ParameterException(spec.commandLine(),
                "File issue : file " + fOutput + " already exist and the Labythin can't overwrite it.");
    }

    @Override
    public String toString() {
        ParseResult pr = spec.commandLine().getParseResult();
        String res = "";

        for (OptionSpec option : spec.options())
            res += String.format("%s, %s was specified: %s\n\t value : %s\n", option.shortestName(),
                option.longestName(), pr.hasMatchedOption(option), option.getValue());
        return (res);
    }
}

// import picocli.CommandLine.Model.OptionSpec;
// import picocli.CommandLine.Model.PositionalParamSpec;
