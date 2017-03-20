package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.agenda.Artist;

/**
 * Created by Arthur on 13-2-2017.
 */
public class TestArtist {

  public static int runTest() {
    int errors = 0;

    //Create a new Agenda.Artist with the given values.
    Artist artist = new Artist("Jantje Smit", "Baggerzooi");

    //Check if the given values are correct by requesting them.
    if (!artist.toString().equals("Artiest: Jantje Smit, Genre: Baggerzooi")) {
      System.out.println("There is a problem with the toString method of the Agenda.Artist class");
      errors++;
    }

    if (!artist.getGenre().equals("Baggerzooi")) {
      System.out.println("There is a problem with the getGenre method of the Agenda.Artist class");
      errors++;
    }

    //Check if the set methods work as they supposed to work.
    artist.setGenre("Rock");
    if (!artist.getGenre().equals("Rock")) {
      System.out
          .println(
              "There is a problem with the getGenre or setGenre method of the Agenda.Artist class");
      errors++;
    }

    return errors;
  }
}
