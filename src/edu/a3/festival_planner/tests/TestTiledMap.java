package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.simulator.Camera;
import edu.a3.festival_planner.simulator.Drawable;
import edu.a3.festival_planner.simulator.TiledMap;
import edu.a3.festival_planner.simulator.Visitor;
import edu.a3.festival_planner.simulator.VisitorImageList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dionb on 20-2-2017.
 */
public class TestTiledMap extends JPanel {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Hello Java2D");
    frame.setDefaultCloseOperation(3);
    frame.setMinimumSize(new Dimension(800, 600));
    frame.setExtendedState(frame.getExtendedState() | 6);
    frame.setContentPane(new TestTiledMap());
    frame.setVisible(true);
  }
/*
    public TestTiledMap() {
        createGUI();
    }

    public void createGUI(){
        JFrame frame = new JFrame("Hello Java2D");
        frame.setDefaultCloseOperation(3);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | 6);
        frame.setContentPane(new TestTiledMap());
        frame.setVisible(true);
    }*/

  final int numberOfVisitors = 100;
  ArrayList<Drawable> drawings;
  Camera camera = new Camera(this);
  TiledMap tm;

  public TestTiledMap() {
    fillVisitorImageList();
    tm = new TiledMap();
    drawings = new ArrayList<>();
    for (int i = 0; i < numberOfVisitors; i++) {
      drawings.add(new Visitor(new Point2D.Double(Math.random() * 100, Math.random() * 100)));
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));
    tm.draw(g2d);
  }

  private void fillVisitorImageList() {
    try {
      VisitorImageList
          .addImage(ImageIO.read(
              this.getClass().getClassLoader().getResource("bezoekers/VisitorBlueBlond.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      VisitorImageList
          .addImage(ImageIO.read(
              this.getClass().getClassLoader().getResource("bezoekers/VisitorGreenBlond.png")));

    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      VisitorImageList
          .addImage(ImageIO.read(
              this.getClass().getClassLoader().getResource("bezoekers/VisitorBlackBlond.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      VisitorImageList
          .addImage(ImageIO
              .read(this.getClass().getClassLoader().getResource("bezoekers/VisitorRedBlack.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
