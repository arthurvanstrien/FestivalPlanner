package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Time;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Created by snick on 20-3-2017.
 */
public class TiledMapView extends JPanel {

  TiledMap tiledMap;
  Camera camera;
  ArrayList<Drawable> visitors;
  BreadthFirstSearch bfs;
  Agenda agenda;
  int maxNumberOfVisitors = 1;

  public TiledMapView(TiledMap tiledMap, Agenda agenda) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
    visitors = new ArrayList<>();
    bfs = new BreadthFirstSearch(tiledMap.getWalkableLayer().getAccesiblePoints());
    this.agenda = agenda;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));
    if (tiledMap != null) {
      tiledMap.draw(g2d);
    }
    for(Drawable v : visitors) {
      v.draw(g2d);
    }
  }

  public void update(double elapsedTime, Time time){
    spawnVisitor(time);
    for(Drawable v : visitors) {
      v.update(visitors, tiledMap.getWalkableLayer(), elapsedTime, agenda, time);
      if(v instanceof  Visitor) {
        if(((Visitor) v).getAtDestination()) {
          v.setDestination(tiledMap.enumToPointDestination(((Visitor) v).getCurrentDestination()), time);
        }
      }
    }

    repaint();
  }

  public void spawnVisitor(Time time) {
    //if the amount of total visitors has not been reached there is a chance to spawn a new visitor
    if (visitors.size() < maxNumberOfVisitors && Math.random() > 0.7) {
      Visitor tempVisitor = new Visitor(visitors, tiledMap.getWalkableLayer(), tiledMap.getAreaLayers(), bfs, agenda,time);
      if (tempVisitor.canSpawnOnLocation(visitors, tiledMap.getWalkableLayer())) {
        tempVisitor.setDestination(tiledMap.enumToPointDestination(tempVisitor.getCurrentDestination()),
            time);
        visitors.add(tempVisitor);
      }
    }
  }

}
