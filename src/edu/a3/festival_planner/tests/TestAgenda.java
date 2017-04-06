package edu.a3.festival_planner.tests;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.agenda.Artist;
import edu.a3.festival_planner.agenda.Show;

import java.util.ArrayList;

/**
 * Created by Arthur on 13-3-2017.
 */
class TestAgenda {

  public static int runTest() {
    int errors = 0;

    ArrayList<Artist> artists = new ArrayList<>();

    Artist artist1 = new Artist("Piet", "Rock");
    Artist artist2 = new Artist("Kees", "Rock");
    Artist artist3 = new Artist("Jan", "Rock");
    Artist artist4 = new Artist("Klaas", "Rock");
    Artist artist5 = new Artist("Koos", "Rock");
    Artist artist6 = new Artist("Cornelis", "Rock");
    Artist artist7 = new Artist("Willem", "Rock");
    Artist artist8 = new Artist("Constantijn", "Rock");
    Artist artist9 = new Artist("Alexander", "Rock");
    Artist artist10 = new Artist("Mark", "Rock");

    artists.add(artist1);
    artists.add(artist2);
    artists.add(artist3);
    artists.add(artist4);
    artists.add(artist5);
    artists.add(artist6);
    artists.add(artist7);
    artists.add(artist8);
    artists.add(artist9);
    artists.add(artist10);

    //Create a new Agenda.Agenda with the given values.
    Agenda agenda = new Agenda(null, "Test", artists);

    if (agenda.getArtists().size() != 10) {
      System.out
          .println("There is a problem with the getArtists method of the Agenda class.");
      errors++;
    }

    ArrayList<Artist> addArtists = new ArrayList<>();
    Artist artist11 = new Artist("Lucy", "Country");
    addArtists.add(artist11);
    agenda.addArtists(addArtists);
    if (agenda.getArtists().size() != 11) {
      System.out
          .println("There is a problem with the addArtists method of the Agenda class.");
      errors++;
    }

    if (!agenda.getName().equals("Test")) {
      System.out.println("There is a problem with the getName method of the Agenda class.");
      errors++;
    }

    agenda.setName("Not a test");
    if (!agenda.getName().equals("Not a test")) {
      System.out.println("There is a problem with the setName method of the Agenda class.");
      errors++;
    }

    if (agenda.getShows().length != 48) {
      System.out.println("There is a problem with the getShows method of the Agenda class.");
      errors++;
    }

    Show shows[][] = agenda.getShows();
    for (int i = 0; i < shows.length; i++) {
      if (agenda.getShows()[i].length != 3) {
        System.out
            .println("There is a problem with the getShows method of the Agenda class. " +
                "The problem occured with show" + i + ".");
        errors++;
      }
    }

    if (agenda.getStages().size() != 3) {
      System.out
          .println("There is a problem with the getStages method of the Agenda class.");
      errors++;
    }

    if (agenda.getTimes().length != 48) {
      System.out.println("There is a problem with the getTimes method of the Agenda class.");
      errors++;
    }

    return errors;
  }
}