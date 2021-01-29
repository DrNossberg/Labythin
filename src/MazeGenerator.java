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

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

class MazeGenerator {
	private final Random rand;
	private ArrayList<Point> activeSet;
	private ArrayList<Point> visitedNodes;
	private Point activeNode; 				//TODO à déplacer dans le maze ? à voir 
	private Point neighbor;


	public MazeGenerator() {
		this.rand = new Random();
		this.activeSet = new ArrayList<Point>();
		this.visitedNodes = new ArrayList<Point>();
		this.activeNode =  new Point(0, 0);
			//IDEA 0,0 pourrait changer, à passer en arg à la construction du maze. à voir avec le parsing
	}

	public Maze createMaze(int width, int height) {
		Maze maze = new Maze(width, height, MazeElement.WALL_UNVISITED.getState());  //TODO '#' shouldn't be hard coded. Const, or interface maybe ?
		
		return (maze);
	}

	/**
	 * 
	 * @param lab
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

	public void generate(Maze maze) {
		this.generate(maze, 1);
	}

	public void generate(Maze maze, int mode) {
		// Originel node
		this.activeSet.add(this.activeNode);
		//this.visitedNodes.add(this.activeNode);
		maze.change(this.activeNode, MazeElement.PATH_VISITED.getState());

		// Main loop of the generator
		while (!this.activeSet.isEmpty()) {
			// Select node from active set
			try {
				this.activeNode = this.activeSet.get(pickActiveNode(mode));
			} catch (Exception e) {
				System.out.println(e);
			}
			maze.change(this.activeNode, MazeElement.PATH_VISITED.getState()); //TODO hardcoded

			// Choose a random neighbor node, if none available remove active node from active set
			if ((neighbor = pickRandomNeighbor(maze)).equals(new Point(-1, -1))) { // no neighbor
				this.activeSet.remove(this.activeNode);
				continue;
			} else {
				// inbetween node
				maze.change(new Point(
					(this.activeNode.x + neighbor.x) / 2,
					(this.activeNode.y + neighbor.y) / 2), 
					MazeElement.PATH_VISITED.getState());
				//TODO simplify
				// visitedNodes.add(inBetweenNode); //non ??
				this.activeSet.add(neighbor);
			}

			// neighbor
			// if (!visitedNodes.contains(neighbor)) //? NOT IN, as known as the worst thing *ever*
			//this.visitedNodes.add(neighbor);
			maze.change(neighbor, MazeElement.PATH_VISITED.getState());
		}

		// Complete borders for pair maze
		// TODO maybe do a method ?
		if (maze.getHeight() % 2 == 0) {
			for (int i = 1; i < maze.getWidth()-1; i++) {
				if (Math.random() > .5)
					maze.change(new Point(i, maze.getHeight()-1), MazeElement.PATH_VISITED.getState());
			}
		} 

		if (maze.getWidth() % 2 == 0) {
			for (int i = 1; i < maze.getHeight()-1; i++) {
				if (Math.random() > .5)
					maze.change(new Point(maze.getWidth()-1, i), MazeElement.PATH_VISITED.getState());
			}
		} 

		// Control the exit of the maze
		if (!maze.isPath(new Point(maze.getWidth()-1, maze.getHeight()-1)))
			maze.change(new Point(maze.getWidth()-1, maze.getHeight()-1), MazeElement.PATH_VISITED.getState());
		
		if (maze.isWall(new Point(maze.getWidth()-1, maze.getHeight()-2)) &&
			maze.isWall(new Point(maze.getWidth()-2, maze.getHeight()-1))) {
				if (Math.random() > .5)
					maze.change(new Point(maze.getWidth()-1, maze.getHeight()-2), MazeElement.PATH_VISITED.getState());
				else
					maze.change(new Point(maze.getWidth()-2, maze.getHeight()-1), MazeElement.PATH_VISITED.getState());
			}
			
	}

	private int pickActiveNode(int mode) throws Exception {
		switch (mode) {
			case 1:
				return(this.activeSet.size() - 1);
			case 2:
				return(this.rand.nextInt(this.activeSet.size()));
			case 3:
				return(0);
			case 4:
				if (Math.random() > 0.5) 
					return(pickActiveNode(1));
				return(pickActiveNode(2));
			default:
				throw new Exception("Unknown mode");
		}
	}

	// create and return an array with all unvisited neighbors
	private ArrayList<Point> findUnvisitedNeighbors(Maze maze) {
		ArrayList<Point> unvisitedNeighbors = new ArrayList<Point>();
		int step[][] = {{2, 0}, {0, 2}, {- 2, 0}, {0, - 2}};

		for (int item[] : step) {
			int x = this.activeNode.x + item[0];
			int y = this.activeNode.y + item[1];
			if ((x >= 0 && x < maze.getWidth()) && 
				(y >= 0 &&  y < maze.getHeight()) &&
				maze.isUnvisited(new Point(x,y)))
				unvisitedNeighbors.add(new Point(x, y));
		}
		return (unvisitedNeighbors);
	}

	/**
	 * Select a random unvisited neighbor of the active Node
	 * @param visitedNodes array of already visited nodes
	 * @return random neighbor (takes into account if none exists)
	 */
	public Point pickRandomNeighbor(Maze maze) {
		ArrayList<Point> unvisitedNeighbors = this.findUnvisitedNeighbors(maze);

		// define selected unvisited neighbor
		if (unvisitedNeighbors.size() > 0)
			return (unvisitedNeighbors.get(this.rand.nextInt(unvisitedNeighbors.size())));
		return (new Point(-1, -1));
	}
	// At this point do we really need this function ? 
}
