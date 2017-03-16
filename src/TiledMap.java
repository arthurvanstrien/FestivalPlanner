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
    ArrayList<TiledLayer> arrayLayers;
    ArrayList<BufferedImage> arrayImages;




    public TiledMap() {
        JsonObject jo = null;
        arrayLayers = new ArrayList<>();
        arrayImages = new ArrayList<>();
        try (
                //All data gets read from the jason file and saved in a JsonObject named jo
                InputStream is = new FileInputStream("maps/Map1.json");
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

            try {
                img = ImageIO.read(getClass().getResource(photoName));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(photoName);
            }

            //The array with images gets filled with the right amount of null
            while(arrayImages.size() < gid + jsonTileSet.getInt("tilecount")) {
                arrayImages.add(null);
            }

            //The array with images gets filled with the right subimages on the right index
            for(int y = 0; y + tileHeight <= photoHeight; y += tileHeight) {
                for(int z = 0; z + tileWidth <= photoWidth; z += tileWidth) {
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

        while(it1.hasNext()) {
            TiledLayer tl = (TiledLayer)it1.next();
            g2d.drawImage((Image)tl.getImage(), new AffineTransform(), null);
        }
    }

    //draws the layers next to the original map so you can see if each layer has been correctly displayed
    public void testDraw(Graphics2D g2d) {
        Iterator<TiledLayer> it1 = arrayLayers.iterator();
        AffineTransform tx = new AffineTransform();
        int layers = arrayLayers.size();
        int index = 1;
        int movedX = 0;
        draw(g2d);
        while(it1.hasNext()) {
            TiledLayer t1 = (TiledLayer) it1.next();
            if(index == 1) {
                g2d.translate(t1.width * 32, 0);
                movedX += t1.width * 32;
            }
            if(index % 4 == 0 && index != 0) {
                g2d.translate(-movedX, t1.height * 32);
                movedX = 0;
            }
            g2d.drawImage((Image) t1.getImage(), tx, null);
            g2d.translate(t1.width * 32, 0);
            movedX += t1.width * 32;
            index++;
        }
    }
}