package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code JDrawingCanvas} is a class that is used as a canvas to draw on. All
 * elements draw on it are from drawing model which must be passed in
 * constructor.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JComponent
 * @see DrawingModelListener
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /** Serial version UID. */
    private static final long serialVersionUID = -6691714734461111245L;
    /** Drawing model. */
    private DrawingModel model;
    /** Start point. */
    private Point start;
    /** Image that will be rendered. */
    private BufferedImage image;

    /**
     * Constructs a new {@code JDrawingCanvas} with specified drawing
     * {@code model}.
     * 
     * @param model
     *            the drawing model
     */
    public JDrawingCanvas(DrawingModel model) {
        setModel(model);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image == null || (image.getWidth() != getWidth() || image.getHeight() != getHeight())) {
            updateImage();
        }

        g.drawImage(image, 0, 0, Color.WHITE, null);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        updateImage();
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        updateImage();
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        updateImage();
        repaint();
    }

    /**
     * Creates image from geometric shapes from drawing model.
     */
    private void updateImage() {
        Dimension size = getSize();

        image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size.width, size.height);

        model.forEach(shape -> shape.paint(g));

        g.dispose();
    }

    /**
     * Returns the starting point.
     * 
     * @return the starting point
     */
    public Point getStartPoint() {
        return start;
    }

    /**
     * Sets the start point.
     * 
     * @param start
     *            new start point
     */
    public void setStartPoint(Point start) {
        if (start == null) {
            this.start = null;
        } else {
            this.start = new Point(start);
        }
    }

    /**
     * Sets the start point.
     * 
     * @param x
     *            new x coordinate
     * @param y
     *            new y coordinate
     */
    public void setStartPoint(int x, int y) {
        setStartPoint(new Point(x, y));
    }

    /**
     * Paints the specified {@code shape} with the image.
     * 
     * @param shape
     *            the shape to be painted
     */
    public void paintShape(GeometricShape shape) {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, Color.WHITE, null);
        shape.paint(g);
    }

    /**
     * Sets the {@code shapes} to the ones from drawing model {@code model} but
     * keeps the same {@code listeners}.
     * 
     * @param model
     *            the drawing model
     * @throws NullPointerException
     *             if parameter {@code model} is a {@code null} reference
     */
    public void setModel(DrawingModel model) {
        Objects.requireNonNull(model, "Null parameter: model");
        image = null;

        if (this.model != null) {
            ((DrawingModelImpl) this.model).setModel((DrawingModelImpl) model);
        } else {
            this.model = model;
            this.model.addDrawingModelListener(this);
        }
    }

}
