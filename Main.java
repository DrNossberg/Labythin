import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        // java Main width height
        int width = 10;//Integer.parseInt(args[0]);
        int height = 10;//Integer.parseInt(args[1]);

        // Create labyrinth filled with only walls
        Labythin lab = new Labythin(width, height);
        lab.printLab();

        ArrayList<Point2D> activeSet = new ArrayList<>();
        ArrayList<Point2D> visitedNodes = new ArrayList<>();
        Point2D activeNode, neighbor;

        // Choose first activeNode
        activeNode = new Point2D.Double(0,0);
        activeSet.add(activeNode);
        visitedNodes.add(activeNode);
        lab.changeToPath(activeNode);


        // Run the maze generation
        while (!activeSet.isEmpty()) {
            // Select random node from active set
            activeNode = activeSet.get(r.nextInt(activeSet.size()));
    
            // Choose a random neighbor node, if none available remove active node from active set
            neighbor = lab.randomNeighbor(visitedNodes, activeNode);
    
            if (neighbor.getX() == -1 && neighbor.getY() == -1) { // no neighbor
                System.out.println("No neighbor found");
                activeSet.remove(activeNode);
            } else {
                lab.changeToPath(neighbor);
            }
        }

        
        lab.printLab();
        
    }

}