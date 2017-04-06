package edu.a3.festival_planner.simulator;

import javax.json.JsonObject;
import java.awt.*;

/**
 * Created by dionb on 20-3-2017.
 */
class Area {
  private double x = 0;
  private double y = 0;
  private double height = 0;
  private double width = 0;
  private String type = "";
  private String name = "";


  public Area(JsonObject jsonObject) {
    this.x = jsonObject.getInt("x");
    this.y = jsonObject.getInt("y");
    this.height = jsonObject.getInt("height");
    this.width = jsonObject.getInt("width");
    this.type = jsonObject.getString("type");
    this.name = jsonObject.getString("name");
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);

    //Draws a rectangle on the right place with the right size for this obstacle
    g2d.fillRect((int)(this.x), (int)(this.y), (int)(this.width), (int)(this.height));
  }

  public String getType(){
    return type;
  }

  public String getName() {
    return name;
  }

  public double getHeight() {
      return height;
  }
  public double getWidth(){
    return width;
  }
  public double getX(){
    return x;
  }
  public double getY(){
    return y;
  }
}
