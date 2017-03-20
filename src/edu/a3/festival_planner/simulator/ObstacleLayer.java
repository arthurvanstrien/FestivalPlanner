package edu.a3.festival_planner.simulator;

import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Created by dionb on 20-3-2017.
 */
public class ObstacleLayer {
  private TiledMap map;
  private ArrayList<Obstacle> stages;
  private ArrayList<Obstacle> entrances;
  private ArrayList<Obstacle> locations;
  private ArrayList<Obstacle> allObstacles;
  private int amountStages = 0;
  private int amountEntrances = 0;
  private int amountLocations = 0;

  public ObstacleLayer(JsonObject layer, TiledMap map ) {
    this.map = map;
    stages = new ArrayList<>();
    entrances = new ArrayList<>();
    locations = new ArrayList<>();
    allObstacles = new ArrayList<>();

    for(int i = 0; i < layer.getJsonArray("objects").size(); i++) {
        JsonObject obstacle = layer.getJsonArray("objects").getJsonObject(i);
        if(obstacle.getString("type").equals("Stage")) {
          //type stage so it's added to the stage list
          amountStages++;
          stages.add(new Obstacle(obstacle));
        } else if(obstacle.getString("type").equals("Entrance")) {
          //Type entrance so it's added to the entrances list
          amountEntrances++;
          entrances.add(new Obstacle(obstacle));
        } else {
          //None of the above type's so it's added to the location list for other obstacles
          amountLocations++;
          locations.add(new Obstacle(obstacle));
        }
    }

    //Every separate list is added to the complete collection
    allObstacles.addAll(stages);
    allObstacles.addAll(entrances);
    allObstacles.addAll(locations);
  }

  public void draw(Graphics2D g2d) {
    for(Obstacle o : allObstacles) {
      o.draw(g2d);
    }
  }
}
