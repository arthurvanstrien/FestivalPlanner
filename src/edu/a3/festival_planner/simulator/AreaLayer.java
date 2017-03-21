package edu.a3.festival_planner.simulator;

import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.json.JsonObject;

/**
 * Created by dionb on 20-3-2017.
 */
public class AreaLayer {
  private TiledMap map;
  private ArrayList<Area> stages;
  private ArrayList<Area> entrances;
  private ArrayList<Area> locations;
  private ArrayList<Area> allAreas;
  private int amountStages = 0;
  private int amountEntrances = 0;
  private int amountLocations = 0;

  public AreaLayer(JsonObject layer, TiledMap map ) {
    this.map = map;
    stages = new ArrayList<>();
    entrances = new ArrayList<>();
    locations = new ArrayList<>();
    allAreas = new ArrayList<>();

    for(int i = 0; i < layer.getJsonArray("objects").size(); i++) {
        JsonObject area = layer.getJsonArray("objects").getJsonObject(i);
        if(area.getString("type").equals("Stage")) {
          //type stage so it's added to the stage list
          amountStages++;
          stages.add(new Area(area));
        } else if(area.getString("type").equals("Entrance")) {
          //Type entrance so it's added to the entrances list
          amountEntrances++;
          entrances.add(new Area(area));
        } else {
          //None of the above type's so it's added to the location list for other areas
          amountLocations++;
          locations.add(new Area(area));
        }
    }

    //Every separate list is added to the complete collection
    allAreas.addAll(stages);
    allAreas.addAll(entrances);
    allAreas.addAll(locations);
  }

  public void draw(Graphics2D g2d) {
    for(Area o : allAreas) {
      o.draw(g2d);
    }
  }
}
