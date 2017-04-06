package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.general.Period;
import edu.a3.festival_planner.general.Time;

/**
 * Created by Arthur on 5-3-2017.
 */
class TestPeriod {

  public static int runTest() {
    int errors = 0;
    Time beginTime = new Time(10, 10);
    Time endTime = new Time(11, 10);
    Time secondBeginTime = new Time(11, 30);
    Time secondEndTime = new Time(13, 0);
    Period period = new Period(beginTime, endTime);

    if (!period.getBeginTime().equals(beginTime)) {
      System.out
          .println("There is a problem with the getBeginTime method of the Period class.");
      errors++;
    }

    if (!period.getEndTime().equals(endTime)) {
      System.out
          .println("There is a problem with the getEndTime method of the Period class.");
      errors++;
    }

    period.setBeginTime(secondBeginTime);
    if (!period.getBeginTime().equals(secondBeginTime)) {
      System.out.println(
          "There is a problem with the getBeginTime or setBeginTime method of the Period class.");
      errors++;
    }

    period.setEndTime(secondEndTime);
    if (!period.getEndTime().equals(secondEndTime)) {
      System.out.println(
          "There is a problem with the getEndTime of setEndTime method of the Period class.");
      errors++;
    }

    return errors;
  }
}
