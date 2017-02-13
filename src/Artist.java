import java.awt.*;

/**
 * Created by dionb on 6-2-2017.
 */
public class Artist extends Human {
    String genre;

    public Artist(String name, String genre) {
        super(name);
        this.genre = genre;
    }

    public String toString() {
        return "Artiest: " + super.getName() + ", Genre: " + genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
