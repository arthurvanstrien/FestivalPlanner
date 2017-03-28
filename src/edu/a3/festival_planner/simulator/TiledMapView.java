package edu.a3.festival_planner.simulator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * Created by snick on 20-3-2017.
 */
public class TiledMapView extends JPanel {

  TiledMap tiledMap;
  Camera camera;
  ArrayList<Drawable> visitors;
  BreadthFirstSearch bfs;

  public TiledMapView(TiledMap tiledMap) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
    visitors = new ArrayList<>();
    bfs = new BreadthFirstSearch(tiledMap.getWalkableLayer().getAccesiblePoints());

    for (int x = 0; x < 50; x++) {
      spawnVisitor();
    }
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

  public void update(double elapsedTime){
    for(Drawable v : visitors) {
      v.update(visitors, tiledMap.getWalkableLayer(), elapsedTime);
    }
    repaint();
  }

  public void spawnVisitor() {
      ArrayList<AreaLayer> entrances = tiledMap.getAreaLayers();
      Visitor visitorToAdd = new Visitor(visitors,  tiledMap.getWalkableLayer(),entrances, bfs);
      if(visitorToAdd.hasSpawned()) {
        visitorToAdd.setNewDestination();
        visitorToAdd.setDestination(tiledMap.enumToPointDestination(visitorToAdd.getCurrentDestination()));
        visitors.add(visitorToAdd);
      }
  }
}
