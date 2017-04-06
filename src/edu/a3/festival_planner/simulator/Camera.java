package edu.a3.festival_planner.simulator;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

class Camera implements MouseListener, MouseMotionListener, MouseWheelListener {

    private Point2D centerPoint = new Double(0.0d, 0.0d);
    private double zoom = .4d;
    private double rotation = 0.0d;
    private Point2D lastMousePos;
    private JPanel panel;

    public Camera(JPanel panel) {
        this.panel = panel;
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.addMouseWheelListener(this);
    }

    public AffineTransform getTransform(int windowWidth, int windowHeight) {
        AffineTransform tx = new AffineTransform();
        tx.translate(windowWidth / 2, windowHeight / 2);
        tx.scale(this.zoom, this.zoom);
        tx.translate(this.centerPoint.getX() - (windowWidth) * 1.25, this.centerPoint.getY() - (windowHeight * 1.25));
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
        if (SwingUtilities.isLeftMouseButton(e)) {
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
        this.zoom *= (double) (1.0F - (float) e.getWheelRotation() / 25.0F);
        this.panel.repaint();
    }
}
