package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.agenda.Artist;
import edu.a3.festival_planner.agenda.Show;
import edu.a3.festival_planner.agenda.Stage;
import edu.a3.festival_planner.general.Time;

import java.util.ArrayList;

/**
 * Created by dionb on 13-2-2017.
 * Edited by Arthur van Strien on 05-03-2017.
 */
class TestShow {

  public static int runTest() {
    int amountOfErrors = 0;

    ArrayList<Artist> artists = new ArrayList<>();
    artists.add(new Artist("artist", "genre"));
    artists.add(new Artist("artist2", "genre2"));
    Time beginTime = new Time(12, 0);
    Time endTime = new Time(16, 0);
    Stage stage = new Stage("Main Stage", 50);
    Show s1 = new Show("Jimi Hendrix Experience", beginTime, endTime, stage, artists, 5);

        /*CRASH IN CODE ON LINE21 BELOW. REASON UNKNOWN:
        if(!s1.toString().equals("Begint om: 12:00:00 eindigd om: 16:00:00 Agenda.Stage{name='stage'} [Artiest: artist Genre: genre, Artiest: artist2 Genre: genre2]")) {
            if(!s1.getBeginTime().equals(new Agenda.Time(12, 0))) {
                System.out.println("There is a problem with the begin time in the getBeginTime method from the Agenda.Show class .");
                amountOfErrors++;
            }

            if(!s1.getEndTime().equals(new Agenda.Time(16, 0))) {
                System.out.println("There is a problem with the getEndTime method from the Agenda.Show class.");
                amountOfErrors++;
            }

            if(s1.getExpectedPopularity() != 0.5) {
                System.out.println("There is a problem with the getExpectedPopularity method of the Agenda.Show class.");
                amountOfErrors++;
            }
        }

        return amountOfErrors;*/

    return 9000; //To make sure someone fixes the error above.
  }
}