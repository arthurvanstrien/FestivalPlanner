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

  public boolean isBefore(Time time){
    return toSeconds() < time.toSeconds();
  }

  public boolean isAfter(Time time){
    return toSeconds() > time.toSeconds();
  }

  public boolean isTheSame(Time time){
    return toSeconds() == time.toSeconds();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Time time = (Time) o;

    return toSeconds() == time.toSeconds();
  }

  @Override
  public int hashCode() {
    return toSeconds();
  }
}