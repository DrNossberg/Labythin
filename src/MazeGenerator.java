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


class MazeGenerator {
	public static final Random R = new Random();
	ArrayList<Point2D> activeSet;
	ArrayList<Point2D> visitedNodes;
	Point2D activeNode; 				//TODO à déplacer dans le maze ? à voir 
	Point2D neighbor;


	public MazeGenerator() {
		this.activeSet = new ArrayList<Point2D>();
		this.visitedNodes = new ArrayList<Point2D>();
		this.activeNode =  new Point2D(0, 0);
			//IDEA 0,0 pourrait changer, à passer en arg à la construction du maze. à voir avec le parsing
		this.activeSet.add(this.activeNode);

	}

	public Maze createMaze(int width, int height) {
		Maze maze = new Maze(width, height, '#');  //TODO '#' shouldn't be hard coded. Const, or interface maybe ?
		
		maze.change(this.activeNode, '.'); //TODO same  /|\
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
	public static void generate(Maze lab) {
		this.generate(maze, 1);
	}

	public static void generate(Maze lab, int mode) throws Exception {
		while (!this.activeSet.isEmpty()) {
			// Select node from active set
			switch (mode) {
				case 1:
					activeNode = this.activeSet.get(this.activeSet.size()-1);
					break;
				case 2:
					activeNode = this.activeSet.get(Main.R.nextInt(this.activeSet.size()));
					break;
				case 3:
					activeNode = this.activeSet.get(0);
					break;
				case 4:
					if (Math.random() > 0.5) 
						activeNode = this.activeSet.get(this.activeSet.size()-1);
					else
						activeNode = this.activeSet.get(Main.R.nextInt(this.activeSet.size()));
					break;
				default:
					throw new Exception("Unknown mode");
			}
			if (!visitedNodes.contains(activeNode))
				visitedNodes.add(activeNode);

			// Choose a random neighbor node, if none available remove active node from active set
			neighbor = lab.randomNeighbor(visitedNodes, activeNode);
	
			/**
			 * A la base j'ai fait ça, mais ça bloque quand le labyrinthe est pair
			 */
			/*
			* if (neighbor.getX() == Main.DEFAULT_NODE && neighbor.getY() == Main.DEFAULT_NODE) { // no neighbor
			*     this.activeSet.remove(activeNode);
			* } else {
			*     lab.changeToPath(new Point2D.Double(
			*         (activeNode.getX() + neighbor.getX()) / 2,
			*         (activeNode.getY() + neighbor.getY()) / 2
			*     ));
			*
			*     // neighbor
			*     lab.changeToPath(neighbor);
			*     this.activeSet.add(neighbor);
			*     visitedNodes.add(neighbor);
			* }
			*/


			if (neighbor.getX() == Main.DEFAULT_NODE && neighbor.getY() == Main.DEFAULT_NODE) { // no neighbor
				this.activeSet.remove(activeNode);
			} else {
				//visitedNodes.add(neighbor); // j'ai essayé d'ajouter le fictif dans le visite nodes ici
				if (!visitedNodes.contains(neighbor))
					visitedNodes.add(neighbor);
				if (neighbor.getX() < 0)
					neighbor = new Point2D.Double(0, neighbor.getY());
				else if (neighbor.getX() > lab.getWidth()-1)
					neighbor = new Point2D.Double(lab.getWidth()-1, neighbor.getY());
				else if (neighbor.getY() < 0)
					neighbor = new Point2D.Double(neighbor.getX(), 0);
				else if (neighbor.getY() > lab.getHeight()-1)
					neighbor = new Point2D.Double(neighbor.getX(), lab.getHeight()-1);
				else {
					// inbetween node
					Point2D inBetweenNode = new Point2D.Double(
						(activeNode.getX() + neighbor.getX()) / 2,
						(activeNode.getY() + neighbor.getY()) / 2
					);
					lab.changeToPath(inBetweenNode);
					visitedNodes.add(inBetweenNode);
					this.activeSet.add(neighbor);
				}

				// neighbor
				lab.changeToPath(neighbor);
				if (!visitedNodes.contains(neighbor))
					visitedNodes.add(neighbor);
			}

		}
	}

	private ArrayList<Point2D> findUnvisitedNeighbors(ArrayList<Point2D> visitedNodes) {
		// create an array with all unvisited neighbors
		ArrayList<Point2D> unvisitedNeighbors = new ArrayList<>();
		double x = this.activeNode.getX();
		double y = this.activeNode.getY();

		// fill array of unvisited neighbors with existing nodes
		if (x > 0 && !visitedNodes.contains(new Point2D(x-2, y))) // x = 0 -> left border
			unvisitedNeighbors.add(new Point2D(x-2, y));

		if (x < this.width-1 && !visitedNodes.contains(new Point2D(x+2, y))) // x = width-1 -> right border
			unvisitedNeighbors.add(new Point2D(x+2,y));

		if (y > 0 && !visitedNodes.contains(new Point2D(x, y-2))) // y = 0 -> top border
			unvisitedNeighbors.add(new Point2D(x, y-2));

		if (y < this.height-1 && !visitedNodes.contains(new Point2D(x, y+2))) // y = height-1 -> bottom border
			unvisitedNeighbors.add(new Point2D(x, y+2));

		return (unvisitedNeighbors);
	}

	/**
	 * Select a random unvisited neighbor of the active Node
	 * @param visitedNodes array of already visited nodes
	 * @return random neighbor (takes into account if none exists)
	 */
	public Point2D pickRandomNeighbor(ArrayList<Point2D> visitedNodes) {
		ArrayList<Point2D> unvisitedNeighbors = this.findUnvisitedNeighbors(visitedNodes);
		Point2D res;
		// define selected unvisited neighbor
		if (unvisitedNeighbors.size() > 0)
			res = unvisitedNeighbors.get(Main.R.nextInt(unvisitedNeighbors.size()));
		else // none exists
			res = new Point2D.Double(Main.DEFAULT_NODE, Main.DEFAULT_NODE);
		
		return res;
	}
}
