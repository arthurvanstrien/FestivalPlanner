package edu.a3.festival_planner.agenda;

import edu.a3.festival_planner.general.Main;

import javax.swing.*;

/**
 * Created by snick on 12/02/2017.
 */
public class Tab extends JScrollPane {

    private AgendaView agendaView;

  /**
   * Create new tab of given agenda and display it in the given main.
   */
  public Tab(Main main, Agenda agenda) {
    super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    agendaView = agenda.getAgendaView();
    getViewport().add(agendaView);
  }

  /**
   * Passes the isEditable boolean to AgendaView setEditable() method.
   */
  public void setEditable(boolean isEditable) {
    agendaView.setEditable(isEditable);
  }
}
