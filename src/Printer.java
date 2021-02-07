/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Project Name
** Author :
**    Antoine Orion
** File description :
**    Display/print the mazes on the standard output or to a file 
*/

// 0 = force 
// 1 = step 

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import picocli.CommandLine.Help.Ansi;

class Printer {
	File file;
	BufferedWriter writer;
	boolean verbose;
	boolean color;
	Map<Character, String> colors_dic;
	int stepping;
	static int step_bro = 0;

	public Printer(File file, boolean color, boolean verbose, int step) {
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

	    try {
			if (file == null)
				this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
			else
				this.writer = new BufferedWriter(new FileWriter(file)); //needs to check if we can write in the file
		} catch (IOException e) {
			print(MessageLevel.FATAL, e.getMessage());
		}
	}

	public void print(MessageLevel level, String message) {
		if (verbose)
			System.out.println(message);
	}

	// public void print(Maze maze) {
	public void display(Maze maze) {
		try {
			stepDisplay(maze);
		} catch (IOException e) {
			print(MessageLevel.FATAL, e.getMessage());
		}
	}

	public void stepDisplay(Maze maze) throws IOException {
		char c = '\0';

		this.print(MessageLevel.FATAL, this.stepping + " step bro : " + this.step_bro);
		if (this.stepping < 0)
			return;
		if (this.stepping > 0 && step_bro < this.stepping) {
			this.step_bro += 1;
			return;
		}
		this.step_bro = 0;
		if (color) {
			displayColor(maze, stepping > 0);
			// displayColorCode();
		} else
			displayBlakAndWhite(maze, stepping > 0);
		this.writer.flush();
		System.out.print("------------------\nPresse [Enter] to continue or insert a new step number : ");
		do {
			c = (char) System.in.read();
			print(MessageLevel.DEBUG, "read : " + c);
		} while (c != '\n'); //more options to come here
						// getter ? or function to get user_intput ? 
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
	}

	private void displayBlakAndWhite(Maze maze, boolean verbose) throws IOException {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				if (verbose)
					this.writer.write(maze.getValue(x, y));
				else
					this.writer.write(maze.getElement(x, y).getChar());
			}
			this.writer.write('\n');
		}
	}
}


// String str = Ansi.AUTO.string("@|bold,green,underline Hello, colored world!|@");
// System.out.println(str);
// @|bg(0;5;0) text with red=0, green=5, blue=0 background|@, or 
// @|fg(46) the same color by index, as foreground color|@.
