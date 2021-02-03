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
import java.util.Scanner;

// import picocli.CommandLine.Model.OptionSpec;
// import picocli.CommandLine.Model.PositionalParamSpec;

@Command(
		name = "Labythin",
		version = 	{"Labythin 2021",
					"Picocli " + picocli.CommandLine.VERSION,
					"JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
					"OS: ${os.name} ${os.version} ${os.arch}"},
		mixinStandardHelpOptions = true,
		sortOptions = false,
		headerHeading = "%n@|bold,underline Name:|@%n" + //+ 
				"@|magenta '|@@|white ##|@@|magenta ::::::::::'|@@|white ###|@@|magenta ::::'|@@|white ########|@@|magenta ::'|@@|white ##|@@|magenta :::'|@@|white ##|@@|magenta :'|@@|white ########|@@|magenta :'|@@|white ##|@@|magenta ::::'|@@|white ##|@@|magenta :'|@@|white ####|@@|magenta :'|@@|white ##|@@|magenta :::|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::::'|@@|white ##|@@|black  |@@|white ##|@@|magenta :::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta :'|@@|white ##|@@|magenta ::...|@@|black  |@@|white ##|@@|magenta ..::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ###|@@|magenta ::|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta ::::::::'|@@|white ##|@@|magenta :.|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::.|@@|black  |@@|white ####|@@|magenta ::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ####|@@|magenta :|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::'|@@|white ##|@@|magenta :::.|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ########|@@|magenta ::::.|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white #########|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|black  |@@|white ##|@@|black  |@@|white ##|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white #########|@@|magenta :|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta .|@@|black  |@@|white ####|@@|magenta :%n|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ....|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta ::|@@|black  |@@|white ##|@@|magenta :.|@@|black  |@@|white ###|@@|magenta :%n|@@|black  |@@|white ########|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :|@@|black  |@@|white ########|@@|magenta :::::|@@|black  |@@|white ##|@@|magenta :::::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta ::::|@@|black  |@@|white ##|@@|magenta :'|@@|white ####|@@|magenta :|@@|black  |@@|white ##|@@|magenta ::.|@@|black  |@@|white ##|@@|magenta :%n........::..:::::..::........::::::..::::::::..:::::..:::::..::....::..::::..::%n|@",
		header = "%n%n@|bold,underline Usage:|@",
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

	@Spec CommandSpec spec;

	@Parameters(paramLabel = "width",  defaultValue = "50", description = "width  of the maze to create. default : ${DEFAULT-VALUE}")
	double width;
	@Parameters(paramLabel = "height", defaultValue = "50", description = "height of the maze to create. default : ${DEFAULT-VALUE}")
	double height;
	@Option(names = {"-v", "--verbose"}, description = "verbose mode of display")
	boolean verbose;	
	@Option(names = {"-c", "--color"}, description = "color the output, makes it look fabulous")
	boolean color;
	@Option(names = {"-f", "--file"}, description = "file to read the maze from", paramLabel =  "FILE")
	File f_intput;
	@Option(names = {"-o", "--output"}, description = "file to output the maze to", paramLabel = "FILE")
	File f_output;

	public static void main(String[] args) {
		System.exit(new CommandLine(new Labythin()).execute(args));
	}

	@Override
	public void run() {
		validate();
		Printer printer = new Printer(f_output, color);
		MazeGenerator generator = new MazeGenerator(((int) width), ((int) height));
		Maze maze = generator.createMaze();
		generator.generate(maze);

		printer.display(maze);
	}

	private void validate() {
		char c = '\0';

		if (width <= 0 || height <= 0)
			throw new ParameterException(spec.commandLine(),
				"Wrong parameter : Both dimention of the maze should be positiv numbers.");
		if (f_intput != null && !f_intput.exists())
			throw new ParameterException(spec.commandLine(),
				"Wrong parameter : The imput file doesn't existe.");
		else if (checkFile(f_intput) == FILE_STATE.BAD_FILE)
			throw new ParameterException(spec.commandLine(),
				"File issue : file " + f_intput + " isn't well formatted."); //maybe add the display of an example here | ill' depend of the format
		if (f_output != null && ! f_output.exists()) {
			do {
				System.out.println("Warning : File " + f_output + " already exist. Do you wants yo override ? y/N: ");
				try {
					c = (char) System.in.read();
				} catch (IOException e) {
					throw new ParameterException(spec.commandLine(),
						"Issue when reading the standard intput.");
				}
				if (c == 'n' || c == 'N')
					throw new ParameterException(spec.commandLine(), "Stopped : " + f_output );
				if (c != 'y' || c != 'Y')
					return;
			} while (c != 'y' || c != 'Y' || c != 'n' || c != 'N');
		}
	}

	private static FILE_STATE checkFile(File file) {
		//hashmap ? 
		//formatage
		return (FILE_STATE.GOOD_FILE);
	}

	private void dump() {
		System.out.println(
			"width   :\t" + width + "\n" +
			"height  :\t" + height + "\n" +
			"verbose :\t" + verbose + "\n" +
			"color   :\t" + color + "\n" +
			"f_intput : " + f_intput 
			);
	}
}
