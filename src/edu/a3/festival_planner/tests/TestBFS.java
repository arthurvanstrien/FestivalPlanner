package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.simulator.BreadthFirstSearch;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snick on 19-3-2017.
 */
public class TestBFS {
  /**
   * Run breadth first search test 1000x from random source point to one random target point.
   * @param xSize size of x-axis of searchable field
   * @param ySize size of y-axis of searchable field
   * @param verbose if true prints routes and runtime
   * @return Error count
   */
  public static int runTest(int xSize, int ySize, boolean verbose) {
    int errors = 0;
    long startTime = System.nanoTime();

    List<Point> points = new ArrayList<>();
    for (int x = 0; x < xSize; x++) {
      for (int y = 0; y < ySize; y++) {
        points.add(new Point(x, y));
      }
    }

    int xTarget = (int) (xSize * Math.random());
    int yTarget = (int) (ySize * Math.random());
    BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(points,
        new Point(xTarget, yTarget));
    for (int i = 0; i < 1000; i++) {
      int xSource = (int) (xSize * Math.random());
      int ySource = (int) (ySize * Math.random());

      try {
        List<Point> shortestPath = breadthFirstSearch
            .searchShortestPath(new Point(xSource, ySource));
        if (verbose) {
          System.out.println(
              "\nFrom: " + xSource + "," + ySource + "\t To: " + xTarget + "," + yTarget
                  + "\t Path:\n" + shortestPath);
        }
        if (shortestPath.size() != Math.abs(xSource - xTarget) + Math.abs(ySource - yTarget)) {
          System.out.println("Path is not as short as possible.");
          errors++;
          break;
        }
      } catch (NullPointerException e) {
        System.out.println("Source and/or target not within scope.");
        errors++;
        break;
      }
    }

    long endTime = System.nanoTime();
    if (verbose) {
      System.out.println("\nRun time: " + (endTime - startTime) / 1000000000.0 + "s");
    }
    return errors;
  }
}
