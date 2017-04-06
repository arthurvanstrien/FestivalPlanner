package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.simulator.BreadthFirstSearch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snick on 19-3-2017.
 */
class TestBFS {

  private static int X1 = 33;
  private static int Y1 = 11;
  private static int X2 = 7;
  private static int Y2 = 89;

  /**
   * Run breadth first search test with predefined variables.
   *
   * @return Error count
   */
  public static int runTest() {
    int errors = 0;

    List<Point> points = new ArrayList<>();
    int x0 = 100;
    for (int x = 0; x < x0; x++) {
      int y0 = 100;
      for (int y = 0; y < y0; y++) {
        points.add(new Point(x, y));
      }
    }

    BreadthFirstSearch bfs = new BreadthFirstSearch(points);
    try {
      List<Point> shortestPath = bfs.searchShortestPath(new Point(X1, Y1), new Point(X2, Y2));

      if (shortestPath.size() != Math.abs(X1 - X2) + Math.abs(Y1 - Y2)) {
        System.out.println("Path is not as short as possible.");
        errors++;
      }
    } catch (NullPointerException e) {
      System.out.println("Source and/or target not within scope.");
      errors++;
    }
    return errors;
  }

  /**
   * Run breadth first search test with parametrised variables.
   *
   * @param x0 x scope
   * @param y0 y scope
   * @param x1 x value source point
   * @param y1 y value source point
   * @param x2 x value target point
   * @param y2 y value target point
   */
  public static int runtTest(int x0, int y0, int x1, int y1, int x2, int y2) {
    X1 = x1;
    Y1 = y1;
    X2 = x2;
    Y2 = y2;
    return runTest();
  }
}
