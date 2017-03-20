package edu.a3.festival_planner.simulator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by robin on 12-3-2017.
 */
public class Visitor implements Drawable {

  Point2D position;
  Point2D destination;
  private BufferedImage image;
  private double angle;
  private double speed;

  public Visitor(Point2D position) {
    speed = 1;
    angle = 0;
    this.position = position;
    destination = position;
    appointImage();
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

  /**
   * Method is unfinished
   *
   * This will change in the future
   *
   * Updates the location of the visitor.
   *
   * @param drawings contains a list of all other objects that have been drawn. The Method wil cycle
   * through te list to meet all constraints and collisions with the objects.
   */
  @Override
  public void update(ArrayList<Drawable> drawings) {
    //De berekening van de nieuwe angle
    double dx = destination.getX() - position.getX();
    double dy = destination.getY() - position.getY();
    double newAngle = Math.atan2(dy, dx);
    double difference = newAngle - angle;
    /**
     while (difference < -Math.PI)
     difference += 2 * Math.PI;
     while (difference > Math.PI)
     difference -= 2 * Math.PI;
     */
    if (difference > Math.PI || difference < -Math.PI) {
      difference = -((difference) % (2 * Math.PI));
    }

    //Wijzigt de angle van het visitor
    if (difference > 0.1) {
      angle += 0.1;
    } else if (difference < -0.1) {
      angle -= 0.1;
    } else {
      angle = newAngle;
    }

    //Berekent de nieuwe positie van de visitor
    Point2D newPosition = new Point2D.Double(
        position.getX() + speed * Math.cos(angle),
        position.getY() + speed * Math.sin(angle));

    boolean collided = false;
    //Collission detection
    for (Drawable drawing : drawings) {
      if (drawing == this) {
        continue;
      }
      if (drawing.getPosition().distance(newPosition) < 15) {
        collided = true;
        break;
      }
    }

    //zorgt ervoor dat visitor om elkaar heen lopen
    if (!collided) {
      position = newPosition;
    } else {
      angle += 0.2;
    }

  }


  /**
   * @return gives the position of the visitor
   */
  @Override
  public Point2D getPosition() {
    return position;
  }

  /**
   * this method sets the destination.
   *
   * @param destination will be the new destination
   */
  @Override
  public void setDestination(Point2D destination) {
    this.destination = destination;
  }

  /**
   * Gives the visitor a random image of a predefined pool
   */
  private void appointImage() {
    int randomNumber = (int) (Math.random() * 100);
//        if (randomNumber < 25) {
//            try {
//                image = ImageIO.read(this.getClass().getResource("bezoekers/VisitorBlueBlond.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (randomNumber >= 25 && randomNumber <= 50) {
//            try {
//                image = ImageIO.read(this.getClass().getResource("bezoekers/VisitorGreenBlond.png"));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (randomNumber > 50 && randomNumber < 75) {
//            try {
//                image = ImageIO.read(this.getClass().getResource("bezoekers/VisitorBlackBlond.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                image = ImageIO.read(this.getClass().getResource("bezoekers/VisitorRedBlack.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
  }
}
