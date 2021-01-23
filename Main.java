import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

public class Main {
    // arrayList.add() -> append to the end of the list
    public static void main(String[] args) {
        // java Main width height
        int width = 10;//Integer.parseInt(args[0]);
        int height = 10;//Integer.parseInt(args[1]);

        // Create labyrinth filled with only walls
        Labythin lab = new Labythin(width, height);
        System.out.println(lab.printLab());

        System.out.println(MazeGenerator(lab, 1));
    }

    /**
     * 
     * @param lab
     * @param heuristic
     *      1: Recursive Backtracker (newest)
     *      2: Prism (random)
     *      3: oldest
     *      4: 50/50
     */
    public static String MazeGenerator(Labythin lab, int heuristic) {
        Random r = new Random();
        ArrayList<Point2D> activeSet = new ArrayList<>();
        ArrayList<Point2D> visitedNodes = new ArrayList<>();
        Point2D activeNode, neighbor;

        // 1. Randomly select a cell and add it to the selected cells list.
        // 2.Select a cell from the selected cells list. Then choose a cell adjacent to it that hasn’t been visited. Add the adjacent cell to the selected list and remove the edge between the two cells.
        // 3. If there are no unvisited neighbors
        //    3.a. Remove from the selected list
        // 4. Repeat 2-3 until the selected list is empty.

        // Choose first activeNode
        activeNode = new Point2D.Double(0,0);
        activeSet.add(activeNode);
        visitedNodes.add(activeNode);
        lab.changeToPath(activeNode);

        // Run the maze generation
        while (!activeSet.isEmpty()) {
            // Select node from active set
            switch (heuristic) {
                case 1:
                    activeNode = activeSet.get(activeSet.size()-1);
                    break;
                case 2:
                    activeNode = activeSet.get(r.nextInt(activeSet.size()));
                    break;
                case 3:
                    activeNode = activeSet.get(0);
                    break;
                case 4:
                    if (Math.random() > 1) 
                        activeNode = activeSet.get(activeSet.size()-1);
                    else
                        activeNode = activeSet.get(r.nextInt(activeSet.size()));
                    break;
                default:
                    System.out.println("Unknown heuristic method");
                    break;
            }
    
            // Choose a random neighbor node, if none available remove active node from active set
            neighbor = lab.randomNeighbor(visitedNodes, activeNode);
    
            if (neighbor.getX() == -1 && neighbor.getY() == -1) { // no neighbor
                activeSet.remove(activeNode);
            } else {
                lab.changeToPath(neighbor);
                activeSet.add(neighbor);
                visitedNodes.add(neighbor);
            }
        }

        return (lab.printLab());
    }

}