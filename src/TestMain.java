/**
 * Created by Arthur on 13-2-2017.
 */
public class TestMain {
    public static void main(String[] args) {
        int errors = 0;
        System.out.println("Running tests...");

        errors = errors + TestArtist.runTest();
        System.out.println("--Einde Artist Test------------------------------------");
        errors = errors + TestShow.runTest();
        System.out.println("--Einde Show Test------------------------------------");
        errors = errors + TestStage.runTest();
        System.out.println("--Einde Stage Test------------------------------------");

        System.out.println("Running completed.");
        System.out.println("There where " + errors + " errors found while running the tests.");
    }
}
