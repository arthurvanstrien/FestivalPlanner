package edu.a3.festival_planner.simulator;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TiledLayer {

  private TiledMap map;
  private int height;
  private int width;
  int[][] data2D;
  private BufferedImage img;
  private boolean isDirty;
  String name;
  private ArrayList<Point> accessiblePoints;
  private ArrayList<Point> greenPoints;

  public TiledLayer(JsonObject jsonLayer, TiledMap map) {
    this.map = map;
    isDirty = true;
    accessiblePoints = new ArrayList<>();
    greenPoints = new ArrayList<>();
    this.height = jsonLayer.getInt("height");
    this.width = jsonLayer.getInt("width");
    data2D = new int[height][width];
    name = jsonLayer.getString("name");

    JsonArray data = jsonLayer.getJsonArray("data");

    int index = 0;

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        data2D[y][x] = data.getInt(index);
        if(data.getInt(index) == 3331 || data.getInt(index) == 0) {
          accessiblePoints.add(new Point(x, y));
        }
        index++;
      }
    }

    this.img = this.getImage();
  }

  public BufferedImage getImage() {
    BufferedImage img = new BufferedImage(width * 32, height * 32, 2);
    Graphics2D g2d = img.createGraphics();
    if (isDirty) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          g2d.drawImage(map.arrayImages.get(this.data2D[y][x]), x * 32, y * 32, null);
        }
      }
      isDirty = false;
      return img;
    } else {
      return this.img;
    }
  }

  public boolean containsPoint(Point2D point) {
    for (Point2D p : accessiblePoints) {
      if(p.getX() == point.getX() && p.getY() == point.getY()) {
        return true;
      }
    }
    return false;
  }

  public Point2D getNearestPoint(Point2D point) {
    Point2D record = accessiblePoints.get(0);
    double recordDistance = point.distance(record);
    for (Point2D p : accessiblePoints) {
      if(p.getX() != point.getX() && p.getY() != point.getY()) {
        if(point.distance(p) < recordDistance) {
          recordDistance = p.distance(point);
          record = p;
        }
      }
    }
    return record;
  }

  public void setDirty() {
    isDirty = true;
  }

  public String getName(){
    return name;
  }

  public ArrayList<Point> getAccessiblePoints() {
    return accessiblePoints;
  }
}