package edu.a3.festival_planner.simulator;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class TiledMap {

  ArrayList<TiledLayer> arrayLayers;
  ArrayList<BufferedImage> arrayImages;
  ArrayList<AreaLayer> arrayObjectLayers;
  TiledLayer walkableLayer;

  public TiledMap(String fileName) {
    JsonObject jo = null;
    arrayLayers = new ArrayList<>();
    arrayImages = new ArrayList<>();
    arrayObjectLayers = new ArrayList<>();
    try (

        InputStream is = new FileInputStream(fileName);
        JsonReader jsonReader = Json.createReader(is)) {
      jo = jsonReader.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
    JsonArray jsonArraySets = jo.getJsonArray("tilesets");
    JsonArray jsonArrayLayers = jo.getJsonArray("layers");

    for (int i = 0; i < jsonArraySets.size(); ++i) {
      JsonObject jsonTileSet = jsonArraySets.getJsonObject(i);

      BufferedImage img = null;
      int tileHeight = jsonTileSet.getInt("tileheight");
      int tileWidth = jsonTileSet.getInt("tilewidth");
      int photoHeight = jsonTileSet.getInt("imageheight");
      int photoWidth = jsonTileSet.getInt("imagewidth");
      int gid = jsonTileSet.getInt("firstgid");
      String photoName = jsonTileSet.getString("image");

      try {
        img = ImageIO.read(getClass().getClassLoader().getResource(photoName));
      } catch (Exception e) {
        e.printStackTrace();
      }

      while (arrayImages.size() < gid + jsonTileSet.getInt("tilecount")) {
        arrayImages.add(null);
      }

      for (int y = 0; y + tileHeight <= photoHeight; y += tileHeight) {
        for (int x = 0; x + tileWidth <= photoWidth; x += tileWidth) {
          arrayImages.set(gid, img.getSubimage(x, y, tileWidth, tileHeight));
          ++gid;
        }
      }
    }

    for (int x = 0; x < jsonArrayLayers.size(); ++x) {
      JsonObject layer = jsonArrayLayers.getJsonObject(x);
        if(layer.getJsonArray("data") != null) {
          TiledLayer tempLayer = new TiledLayer(layer, this);
          if(layer.getString("name").equals("Walkable")) {
            walkableLayer = tempLayer;
          }
          arrayLayers.add(tempLayer);
        } else {
          arrayObjectLayers.add(new AreaLayer(layer, this));
        }
      }

  }

  public void draw(Graphics2D g2d) {
    Iterator<TiledLayer> it1 = arrayLayers.iterator();
    while (it1.hasNext()) {
      TiledLayer tl = (TiledLayer) it1.next();
      if(!tl.name.equals("Walkable")) {
        g2d.drawImage((Image) tl.getImage(), new AffineTransform(), (ImageObserver) null);
      }
    }
  }

  public ArrayList<AreaLayer> getAreaLayers(){
    return arrayObjectLayers;
  }
  public ArrayList<TiledLayer> getTiledLayers(){
    return arrayLayers;
  }
  public TiledLayer getWalkableLayer() {return walkableLayer;}

  //translates the enum to an actuel spot on the map
  public Point2D enumToPointDestination(Location currentDestination) {
    ArrayList<Area> entrances = getAreaLayers().get(0).getEntrances();
    ArrayList<Area> stages = getAreaLayers().get(0).getStages();
    ArrayList<Area> otherAreas = getAreaLayers().get(0).getOtherAreas();
      switch (currentDestination) {
        case STAGE_1: return new Point2D.Double(stages.get(0).getX() + (stages.get(0).getWidth() / 2), (stages.get(0).getY() + (stages.get(0).getHeigt() / 2)));
        case STAGE_2: return new Point2D.Double(stages.get(1).getX() + (stages.get(1).getWidth() / 2), (stages.get(1).getY() + (stages.get(1).getHeigt() / 2)));
        case STAGE_3: return new Point2D.Double(stages.get(2).getX() + (stages.get(2).getWidth() / 2), (stages.get(2).getY() + (stages.get(2).getWidth() / 2)));
        case TOILET_1:return new Point2D.Double(otherAreas.get(0).getX() + (otherAreas.get(0).getWidth() / 2), otherAreas.get(0).getY() + (otherAreas.get(0).getHeigt() / 2));
        case TOILET_2:return new Point2D.Double(otherAreas.get(1).getX() + (otherAreas.get(1).getWidth() / 2), otherAreas.get(1).getY() + (otherAreas.get(1).getHeigt() / 2));
        case GRASS: return new Point2D.Double(otherAreas.get(2).getX() + (otherAreas.get(2).getWidth() / 2), otherAreas.get(2).getY() + (otherAreas.get(2).getHeigt() / 2));
        case EXIT:return new Point2D.Double(entrances.get(0).getX() + (entrances.get(0).getWidth() / 2), entrances.get(0).getY() + (entrances.get(0).getHeigt()) /2);
        default:return new Point2D.Double(2800, 1500);
      }
  }
}