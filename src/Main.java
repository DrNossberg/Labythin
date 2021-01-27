/*
** IUT Nancy-Charlemagne, 2021
** Projet :
**    Labythin
** Author :
**    Erin      Bernardoni
**    Antoine   Orion
** File description :
**    ceci est principalement là pour que t'ai un merge :P
*/

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

public class Main {
    public static final Random R = new Random();
    public static final int DEFAULT_NODE = -42;
    // arrayList.add() -> append to the end of the list
    public static void main(String[] args) throws Exception {
        // java Main width height
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        // Create labyrinth filled with only walls
        Maze lab = new Maze(width, height);

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
    public static String MazeGenerator(Maze lab, int mode) throws Exception {
        
        ArrayList<Point2D> activeSet = new ArrayList<>();
        ArrayList<Point2D> visitedNodes = new ArrayList<>();
        Point2D activeNode, neighbor;

        // 1. Randomly select a cell and add it to the selected cells list.
        // 2.Select a cell from the selected cells list. Then choose a cell adjacent to it that hasn’t been visited. 
        //Add the adjacent cell to the selected list and remove the edge between the two cells.
        // 3. If there are no unvisited neighbors
        //    3.a. Remove from the selected list
        // 4. Repeat 2-3 until the selected list is empty.

        // Choose first activeNode
        activeNode = new Point2D.Double(0,0);
        activeSet.add(activeNode);
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
            if (!visitedNodes.contains(activeNode))
                visitedNodes.add(activeNode);

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


            if (neighbor.getX() == Main.DEFAULT_NODE && neighbor.getY() == Main.DEFAULT_NODE) { // no neighbor
                activeSet.remove(activeNode);
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
                    activeSet.add(neighbor);
                }

                // neighbor
                lab.changeToPath(neighbor);
                if (!visitedNodes.contains(neighbor))
                    visitedNodes.add(neighbor);
            }

        }

        return (lab.printLab());
    }

}
