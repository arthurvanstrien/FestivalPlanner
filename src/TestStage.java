/**
 * Created by dionb on 13-2-2017.
 * Edited by Arthur on 05-03-2017.
 */
public class TestStage {

  public static int runTest() {
    int amountOfErrors = 0;
    Stage s1 = new Stage("Test stage 1", 100);

    if (!s1.toString().equals("Test stage 1")) {
      System.out.println("The to string method does not give back the right information");
      amountOfErrors++;

      if (!s1.getName().equals("Test stage 1")) {
        System.out.println("There is a problem with the getName method of the class Stage.");
        amountOfErrors++;
      }

      if (s1.getSurfaceArea() != 100) {
        System.out.println("There is a problem with the getSurfaceArea method of the class Stage.");
        amountOfErrors++;
      }
    }
    return amountOfErrors;
  }
}