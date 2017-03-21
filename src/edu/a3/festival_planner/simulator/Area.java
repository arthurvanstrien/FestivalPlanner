package edu.a3.festival_planner.simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.json.JsonObject;

/**
 * Created by dionb on 20-3-2017.
 */
public class Area {
  private double x = 0;
  private double y = 0;
  private double height = 0;
  private double width = 0;
  private String type = "";


  public Area(JsonObject jsonObject) {
    this.x = jsonObject.getInt("x");
    this.y = jsonObject.getInt("y");
    this.height = jsonObject.getInt("height");
    this.width = jsonObject.getInt("width");
    this.type = jsonObject.getString("type");
  }

  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);

    //Draws a rectangle on the right place with the right size for this obstacle
    g2d.fillRect((int)(this.x), (int)(this.y), (int)(this.width), (int)(this.height));
  }

}
