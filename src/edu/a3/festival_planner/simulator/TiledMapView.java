package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Time;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
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
  int maxNumberOfVisitors;
  int saves;

  public TiledMapView(TiledMap tiledMap, Agenda agenda, int maxNumberOfVisitors, int saves) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
    visitors = new ArrayList<>();
    bfs = new BreadthFirstSearch(tiledMap.getWalkableLayer().getAccesiblePoints());
    this.agenda = agenda;
    this.maxNumberOfVisitors = maxNumberOfVisitors;
    this.saves = saves;
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
    Iterator it = visitors.iterator();
    while(it.hasNext()) {
      Visitor v = (Visitor) it.next();
      v.update(visitors, tiledMap.getWalkableLayer(), elapsedTime, agenda, time, tiledMap);
    }
    if(visitors.size() > 0) {
      ((Visitor) visitors.get(0)).printBlatter();
    }
    repaint();
  }

  public void spawnVisitor(Time time) {
    //if the amount of total visitors has not been reached there is a chance to spawn a new visitor
    if (visitors.size() < maxNumberOfVisitors && Math.random() > 0.7) {
      Visitor tempVisitor = new Visitor(visitors, tiledMap.getWalkableLayer(), tiledMap.getAreaLayers(), bfs, agenda,time, tiledMap);
      if (tempVisitor.canSpawnOnLocation(visitors, tiledMap.getWalkableLayer())) {
        tempVisitor.setDestination(tiledMap.enumToPointDestination(tempVisitor.getCurrentDestination()),
            time);
        visitors.add(tempVisitor);
      }
    }
  }

}
