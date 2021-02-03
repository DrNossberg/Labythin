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
	private int height; // DCL52-J. Do not declare more then one variable per declaration
	private char[][] tab;

	public Maze(int width, int height, char default_char) {
		this.width = width;
		this.height = height;

		this.tab = new char[this.height][this.width];
		for (int y = 0; y < this.height; y++) // row by row
				Arrays.fill(tab[y], default_char);
	}

	/**
	 * Change a gived position
	 * @param node
	 */
	public void change(Point node, char value) {
		this.tab[node.y][node.x] = value; //
	}

	public boolean isWall(Point node) {
		return (this.getValue(node.x, node.y) == MazeElement.WALL_VISITED.getState() || 
				this.getValue(node.x, node.y) == MazeElement.WALL_UNVISITED.getState());
	}

	public boolean isUnvisited(Point node) {
		return (this.getValue(node.x, node.y) == MazeElement.PATH_UNVISITED.getState() ||
				this.getValue(node.x, node.y) == MazeElement.WALL_UNVISITED.getState());
	}

	public char getValue(int x, int y) { return this.tab[y][x]; }
	public int getWidth()  {    return this.width;  }
	public int getHeight() {    return this.height; }
}
