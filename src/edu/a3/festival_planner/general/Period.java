package edu.a3.festival_planner.general;

import java.util.HashSet;

/**
 * Created by robin on 6-2-2017.
 */
public class Period {

  private Time beginTime;
  private Time endTime;

  public Period(Time beginTime, Time endTime) {
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  public Time getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Time beginTime) {
    this.beginTime = beginTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  /**
   * Returns true if the given time falls between the given begin and end time.
   */
  public static boolean checkIfTimeIsInPeriod(Time time, Time beginTime, Time endTime) {
    try {
      return time.toSeconds() >= beginTime.toSeconds() && time.toSeconds() < endTime.toSeconds();
    } catch (NullPointerException e) {
      return false;
    }
  }

  private Time getPeriodDurationInTime() {
    int hours = endTime.getHour() - beginTime.getHour();
    int minutes = endTime.getMinute() - beginTime.getMinute();
    return new Time(hours, minutes);
  }

  /**
   * Returns HashSet container all times which fall between the given begin and end time.
   */
  public static HashSet<Time> getTimes(Time beginTime, Time endTime) {
    Time[] times = new Time[48];
    for (int i = 0; i < 48; i++) {
      int minutes = i * 30;
      int hours = minutes / 60;
      times[i] = new Time(hours, minutes % 60);
    }
    HashSet<Time> timeHashSet = new HashSet<>();

    for (int i = 0; i < 48; i++) {
      if (Period.checkIfTimeIsInPeriod(times[i], beginTime, endTime)) {
        timeHashSet.add(times[i]);
      }
    }
    return timeHashSet;
  }
}
