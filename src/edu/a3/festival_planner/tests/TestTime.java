package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.general.Time;

/**
 * Created by Arthur on 5-3-2017.
 */
public class TestTime {

  public static int runTest() {
    int errors = 0;
    Time time = new Time(11, 10);

    if (time.toSeconds() != 40200) {
      System.out.println("There is a problem with the toSeconds method of the Agenda.Time class.");
      errors++;
    }

    if (!time.toString().equals("11:10")) {
      System.out.println("There is a problem with the toString method of the Agenda.Time class.");
      errors++;
    }

    if (time.getHour() != 11) {
      System.out.println("There is a problem with the getHour method of the Agenda.Time class.");
      errors++;
    }

    if (time.getMinute() != 10) {
      System.out.println("There is a problem with the getMinute method of the Agenda.Time class.");
      errors++;
    }

    time.setHour(10);
    if (time.getHour() != 10) {
      System.out
          .println(
              "There is a problem with the getHour or setHour method of the Agenda.Time class.");
      errors++;
    }

    time.setMinute(4);
    if (time.getMinute() != 4) {
      System.out
          .println(
              "There is a problem with the getMinute or setMinute method of the Agenda.Time class.");
      errors++;
    }

    return errors;
  }
}
