package edu.a3.festival_planner.simulator;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 12-3-2017.
 */
public class Visitor implements Drawable {

  private final int red = 3329;
  private final int green = 3331;

  private Location currentLocation;
  private Location currentDestination;
  Point2D position;
  Point2D destination;
  Point2D inBetweenDestination;
  List<Point> path;
  int currentStepInPath = 0;
  BreadthFirstSearch bfs;
  boolean newDestination = false;
  long totalPastTime = 0;
  private BufferedImage image;
  private double angle;
  private double speed;
  private boolean isOnLocation;
  private int imageDiameter;

//  public Visitor(ArrayList<Drawable> drawings, TiledLayer walklayer, Point2D position) {
//    if (canSpawnOnLocation(drawings, walklayer, position)) {
//      speed = 10;
//      angle = 0;
//      this.position = position;
//      destination = position;
//      appointImage();
//      isOnLocation = false;
//    }
//  }

  /**
   * Constructor to spawn a visitor at the entrance
   * @param drawings
   * @param walklayer
   */
  public Visitor(ArrayList<Drawable> drawings, TiledLayer walklayer, ArrayList<AreaLayer> entrances){
      speed = 10;
      angle = 0;
      this.position = spawnOnEntrance(drawings,walklayer,entrances);
      destination = new Point2D.Double(0, 0);
      appointImage();
      isOnLocation = false;
  }

  public Visitor(ArrayList<Drawable> drawings, TiledLayer walklayer, ArrayList<AreaLayer> entrances, BreadthFirstSearch bfs){
    speed = 5;
    angle = Math.PI;
    this.position = spawnOnEntrance(drawings,walklayer,entrances);
    appointImage();
    isOnLocation = false;
    this.bfs = bfs;
    setNewDestination();
    destination = new Point2D.Double(2800, 1500);
  }

  /**
   * gebruik data uit de Area entrance om te bepalen waar de visitors mogen spawnen, let op, zet collision wel aan
   */
  private Point2D spawnOnEntrance(ArrayList<Drawable> drawings, TiledLayer walklayer,ArrayList<AreaLayer> entrances) {
    Point2D point= null;
    Area entrance = entrances.get(0).getEntrances().get(0);
    point = new Point2D.Double(entrance.getX() + (entrance.getWidth() - 50 * Math.random()), entrance.getY() + (entrance.getHeigt() * Math.random()));
    return point;
  }

  /**
   * checks if a visitor can spawn on this location
   */
  public boolean canSpawnOnLocation(ArrayList<Drawable> drawings, TiledLayer walklayer) {
    return !collides(drawings, walklayer, position);
  }

  /**
   * Draws the visitor.
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
  public void update(ArrayList<Drawable> drawings, TiledLayer walklayer, long pastTime) {
    totalPastTime += pastTime;
    Point2D newPosition;
    if (isOnLocation) {
      /**
       * Set een timer die na een n.t.b. tijd de dance.activiteit uit en een nieuwe destination zetten.
       */
    } else {
      /**
       * code met if in objectlayer van een Location, isOnLocation = true;
       * set current location to location van objectlayer.
       *
       */
      if (newDestination) {
        //the new path gets calculated and the inBetweenDestination gets set to the next inBetweedDestination
        newDestination = false;
        path = bfs.searchShortestPath(new Point((int) position.getX() / 32, (int) position.getY() / 32),
                new Point((int) destination.getX() / 32, (int) destination.getY() / 32));
        currentStepInPath = 0;
        if (currentStepInPath < path.size()) {
          inBetweenDestination = new Point2D.Double(path.get(currentStepInPath).getX() * 32,
              path.get(currentStepInPath).getY() * 32);
        } else {
          isOnLocation = true;
        }
      } else {
        //check if het visitor is at the current inBetweenDestination if true then the next inBetweenDestination is set
        if (position.getX() >= (inBetweenDestination.getX() - 8) && position.getX() <= (
            inBetweenDestination.getX() + 8) && position.getY() >= (inBetweenDestination.getY() - 8)
            && position.getY() <= (inBetweenDestination.getY() + 8)) {
          if (currentStepInPath < path.size()) {
            inBetweenDestination = new Point2D.Double(path.get(currentStepInPath).getX() * 32,
                path.get(currentStepInPath).getY() * 32);
            currentStepInPath++;
          } else {
            isOnLocation = true;
          }
        }
      }
    }

    //De berekening van de nieuwe angle
    double dx = inBetweenDestination.getX() - position.getX();
    double dy = inBetweenDestination.getY() - position.getY();
    double newAngle = Math.atan2(dy, dx);
    double difference = newAngle - angle;

    if (difference > Math.PI || difference < -Math.PI) {
      difference = -((difference) % (2 * Math.PI));
    }

    //Wijzigt de angle van het visitor
    if (difference > 1) {
      angle += 1;
    } else if (difference < -1) {
      angle -= 1;
    } else {
      angle = newAngle;
    }

    //Berekent de nieuwe positie van de visitor
    newPosition = new Point2D.Double(
        position.getX() + speed * Math.cos(angle),
        position.getY() + speed * Math.sin(angle));

    if (!collides(drawings, walklayer, newPosition)) {
      position = newPosition;
    } else {
      if(Math.random() > 0.5) {
        angle += 2;
      } else {
        angle -= 2;
      }
    }
  }

  /**
   * Checks for collision with unwalkable paths and other drawables
   */
  public boolean collides(ArrayList<Drawable> drawings, TiledLayer walklayer, Point2D newPosition) {
    if(newPosition.getY() > 99 * 32) {
      newPosition = new Double(newPosition.getX(), 99 * 32);
    } else if(newPosition.getX() > 99 * 32) {
      newPosition = new Double(99 * 32, newPosition.getX());
    }
      int currentTile = walklayer.data2D[ (int)Math.floor(newPosition.getY() / 32)][(int) Math.floor(newPosition.getX()
          / 32)];

      if (currentTile == red) {
        //System.out.println("Collision met map");
        return true;
      } else {
        for (Drawable drawing : drawings) {
          if (drawing == this) {
            continue;
          }
          if (newPosition.distance(drawing.getPosition()) < imageDiameter) {
            //System.out.println("Collision met drawing");
            return true;
          }
        }
      }
    return false;
  }


  /**
   * Lets teh visitor dance within tebounds of the stage
   *
   * @param drawings --> to check for collision
   */
  public void dance(ArrayList<Drawable> drawings) {
    /**
     * Code that lets teh visitor dance;
     */
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
    imageDiameter = 8;
    int randomNumber = (int) (Math.random() * 100);
    if (randomNumber < 25) {
      image = VisitorImageList.getImageAtIndex(0);
    } else if (randomNumber < 50) {
      image = VisitorImageList.getImageAtIndex(1);
    } else if (randomNumber < 75) {
      image = VisitorImageList.getImageAtIndex(2);
    } else {
      image = VisitorImageList.getImageAtIndex(3);
    }

  }

  public void setNewDestination() {
    int number = (int)(Math.random() * 100);
    if (number < 2) {
      currentDestination = Location.EXIT;
    } else if (number < 7) {
      currentDestination = Location.TOILET_2;
    } else if (number < 10) {
      currentDestination = Location.TOILET_1;
    } else if (number < 40) {
      currentDestination = Location.STAGE_1;
    } else if (number < 70) {
      currentDestination = Location.STAGE_2;
    } else {
      currentDestination = Location.STAGE_3;
    }
    isOnLocation = false;
    newDestination = true;
  }

  public Location getCurrentDestination() {
    return currentDestination;
  }

  public boolean getAtDestination() {
    return isOnLocation;
  }
}
