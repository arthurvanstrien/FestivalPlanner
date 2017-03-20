package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.general.Time;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Eversdijk on 14-3-2017.
 */
public class TimeGui extends JDialog {

  private JComboBox<Integer> hoursC, minutesC;
  private SimulationView frame;

  private Time time;

  /**
   * Creates Gui
   * @param simulationView
   */

  public TimeGui(SimulationView simulationView) {
    super(simulationView, "Agenda.Time selection", true);
    setLocationRelativeTo(simulationView);
    setResizable(false);
    setSize(200, 200);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    time = new Time(0, 0);
    this.frame = simulationView;
    JPanel content = new JPanel(new GridLayout(0, 1));
    add(content);

    JPanel mainFrame = new JPanel(new GridLayout(0, 1));

    //Timepanel
    JPanel timePanel = new JPanel(new GridLayout(1, 0));
    JLabel hoursL = new JLabel("Hours: ");
    JLabel minutesL = new JLabel("Minutes: ");

    hoursC= new JComboBox<>();
    minutesC= new JComboBox<>();

    for(int i = 0; i < 24; i++) {
      hoursC.addItem(new Integer(i));
    }
    minutesC.addItem(new Integer(0));
    minutesC.addItem(new Integer(30));

    timePanel.add(hoursL);
    timePanel.add(hoursC);
    timePanel.add(minutesL);
    timePanel.add(minutesC);

    JButton setTime = new JButton("Set time");
    setTime.addActionListener(e -> {
      setTime();
      dispose();
    });

    content.add(mainFrame);
    content.add(timePanel);
    content.add(setTime);

    setVisible(true);
  }

  /**
   *Set time of the given simulationView
   */

  public void setTime() {

    int hours = (int) hoursC.getSelectedItem();
    int minutes = (int) minutesC.getSelectedItem();
    time = new Time(hours, minutes);
    System.out.println("Agenda.Time:" + time.toString());
    frame.setTime(time);
  }
}
