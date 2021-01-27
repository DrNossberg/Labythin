import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

public class Main {
    public static final Random R = new Random();
    public static final int DEFAULT_NODE = -42;
    // arrayList.add() -> append to the end of the list
    public static void main(String[] args) throws Exception {
        // java Main width height
        int width = 10;//Integer.parseInt(args[0]);
        int height = 10;//Integer.parseInt(args[1]);
        // Create labyrinth filled with only walls
        Labythin lab = new Labythin(width, height);

        // Run the maze generator
        System.out.println(MazeGenerator(lab, 1));
    }

    /**
     * 
     * @param lab
     * @param mode 1: Recursive Backtracker (newest) 2: Prism (random) 3: oldest 4:
     *             50/50
     * @throws Exception
     */
    public static String MazeGenerator(Labythin lab, int mode) throws Exception {
        
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
            switch (mode) {
                case 1:
                    activeNode = activeSet.get(activeSet.size()-1);
                    break;
                case 2:
                    activeNode = activeSet.get(Main.R.nextInt(activeSet.size()));
                    break;
                case 3:
                    activeNode = activeSet.get(0);
                    break;
                case 4:
                    if (Math.random() > 0.5) 
                        activeNode = activeSet.get(activeSet.size()-1);
                    else
                        activeNode = activeSet.get(Main.R.nextInt(activeSet.size()));
                    break;
                default:
                    throw new Exception("Unknown mode");
            }

            // Choose a random neighbor node, if none available remove active node from active set
            neighbor = lab.randomNeighbor(visitedNodes, activeNode);
    
            /**
             * A la base j'ai fait ça, mais ça bloque quand le labyrinthe est pair
             */
            // if (neighbor.getX() == Main.DEFAULT_NODE && neighbor.getY() == Main.DEFAULT_NODE) { // no neighbor
            //     activeSet.remove(activeNode);
            // } else {
            //     lab.changeToPath(new Point2D.Double(
            //         (activeNode.getX() + neighbor.getX()) / 2,
            //         (activeNode.getY() + neighbor.getY()) / 2
            //     ));

            //     // neighbor
            //     lab.changeToPath(neighbor);
            //     activeSet.add(neighbor);
            //     visitedNodes.add(neighbor);
            // }


            /**
             * Du coup j'ai fait ça, mais ça marchait pas (tourne à l'infini)
             * Quand le actineNode est à 1 du bord, au lieu de prendre en diagonale et prendre 
             * le milieu (ce qui serait chiant), je partais sur prendre juste celui sur le bord
             * Et le problème c'est sûrement que du coup les nodes "fictifs" ne sont pas stockés dans les visited nodes
             * donc j'ai essayé de le faire mais ça me met full points
             */
            if (neighbor.getX() == Main.DEFAULT_NODE && neighbor.getY() == Main.DEFAULT_NODE) { // no neighbor
                activeSet.remove(activeNode);
            } else {
                visitedNodes.add(neighbor); // j'ai essayé d'ajouter le fictif dans le visite nodes ici
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
                    lab.changeToPath(new Point2D.Double(
                        (activeNode.getX() + neighbor.getX()) / 2,
                        (activeNode.getY() + neighbor.getY()) / 2
                    ));
                }

                // neighbor
                lab.changeToPath(neighbor);
                activeSet.add(neighbor);
                //visitedNodes.add(neighbor);
            }

        }

        return (lab.printLab());
    }

}