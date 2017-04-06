package edu.a3.festival_planner.agenda;

import edu.a3.festival_planner.exceptions.ArtistException;
import edu.a3.festival_planner.exceptions.NameException;
import edu.a3.festival_planner.exceptions.PopularityException;
import edu.a3.festival_planner.exceptions.TimeException;
import edu.a3.festival_planner.general.Main;
import edu.a3.festival_planner.general.Period;
import edu.a3.festival_planner.general.Time;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Robin on 6-2-2017.
 */
public class AgendaView extends JTable {

  private Main main;
  private ArrayList<Artist> artists;
  private ArrayList<Stage> stages;
  private AbstractTableModel tableModel;
  private Show[][] shows;
  private Time[] times;
  private boolean isEditable;

  public AgendaView(Main main, Agenda agenda) {
    this.main = main;
    shows = agenda.getShows();
    artists = agenda.getArtists();
    stages = agenda.getStages();
    times = agenda.getTimes();
    tableModel = new AbstractTableModel() {
      @Override
      public int getRowCount() {
        return 48;
      }

      @Override
      public int getColumnCount() {
        return agenda.getStages().size() + 1;
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
          return times[rowIndex];
        }
        return shows[rowIndex][columnIndex];
      }

      @Override
      public String getColumnName(int column) {
        if (column == 0) {
          return "Time";
        }
        return agenda.getStages().get(column - 1).getName();
      }
    };
    setModel(tableModel);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          JTable target = (JTable) e.getSource();
          int row = target.getSelectedRow();
          int column = target.getSelectedColumn();
          doubleClickEvent(row, column);
        }
      }
    });
    getTableHeader().setReorderingAllowed(false);
  }

  /**
   * Changes the ability to edit of the agenda.
   */
  public void setEditable(boolean isEditable) {
    this.isEditable = isEditable;
  }

  private void doubleClickEvent(int row, int col) {
    if (col != 0) {
      if (isEditable) {
        new ShowEditWindow(row, col);
      } else {
        new ShowDisplayWindow(row, col);
      }
    }
  }

  @Override
  public Object getValueAt(int row, int col) {
    if (col == 0) {
      return times[row];
    }
    return shows[row][col - 1];
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    if (col != 0) {
      shows[row][col - 1] = (Show) value;
      tableModel.fireTableCellUpdated(row, col);
    }
  }

  private class ShowEditWindow extends JDialog {

    private String name;
    private ArrayList<Artist> artists;
    private Time beginTime, endTime;
    private Stage stage;
    private int expectedPopularity;

    public ShowEditWindow(int row, int col) {
      super(main.getFrame(), "Edit Show");
      Show show;

      JPanel tempPanel = new JPanel(new BorderLayout());
      JPanel southPanel = new JPanel(new FlowLayout());
      JPanel artistsPanel = new JPanel(new GridLayout(0, 1));
      JScrollPane centerPanel = new JScrollPane(artistsPanel,
          ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      JPanel northPanel = new JPanel(new GridLayout(0, 2, 10, 10));
      tempPanel.add(southPanel, BorderLayout.SOUTH);
      tempPanel.add(centerPanel, BorderLayout.CENTER);
      tempPanel.add(northPanel, BorderLayout.NORTH);
      add(tempPanel);
      Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
      double screenWidth = screenSize.getWidth();
      double screenHeight = screenSize.getHeight();
      setSize((int) screenWidth / 6, (int) screenHeight / 3);
      setMinimumSize(new Dimension(320,360));
      setLocationRelativeTo(main.getFrame());
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      setResizable(false);

      JLabel showNameLabel = new JLabel("Show Name");
      JTextField showNameText = new JTextField("");

      JLabel stageLabel = new JLabel("Stage");
      JComboBox stageCB = new JComboBox(stages.toArray());

      JLabel timeLabel = new JLabel("Begin / End Time");
      JComboBox beginTimeCB = new JComboBox(times);
      JComboBox endTimeCB = new JComboBox(times);
      JPanel timeCB = new JPanel(new GridLayout(1, 2));
      timeCB.add(beginTimeCB);
      timeCB.add(endTimeCB);

      JComboBox artistsCB = new JComboBox(AgendaView.this.artists.toArray());
      JButton addArtistButton = new JButton("Add/Remove Artist");

      JLabel popularityLabel = new JLabel("Expected Popularity");
      JTextField popularityText = new JTextField("");

      JButton saveButton = new JButton("Save");
      JButton removeShowButton = new JButton("Remove");
      JButton closeButton = new JButton("Close");

      if (shows[row][col - 1] != null) {
        show = shows[row][col - 1];
        loadDetails(show);
        showNameText.setText(name);
        stageCB.setSelectedItem(stage);
        beginTimeCB.setSelectedItem(beginTime);
        endTimeCB.setSelectedItem(endTime);
        for (Artist artist : artists) {
          artistsPanel.add(new JLabel(artist.toString()));
        }
        popularityText.setText(String.valueOf(expectedPopularity));
      } else {
        show = new Show();
        artists = new ArrayList<>();
        stageCB.setSelectedItem(stages.get(col - 1));
        beginTimeCB.setSelectedItem(times[row]);
        endTimeCB.setSelectedItem(times[row + 1]);
      }

      addArtistButton.addActionListener(e -> {
        if (artistsCB.getItemCount() != 0) {
          Artist artist = (Artist) artistsCB.getSelectedItem();
          if (!artists.contains(artist)) {
            artists.add(artist);
            artistsPanel.add(new JLabel(artist.toString()));
          } else {
            artistsPanel.remove(artists.indexOf(artist));
            artists.remove(artist);
          }
          artistsPanel.revalidate();
          artistsPanel.repaint();
        }
      });

      closeButton.addActionListener(e -> dispose());

      saveButton.addActionListener(e -> {
        name = showNameText.getText();

        beginTime = (Time) beginTimeCB.getSelectedItem();
        endTime = (Time) endTimeCB.getSelectedItem();
        stage = (Stage) stageCB.getSelectedItem();
        try {
          expectedPopularity = Integer.valueOf(popularityText.getText());
        } catch (NumberFormatException nfe) {
          expectedPopularity = 0;
        }

        saveDetails(show);
      });

      removeShowButton.addActionListener(e -> {
        removeShow(show);
        dispose();
      });

      northPanel.add(showNameLabel);
      northPanel.add(showNameText);
      northPanel.add(stageLabel);
      northPanel.add(stageCB);
      northPanel.add(timeLabel);
      northPanel.add(timeCB);
      northPanel.add(popularityLabel);
      northPanel.add(popularityText);
      northPanel.add(addArtistButton);
      northPanel.add(artistsCB);
      southPanel.add(saveButton);
      southPanel.add(removeShowButton);
      southPanel.add(closeButton);
      setVisible(true);
    }

    private void loadDetails(Show show) {
      name = show.getName();
      artists = show.getArtists();
      beginTime = show.getBeginTime();
      endTime = show.getEndTime();
      stage = show.getStage();
      expectedPopularity = show.getExpectedPopularity();
    }

    private void saveDetails(Show show) {
      try {
        removeShow(show);

        Show newShow = new Show();

        newShow.setName(name);
        newShow.setBeginTime(beginTime);
        newShow.setEndTime(endTime);
        newShow.setStage(stage);
        newShow.setExpectedPopularity(expectedPopularity);
        newShow.setArtists(artists);

        addShow(newShow);
        dispose();
      } catch (ArtistException | TimeException | NameException | PopularityException e) {
        addShow(show);
        showWarning(e);
      }
    }

    private void removeShow(Show show) {
      try {
        for (Artist artist : show.getArtists()) {
          for (Time time : Period.getTimes(show.getBeginTime(), show.getEndTime())) {
            artist.setPerforming(time, false);
          }
        }

        for (int i = 0; i < 48; i++) {
          if (Period.checkIfTimeIsInPeriod(times[i], show.getBeginTime(), show.getEndTime())) {
            setValueAt(null, i, 1 + stages.indexOf(show.getStage()));
          }
        }
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }

    private void addShow(Show show) {
      try {
        for (Artist artist : show.getArtists()) {
          for (Time time : Period.getTimes(show.getBeginTime(), show.getEndTime())) {
            artist.setPerforming(time, true);
          }
        }

        for (int i = 0; i < 48; i++) {
          if (Period.checkIfTimeIsInPeriod(times[i], show.getBeginTime(), show.getEndTime())) {
            setValueAt(show, i, 1 + stages.indexOf(show.getStage()));
          }
        }
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }

    private void showWarning(Exception e) {
      JOptionPane
          .showMessageDialog(this, e.getMessage(), "Show", JOptionPane.WARNING_MESSAGE);
    }
  }

  private class ShowDisplayWindow extends JDialog {

    public ShowDisplayWindow(int row, int col) {
      super(main.getFrame(), "Show Info");

      Show show = shows[row][col - 1];

      if (show != null) {
        JPanel tempPanel = new JPanel(new GridLayout(6, 2));
        add(tempPanel);
        Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        setSize((int) screenWidth / 5, (int) screenHeight / 4);
        setMinimumSize(new Dimension(384,270));
        setLocationRelativeTo(main.getFrame());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        //JLabel showNameLabel = new JLabel(show.getName());
        //showNameLabel.setBounds(120, 10, 100, 25);
        JLabel artistsLabel = new JLabel("Artist(s)");
        JTextArea nameText = new JTextArea();
        JScrollPane artistPane = new JScrollPane(nameText,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nameText.setLineWrap(true);
        nameText.setBackground(getBackground());
        nameText.setWrapStyleWord(true);
        nameText.setEditable(false);
        JLabel startTimeTextLabel = new JLabel("Start time");
        JLabel startTimeLabel = new JLabel(show.getBeginTime().toString());
        JLabel endTimeTextLabel = new JLabel("End time");
        JLabel endTimeLabel = new JLabel(show.getEndTime().toString());
        JLabel stageTextLabel = new JLabel("Stage");
        JLabel stageLabel = new JLabel(show.getStage().toString());
        JLabel expectedPopularityLabel = new JLabel("Expected Popularity");
        JLabel expectedPopularityTextLabel = new JLabel(
            String.valueOf(show.getExpectedPopularity()));
        StringBuilder artistsString = new StringBuilder();
        for (Artist artist : show.getArtists()) {
          artistsString.append(artist.getName()).append(" (").append(artist.getGenre()).append(")");
          if (show.getArtists().indexOf(artist) < show.getArtists().size()) {
            artistsString.append(", ");
          }
        }
        nameText.setText(artistsString.substring(0, artistsString.length() - 2));

        tempPanel.add(artistsLabel);
        tempPanel.add(artistPane);
        tempPanel.add(startTimeTextLabel);
        tempPanel.add(startTimeLabel);
        tempPanel.add(endTimeTextLabel);
        tempPanel.add(endTimeLabel);
        tempPanel.add(stageTextLabel);
        tempPanel.add(stageLabel);
        tempPanel.add(expectedPopularityLabel);
        tempPanel.add(expectedPopularityTextLabel);
        setVisible(true);
      } else {
        JOptionPane
            .showMessageDialog(AgendaView.this, "No show.", "Show",
                JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}