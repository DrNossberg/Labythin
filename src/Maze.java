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
		this.tab[y][x] = value.getChar(); // PAS TOUCHE ICI !
	}

	public void change(int x, int y) {
		change(x, y, MazeElement.PATH_VISITED);
	}

	public void change(Point node, MazeElement value) {
		change(node.x, node.y, value);
	}

	public void change(Point node) {
		change(node, MazeElement.PATH_VISITED);
	}

	public boolean isWall(Point node) {
		return (isWall(node.x, node.y));
	}

	public boolean isWall(int x, int y) {
		char temp = this.getValue(x, y);
		return (temp == MazeElement.WALL_VISITED.getChar() || 
				temp == MazeElement.WALL_UNVISITED.getChar());
	}

	public boolean isUnvisited(int x, int y) {
		return (this.getValue(x, y) == MazeElement.PATH_UNVISITED.getChar() ||
				this.getValue(x, y) == MazeElement.WALL_UNVISITED.getChar());
	}

	public char getValue(int x, int y) { return this.tab[y][x]; }
	public int getWidth()  {    return this.width;  }
	public int getHeight() {    return this.height; }
}
