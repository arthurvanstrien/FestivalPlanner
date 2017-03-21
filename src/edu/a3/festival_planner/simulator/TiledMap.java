package edu.a3.festival_planner.simulator;

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

//  public TiledMap() {
//    JsonObject jo = null;
//    arrayLayers = new ArrayList<>();
//    arrayImages = new ArrayList<>();
//    try (
//        InputStream is = new FileInputStream("maps/Map1.json");
//        JsonReader jsonReader = Json.createReader(is);
//    ) {
//      jo = jsonReader.readObject();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    JsonArray jsonArraySets = jo.getJsonArray("tilesets");
//    JsonArray jsonArrayLayers = jo.getJsonArray("layers");
//
//    System.out.println(jo);
//
//    System.out.println(jsonArraySets);
//
//    for (int i = 0; i < jsonArraySets.size(); ++i) {
//      JsonObject jsonTileSet = jsonArraySets.getJsonObject(i);
//
//      BufferedImage img = null;
//      int tileHeight = jsonTileSet.getInt("tileheight");
//      int tileWidth = jsonTileSet.getInt("tilewidth");
//      int photoHeight = jsonTileSet.getInt("imageheight");
//      int photoWidth = jsonTileSet.getInt("imagewidth");
//      int gid = jsonTileSet.getInt("firstgid");
//      String photoName = jsonTileSet.getString("image");
//
//      try {
//        img = ImageIO.read(getClass().getClassLoader().getResource(photoName));
//      } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println(photoName);
//      }
//
//      while (arrayImages.size() < gid + jsonTileSet.getInt("tilecount")) {
//        arrayImages.add(null);
//      }
//
//      for (int y = 0; y + tileHeight <= photoHeight; y += tileHeight) {
//        for (int x = 0; x + tileWidth <= photoWidth; x += tileWidth) {
//          arrayImages.set(gid, img.getSubimage(x, y, tileWidth, tileHeight));
//          ++gid;
//        }
//      }
//    }
//
//    for (int x = 0; x < jsonArrayLayers.size(); ++x) {
//      arrayLayers.add(new TiledLayer(jsonArrayLayers.getJsonObject(x), this));
//    }
//
//  }

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
          arrayLayers.add(new TiledLayer(layer, this));
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

}