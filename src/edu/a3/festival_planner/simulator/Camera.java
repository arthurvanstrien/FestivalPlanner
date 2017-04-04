package edu.a3.festival_planner.simulator;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Camera implements MouseListener, MouseMotionListener, MouseWheelListener {

  Point2D centerPoint = new Double(0.0D, 0.0D);
  double zoom = 0.4D;
  double prevZoom = 0.4D;
  double rotation = 0.0D;
  Point2D lastMousePos;
  Point2D currentMousePos;
  JPanel panel;

  public Camera(JPanel panel) {
    this.panel = panel;
    panel.addMouseListener(this);
    panel.addMouseMotionListener(this);
    panel.addMouseWheelListener(this);
  }

  public AffineTransform getTransform(int windowWidth, int windowHeight) {
    AffineTransform tx = new AffineTransform();
    tx.translate((double) (0), (double) (0));
    //tx.translate((double)(windowWidth / 2), (double)(windowHeight / 2));
    if(lastMousePos != null) {
      if (lastMousePos != null && zoom != prevZoom) {
        lastMousePos = MouseInfo.getPointerInfo().getLocation();
        prevZoom = zoom;
      }
      tx.translate(lastMousePos.getX(), lastMousePos.getY());
      tx.scale(this.zoom, this.zoom);
      tx.translate(-lastMousePos.getX(), -lastMousePos.getY());
    }
    tx.translate(centerPoint.getX(), centerPoint.getY());
    tx.rotate(this.rotation);
    return tx;
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    this.lastMousePos = e.getPoint();
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseDragged(MouseEvent e) {
    if (SwingUtilities.isMiddleMouseButton(e)) {
      this.centerPoint = new Double(
          this.centerPoint.getX() - (this.lastMousePos.getX() - (double) e.getX()) / this.zoom,
          this.centerPoint.getY() - (this.lastMousePos.getY() - (double) e.getY()) / this.zoom);
      this.lastMousePos = e.getPoint();
      this.panel.repaint();
    }

  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseWheelMoved(MouseWheelEvent e) {
    this.currentMousePos = e.getLocationOnScreen();
    this.zoom *= (double) (1.0F - (float) e.getWheelRotation() / 25.0F);
    this.panel.repaint();
  }
}
