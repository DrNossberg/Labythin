/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    Implementation of the Labyrinthe. Contain every majors functions to create one
*/

import java.awt.Point;
import java.util.Arrays;

public class Maze {
	private int width;
	private int height;
	private char[][] tab;

	public Maze(int width, int height, MazeElement default_element) {
		this.width = width;
		this.height = height;
		this.tab = new char[this.height][this.width];
		for (int y = 0; y < this.height; y++) // row by row
				Arrays.fill(tab[y], default_element.getChar());
	}

	/**
	 * Change a gived position
	 * @param node
	 */

	public void change(int x, int y, MazeElement value) {
		this.tab[x][y] = value.getChar();
	}

	public void change(int x, int y) {
		change(x, y, MazeElement.PATH_UNVISITED);
	}

	public void change(Point node, MazeElement value) {
		change(node.x, node.y, MazeElement.PATH_UNVISITED);
	}

	public void change(Point node) {
		change(node, MazeElement.PATH_UNVISITED);
	}

	public boolean isPath(Point node) {
		return (isWall(node.x, node.y));
	}

	public boolean isPath(int width, int height) {
		return (getElement(width, height) == MazeElement.PATH);
	}

	public boolean isWall(Point node) {
		return (isWall(node.x, node.y));
	}

	public boolean isWall(int width, int height) {
		return (getElement(width, height) == MazeElement.WALL);		
	}

	public boolean isUnvisited(Point node) {
		return (this.getValue(node.x, node.y) == MazeElement.PATH_UNVISITED.getChar() ||
				this.getValue(node.x, node.y) == MazeElement.WALL_UNVISITED.getChar());
	}

	public char getValue(int x, int y) 	{ return this.tab[y][x]; }
	public int 	getWidth()  			{ return this.width; }
	public int 	getHeight() 			{ return this.height; }

	public MazeElement getElement(int width, int height) {
		char temp = this.getValue(width, height);

		if (temp == MazeElement.WALL_VISITED.getChar() || 
			temp == MazeElement.WALL_UNVISITED.getChar());
			return (MazeElement.WALL);
		return (MazeElement.PATH);
	}
}
