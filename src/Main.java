//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * Created by snick on 11/02/2017.
// */
//public class Main extends JTabbedPane {
//    private JFrame frame;
//    private FileManager fileManager;
//    private ArrayList<Agenda> agendas;
//    private MainMenuBar menuBar;
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new Main(args);
//            }
//        });
//    }
//
//    public Main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        frame = new JFrame("Agenda");
//        fileManager = new FileManager(this);
//        agendas = new ArrayList<>();
//        Dimension screenSize = (Toolkit.getDefaultToolkit().getScreenSize());
//        double screenWidth = screenSize.getWidth();
//        double screenHeight = screenSize.getHeight();
//        frame.setSize((int) screenWidth/2, (int) screenHeight/2);
//        frame.setMinimumSize(new Dimension((int) screenWidth / 2, (int) screenHeight / 2));
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        frame.setLocation((int) screenSize.getWidth()/4, (int) screenSize.getHeight()/4);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        menuBar = new MainMenuBar();
//        frame.setJMenuBar(menuBar);
//        frame.setContentPane(this);
//
//        try
//        {
//            ImageIcon img = new ImageIcon(this.getClass().getResource("/Images/Agenda.png"));
//            frame.setIconImage(img.getImage());
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        if (args.length > 0) {
//            for(int i = 0; i < args.length; i++) {
//                File file = new File(args[i]);
//                try {
//                    add(fileManager.loadFile(file));
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        frame.setVisible(true);
//    }
//
//    private void add(Agenda agenda) {
//        agendas.add(agenda);
//        addTab(agenda.getName(), new Tab(agenda));
//        menuBar.update(agenda);
//    }
//
//    public class MainMenuBar extends JMenuBar {
//        private JMenu fileMenu, editMenu, saveAgendaMenu;
//        private JMenuItem newAgendaItem, loadAgendaItem, addStageMenuItem;
//        private JCheckBoxMenuItem editableCheckBox;
//
//        private MainMenuBar() {
//            fileMenu = new JMenu("File");
//            editMenu = new JMenu("Edit");
//            saveAgendaMenu = new JMenu("Save");
//            newAgendaItem = new JMenuItem("New");
//            loadAgendaItem = new JMenuItem("Load");
//            addStageMenuItem = new JMenuItem("Add Stage");
//            editableCheckBox = new JCheckBoxMenuItem("Editable");
//
//            newAgendaItem.addActionListener(e -> newAgendaEvent());
//            loadAgendaItem.addActionListener(e -> loadAgendaEvent());
//            addStageMenuItem.addActionListener(e -> addStageEvent());
//            editableCheckBox.addActionListener(e -> editableEvent());
//
//            addStageMenuItem.setEnabled(false);
//
//            fileMenu.add(newAgendaItem);
//            fileMenu.add(loadAgendaItem);
//            fileMenu.add(saveAgendaMenu);
//            editMenu.add(addStageMenuItem);
//            editMenu.add(editableCheckBox);
//            add(fileMenu);
//            add(editMenu);
//        }
//
//        public void update(Agenda agenda) {
//            JMenuItem saveAgendaMenuItem = new JMenuItem(agenda.getName());
//            saveAgendaMenuItem.addActionListener(e -> saveAgendaEvent(agenda));
//            saveAgendaMenu.add(saveAgendaMenuItem);
//        }
//
//        private void newAgendaEvent() {
//            String name;
//            do {
//                name = (JOptionPane.showInputDialog(this, "Agenda name?"));
//            } while(name.equals(""));
//            if(name != null) {
//                Main.this.add(new Agenda(name));
//            }
//        }
//
//        private void loadAgendaEvent() {
//            try {
//                Main.this.add(fileManager.loadFile());
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void saveAgendaEvent(Agenda agenda) {
//            fileManager.saveFile(agenda);
//        }
//
//        private void addStageEvent() {
//            Tab tab = (Tab) Main.this.getSelectedComponent();
//            String name;
//            String surfaceArea;
//            do {
//                name = (JOptionPane.showInputDialog(this, "Stage name?"));
//            } while (name.equals(""));
//            if (name != null) {
//                tab.addStage(new Stage(name));
//            }
//        }
//
//        private void editableEvent() {
//            addStageMenuItem.setEnabled(editableCheckBox.getState());
//            for(int i = 0; i < Main.this.getComponentCount(); i++) {
//                Tab tab = (Tab) Main.this.getComponent(i);
//                tab.setEditable(editableCheckBox.getState());
//            }
//        }
//    }
//}
