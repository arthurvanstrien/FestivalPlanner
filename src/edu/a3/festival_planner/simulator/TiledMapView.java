package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Time;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
  Time prevTime;
  Map<Time, TiledMapView> saves;

  public TiledMapView(TiledMap tiledMap, Agenda agenda, int visitorAmount) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
    visitors = new ArrayList<>();
    maxNumberOfVisitors = visitorAmount;
    bfs = new BreadthFirstSearch(tiledMap.getWalkableLayer().getAccesiblePoints());
    this.agenda = agenda;
    this.saves = new HashMap();
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
    boolean updateBlatter = false;
    if(prevTime != null) {
      if(!prevTime.isTheSame(time)) {
        updateBlatter = true;
      }
    }
    prevTime = time;
    if(time.isBefore(agenda.getEndTime())) {
      spawnVisitor(time);
    }
    Iterator it = visitors.iterator();
    while(it.hasNext()) {
      Visitor v = (Visitor) it.next();
      if(v.getExited()) {
        it.remove();
      } else {
        v.update(visitors, elapsedTime, agenda, time, tiledMap);
        if(updateBlatter) {
          v.updateBlatter();
        }
      }
    }
    repaint();
  }

  public void spawnVisitor(Time time) {
    //if the amount of total visitors has not been reached there is a chance to spawn a new visitor
    if (visitors.size() < maxNumberOfVisitors && Math.random() > 0.85) {
      Visitor tempVisitor = new Visitor(visitors, tiledMap.getAreaLayers(), bfs, agenda,time, tiledMap);
      if (tempVisitor.canSpawnOnLocation(visitors, tiledMap.getWalkableLayer())) {
        tempVisitor.setDestination(tiledMap.enumToPointDestination(tempVisitor.getCurrentDestination()),
                time);
        visitors.add(tempVisitor);
      }
    }
  }
}
