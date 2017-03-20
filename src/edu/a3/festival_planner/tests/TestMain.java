package edu.a3.festival_planner.tests;

/**
 * Created by Arthur on 13-2-2017.
 */
public class TestMain {

  public static void main(String[] args) {
    int errors = 0;
    System.out.println("Running tests...");

    System.out.println("--Begin Artist Test------------------------------------");
    errors = errors + TestArtist.runTest();
    System.out.println("--Begin Agenda Test------------------------------------");
    errors = errors + TestAgenda.runTest();
    //System.out.println("--Begin Agenda.Show Test------------------------------------");
    //errors = errors + TestShow.runTest();
    System.out.println("--Begin Stage Test------------------------------------");
    errors = errors + TestStage.runTest();
    System.out.println("--Begin Time Test------------------------------------");
    errors = errors + TestTime.runTest();
    System.out.println("--Begin Period Test------------------------------------");
    errors = errors + TestPeriod.runTest();
    System.out.println("--Begin BFS Test------------------------------------");
    errors += TestBFS.runTest();

    System.out.println("=======================================================");
    System.out.println("Running completed.");
    System.out.println("There where " + errors + " errors found while running the tests.");
  }
}
