package edu.a3.festival_planner.agenda;

import java.io.Serializable;

/**
 * Created by dionb on 6-2-2017.
 */
abstract class Human implements Serializable {

  private String name;

    Human(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
