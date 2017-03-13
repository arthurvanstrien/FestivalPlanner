/**
 * Created by Arthur on 13-3-2017.
 */
public class TestAgenda {
    public static int runTest() {
        int errors = 0;

        //Create a new Agenda with the given values.
        //Agenda agenda = new Agenda()



        return errors;
    }
}

//import java.io.*;
//import java.sql.Time;
//import java.util.*;
//
///**
// * Created by dionb on 6-2-2017.
// */
//public class TestAgenda extends Agenda implements Serializable {
//    public TestAgenda() {
//        create();
//    }
//
//    public TestAgenda(String name) {
//        setName(name);
//        create();
//    }
//
//    private void create() {
//        Stage mainStage = new Stage("Main Stage", 1000);
//        Stage secondStage = new Stage("Second Stage", 500);
//        Stage thirdStage = new Stage("Third Stage", 400);
//        Stage backStage = new Stage("Back Stage", 200);
//
//        Artist artiest1 = new Artist("artiest1", "rock");
//        Artist djAdolf = new Artist("dj ah", "Blitzkrieg");
//        Artist djStalin = new Artist("dj st", "rsv");
//        Artist robbel = new Artist("Erwin Robbel", "panzer");
//        Artist overig = new Artist("overig", "overig");
//        Artist ochtendArtiest = new Artist("ochtendArtiest", "saai");
//
//        Show hairyPotter = new Show(new Time(14, 0, 0), new Time(16, 0, 0), mainStage, artiest1, 0.1);
//        Show show88 = new Show(new Time(16, 30, 0), new Time(19, 0, 0), mainStage, djAdolf, 0.9);
//        Show polkaShow = new Show(new Time(16, 30, 0), new Time(18, 0, 0), backStage, djStalin, 0.7);
//        Show hydra = new Show(new Time(14, 0, 0), new Time(18, 0, 0), secondStage, robbel, 0.3);
//        Show overigeShow = new Show(new Time(15, 0, 0), new Time(17, 0, 0), thirdStage, overig, 0.1);
//        Show testOchtend = new Show(new Time(7, 0, 0), new Time(13, 0, 0), thirdStage, ochtendArtiest, 0.1);
//
//        addStage(mainStage);
//        addStage(secondStage);
//        addStage(thirdStage);
//        addStage(backStage);
//
//        addArtist(artiest1);
//        addArtist(djAdolf);
//        addArtist(djStalin);
//        addArtist(robbel);
//        addArtist(overig);
//        addArtist(ochtendArtiest);
//
//        addShow(hairyPotter);
//        addShow(show88);
//        addShow(polkaShow);
//        addShow(hydra);
//        addShow(overigeShow);
//        addShow(testOchtend);
//
//        //System.out.println(this.toString());
//    }
//}