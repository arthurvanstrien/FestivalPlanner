package edu.a3.festival_planner.agenda;

import edu.a3.festival_planner.simulator.Location;

import java.io.Serializable;

/**
 * Created by dionb on 6-2-2017.
 */
public class Stage implements Serializable, Comparable {

  private String name;
  private Location location;
  private int surfaceArea;

  public Stage(String name) {
    this.name = name;
  }

  public Stage(String name, int surfaceArea) {
    this.name = name;
      switch (name) {
          case "Main Stage":
              location = Location.STAGE_1;
              break;
          case "Second Stage":
              location = Location.STAGE_2;
              break;
          default:
              location = Location.STAGE_3;
              break;
      }
    this.surfaceArea = surfaceArea;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSurfaceArea() {
    return surfaceArea;
  }

  public void setSurfaceArea(int surfaceArea) {
    this.surfaceArea = surfaceArea;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(Object o) {
    Stage comparableStage = (Stage) o;
    return comparableStage.getSurfaceArea() - this.getSurfaceArea();
  }

  public Location getLocation() {
    return location;
  }
}
