import Exceptions.ArtistException;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by dionb on 6-2-2017.
 */
public class Artist extends Human implements Comparable {
    String genre;
    Image image;
    HashMap<Integer, Boolean> performingTimes;

    public Artist(String name, String genre) {
        super(name);
        this.genre = genre;
        performingTimes = new HashMap<>();
    }

    public String toString() {
        return super.getName();
    }

    public String getGenre() {
        return genre;
    }

    public Image getImage() {
        return image;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
    Returns true if the artist is performing at a given time.
     */
    public boolean isPerforming(Time time) throws ArtistException {
        try {
            return performingTimes.get(time.toSeconds());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    Set time at which the artist is performing or not.
     */
    public void setPerforming(Time time, boolean isPerforming) {
        performingTimes.put(time.toSeconds(), isPerforming);
    }

    @Override
    public int compareTo(Object o) {
        Artist comparableArtist = (Artist) o;
        return this.getName().compareTo(comparableArtist.getName());
    }
}
