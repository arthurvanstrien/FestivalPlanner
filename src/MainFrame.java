import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dionb on 20-2-2017.
 */
public class MainFrame extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Java2D");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | 6);
        frame.setContentPane(new MainFrame());
        frame.setVisible(true);
    }

    final int numberOfVisitors = 100;
    ArrayList<Drawable> drawings;
    Camera camera = new Camera(this);
    TiledMap tm;

    public MainFrame() {
        fillVisitorImageList();
        tm = new TiledMap();
        drawings = new ArrayList<>();
        for(int i = 0; i < numberOfVisitors; i++){
            drawings.add(new Visitor(new Point2D.Double(Math.random()*100, Math.random()*100)));
        }





    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));

        //draws map complete
        tm.draw(g2d);

        //draws map complete and with all the layers next to it
        tm.testDraw(g2d);
    }

    private  void fillVisitorImageList(){
        try {
            VisitorImageList.addImage(ImageIO.read(this.getClass().getResource("bezoekers/VisitorBlueBlond.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            VisitorImageList.addImage(ImageIO.read(this.getClass().getResource("bezoekers/VisitorGreenBlond.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            VisitorImageList.addImage(ImageIO.read(this.getClass().getResource("bezoekers/VisitorBlackBlond.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            VisitorImageList.addImage(ImageIO.read(this.getClass().getResource("bezoekers/VisitorRedBlack.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
