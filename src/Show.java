import java.io.Serializable;
import java.sql.Time;
import java.util.*;

/**
 * Created by dionb on 6-2-2017.
 */
public class Show implements Serializable {
    private Time beginTime;
    private Time endTime;
    private Stage stage;
    private ArrayList<Artist> artists;
    private Double expectedPopularity;

    public Show(Time beginTime, Time endTime, Stage stage, ArrayList artists, Double expectedPopularity) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.stage = stage;
        this.artists = artists;
        this.expectedPopularity = expectedPopularity;
    }

    public String toString() {
        return "Begint om: " + beginTime.toString() + " eindigd om: " + endTime.toString() + " " + stage.toString() + " " + artists.toString();
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ArrayList<Artist> getArtist() {
        return artists;
    }

    public void setArtist(ArrayList artists) {
        this.artists = artists;
    }

    public Double getExpectedPopularity() {
        return expectedPopularity;
    }

    public void setExpectedPopularity(Double expectedPopularity) {
        this.expectedPopularity = expectedPopularity;
    }
}
