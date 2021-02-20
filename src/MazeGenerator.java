/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    MazeGenerator - object able to create and modify maze depending their
**    size and the desired algorithme/output
*/

import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

class MazeGenerator {
	private Random rand = new Random();
	private Printer printer;
	private Point activeNode; 				
		//TODO à déplacer dans le maze ? à voir 
	private Point neighbor;
	private int width;
	private int height;

	public MazeGenerator(Printer printer, int width, int height) {
		this.activeNode =  new Point(0, 0);
			//IDEA 0,0 pourrait changer, à passer en arg à la construction du maze. à voir avec le parsing
		this.width = width;
		this.height = height;
		this.printer = printer;
	}

	public Maze createMaze() {
		return (new Maze(this.width, this.height, MazeElement.WALL_UNVISITED));
	}

	/**
	 * 
	 * @param maze
	 * @param mode 1: Recursive Backtracker (newest) 2: Prism (random) 3: oldest 4:
	 *             50/50
	 * @throws Exception
	 *
	 *  /**
		 * 1. Randomly select a cell and add it to the selected cells list.
		 * 2. Select a cell from the selected cells list. Then choose a cell adjacent to it that hasn’t been visited. 
		 *    	Add the adjacent cell to the selected list and remove the edge between the two cells.
		 * 3. If there are no unvisited neighbors
		 *    3.a. Remove from the selected list
		 * 4. Repeat 2-3 until the selected list is empty.
		 *
		 *
		 * Choose first activeNode
		 * Run the maze generation
		 */

	public double generate(Maze maze, Mode mode) {
		Instant start = Instant.now();

		this.printer.print(MessageLevel.INFO, "Generating the maze...");
		if (mode == Mode.NONE)
			this.do_recursive(maze, this.activeNode);
		else
			this.do_iterative(maze, mode);
		
		this.polish(maze);
		this.completeBorder(maze, maze.getHeight(), maze.getWidth(), 0);
		this.completeBorder(maze, maze.getWidth(),  maze.getHeight(), 1);
		this.controlExit(maze);
		return (Duration.between(start, Instant.now()).toMillis());
	}


	/**
	 * Recursive algorithm for Recursive Backtracker mode
	 */
	public void do_recursive(Maze maze, Point activeNode) {
		Point next = null;

		this.printer.stepDisplay(maze);
		maze.change(activeNode);
		while (!(next = this.pickRandomNeighbor(maze, activeNode)).equals(new Point(-1, -1))) {
			this.printer.stepDisplay(maze);
			maze.change((activeNode.x + next.x) / 2,
						(activeNode.y + next.y) / 2, MazeElement.PATH);
			do_recursive(maze, next);
			this.printer.stepDisplay(maze);
			
		}
		maze.change(activeNode, MazeElement.PATH);
		this.printer.stepDisplay(maze);
	}

	/**
	 * Iterative algorithms
	 */
	public void do_iterative(Maze maze, Mode mode) {
		ArrayList<Point> activeSet = new ArrayList<Point>();

		// Originel node
		activeSet.add(this.activeNode);
		this.printer.stepDisplay(maze);
		maze.change(this.activeNode);
		while (!activeSet.isEmpty()) {
			this.printer.stepDisplay(maze);
			// Select node from active set
			try {
				this.activeNode = activeSet.get(pickActiveNode(mode, activeSet));
			} catch (Exception e) {
				this.printer.print(MessageLevel.FATAL, e.getMessage());
			}
			maze.change(activeNode);
			// Choose a random neighbor node, if none available remove active node from active set
			if ((neighbor = pickRandomNeighbor(maze, this.activeNode)).equals(new Point(-1, -1))) { // no neighbor
				activeSet.remove(this.activeNode);
				continue;
			} else {
				// inbetween node
				maze.change((this.activeNode.x + neighbor.x) / 2,
							(this.activeNode.y + neighbor.y) / 2);
				activeSet.add(neighbor);
			}
			// neighbor
			maze.change(neighbor);
		}
	}	


	/**
	 * Control if an exit for the maze exists
	 * If none exists, create one
	 */
	public void controlExit(Maze maze) {
		int x = maze.getWidth() - 1;
		int y = maze.getHeight() - 1;

		maze.change(x, y, MazeElement.PATH);
		// Make path to one of node at the right bottom's direct neighbors
		if (maze.isWall(x, y - 1) && maze.isWall(x - 1, y))
			if (Math.random() > .5)
				maze.change(x, y - 1, MazeElement.PATH);
			else
				maze.change(x - 1, y, MazeElement.PATH);
	}

	/**
	 * Complete the right or bottom border
	 * @param maze 
	 * @param currentBorder size of side to check
	 * @param borderLength size of the border to complete
	 * @param vertical 1 if working on vertical, 0 if not
	 */
	public void completeBorder(Maze maze, int currentBorder, int borderLength, int vertical) {
		for (int i = 1; i < borderLength - 1 && currentBorder % 2 == 0; i++)
			if (Math.random() > .5) {
				if (vertical == 1 && !maze.isPath(currentBorder - 2, i)) { // working on right side
					maze.change(currentBorder - 1, i);
					maze.change(currentBorder - 2, i);
				} 
				else 
					if (!maze.isPath(i, currentBorder - 2)) { // working on bottom side
						maze.change(i, currentBorder - 1);
						maze.change(i, currentBorder - 2);
					} 
			}	
	}

	/**
	 * Pick an active Node from the active set regarding the mode
	 * @param mode for choosing the active node
	 * @return index of the active set where to find the active node
	 */
	private int pickActiveNode(Mode mode, ArrayList<Point> activeSet) throws Exception {
		switch (mode) {
			case RECURSIVE_BACKTRACKER :
				return(activeSet.size() - 1);
			case PRISM :
				return(this.rand.nextInt(activeSet.size()));
			case OLDEST :
				return(0);
			case FIFTY_FITY :
				if (Math.random() > 0.5) 
					return(pickActiveNode(Mode.RECURSIVE_BACKTRACKER, activeSet));
				return(pickActiveNode(Mode.PRISM, activeSet));
			default:
				throw new Exception("Issue when picking active node : mode unknow");
		}
	}

	/**
	 * Select a random unvisited neighbor of the active Node
	 * @param maze 
	 * @return random neighbor (takes into account if none exists)
	 */
	public Point pickRandomNeighbor(Maze maze, Point activeN) {
		ArrayList<Point> unvisitedNeighbors = new ArrayList<Point>();
		int step[][] = {{2, 0}, {0, 2}, {- 2, 0}, {0, - 2}};

		for (int item[] : step) {
			int x = activeN.x + item[0];
			int y = activeN.y + item[1];
			if ((x >= 0 && x < maze.getWidth()) && 
				(y >= 0 &&  y < maze.getHeight())
				&& maze.isUnvisited(x, y))
				unvisitedNeighbors.add(new Point(x, y));
		}
		// define selected unvisited neighbor
		if (unvisitedNeighbors.size() > 0)
			return (unvisitedNeighbors.get(this.rand.nextInt(unvisitedNeighbors.size())));
		return (new Point(-1, -1));
	}

	private void polish(Maze maze) {
		int wallCount;

		this.printer.print(MessageLevel.DEBUG, "polish : \n");
		for (int y = 0; y < this.height ; y++) {
			wallCount = 0;
			for (int x = 0; x < this.width ; x++)
				if (maze.isWall(x, y))
					wallCount++;
			this.printer.print(MessageLevel.DEBUG, " line nbr :" + y +" prcentOfWall : " + ((double)  ((double) wallCount / (double) this.width)));
			addPath(maze, y, (double) ((double) wallCount / (double) this.width));
		}
	}

	/**
	 * The value of the node to be chaned isn't check, witch mean that we don't know
	 * if the changed node was a wall or not. This is inteded as a try to not interfere with the
	 * random of the function.
	 */
	private void addPath(Maze maze, int y, double prcentOfWall) {
		if (prcentOfWall < 0.5)
			return;
		for (; prcentOfWall >= .5; prcentOfWall -= (double) 1 / (double) this.width) {
			this.printer.print(MessageLevel.DEBUG, "On node changed (or not)");
			maze.change(this.rand.nextInt(width), y, MazeElement.PATH); 
		}
	}
}