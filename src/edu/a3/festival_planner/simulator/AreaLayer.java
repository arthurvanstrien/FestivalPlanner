package edu.a3.festival_planner.simulator;

import javax.json.JsonObject;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dionb on 20-3-2017.
 */
class AreaLayer {
    private ArrayList<Area> stages;
  private ArrayList<Area> entrances;
  private ArrayList<Area> locations;
  private ArrayList<Area> stands;
  private ArrayList<Area> toilets;
    private ArrayList<Area> allAreas;
    private int amountFields = 0;

  public AreaLayer(JsonObject layer, TiledMap map ) {
      TiledMap map1 = map;
    stages = new ArrayList<>();
    entrances = new ArrayList<>();
    locations = new ArrayList<>();
    stands = new ArrayList<>();
    toilets = new ArrayList<>();
    allAreas = new ArrayList<>();
      ArrayList<Area> fields = new ArrayList<>();

    for(int i = 0; i < layer.getJsonArray("objects").size(); i++) {
        JsonObject area = layer.getJsonArray("objects").getJsonObject(i);
        if(area.getString("type").equals("Stage")) {
          //type stage so it's added to the stage list
            int amountStages = 0;
            amountStages++;
          stages.add(new Area(area));
        } else if(area.getString("type").equals("Entrance")) {
          //Type entrance so it's added to the entrances list
            int amountEntrances = 0;
            amountEntrances++;
          entrances.add(new Area(area));
        } else if (area.getString("type").equals("Stand")){
          //Type Stand so it's  added to the stand list
            int ammountStands = 0;
            ammountStands++;
          stands.add(new Area(area));
        }else if(area.getString("type").equals("Toilet")){
            int ammountToilets = 0;
            ammountToilets++;
          toilets.add(new Area(area));
        }
//        else if(area.getString("type").equals("freefield")){
//          ammountFields++;
//          fields.add(new Area(area));
//        }
        else{
          //None of the above type's so it's added to the location list for other areas
            int amountLocations = 0;
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

  public ArrayList<Area> getEntrances(){
    return entrances;
  }

  public ArrayList<Area> getStages() {return stages;}

  public ArrayList<Area> getOtherAreas() {return locations;}

  public ArrayList<Area> getStandArea(){return stands;}

  public ArrayList<Area> getToiletArea(){return toilets;}

  public ArrayList<Area> getFields(){return toilets;}
}
