package edu.a3.festival_planner.simulator;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by snick on 19-3-2017.
 */
public class BreadthFirstSearch {

  List<Point> accessiblePoints;
  Vertex target;
  Map<Vertex, Integer> distance;

  /**
   * Maps the distance from each point in accessiblePoints to the target point.
   * @param accessiblePoints list of points available for search.
   * @param target point to navigate to
   * @throws NullPointerException Target not within scope.
   */
  public BreadthFirstSearch(@NotNull List<Point> accessiblePoints, @NotNull Point target)
      throws NullPointerException {
    this.accessiblePoints = accessiblePoints;
    this.target = new Vertex(target, null);
    bfs();
  }

  /**
   * @param source point to start at
   * @return List containing the points between source and target, excludes source, includes target.
   * @throws NullPointerException Source not within scope.
   */
  public List<Point> searchShortestPath(@NotNull Point source) throws NullPointerException {
    Vertex src = new Vertex(source, null);
    for (Vertex v : distance.keySet()) {
      if (v.equals(src)) {
        src = v;
      }
    }
    List<Point> path = new ArrayList<>();
    while (distance.get(target) < distance.get(src)) {
      path.add(src.location);
      src = src.predecessor;
    }
    path.remove(source);
    path.add(target.location);
    return path;
  }

  private void bfs() {
    Queue<Vertex> queue = new LinkedList<>();
    distance = new HashMap<>();
    queue.add(target);
    distance.put(target, 0);

    while (!queue.isEmpty()) {
      Vertex current = queue.poll();
      for (Vertex v : current.getAdjacentVertices()) {
        if (!distance.containsKey(v)) {
          v.setPredecessor(current);
          queue.add(v);
          distance.put(v, distance.get(current) + 1);
        }
      }
    }
  }

  public class Vertex {

    private Point location;
    private Vertex predecessor;

    /**
     * Create new Vertex
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

    @Override
    public int hashCode() {
      return location.hashCode();
    }
  }
}
