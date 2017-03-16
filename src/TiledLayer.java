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

    public TiledLayer(JsonObject jsonLayer, TiledMap map) {
        this.map = map;
        isDirty = true;
        this.height = jsonLayer.getInt("height");
        this.width = jsonLayer.getInt("width");
        data2D = new int[height][width];

        JsonArray data = jsonLayer.getJsonArray("data");

        int index = 0;

        //the right tiles get connected to the structure (data is a 1D array so with this loop it gets structured in a 2D array)
        for(int y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                data2D[y][x] = data.getInt(index);
                ++index;
            }
        }

        this.img = this.getImage();
    }

    //if isDirty is true then the layer gets redrawn
    public BufferedImage getImage() {
        BufferedImage img = new BufferedImage(width * 24, height * 24, 2);
        Graphics2D g2 = img.createGraphics();
        if(isDirty) {
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    g2.drawImage((Image) map.arrayImages.get(this.data2D[y][x]), x * 24, y * 24, (ImageObserver) null);
                }
            }
            isDirty = false;
            return img;
        } else {
            return this.img;
        }
    }

    //set isDirty true so that the layer gets redrawn
    public void setDirty() {
        isDirty = true;
    }
}