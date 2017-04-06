package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Time;

import java.awt.*;
import java.util.List;
import java.awt.geom.Point2D;

/**
 * Created by robin on 12-3-2017.
 */
public interface Drawable {

  void draw(Graphics2D g2d);

  void update(List<Drawable> drawings, double pastTime, Agenda agenda, Time time, TiledMap tiledMap);

  Point2D getPosition();

  void setDestination(Point2D destination, Time time);

  Drawable cloneDrawable();
}
