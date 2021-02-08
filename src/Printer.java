/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Project Name
** Author :
**    Antoine Orion
** File description :
**    Display/print the mazes on the standard output or to a file 
*/

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import picocli.CommandLine.Help.Ansi;

class Printer implements AutoCloseable {
	File file;
	BufferedWriter writer;
	boolean verbose;
	boolean color;
	Map<Character, String> colors_dic;
	int stepping;
	static int step_bro = 0;

	public Printer(File file, boolean color, boolean verbose, int step) throws IOException {
		this.file = file;
		this.verbose = verbose;
		this.color = color;
		this.colors_dic = new Hashtable<Character, String>();
		this.stepping = step;
		this.step_bro = 0;

		this.colors_dic.put(MazeElement.WALL.getChar(),				"white");
	    this.colors_dic.put(MazeElement.WALL_UNVISITED.getChar(),	"green");
	    this.colors_dic.put(MazeElement.WALL_VISITED.getChar(),		"white");
	    this.colors_dic.put(MazeElement.PATH.getChar(),				"bold,magenta");
	    this.colors_dic.put(MazeElement.PATH_UNVISITED.getChar(),	"red");
	    this.colors_dic.put(MazeElement.PATH_VISITED.getChar(),		"blue");
		// instead of red|green, we can put alot of things here, see 
		// last comment for more infos -> option...?

		if (file == null)
			this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
		else
			this.writer = new BufferedWriter(new FileWriter(file)); //needs to check if we can write in the file
	}

	public void print(MessageLevel level, String message) {
		if (verbose || level == MessageLevel.IMPORTANT || level == MessageLevel.FATAL)
			System.out.println(message);
	}

	// public void print(Maze maze) {
	public void display(Maze maze) {
		try {
			if (color) {
				displayColor(maze, stepping > 0);
				// displayColorCode();
			} else {
				displayBlakAndWhite(maze, stepping > 0);
			}
		} catch (IOException e) {
			print(MessageLevel.FATAL, e.getMessage());
		}
		// 	stepDisplay(maze);
	}

	public void stepDisplay(Maze maze) {
		char c = '\0';

		if (this.stepping <= 0 || step_bro < this.stepping) {
			this.step_bro += 1;
			return;
		}
		this.print(MessageLevel.INFO, "displayed every " + this.stepping + " loop.");
		this.print(MessageLevel.IMPORTANT, "------------------\n");
		this.step_bro = 0;
		this.display(maze);
		try {
			this.print(MessageLevel.IMPORTANT, "------------------\n");
			this.print(MessageLevel.IMPORTANT, "Presse [Enter] to continue or insert a new step number : ");
			do {
				c = (char) System.in.read();
				print(MessageLevel.DEBUG, "read : " + c);
			} while (c != '\n'); //turn into reader ?
		} catch (IOException e) {
			this.print(MessageLevel.FATAL, "brhu");
		}

	}

	private void displayColor(Maze maze, boolean verbose) throws IOException {
		StringBuffer strbuf = new StringBuffer();
		char onHold = maze.getElement(0, 0).getChar();
		char node = onHold;

		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++, strbuf.append(node)) {
				node = verbose ? maze.getValue(x, y) : maze.getElement(x, y).getChar();
				if (node != onHold) {
					this.writer.write(Ansi.AUTO.string("@|" + this.colors_dic.get(onHold) + ' ' + strbuf + "|@"));
					strbuf.setLength(0);
					onHold = node;
				}
			}
			strbuf.append('\n');
		}
		this.writer.write(Ansi.AUTO.string("@|" + this.colors_dic.get(onHold) + ' ' + strbuf + "|@"));
		this.writer.flush();
	}

	private void displayBlakAndWhite(Maze maze, boolean verbose) throws IOException {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				if (verbose)
					this.writer.write(maze.getValue(x, y));
				else {
					this.writer.write(maze.getElement(x, y).getChar());
				}
			}
			this.writer.write('\n');
		}
		this.writer.flush();
	}

	@Override
	public void close() {
		try {
			this.writer.close();
			System.out.println("ressources fully closed");
		} catch (Exception e) {
			this.print(MessageLevel.FATAL, "Printer failed to close writer normally");
		}
	}
}


// String str = Ansi.AUTO.string("@|bold,green,underline Hello, colored world!|@");
// System.out.println(str);
// @|bg(0;5;0) text with red=0, green=5, blue=0 background|@, or 
// @|fg(46) the same color by index, as foreground color|@.
