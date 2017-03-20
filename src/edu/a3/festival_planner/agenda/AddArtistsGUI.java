package edu.a3.festival_planner.agenda;

import edu.a3.festival_planner.general.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by snick on 14/02/2017.
 */
public class AddArtistsGUI extends JDialog {

  private ArrayList<Artist> artists;

  /**
   * Add artists while creating new agenda.
   */
  public AddArtistsGUI(Main main, String name) {
    super(main.getFrame(), "Add Artists", true);
    createGUI(main, name, null);
  }

  /**
   * Add artists to existing agenda
   */
  public AddArtistsGUI(Agenda agenda, Main main) {
    super(main.getFrame(), "Add Artists", true);
    createGUI(main, null, agenda);
  }

  private void createGUI(Main main, String name, Agenda agenda) {
    artists = new ArrayList<>();
    JPanel tempPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel(new FlowLayout());
    JPanel centerPanel = new JPanel(null);
    tempPanel.add(southPanel, BorderLayout.SOUTH);
    tempPanel.add(centerPanel, BorderLayout.CENTER);
    add(tempPanel);
    Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();
    setSize((int) screenWidth / 7, (int) screenHeight / 7);
    setLocationRelativeTo(main.getFrame());
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
        if (agenda == null) {
          addToMain(main, name);
        } else {
          addToAgenda(agenda);
        }
      }
    });

    setResizable(false);
    JLabel nameLabel = new JLabel("Artist name");
    nameLabel.setBounds(10, 10, 100, 25);
    JLabel genreLabel = new JLabel("Genre");
    genreLabel.setBounds(10, 50, 100, 25);
    JTextField nameText = new JTextField("");
    nameText.setBounds(110, 10, 150, 25);
    JTextField genreText = new JTextField("");
    genreText.setBounds(110, 50, 150, 25);
    JButton addButton = new JButton("Add");
    addButton.addActionListener(e -> {
      if (!nameText.getText().equals("") && !genreText.getText().equals("")) {
        artists.add(new Artist(nameText.getText(), genreText.getText()));
        JOptionPane.showMessageDialog(this, "Artist added", "Add Artists",
            JOptionPane.INFORMATION_MESSAGE);
        nameText.setText("");
        genreText.setText("");
      } else {
        JOptionPane.showMessageDialog(this, "Please enter all fields.", "Add Artists",
            JOptionPane.WARNING_MESSAGE);
      }
    });
    JButton closeButton = new JButton("Done");
    closeButton.addActionListener(e -> {
      dispose();
      if (agenda == null) {
        addToMain(main, name);
      } else {
        addToAgenda(agenda);
      }
    });
    centerPanel.add(nameLabel);
    centerPanel.add(genreLabel);
    centerPanel.add(nameText);
    centerPanel.add(genreText);
    southPanel.add(addButton);
    southPanel.add(closeButton);
    setVisible(true);
  }

  private void addToMain(Main main, String name) {
    main.add(new Agenda(main, name, artists));
  }

  private void addToAgenda(Agenda agenda) {
    agenda.addArtists(artists);
  }
}
