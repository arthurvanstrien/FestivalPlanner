package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Time;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by snick on 20-3-2017.
 */
class TiledMapView extends JPanel {

  private TiledMap tiledMap;
  private Camera camera;
  private List<Drawable> visitors;
  private BreadthFirstSearch bfs;
  private Agenda agenda;
  private int maxNumberOfVisitors;
  private Time prevTime;
  private Map<Time, List<Drawable>> saves;

  public TiledMapView(TiledMap tiledMap, Agenda agenda, int visitorAmount) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
    visitors = new ArrayList<>();
    maxNumberOfVisitors = visitorAmount;
    bfs = new BreadthFirstSearch(tiledMap.getWalkableLayer().getAccessiblePoints());
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
    boolean shouldOverride = false;
    shouldOverride = !checkTimeExists(time);
    if (time.toSeconds() % 1800 == 0) {
      saveCurrentSituation(time);
    }
    boolean updateBladder = false;
    boolean updateStomach = false;
    if(prevTime != null) {
      if(!prevTime.isTheSame(time)) {
        updateBladder = true;
        updateStomach = true;
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
        if (updateBladder) {
          v.updateBlatter();
        }
        if(updateStomach){
          v.updateStomach();
        }
      }
    }
    repaint();
  }

  private void saveCurrentSituation(Time time) {
    if (!saves.containsKey(time)) {
      boolean debug = false;
      if (debug) {
        System.out.println("Time saved: " + time.getHour() + "   " + time.getMinute());
      }
      ArrayList<Drawable> toAdd = new ArrayList<>();
      for (Drawable d : visitors) {
        Drawable v = d.cloneDrawable();
        if (v != null) {
          toAdd.add(v);
        }
      }
      saves.put(time, toAdd);
    }
  }

  private void spawnVisitor(Time time) {
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

  public Map<Time, List<Drawable>> getSaves() {
    return saves;
  }

  private boolean checkTimeExists(Time time) {
    for (Time t : saves.keySet()) {
      if (t.isTheSame(time)) {
        return true;
      }
    }
    return false;
  }

  public void setVisitors(ArrayList<Drawable> visitors) {
    this.visitors = visitors;
  }

}
