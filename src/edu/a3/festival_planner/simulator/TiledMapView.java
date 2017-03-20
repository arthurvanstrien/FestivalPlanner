package edu.a3.festival_planner.simulator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Created by snick on 20-3-2017.
 */
public class TiledMapView extends JPanel {

  TiledMap tiledMap;
  Camera camera;

  public TiledMapView(TiledMap tiledMap) {
    this.tiledMap = tiledMap;
    camera = new Camera(this);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));
    if (tiledMap != null) {
      tiledMap.draw(g2d);
    }
  }
}
