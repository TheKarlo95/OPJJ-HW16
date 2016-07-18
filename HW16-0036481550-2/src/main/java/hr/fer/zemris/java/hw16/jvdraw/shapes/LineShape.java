package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;

/**
 * {@code LineShape} is a Swing component class that represents a
 * {@link GeometricShape geometric shape} line.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see GeometricShape
 */
public class LineShape extends GeometricShape {

    /** Serial version UID. */
    private static final long serialVersionUID = -6727683780813568064L;

    /** Name of the geometric shape. */
    public static final String SHAPE_NAME = "LINE";

    /** The first point. */
    private Point start;
    /** The second point. */
    private Point end;

    /**
     * Constructs a new {@code LineShape} as a deep copy of parameter
     * {@code other}.
     * 
     * @param other
     *            the {@code LineShape} that is going to be copied
     */
    public LineShape(LineShape other) {
        this(other.start, other.end, other.foreground);
    }

    /**
     * Constructs a new {@code LineShape} with specified {@code start} and
     * {@code end} point and {@code color} of the line.
     * 
     * @param start
     *            the first point
     * @param end
     *            the second point
     * @param color
     *            the color of the line
     */
    public LineShape(Point start, Point end, Color color) {
        this(start.x, start.y, end.x, end.y, color);
    }

    /**
     * Constructs a new {@code LineShape} with specified first and second
     * point's x and y coordinates and {@code color} of the line.
     * 
     * @param x1
     *            the first point's x coordinate
     * @param y1
     *            the first point's y coordinate
     * @param x2
     *            the second point's x coordinate
     * @param y2
     *            the second point's y coordinate
     * @param color
     *            the color of the line
     */
    public LineShape(int x1, int y1, int x2, int y2, Color color) {
        set(new Point(x1, y1), new Point(x2, y2), color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(background);
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public String getString() {
        return SHAPE_NAME + " " + start.x + " " + start.y + " " + end.x + " " + end.y + " " + colorToString(foreground);
    }

    @Override
    public String getName() {
        return SHAPE_NAME;
    }

    @Override
    public void showChangeDialog(Component parent) {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));

        JTextField startXTextField = new JTextField(Integer.toString(start.x));
        JTextField startYTextField = new JTextField(Integer.toString(start.y));
        JTextField endXTextField = new JTextField(Integer.toString(end.x));
        JTextField endYTextField = new JTextField(Integer.toString(end.y));
        JColorArea color = new JColorArea(parent, "Choose color", foreground);

        panel.add(new JLabel("Start:"));
        panel.add(startXTextField);
        panel.add(startYTextField);
        panel.add(new JLabel("End:"));
        panel.add(endXTextField);
        panel.add(endYTextField);
        panel.add(new JLabel("Color:"));
        panel.add(color);

        if (JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Change line",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Point start = new Point();
                Point end = new Point();

                try {
                    start.x = Integer.parseInt(startXTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("X coordinate of starting point must be of integer type.");
                }
                try {
                    start.y = Integer.parseInt(startYTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Y coordinate of starting point must be of integer type.");
                }
                try {
                    end.x = Integer.parseInt(endXTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("X coordinate of ending point must be of integer type.");
                }
                try {
                    end.y = Integer.parseInt(endYTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Y coordinate of ending point must be of integer type.");
                }

                set(start, end, color.getCurrentColor());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                        parent,
                        e.getMessage(),
                        "Invalid parameter",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Changes {@code start} point, {@code end} point and the {@code color} of
     * the line.
     * 
     * @param start
     *            new first point
     * @param end
     *            new first point
     * @param color
     *            new color of the line
     * @see GeometricShape#set(int, int, int, int, Color, Color)
     */
    private void set(Point start, Point end, Color color) {
        Objects.requireNonNull(start, "Null parameter: start");
        Objects.requireNonNull(end, "Null parameter: end");
        Objects.requireNonNull(color, "Null parameter: color");

        this.start = start;
        this.end = end;

        super.set(
                Math.min(start.x, end.x),
                Math.min(start.y, end.y),
                Math.abs(start.x - end.x),
                Math.abs(start.y - end.y),
                color,
                color);
    }

}
