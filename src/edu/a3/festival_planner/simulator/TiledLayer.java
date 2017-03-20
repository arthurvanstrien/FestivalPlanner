package edu.a3.festival_planner.simulator;

import javax.json.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class TiledLayer {

  TiledMap map;
  int height;
  int width;
  int[][] data2D;
  BufferedImage img;
  boolean isDirty;
  String name;

  public TiledLayer(JsonObject jsonLayer, TiledMap map) {
    this.map = map;
    isDirty = true;
    this.height = jsonLayer.getInt("height");
    this.width = jsonLayer.getInt("width");
    data2D = new int[height][width];
    name = jsonLayer.getString("name");

    JsonArray data = jsonLayer.getJsonArray("data");

    int index = 0;

    for (int y = 0; y < this.height; ++y) {
      for (int x = 0; x < this.width; ++x) {
        data2D[y][x] = data.getInt(index);
        ++index;
      }
    }

    this.img = this.getImage();
  }

  public BufferedImage getImage() {
    BufferedImage img = new BufferedImage(width * 32, height * 32, 2);
    Graphics2D g2d = img.createGraphics();
    if (isDirty) {
      for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
          g2d.drawImage((Image) map.arrayImages.get(this.data2D[y][x]), x * 32, y * 32, (ImageObserver) null);
        }
      }
      isDirty = false;
      return img;
    } else {
      return this.img;
    }
  }

  public void setDirty() {
    isDirty = true;
  }
}