package edu.a3.festival_planner.simulator;

import edu.a3.festival_planner.agenda.Agenda;
import edu.a3.festival_planner.general.Main;
import edu.a3.festival_planner.general.Time;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eversdijk on 29-3-2017.
 */
public class OpenSimulator extends JDialog {

    private JTextField visitorsT;

    private JLabel errorMessage;

    /**
     * Creates Gui
     */

    public OpenSimulator(Main main, Agenda agenda) {
        super(main.getFrame(), "Selection of the", true);
        setResizable(false);
        Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        setSize((int) screenWidth / 8, (int) screenHeight / 8);
        setMinimumSize(new Dimension(250, 100));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(main.getFrame());
        Time time = new Time(0, 0);
        JPanel content = new JPanel(new BorderLayout());
        add(content);


        //Selection panel
        JPanel selectionPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel visitorsL = new JLabel("Maximum amount of visitors: ");


        visitorsT = new JTextField();

        selectionPanel.add(visitorsL);
        selectionPanel.add(visitorsT);

        errorMessage = new JLabel();
        content.add(errorMessage, BorderLayout.CENTER);

        //Start button
        JButton openSimulationView = new JButton("Start simulation");
        openSimulationView.addActionListener(e -> {
            if (isValid(visitorsT.getText())) {
                dispose();
                new SimulationView(main, agenda, Integer.parseInt(visitorsT.getText()));
            }
        });

        content.add(selectionPanel, BorderLayout.NORTH);
        content.add(openSimulationView, BorderLayout.SOUTH);

        setVisible(true);
    }


    private boolean isValid(String input) {
        boolean valid = true;
        try {
            if (Long.parseLong(input) < 0) {
                errorMessage.setText("Minimal of 0 visitors required.");
                valid = false;
            } else if (Long.parseLong(input) > Integer.MAX_VALUE) {
                errorMessage.setText("Maximum of " + Integer.MAX_VALUE + " visitors allowed.");
                valid = false;
            }
        } catch (NumberFormatException e) {
            errorMessage.setText("No valid input");
            valid = false;
        }
        setVisible(true);
        return valid;
    }
}
