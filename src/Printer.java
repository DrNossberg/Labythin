/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Project Name
** Author :
**    Antoine Orion
** File description :
**    Display/print the mazes on the standard output or to a file 
*/

import java.io.File;
import java.awt.Point;

class Printer {

	File file;
	boolean color;

	public Printer(File file, boolean color) {
		this.file = file;
		this.color = color;
	}

	public void display(Maze maze) {
	// 	if (file != null)
		for (int y = 0; y < maze.getHeight(); y++) { // row by row
			for (int x = 0; x < maze.getWidth(); x++) { // column by column
				if (maze.isWall(x, y))
					System.out.print("#");
				else
					System.out.print(".");
			}
			System.out.println("");
		}
	}
}

// import picocli.CommandLine;

// import picocli.CommandLine.Help.Ansi;
// // ...
// String str = Ansi.AUTO.string("@|bold,green,underline Hello, colored world!|@");
// System.out.println(str);
// , @|bg(0;5;0) text with red=0, green=5, blue=0 background|@, or @|fg(46) the same color by index, as foreground color|@.

// 4.2.1. Single Parameter Type Converters
//  to instantly convert to maze -> meh-size option
