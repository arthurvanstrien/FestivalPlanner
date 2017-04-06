package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Main;
import edu.a3.festival_planner.general.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Eversdijk on 14-3-2017.
 */
public class SimulationView extends JFrame implements ActionListener {

  private Main main;
  private Agenda agenda;
  private Time time;
  private Timer timer;
  private JLabel currentTime, currentSpeed;
  private JSlider sliderSpeed;
  private long lastTime;
  private long currenttime;
  private double seconds;
  private TiledMapView tiledMapView;
  private int saves, visitors;


  /**
   * Generates GUI with an agenda to simulate
   * @param agenda
   */
  public SimulationView(Main main, Agenda agenda, int visitors) {
    super("Simulation: " + agenda.getName());
    //Initializing atributes
    time = new Time(0, 0);
    lastTime = System.nanoTime();
    timer = new Timer(15, this);
    timer.start();
    seconds = 0;
    this.main = main;
    this.agenda = agenda;
    this.visitors = visitors;

    Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();
    setSize((int) screenWidth / 2, (int) screenHeight / 2);
    setMinimumSize(new Dimension((int) screenWidth / 2, (int) screenHeight / 2));
    setExtendedState(Frame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    try {
      ImageIcon img = new ImageIcon(
              this.getClass().getClassLoader().getResource("\\images\\Cogs.png"));
      setIconImage(img.getImage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    JPanel content = new JPanel(new BorderLayout());
    add(content);

    //SimulationPanel

    JPanel simulation = new JPanel(new BorderLayout());

      tiledMapView = new TiledMapView(main.getTiledMap(), agenda, visitors);
    JPanel tiledMap = tiledMapView;
    simulation.setBorder(BorderFactory.createEtchedBorder());
    simulation.add(tiledMap, BorderLayout.CENTER);

    //optionsPanel

    JPanel options = new JPanel(new GridLayout(0, 1));

    //Time Grid

    JPanel timePanel = new JPanel(new GridLayout(0, 1));
    currentTime = new JLabel("Current time : " + time.toString());
    ImageIcon clock = new ImageIcon(
            new ImageIcon("res\\images\\Clock.png").getImage().getScaledInstance(50, 50, 1));
    JButton clockImage = new JButton(clock);
    clockImage.addActionListener(e -> new TimeGui(this));

    timePanel.add(currentTime);
    timePanel.add(clockImage);

    //Speed grid

    JPanel speed = new JPanel();
    sliderSpeed = new JSlider(JSlider.VERTICAL, 0, 6000, 100);
    currentSpeed = new JLabel("speed: " + sliderSpeed.getValue());
    sliderSpeed.setMajorTickSpacing(100);
    speed.add(currentSpeed);
    speed.add(sliderSpeed);

    options.add(timePanel);
    options.add(speed);

    //if frame closed then this code has to run to stop the timer
  this.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(WindowEvent e) {
      timer.stop();
      super.windowClosed(e);
    }
  });

    //Final actions

    content.add(simulation, BorderLayout.CENTER);
    content.add(options, BorderLayout.EAST);
    setVisible(true);
  }

  public void setTime(Time time) {
    this.time = time;
    seconds = ((time.getHour() * 60) + time.getMinute()) * 60;

  }

  public void update() {
    currenttime = System.nanoTime();
    double elapsedTime = (currenttime - lastTime) / 1000000000.0;
    lastTime = currenttime;
    seconds += (elapsedTime * (sliderSpeed.getValue() / 100));
    time = new Time((int) ((seconds / 60) / 60) % 24, (int) (seconds / 60) % 60);
    currentTime.setText("Current time : " + time.toString());
    currentSpeed.setText("speed: " + sliderSpeed.getValue());

    tiledMapView.update(elapsedTime * (sliderSpeed.getValue() / 100), time);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    update();
  }
}

