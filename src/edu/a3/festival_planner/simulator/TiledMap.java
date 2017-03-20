package edu.a3.festival_planner.simulator;
import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class TiledMap {

  private ArrayList<TiledLayer> arrayLayers;
  private ArrayList<BufferedImage> arrayImages;


  public TiledMap(String fileName) {
    JsonObject jo = null;
    arrayLayers = new ArrayList<>();
    arrayImages = new ArrayList<>();
    try (
        //All data gets read from the jason file and saved in a JsonObject named jo
        InputStream is = new FileInputStream(fileName);
        JsonReader jsonReader = Json.createReader(is);
    ) {
      jo = jsonReader.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }

    //The tilesets get read from the JsonObject which is an JsonArray
    JsonArray jsonArraySets = jo.getJsonArray("tilesets");
    //The layers get read from the JsonObject which is an JsonArray
    JsonArray jsonArrayLayers = jo.getJsonArray("layers");

    //In this loop every tileset gets read
    for (int x = 0; x < jsonArraySets.size(); ++x) {
      JsonObject jsonTileSet = jsonArraySets.getJsonObject(x);
      BufferedImage img = null;
      int tileHeight = jsonTileSet.getInt("tileheight");
      int tileWidth = jsonTileSet.getInt("tilewidth");
      int photoHeight = jsonTileSet.getInt("imageheight");
      int photoWidth = jsonTileSet.getInt("imagewidth");
      int gid = jsonTileSet.getInt("firstgid");
      String photoName = jsonTileSet.getString("image");
      System.out.println(photoName);
      try {
        img = ImageIO.read(getClass().getClassLoader().getResource(photoName));
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(photoName);
      }

      //The array with images gets filled with the right amount of null
      while (arrayImages.size() < gid + jsonTileSet.getInt("tilecount")) {
        arrayImages.add(null);
      }

      //The array with images gets filled with the right subimages on the right index
      for (int y = 0; y + tileHeight <= photoHeight; y += tileHeight) {
        for (int z = 0; z + tileWidth <= photoWidth; z += tileWidth) {
          arrayImages.set(gid, img.getSubimage(z, y, tileWidth, tileHeight));
          ++gid;
        }
      }
    }

    //For each layer a TiledLayer object gets made and added to the array of layers
    for (int x = 0; x < jsonArrayLayers.size(); x++) {
      arrayLayers.add(new TiledLayer(jsonArrayLayers.getJsonObject(x), this));
    }

  }

  //draws the complete map
  public void draw(Graphics2D g2d) {
    Iterator<TiledLayer> it1 = arrayLayers.iterator();

    while (it1.hasNext()) {
      TiledLayer tl = (TiledLayer) it1.next();
      g2d.drawImage((Image) tl.getImage(), new AffineTransform(), null);
    }
  }

  public ArrayList<TiledLayer> getArrayLayers() {
    return arrayLayers;
  }

  public ArrayList<BufferedImage> getArrayImages() {
    return arrayImages;
  }
}