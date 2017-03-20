package edu.a3.festival_planner.simulator;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by snick on 19-3-2017.
 */
public class BreadthFirstSearch {

  private List<Point> accessiblePoints;

  /**
   * Holds list of points to repeatedly search path for.
   *
   * @param accessiblePoints List of points available for search.
   */
  public BreadthFirstSearch(@NotNull List<Point> accessiblePoints) {
    this.accessiblePoints = accessiblePoints;
  }

  /**
   * @param source point to start at
   * @param target point to navigate to
   * @return List containing the points between source and target, excludes source, includes target.
   * @throws NullPointerException Source and/or target not within scope.
   */
  public List<Point> searchShortestPath(@NotNull Point source, @NotNull Point target)
      throws NullPointerException {
    Vertex src = new Vertex(source, null);
    Vertex trg = new Vertex(target, null);
    bfs(src, trg);

    List<Point> path = new ArrayList<>();
    while (trg.getPredecessor() != src) {
      trg = trg.getPredecessor();
      path.add(trg.location);
    }
    Collections.reverse(path);
    return path;
  }

  private void bfs(Vertex source, Vertex target) {
    Queue<Vertex> queue = new LinkedList<>();
    List<Vertex> visited = new ArrayList<>();
    queue.add(source);
    visited.add(source);

    while (!queue.isEmpty()) {
      Vertex current = queue.poll();
      if (current.equals(target)) {
        target.setPredecessor(current);
        return;
      }
      for (Vertex v : current.getAdjacentVertices()) {
        if (!visited.contains(v)) {
          v.setPredecessor(current);
          queue.add(v);
          visited.add(v);
        }
      }
    }
  }

  public class Vertex {

    private Point location;
    private Vertex predecessor;

    /**
     * Create new Vertex
     *
     * @param location location of the vertex
     * @param predecessor preceding vertex
     */
    public Vertex(Point location, @Nullable Vertex predecessor) {
      this.location = location;
      this.predecessor = predecessor;
    }

    /**
     * @return List of vertices connected to this vertex.
     */
    public List<Vertex> getAdjacentVertices() {
      List<Vertex> adj = new ArrayList<>();

      Point up = new Point(location.x, location.y + 1);
      Point down = new Point(location.x, location.y - 1);
      Point left = new Point(location.x - 1, location.y);
      Point right = new Point(location.x + 1, location.y);

      if (accessiblePoints.contains(up)) {
        adj.add(new Vertex(up, this));
      }
      if (accessiblePoints.contains(down)) {
        adj.add(new Vertex(down, this));
      }
      if (accessiblePoints.contains(left)) {
        adj.add(new Vertex(left, this));
      }
      if (accessiblePoints.contains(right)) {
        adj.add(new Vertex(right, this));
      }

      return adj;
    }

    /**
     * @return Vertex preceding this vertex
     */
    public Vertex getPredecessor() {
      return predecessor;
    }

    /**
     * @param predecessor vertex to precede this vertex
     */
    public void setPredecessor(Vertex predecessor) {
      this.predecessor = predecessor;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Vertex) {
        if (this.location.equals(((Vertex) obj).location)) {
          return true;
        }
      }
      return super.equals(obj);
    }

//    public boolean equals(Object obj) {
//      if (obj instanceof Vertex) {
//        if (this.location.equals(((Vertex) obj).location)) {
//          return true;
//        }
//      }
//      return super.equals(obj);
//    }
  }
}
