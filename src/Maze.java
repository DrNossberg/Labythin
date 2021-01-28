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

import java.util.Arrays;
import java.awt.Point;

public class Maze {
	private int width;
	private int height; // DCL52-J. Do not declare more then one variable per declaration
	char[][] tab;

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

	public void display() { // color option for colored output ?
		for (int y = 0; y < this.height; y++) { // row by row
			for (int x = 0; x < this.width; x++) { // column by column
				System.out.print(this.tab[y][x]);
			}
			System.out.println("");
		}
	}

	public int getWidth()  {    return this.width;  }
	public int getHeight() {    return this.height; }
}
