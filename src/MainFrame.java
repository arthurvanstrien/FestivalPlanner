import javax.swing.*;
import java.awt.*;

/**
 * Created by dionb on 20-2-2017.
 */
public class MainFrame extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Java2D");
        frame.setDefaultCloseOperation(3);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | 6);
        frame.setContentPane(new MainFrame());
        frame.setVisible(true);
    }

    Camera camera = new Camera(this);
    TiledMap tm;
    public MainFrame() {
        tm = new TiledMap();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(this.camera.getTransform(this.getWidth(), this.getHeight()));
        tm.draw(g2d);
    }
}
