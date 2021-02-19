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
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.Optional;

@Command(
		name = "Labythin",
		version = 	{"Labythin 2021 2.0",
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
		description = "Create mazes using the growing tree algorithme.%n",
		parameterListHeading = "@|bold,underline Parameters:|@%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		footerHeading="%n@|bold,underline Authors:|@%n",
		footer = "BERNARDONI Erin%nORION Antoine")

class Labythin implements Runnable {
	private enum FILE_STATE {
		BAD_FILE, GOOD_FILE };
	private MazeGenerator generator;

	@Spec CommandSpec spec;

	@Parameters(paramLabel = "width",  defaultValue = "5", description = "width  of the maze to create. default : ${DEFAULT-VALUE}")
	int width;
	@Parameters(paramLabel = "height", defaultValue = "5", description = "height of the maze to create. default : ${DEFAULT-VALUE}")
	int height;
	@Parameters(paramLabel = "algorithme", defaultValue = "NONE",
		description = "Mode with which the maze will be generated.\nIf specified, the maze will be generated iteratively, otherwise it will be done recursively\n" +
		"values : ${COMPLETION-CANDIDATES}\n" +
		"default : ${DEFAULT-VALUE} ")
	Mode mode;
	@Option(names = {"-s", "--step"},  arity = "0..1", defaultValue = "0", fallbackValue = "1", description = "will display the maze at every step of the maze's creation")
	int step;
	@Option(names = {"-v", "--verbose"}, description = "verbose mode of display")
	boolean verbose;
	@Option(names = {"-c", "--color"}, description = "color the output, makes it look fabulous")
	boolean color;
	@Option(names = {"-f", "--file"}, description = "[NOT tested]file to read the maze from", paramLabel =  "FILE")
	File f_intput;
	@Option(names = {"-o", "--output"}, description = "[NOT tested] file to output the maze to", paramLabel = "FILE")
	File f_output;
	// @Option(names = {"-t", "--time"}, description = "[NOT IMPLEMENTED] time took to generate the maze", paramLabel = "FILE")
	// int time;

	public static void main(String[] args) {
		System.exit(new CommandLine(new Labythin()).execute(args));
	}

	@Override
	public void run() {
		Instant start = Instant.now();
		Maze maze;

		check_args();
		try (Printer printer = new Printer(f_output, color, verbose, step)) {
			this.generator = new MazeGenerator(printer, (width), (height));
			maze = this.generator.createMaze();
			printer.print(MessageLevel.INFO, toString());
			this.generator.generate(maze, mode);
			printer.display(maze);
		} catch (IOException e) {
			System.out.println(e);
		}
		// Instant finish = Instant.now();
		// System.out.println(Duration.between(start, finish).toMillis());
	}

	private void check_args() {
		if (width <= 0 || height <= 0)
			throw new ParameterException(spec.commandLine(),
				"Wrong parameter : Both dimention of the maze should be positiv numbers.");
		if (f_intput != null && !f_intput.exists())
			throw new ParameterException(spec.commandLine(),
				"Wrong parameter : The imput file doesn't existe.");
		else if (checkFile(f_intput) == FILE_STATE.BAD_FILE)
			throw new ParameterException(spec.commandLine(),
				"File issue : file " + f_intput + " isn't well formatted."); //maybe add the display of an example here | ill' depend of the format
		if (f_output != null && !f_output.canWrite())
			throw new ParameterException(spec.commandLine(),
				"File issue : file " + f_output + " already exist and the Labythin can't overwrite it.");
		if (f_intput != null) {
			if (!f_intput.exists())
				throw new ParameterException(spec.commandLine(),
					"File issue : file " + f_intput + " intput file doesn't exists");
			if (!f_intput.canRead())
				throw new ParameterException(spec.commandLine(),
					"File issue : file " + f_intput + " existe but the Labythin can't read it.");
		}
	}

	private static FILE_STATE checkFile(File file) {
		//hashmap ? 
		//formatage
		return (FILE_STATE.GOOD_FILE);
	}

	@Override
	public String toString() {
		return (
			"width   :\t" + width + "\n" +
			"height  :\t" + height + "\n" +
			"verbose :\t" + verbose + "\n" +
			"color   :\t" + color + "\n" +
			"f_intput : " + f_intput + "\n" +
			"f_output : " + f_output + "\n" +
			"mode : \t" + mode + "\n" +
			"step :\t" + step
			);
	}
}

// import picocli.CommandLine.Model.OptionSpec;
// import picocli.CommandLine.Model.PositionalParamSpec;
