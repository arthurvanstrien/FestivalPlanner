import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Robin on 13-3-2017.
 */
public class VisitorImageList {
    private static ArrayList<BufferedImage> images = new ArrayList<>();

    public static void addImage(BufferedImage bi) {
        images.add(bi);
    }

    public static BufferedImage getImageAtIndex(int index){
            return images.get(index);
    }
}
