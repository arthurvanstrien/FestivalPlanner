//package edu.a3.festival_planner.tests;
//
//import edu.a3.festival_planner.simulator.AreaLayer;
//import edu.a3.festival_planner.simulator.BreadthFirstSearch;
//import edu.a3.festival_planner.simulator.Camera;
//import edu.a3.festival_planner.simulator.Drawable;
//import edu.a3.festival_planner.simulator.TiledLayer;
//import edu.a3.festival_planner.simulator.TiledMap;
//import edu.a3.festival_planner.simulator.Visitor;
//import edu.a3.festival_planner.simulator.VisitorImageList;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * Created by dionb on 20-2-2017.
// */
//public class TestTiledMap extends JPanel implements ActionListener {
//
//  public static void main(String[] args) {
//    JFrame frame = new JFrame("Hello Java2D");
//    frame.setDefaultCloseOperation(3);
//    frame.setMinimumSize(new Dimension(800, 600));
//    frame.setExtendedState(frame.getExtendedState() | 6);
//    frame.setContentPane(new TestTiledMap());
//    frame.setVisible(true);
//  }
///*
//    public TestTiledMap() {
//        createGUI();
//    }
//
//    public void createGUI(){
//        JFrame frame = new JFrame("Hello Java2D");
//        frame.setDefaultCloseOperation(3);
//        frame.setMinimumSize(new Dimension(800, 600));
//        frame.setExtendedState(frame.getExtendedState() | 6);
//        frame.setContentPane(new TestTiledMap());
//        frame.setVisible(true);
//    }*/
//
//  long lastTime = System.currentTimeMillis();
//  long totalPastTime = 0;
//  final int startNumberOfVisitors = 1;
//  final int totalNumberOfVisitors = 200;
//  ArrayList<Drawable> drawings;
//  ArrayList<Visitor> visitors;
//  Camera camera = new Camera(this);
//  TiledMap tm;
//  BreadthFirstSearch bfs;
//
//  public TestTiledMap() {
//    fillVisitorImageList();
//    visitors = new ArrayList<>();
//    //tm = new TiledMap("C:\\Users\\Gebruiker\\Festival Planner\\Maps\\Map_20_03_2017_V4.json");
//    tm = new TiledMap("maps/MapV7.json");
//    drawings = new ArrayList<>();
//    TiledLayer walkable = tm.getWalkableLayer();
//    bfs = new BreadthFirstSearch(walkable.getAccesiblePoints());
//    ArrayList<AreaLayer> entrances = tm.getAreaLayers();
//    for (int i = 0; visitors.size() < startNumberOfVisitors; i++) {
//      Visitor tempVisitor = new Visitor(drawings,walkable,entrances, bfs, null);
//      if(tempVisitor.canSpawnOnLocation(drawings, walkable)) {
//        tempVisitor.setNewDestination(null);
//        tempVisitor.setDestination(tm.enumToPointDestination(tempVisitor.getCurrentDestination()),
//            time);
//        drawings.add(tempVisitor);
//        visitors.add(tempVisitor);
//      }
//    }
//    new Timer(1000/100, this).start();
//  }
//
//  public void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    Graphics2D g2d = (Graphics2D) g;
//    g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));
//    tm.draw(g2d);
//    for(Drawable drawable: drawings){
//      drawable.draw(g2d);
//    }
//  }
//
//  public void update() {
//    long pastTime = System.currentTimeMillis() - lastTime;
//    totalPastTime += pastTime;
//    lastTime = lastTime;
//
//    //if the amount of total visitors has not been reached there is a chance to spawn a new visitor
//    if (visitors.size() < totalNumberOfVisitors && Math.random() > 0.7) {
//      Visitor tempVisitor = new Visitor(drawings, tm.getWalkableLayer(), tm.getAreaLayers(), bfs, null);
//      if (tempVisitor.canSpawnOnLocation(drawings, tm.getWalkableLayer())) {
//        tempVisitor.setNewDestination(null);
//        tempVisitor.setDestination(tm.enumToPointDestination(tempVisitor.getCurrentDestination()),
//            time);
//        drawings.add(tempVisitor);
//        visitors.add(tempVisitor);
//      }
//    }
//
//    //check wether the visitor is at his destination, if he is a new location is set
//    for(Visitor v : visitors) {
//      if(v.getAtDestination()) {
//        v.setNewDestination(null);
//        v.setDestination(tm.enumToPointDestination(v.getCurrentDestination()), time);
//      }
//    }
//
//    for (Drawable drawable : drawings) {
//        drawable.update(drawings, tm.getWalkableLayer(), pastTime, null);
//      }
//      repaint();
//    }
//
//  private void fillVisitorImageList() {
//    try {
//      VisitorImageList
//          .addImage(ImageIO.read(
//              this.getClass().getClassLoader().getResource("bezoekers/VisitorBlueBlond.png")));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    try {
//      VisitorImageList
//          .addImage(ImageIO.read(
//              this.getClass().getClassLoader().getResource("bezoekers/VisitorGreenBlond.png")));
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    try {
//      VisitorImageList
//          .addImage(ImageIO.read(
//              this.getClass().getClassLoader().getResource("bezoekers/VisitorBlackBlond.png")));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    try {
//      VisitorImageList
//          .addImage(ImageIO
//              .read(this.getClass().getClassLoader().getResource("bezoekers/VisitorRedBlack.png")));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  @Override
//  public void actionPerformed(ActionEvent e) {
//    update();
//  }
//}
