import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dionb on 6-2-2017.
 */
public class Agenda implements Serializable {

  private String name;
  private Show[][] shows;
  private Time[] times;
  private transient AgendaView agendaView;
  private ArrayList<Stage> stages;
  private ArrayList<Artist> artists;

  public Agenda(Main main, String name, ArrayList<Artist> artists) {
    this.name = name;
    this.artists = artists;
    createStages();
    shows = new Show[48][stages.size()];
    times = new Time[48];
    for (int i = 0; i < 48; i++) {
      int minutes = i * 30;
      int hours = minutes / 60;
      times[i] = new Time(hours, minutes % 60);
    }
    agendaView = new AgendaView(main, this);
    Collections.sort(this.artists);
    Collections.sort(stages);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Show[][] getShows() {
    return shows;
  }

  private void createStages() {
    stages = new ArrayList<>();
    stages.add(new Stage("Main Stage", 100));
    stages.add(new Stage("Second Stage", 75));
    stages.add(new Stage("Third Stage", 50));
  }

  public ArrayList<Stage> getStages() {
    return stages;
  }

  public ArrayList<Artist> getArtists() {
    return artists;
  }

  /**
   * Add arrayList of artists to existing arrayList of artists.
   */
  public void addArtists(ArrayList<Artist> artistsToAdd) {
    artists.addAll(artistsToAdd);
    Collections.sort(artists);
  }

  public AgendaView getAgendaView() {
    return agendaView;
  }

  public void setAgendaView(AgendaView agendaView) {
    this.agendaView = agendaView;
  }

  public Time[] getTimes() {
    return times;
  }
}
