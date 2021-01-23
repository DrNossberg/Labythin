import java.util.ArrayList;
import java.awt.geom.*;

public class Main {
    public static void main(String[] args) {
        // java Main width height
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        // Create labyrinth
        Labythin lab = new Labythin(width, height);
        lab.printLab();

        ArrayList<Point2D> activeSet = new ArrayList<>();
        ArrayList<Point2D> visitedNodes = new ArrayList<>();
        Point2D.Double activeNode;

        // Choose first activeNode
        activeNode = new Point2D.Double(0,0);
        activeSet.add(activeNode);
        visitedNodes.add(activeNode);

        // Run the maze generation
        while (!activeSet.isEmpty()) {
            // Choose a neighbor cell, if none availible remove active node from active set
            Point2D neighbor = lab.randomNeighbor(activeSet, activeNode);
            if (neighbor.getX() == -1 && neighbor.getY() == -1) { // no neighbor
                activeSet.remove(activeNode);
                System.out.println("Removing node");
            } 
            else {
                activeSet.add(neighbor);
                lab.changeToPath((int) activeNode.getX(), (int) activeNode.getY());
                System.out.println("Adding node");
            }
        }

        lab.printLab();
        
    }

}