//import javax.swing.*;
//import javax.swing.table.AbstractTableModel;
//import java.io.Serializable;
//
///**
// * Created by Robin on 6-2-2017.
// */
//public class AgendaView extends JTable {
//    private Agenda agenda;
//    private int minutes;
//    private int hours;
//
//    public AgendaView(Agenda agenda) {
//        this.agenda = agenda;
//        minutes = 0;
//        hours = 0;
//        setModel(new AbstractTableModel() {
//            @Override
//            public int getRowCount() {
//                return (24 * 2) + 1;
//            }
//
//            @Override
//            public int getColumnCount() {
//                return (agenda.getStages().size() + 1);
//            }
//
//            @Override
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                minutes = rowIndex * 30;
//                hours = minutes / 60;
//                boolean correctStage;
//
//                int beginHours = 0;
//                int beginMinutes = 0;
//                int endHours = 0;
//                int endMinutes = 0;
//
//                boolean correctRow = false;
//                for (Show show : agenda.getShows()) {
//                    beginHours = show.getBeginTime().getHours();
//                    beginMinutes = show.getBeginTime().getMinutes();
//                    endHours = show.getEndTime().getHours();
//                    endMinutes = show.getEndTime().getMinutes();
//
//                    if (columnIndex == 0)
//                        correctStage = false;
//                    else //Þetta er Boolean
//                        correctStage = agenda.getStages().get(columnIndex - 1).getName().equals(show.getStage().getName());
//
//                    if ((beginHours < hours) && (endHours > hours)) {
//                        correctRow = true;
//                    } else if ((beginHours == hours) && (beginMinutes <= (minutes % 60))) {
//                        correctRow = true;
//                    } else if ((endHours == hours) && (endMinutes >= (minutes % 60))) {
//                        correctRow = true;
//                    } else {
//                        correctRow = false;
//                    }
//
//                    if (columnIndex == 0)
//                        return String.format("%02d", hours % 25) + ":" + String.format("%02d", minutes % 60);
//                    else {//what needs to bee in the other squares
//
//                        if (correctStage && correctRow) {
//                            return show.getArtist().getName();
//                        } else {
//                        }
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            public String getColumnName(int column) {
//                if (column == 0)
//                    return "Time";
//                else {
//                    return agenda.getStages().get(column - 1).getName();
//
//                }
//            }
//        });
//    }
//
//    public void setEditable(boolean isEditable) {
//        setModel(new AbstractTableModel() {
//            @Override
//            public int getRowCount() {
//                return (24 * 2) + 1;
//            }
//
//            @Override
//            public int getColumnCount() {
//                return (agenda.getStages().size() + 1);
//            }
//
//            @Override
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                minutes = rowIndex * 30;
//                hours = minutes / 60;
//                boolean correctStage;
//
//                int beginHours = 0;
//                int beginMinutes = 0;
//                int endHours = 0;
//                int endMinutes = 0;
//
//                boolean correctRow = false;
//                for (Show show : agenda.getShows()) {
//                    beginHours = show.getBeginTime().getHours();
//                    beginMinutes = show.getBeginTime().getMinutes();
//                    endHours = show.getEndTime().getHours();
//                    endMinutes = show.getEndTime().getMinutes();
//
//                    if (columnIndex == 0)
//                        correctStage = false;
//                    else //Þetta er Boolean
//                        correctStage = agenda.getStages().get(columnIndex - 1).getName().equals(show.getStage().getName());
//
//                    if ((beginHours < hours) && (endHours > hours)) {
//                        correctRow = true;
//                    } else if ((beginHours == hours) && (beginMinutes <= (minutes % 60))) {
//                        correctRow = true;
//                    } else if ((endHours == hours) && (endMinutes >= (minutes % 60))) {
//                        correctRow = true;
//                    } else {
//                        correctRow = false;
//                    }
//
//                    if (columnIndex == 0)
//                        return String.format("%02d", hours % 25) + ":" + String.format("%02d", minutes % 60);
//                    else {//what needs to bee in the other squares
//
//                        if (correctStage && correctRow) {
//                            return show.getArtist().getName();
//                        } else {
//                        }
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            public String getColumnName(int column) {
//                if (column == 0)
//                    return "Time";
//                else {
//                    return agenda.getStages().get(column - 1).getName();
//
//                }
//            }
//        });
//    }
//
//    public void addStage(Stage stage) {
//
//    }
//}
