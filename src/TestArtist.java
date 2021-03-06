/**
 * Created by Arthur on 13-2-2017.
 */
public class TestArtist {
    public static int runTest() {
        int errors = 0;
        Artist artist = new Artist("Jantje Smit", "Baggerzooi");

        if(!artist.toString().equals("Artiest: Jantje Smit, Genre: Baggerzooi")) {
            System.out.println("There is a problem with the toString method of the Artist class");
            errors++;
        }

        if(!artist.getGenre().equals("Baggerzooi")) {
            System.out.println("There is a problem with the getGenre method of the Artist class");
            errors++;
        }

        artist.setGenre("Rock");
        if(!artist.getGenre().equals("Rock")) {
            System.out.println("There is a problem with the getGenre or setGenre method of the Artist class");
            errors++;
        }

        return errors;
    }
}
