package edu.a3.festival_planner.simulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by robin on 12-3-2017.
 */
public class Toilet implements Drawable {

  private int angle;
  Point2D position;
  private BufferedImage image;

  Toilet(Point2D position, int angle) {
    this.position = position;
    this.angle = angle;
    try {
      image = ImageIO.read(this.getClass().getClassLoader().getResource("facilities/Toilet.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Draws the visitor.
   *
   * @param g2d is the graphics object the visitor wil be drawn on.
   */
  @Override
  public void draw(Graphics2D g2d) {
    AffineTransform tx = new AffineTransform();
    tx.translate(position.getX(), position.getY());
    tx.rotate(angle);
    tx.translate(-image.getWidth() / 2, -image.getHeight() / 2);
    g2d.drawImage(image, tx, null);
  }

  @Override
  public void update(ArrayList<Drawable> drawings, TiledLayer walklayer, double pastTime) {

  }

  @Override
  public Point2D getPosition() {
    return position;
  }

  @Override
  public void setDestination(Point2D destination) {

  }
}
