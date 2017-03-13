import Exceptions.ArtistException;
import Exceptions.NameException;
import Exceptions.PopularityException;
import Exceptions.TimeException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by dionb on 6-2-2017.
 */
public class Show implements Serializable {

  private String name;
  private Time beginTime;
  private Time endTime;
  private Stage stage;
  private ArrayList<Artist> artists;
  private int expectedPopularity;

  public Show() {

  }

  public Show(String name, Time beginTime, Time endTime, Stage stage, ArrayList<Artist> artists,
      int expectedPopularity) {
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.stage = stage;
    this.artists = artists;
    this.expectedPopularity = expectedPopularity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws NameException {
    if (name.equals("")) {
      throw new NameException("Name is empty.");
    } else {
      this.name = name;
    }
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

  public void setEndTime(Time endTime) throws TimeException {
    if (endTime.toSeconds() <= beginTime.toSeconds()) {
      throw new TimeException("End time is earlier than begin time.");
    } else {
      this.endTime = endTime;
    }
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public ArrayList<Artist> getArtists() {
    return artists;
  }

  public void setArtists(ArrayList<Artist> artists) throws ArtistException {
    for (Artist artist : artists) {
      for (Time time : Period.getTimes(beginTime, endTime)) {
        if (artist.isPerforming(time)) {
          throw new ArtistException("Artist unavailable");
        } else {
          artist.setPerforming(time, true);
        }
      }
    }
    if (!artists.isEmpty()) {
      this.artists = artists;
    } else {
      throw new ArtistException("No Artists.");
    }
  }

  public int getExpectedPopularity() {
    return expectedPopularity;
  }

  public void setExpectedPopularity(int expectedPopularity) throws PopularityException {
    if (expectedPopularity > 0 && expectedPopularity <= 10) {
      this.expectedPopularity = expectedPopularity;
    } else {
      throw new PopularityException("Popularity rating on a scale of 1 to 10 needed.");
    }
  }

  @Override
  public String toString() {
    String artistsString = "";
    for (Artist artist : artists) {
      artistsString += artist.getName() + ", ";
    }
    return name;
  }
}
