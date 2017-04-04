package edu.a3.festival_planner.simulator;


import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.agenda.Show;
import edu.a3.festival_planner.general.Time;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import sun.text.CodePointIterator;

/**
 * Created by robin on 12-3-2017.
 */
public class Visitor implements Drawable {

  private final int red = 3329;
  private final int green = 3331;
  private final double pathAccurency = (Math.random() * 8) + 8;

  private Location previousDestination;
  private Location currentDestination;
  Point2D position;
  Point2D destination;
  Point2D inBetweenDestination;
  List<Point> path;
  int currentStepInPath = 0;
  BreadthFirstSearch bfs;
  boolean newDestination = false;
  long totalElapsedTime = 0;
  private BufferedImage image;
  private double angle;
  private double speed;
  private boolean isOnLocation;
  private int imageDiameter;
  private final int mapWith = 100;
  private final int mapHeight = 100;
  private int collisionCounter = 0;
  private Time arrivalTime;
  private HashMap<Location,Show> shows;
  private boolean hasPooped;
  private boolean debug = false;
  private boolean exited = false;
  private double blatter = 0;


  public Visitor(ArrayList<Drawable> drawings, TiledLayer walklayer, ArrayList<AreaLayer> entrances,
      BreadthFirstSearch bfs, Agenda agenda, Time time, TiledMap tiledMap) {
    speed = 2;
    angle = Math.PI;
    this.position = spawnOnEntrance(drawings, walklayer, entrances);
    appointImage();
    isOnLocation = false;
    this.bfs = bfs;
    setNewDestination(agenda, time, tiledMap);
    //destination = new Point2D.Double(2800, 1500);
    hasPooped = false;
  }

  /**
   * gebruik data uit de Area entrance om te bepalen waar de visitors mogen spawnen, let op, zet
   * collision wel aan
   */
  private Point2D spawnOnEntrance(ArrayList<Drawable> drawings, TiledLayer walklayer,
      ArrayList<AreaLayer> entrances) {
    Point2D point = null;
    Area entrance = entrances.get(0).getEntrances().get(0);
    point = new Point2D.Double(entrance.getX() + (entrance.getWidth() - 50 * Math.random()),
        entrance.getY() + (entrance.getHeigt() * Math.random()));
    return point;
  }

  /**
   * checks if a visitor can spawn on this location
   */
  public boolean canSpawnOnLocation(ArrayList<Drawable> drawings, TiledLayer walklayer) {
    return !collides(drawings, walklayer, position);
  }

  public boolean hasSpawned() {
    if (position == null) {
      return false;
    } else {
      return true;
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
  public void update(ArrayList<Drawable> drawings, TiledLayer walklayer, double elapsedTime, Agenda agenda, Time time, TiledMap tiledMap) {
    totalElapsedTime += elapsedTime;

    if (isOnLocation) {
      switch (currentDestination) {
        case STAGE_1: {
          if (debug) {
            System.out.println(
                time.toString() + " klaar om:" + shows.get(Location.STAGE_1).getEndTime());
          }
          if (time.isAfter(shows.get(Location.STAGE_1).getEndTime()) && !time
              .isBefore(shows.get(Location.STAGE_1).getBeginTime())) {
            setNewDestination(agenda, time, tiledMap);
          } else if (shows.get(Location.STAGE_1) == null) {
            setNewDestination(agenda, time, tiledMap);
          }
        }
        break;
        case STAGE_2: {
          if (debug) {
            System.out.println(
                time.toString() + " klaar om:" + shows.get(Location.STAGE_2).getEndTime());
          }
          if (time.isAfter(shows.get(Location.STAGE_2).getEndTime()) && !time
              .isBefore(shows.get(Location.STAGE_2).getBeginTime())) {
            setNewDestination(agenda, time, tiledMap);
          } else if (shows.get(Location.STAGE_2) == null) {
            setNewDestination(agenda, time, tiledMap);
          }

        }
        break;
        case STAGE_3: {
          if (debug) {
            System.out.println(
                time.toString() + " klaar om:" + shows.get(Location.STAGE_3).getEndTime());
          }
          if (time.isAfter(shows.get(Location.STAGE_3).getEndTime()) && !time
              .isBefore(shows.get(Location.STAGE_3).getBeginTime())) {
            setNewDestination(agenda, time, tiledMap);
          } else if (shows.get(Location.STAGE_3) == null) {
            setNewDestination(agenda, time, tiledMap);
          }

        }
        break;
        case TOILET_1: {
          int poopinTime = (int) ((Math.random() * (2 * 60))) + 60;
          if (time.toSeconds() > arrivalTime.toSeconds() + poopinTime) {
            setNewDestination(agenda, time, tiledMap);
            hasPooped = true;
            blatter = 0;
          }
          if (debug) {
            System.out.println("gestart om" + arrivalTime);
          }
        }
        break;
        case TOILET_2: {
          int poopinTime = (int) (Math.random() * (2 * 60) + 60);
          if (time.toSeconds() > arrivalTime.toSeconds() + poopinTime) {
            setNewDestination(agenda, time, tiledMap);
            hasPooped = true;
            blatter = 0;
          }
          if (debug) {
            System.out.println("gestart om" + arrivalTime);
          }
        }
        break;
        case EXIT: {
          exited = true;

        }
        break;
        case GRASS:
          if (time.isAfter(new Time(arrivalTime.getHour(), arrivalTime.getMinute() + 5))) {
            if(debug) {
              System.out.println("klaar met gras");
            }
            setNewDestination(agenda, time, tiledMap);
          }
          ;
          break;
        default:
          break;

      }
      /**
       * Set een timer die na een n.t.b. tijd de dance.activiteit uit en een nieuwe destination zetten.
       */
      //setNewDestination(agenda);
    } else {
      /**
       * code met if in objectlayer van een Location, isOnLocation = true;
       * set current location to location van objectlayer.
       *
       */
      if (newDestination) {
        //the new path gets calculated and the inBetweenDestination gets set to the next inBetweedDestination
        newDestination = false;
        if (debug) {
          System.out.println(
              position.getX() + "   " + position.getY() + "   " + destination.getX() + "   "
                  + destination.getY() + "   " + currentDestination);
        }
        if(!walklayer.containsPoint(position)) {
          if(debug) {
            System.out.println("Punt niet op map, ander punt zoeken");
          }
          Point2D closestOnMap = walklayer.getNearestPoint(position);
          path = bfs
              .searchShortestPath(
                  new Point((int) Math.floor(closestOnMap.getX()) / 32,
                      (int) Math.floor(closestOnMap.getY()) / 32),
                  new Point((int) Math.floor(destination.getX() / 32),
                      (int) Math.floor(destination.getY()) / 32));
        }else {
          path = bfs
              .searchShortestPath(
                  new Point((int) Math.floor(position.getX()) / 32,
                      (int) Math.floor(position.getY()) / 32),
                  new Point((int) Math.floor(destination.getX() / 32),
                      (int) Math.floor(destination.getY()) / 32));
        }
        currentStepInPath = 0;
        if (path.size() < 2) {
          previousDestination = currentDestination;
          isOnLocation = true;
          arrivalTime = time;
        } else if (currentStepInPath < path.size()) {
          inBetweenDestination = new Point2D.Double(
              (path.get(currentStepInPath).getX() * 32) + 10,
              (path.get(currentStepInPath).getY() * 32) + 10);
        }

      } else {
        //check if het visitor is at the current inBetweenDestination if true then the next inBetweenDestination is set
        if (path.size() - currentStepInPath < 2) {
          previousDestination = currentDestination;
          isOnLocation = true;
          arrivalTime = time;
          totalElapsedTime = 0;
        } else {
          if (position.getX() >= (inBetweenDestination.getX() - pathAccurency)
              && position.getX() <= (
              inBetweenDestination.getX() + pathAccurency) && position.getY() >= (
              inBetweenDestination.getY() - pathAccurency)
              && position.getY() <= (inBetweenDestination.getY() + pathAccurency)) {
            if (currentStepInPath < path.size()) {
              nextPointInPath();
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
        Point2D newPosition = new Point2D.Double(
            position.getX() + elapsedTime * speed * Math.cos(angle),
            position.getY() + elapsedTime * speed * Math.sin(angle));

        if (!collidesWithVisitor(drawings, newPosition)) {
          if (!collidesWithTiles(walklayer, newPosition)) {
            position = newPosition;
            collisionCounter = 0;
          } else {
            rotateLikeADumbAss();
          }
        } else {
          evadeVisitor(newPosition, drawings);
        }

        blatter += (elapsedTime * speed) / 10000;
      }
    }
  }


  private void nextPointInPath() {
    if(currentStepInPath < path.size()) {
      inBetweenDestination = new Point2D.Double(path.get(currentStepInPath).getX() * 32,
          path.get(currentStepInPath).getY() * 32);
      currentStepInPath++;
    } else {
      //System.out.println("Visitor weet niets meer");
    }
  }

  /**
   * Let a visitor evade another visitor, we think
   * @param newPosition
   * @param drawings
   */
  public void evadeVisitor(Point2D newPosition,ArrayList<Drawable> drawings){
    angle = angle % 360;
    if(angle >= 45 && angle <= 135){
      newPosition = new Double(newPosition.getX() + 2, newPosition.getY());
    }else if(angle > 135 && angle <= 225){
      newPosition = new Double(newPosition.getX(), newPosition.getY() - 2);
    }else if(angle > 225 && angle <= 315){
      newPosition = new Double(newPosition.getX() - 2, newPosition.getY());
    }else {
      newPosition = new Double(newPosition.getX(), newPosition.getY() + 2);
    }
    if(!collidesWithVisitor(drawings,newPosition)) {
      position = newPosition;
    }else {
      if (collisionCounter < 30) {
        rotateLikeADumbAss();
      } else {
        collisionCounter = 0;
        nextPointInPath();
      }
    }
  }


  /**
   * Checks for collision with unwalkable paths and other drawables
   * Old method, do not use.
   */
  public boolean collides(ArrayList<Drawable> drawings, TiledLayer walklayer, Point2D newPosition) {
    if (newPosition.getY() > (mapWith - 1) * 32) {
      newPosition = new Double(newPosition.getX(), 99 * 32);
    } else if (newPosition.getX() > (mapHeight - 1) * 32) {
      newPosition = new Double(99 * 32, newPosition.getX());
    }
    int currentTile = walklayer.data2D[(int) Math.floor(newPosition.getY() / 32)][(int) Math
        .floor(newPosition.getX()
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
   * Let the visitor rotate like a dumbass
   */
  private void rotateLikeADumbAss(){
    if (Math.random() > 0.5) {
      angle += 2;
    } else {
      angle -= 2;
    }
    collisionCounter++;
  }

  /**
   * checks collision with visitors
   */
  public boolean collidesWithVisitor(ArrayList<Drawable> drawings, Point2D newPosition) {
    for (Drawable drawing : drawings) {
      if (drawing == this) {
        continue;
      }
      if (newPosition.distance(drawing.getPosition()) < imageDiameter) {
        //System.out.println("Collision met drawing");
        return true;
      }
    }
    return false;
  }

  /**
   * Checks collision with the map
   */
  public boolean collidesWithTiles(TiledLayer walklayer, Point2D newPosition) {
    if (newPosition.getY() > (mapWith - 1) * 32) {
      newPosition = new Double(newPosition.getX(), (mapWith - 1) * 32);
    }
    if (newPosition.getX() > (mapHeight - 1) * 32) {
      newPosition = new Double((mapWith - 1) * 32, newPosition.getY());
    }
    int currentTile = walklayer.data2D[(int) Math.floor((newPosition.getY())/ 32)][(int) Math
        .floor((newPosition.getX())
            / 32)];
    if (currentTile == red) {
      //System.out.println("Collision met map");
      return true;
    } else {
      return false;
    }
  }


  /**
   * Lets the visitor dance within the bounds of the stage.
   */
  public void dance() {
    /**
     * Code that lets the visitor dance;
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
   * @param time
   */
  @Override
  public void setDestination(Point2D destination, Time time) {
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

  public void setNewDestination(Agenda agenda, Time time, TiledMap tiledMap) {
    Set<Show> shows = agenda.getAllShows();
    HashMap<Location,Show> showMap =  new HashMap<>();
    double totalPopularity = 0;
    double toiletPopularity = 2 * blatter;
    if(!hasPooped){
      totalPopularity += (toiletPopularity + toiletPopularity);
    }

    double fieldPopularity = 0.5;
    totalPopularity += fieldPopularity;
    for (Show s:shows) {
      if(!s.getBeginTime().isBefore(time)) {
        if(time.toSeconds() >= s.getBeginTime().toSeconds()-(60*45)) {
          totalPopularity += s.getExpectedPopularity();
          showMap.put(s.getStage().getLocation(), s);
        }
      }
    }
    this.shows = showMap;
    double popularityCounter = 0;

    double number = (Math.random() * totalPopularity);
    if((showMap.get(Location.STAGE_1)) != null) {
      popularityCounter += ((showMap.get(Location.STAGE_1).getExpectedPopularity()));
    }
    if (number < popularityCounter){
      currentDestination = Location.STAGE_1;
    } else {

      if((showMap.get(Location.STAGE_2)) != null) {
        popularityCounter += ((showMap.get(Location.STAGE_2).getExpectedPopularity()));
      }
      if (number < popularityCounter) {
        currentDestination = Location.STAGE_2;

      } else {
        if((showMap.get(Location.STAGE_3)) != null) {
          popularityCounter += ((showMap.get(Location.STAGE_3).getExpectedPopularity()));
        }
          if(number < popularityCounter) {
          currentDestination = Location.STAGE_3;
          }
          else {
            popularityCounter += fieldPopularity;
            if (number < popularityCounter) {
              if(time.isBefore(new Time(agenda.getEndTime().getHour() - 1, agenda.getEndTime().getMinute()))) {
                currentDestination = Location.GRASS;
              } else {
                currentDestination = Location.EXIT;
              }
            } else {
              if(!hasPooped) {
                popularityCounter += toiletPopularity;
                if (number < popularityCounter) {
                  currentDestination = Location.TOILET_1;
                } else {

                  popularityCounter += toiletPopularity;
                  if (number < popularityCounter) {
                    currentDestination = Location.TOILET_2;
                  }
                }
              }else {
                currentDestination = Location.GRASS;
              }
                }
              }
            }
      }
    destination = tiledMap.enumToPointDestination(currentDestination);
      if(debug) {
        System.out.println(currentDestination + "word vertaald naar");

        System.out.println(destination.getX() + "   " + destination.getY() + " op de map");
      }
    isOnLocation = false;
    newDestination = true;
  }

  public Location getCurrentDestination() {
    return currentDestination;
  }


  public boolean getExited() {
    return exited;
  }

  public void printBlatter() {
    System.out.println(blatter);
  }
}
