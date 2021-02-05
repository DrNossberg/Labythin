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

class Printer {
	File file;
	BufferedWriter writer;
	boolean color;
	Map<MazeElement, String> colors_dic;

	public Printer(File file, boolean color) {
		this.file = file;
		this.color = color;
		this.colors_dic = new Hashtable<MazeElement, String>();
		this.colors_dic.put(MazeElement.WALL, "red");
		this.colors_dic.put(MazeElement.PATH, "green");
		// instead of red|green, we can put alot of things here, see 
		// last comment for more infos -> option...?
	}

	public void display(Maze maze) {
		try {
			if (file == null)
				this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
			else
				this.writer = new BufferedWriter(new FileWriter(file)); //needs to check if we can write in the file
			if (color)
				display_colored(maze);
			else
				display_simple(maze);
			this.writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void display_colored(Maze maze) throws IOException {
		StringBuffer strbuf = new StringBuffer();
		MazeElement onHold = MazeElement.UNDEFINED;
		MazeElement node;

		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++, strbuf.append(onHold.getChar())) {
				if ((node = maze.getElement(x, y)) != onHold) {
					this.writer.write(Ansi.AUTO.string("@|" + this.colors_dic.get(onHold) + ' ' + strbuf + "|@"));
					onHold = node;
				}
			}
			this.writer.write('\n');
		}
	}

	private void display_simple(Maze maze) throws IOException {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++)
				this.writer.write(maze.getElement(x, y).getChar());
			this.writer.write('\n');
		}
	}
}


// String str = Ansi.AUTO.string("@|bold,green,underline Hello, colored world!|@");
// System.out.println(str);
// @|bg(0;5;0) text with red=0, green=5, blue=0 background|@, or 
// @|fg(46) the same color by index, as foreground color|@.
