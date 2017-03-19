import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by snick on 11/02/2017.
 */
public class Main extends JTabbedPane {

  private JFrame frame;
  private FileManager fileManager;
  private ArrayList<Agenda> agendas;
  private MainMenuBar menuBar;
  private TiledMap tiledMap;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main(args);
      }
    });
  }

  public Main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    frame = new JFrame("Festival Planner");
    fileManager = new FileManager(this);
    tiledMap = new TiledMap();
    agendas = new ArrayList<>();
    Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();
    frame.setSize((int) screenWidth / 2, (int) screenHeight / 2);
    frame.setMinimumSize(new Dimension((int) screenWidth / 2, (int) screenHeight / 2));
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    menuBar = new MainMenuBar();
    frame.setJMenuBar(menuBar);
    frame.setContentPane(this);

    try {
      ImageIcon img = new ImageIcon(this.getClass().getResource("/Images/Agenda.png"));
      frame.setIconImage(img.getImage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        File file = new File(args[i]);
        try {
          add(fileManager.loadAgenda(file));
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      }
    }

    frame.setVisible(true);
  }

  /**
   * Add new agenda tab to the program.
   */
  public void add(Agenda agenda) {
    agendas.add(agenda);
    Tab tab = new Tab(this, agenda);
    addTab(agenda.getName(), tab);
    setSelectedComponent(tab);
    menuBar.updateAgendas(agenda);
    menuBar.updateArtists(agenda);
  }

  public JFrame getFrame() {
    return frame;
  }

  private class MainMenuBar extends JMenuBar {

    private JMenu agendaMenu, mapMenu, saveAgendaMenu, addArtistMenu;
    private JMenuItem newAgendaItem, loadAgendaItem, loadMapItem, simulateItem;
    private JCheckBoxMenuItem editableCheckBox;

    private MainMenuBar() {
      agendaMenu = new JMenu("Agenda");
      mapMenu = new JMenu("Map");
      saveAgendaMenu = new JMenu("Save");
      addArtistMenu = new JMenu("Add artist(s) to: ");
      newAgendaItem = new JMenuItem("New");
      loadAgendaItem = new JMenuItem("Load");
      loadMapItem = new JMenuItem("Load");
      editableCheckBox = new JCheckBoxMenuItem("Editable");
      simulateItem = new JMenuItem("Simulate");

      newAgendaItem.addActionListener(e -> newAgendaEvent());
      loadAgendaItem.addActionListener(e -> loadAgendaEvent());
      loadMapItem.addActionListener(e -> loadMapEvent());
      editableCheckBox.addActionListener(e -> editableEvent());
      simulateItem.addActionListener(e -> new SimulationView());

      agendaMenu.add(newAgendaItem);
      agendaMenu.add(loadAgendaItem);
      agendaMenu.add(saveAgendaMenu);
      agendaMenu.add(simulateItem);
      agendaMenu.add(addArtistMenu);
      agendaMenu.add(editableCheckBox);
      mapMenu.add(loadMapItem);
      add(agendaMenu);
      add(mapMenu);
    }

    public void updateAgendas(Agenda agenda) {
      JMenuItem saveAgendaMenuItem = new JMenuItem(agenda.getName());
      saveAgendaMenuItem.addActionListener(e -> saveAgendaEvent(agenda));
      saveAgendaMenu.add(saveAgendaMenuItem);
    }

    public void updateArtists(Agenda agenda) {
      JMenuItem addArtistAgendaMenuItem = new JMenuItem(agenda.getName());
      addArtistAgendaMenuItem.addActionListener(e -> addArtistEvent(agenda));
      addArtistMenu.add(addArtistAgendaMenuItem);
    }

    private void newAgendaEvent() {
      String name;
      do {
        name = (JOptionPane
            .showInputDialog(Main.this, "Name?", "Agenda", JOptionPane.QUESTION_MESSAGE));
      } while (name.equals(""));
      if (name != null) {
        new AddArtistsGUI(Main.this, name);
        editableEvent();
      }
    }

    private void loadAgendaEvent() {
      try {
        Main.this.add(fileManager.loadAgenda());
        editableEvent();
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }

    private void saveAgendaEvent(Agenda agenda) {
      fileManager.saveAgenda(agenda);
    }

    private void loadMapEvent() {
      tiledMap = fileManager.loadTiledMap();
    }

    private void editableEvent() {
      for (int i = 0; i < Main.this.getComponentCount(); i++) {
        Tab tab = (Tab) Main.this.getComponent(i);
        tab.setEditable(editableCheckBox.getState());
      }
    }

    private void addArtistEvent(Agenda agenda) {
      new AddArtistsGUI(agenda, Main.this);
    }
  }
}
