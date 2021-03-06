import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by dionb on 13-2-2017.
 */
public class TestShow {

    public static int runTest(){
        int amountOfErrors = 0;

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("artist", "genre"));
        artists.add(new Artist("artist2", "genre2"));
        Show s1 = new Show(new Time(12, 0, 0), new Time(16,0, 0), new Stage("stage", 50), artists,0.5);

        if(!s1.toString().equals("Begint om: 12:00:00 eindigd om: 16:00:00 Stage{name='stage'} [Artiest: artist Genre: genre, Artiest: artist2 Genre: genre2]")) {
            if(!s1.getBeginTime().equals(new Time(12, 0, 0))) {
                System.out.println("There is a problem with the begin time in the getBeginTime method from the Show class .");
                amountOfErrors++;
            }

            if(!s1.getEndTime().equals(new Time(16, 0, 0))) {
                System.out.println("There is a problem with the getEndTime method from the Show class.");
                amountOfErrors++;
            }

            if(s1.getExpectedPopularity() != 0.5) {
                System.out.println("There is a problem with the getExpectedPopularity method of the Show class.");
                amountOfErrors++;
            }
        }

        return amountOfErrors;
    }
}