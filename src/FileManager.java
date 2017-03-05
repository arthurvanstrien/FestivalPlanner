import javax.naming.InvalidNameException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


/**
 * Created by kevin on 9-2-2017.
 */
public class FileManager {
    private JFileChooser fileChooser;
    private Main main;

    public FileManager(Main main) {
        this.main = main;
        File dir = new File(System.getProperty("user.home") + File.separator + "Agendas");
        dir.mkdirs();
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Agenda (*.agd)", "agd"));
        fileChooser.setCurrentDirectory(dir);
    }

    /**
    Save agenda to file.
     */
    public void saveFile(Agenda agenda) {
        File file;

        int returnVal = fileChooser.showSaveDialog(main);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            file = null;  // cancelled
        } else {
            String fileName = fileChooser.getSelectedFile().toString();
            if (!fileName.endsWith(".agd")) {
                fileName += ".agd";
                file = new File(fileName);
            } else {
                file = fileChooser.getSelectedFile();
            }
        }

            if(file != null) {
                try {
                    if(file.getName().matches(".*'|'':''\"''<''>''\''\'.*")) {
                        throw new FileNotFoundException("Invalid file name.");
                    }
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                    outputStream.writeObject(agenda);
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(fileChooser, "Incorrect file name.", "Save", JOptionPane.WARNING_MESSAGE);
                    saveFile(agenda);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public Agenda loadFile() {
        File file;

        int returnVal = fileChooser.showOpenDialog(main);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            file = null;  // cancelled
        }

        file = fileChooser.getSelectedFile();

        return load(file);
    }

    /**
    Load agenda from file.
     */
    public Agenda loadFile(File file) {
        return load(file);
    }

    private Agenda load(File file) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            Agenda agenda = (Agenda) inputStream.readObject();
            agenda.setAgendaView(new AgendaView(main, agenda));
            inputStream.close();
            return agenda;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class InvalidFileNameException extends Exception {
        public InvalidFileNameException(String message) {
            super(message);
        }
    }
}
