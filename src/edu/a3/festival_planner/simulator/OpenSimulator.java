package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Main;
import edu.a3.festival_planner.general.Time;
import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eversdijk on 29-3-2017.
 */
public class OpenSimulator extends JDialog {

    private JTextField savesT, visitorsT;
    private SimulationView frame;

    private Time time;

    /**
     * Creates Gui
     */

    public OpenSimulator(Main main, Agenda agenda) {
        super(main.getFrame(), "Selection of the", true);
        setLocationRelativeTo(main);
        setResizable(false);
        Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        setSize((int) screenWidth / 8, (int) screenHeight / 8);
        setMinimumSize(new Dimension(240, 100));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        time = new Time(0, 0);
        JPanel content = new JPanel(new BorderLayout());
        add(content);


        //Selectionpanel
        JPanel selectionPanel = new JPanel(new GridLayout(2, 2, 5, 15));
        JLabel savesL = new JLabel("visitors: ");
        JLabel visitorsL = new JLabel("saves: ");

        savesT = new JTextField();
        visitorsT = new JTextField();

        selectionPanel.add(visitorsL);
        selectionPanel.add(visitorsT);
        selectionPanel.add(savesL);
        selectionPanel.add(savesT);

        JLabel errorMessage = new JLabel();
        content.add(errorMessage, BorderLayout.CENTER);

        //Start button
        JButton openSimulationView = new JButton("Start simulation");
        openSimulationView.addActionListener(e -> {
            if (isParsable(savesT.getText()) || isParsable(visitorsT.getText())) {
                new SimulationView(main, agenda, Integer.parseInt(savesT.getText()), Integer.parseInt(visitorsT.getText()));
                dispose();
            } else {
                errorMessage.setText("No valid input");
            }
        });

        content.add(selectionPanel, BorderLayout.NORTH);
        content.add(openSimulationView, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static boolean isParsable(String input) {
        boolean parsable = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;
    }
}
