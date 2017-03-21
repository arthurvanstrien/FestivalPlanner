package edu.a3.festival_planner.simulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by robin on 12-3-2017.
 */
public interface Drawable {

  void draw(Graphics2D g2d);

  void update(ArrayList<Drawable> drawings, TiledLayer walklayer);

  Point2D getPosition();

  void setDestination(Point2D destination);

}
