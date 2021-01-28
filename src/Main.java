/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Main file of the 
*/

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

public class Main {
	static final int DEFAULT_NODE = -42;

	// arrayList.add() -> append to the end of the list
	public static void main(String[] args) throws Exception {

		MazeGenerator generator = new MazeGenerator();

		// Create labyrinth filled with only walls
		Maze maze = generator.createMaze(args[0], args[1]); 
			// args[0] et [1] changerons à l'ajout de picocli (le parsing)
			// et seront aussi formatés à ce moment là. Les erreur y seront gérés

		// Run the maze generator
		generator.generate(maze);
		maze.display();
	}
}
