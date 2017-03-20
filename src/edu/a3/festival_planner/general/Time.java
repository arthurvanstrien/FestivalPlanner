package edu.a3.festival_planner.general;

import java.io.Serializable;

/**
 * Created by robin on 6-2-2017.
 */
public class Time implements Serializable {

  private int hour;
  private int minute;

  public Time(int hour, int minute) {
    this.hour = hour;
    this.minute = minute;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  /**
   * Returns time converted to seconds.
   */
  public int toSeconds() {
    return (hour * 3600) + (minute * 60);
  }

  @Override
  public String toString() {
    return String.format("%02d", hour) + ":" + String.format("%02d", minute);
  }
}