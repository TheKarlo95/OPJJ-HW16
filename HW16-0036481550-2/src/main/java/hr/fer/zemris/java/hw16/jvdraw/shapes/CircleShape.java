package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;

/**
 * {@code CircleShape} is a Swing component class that represents a
 * {@link GeometricShape geometric shape} circle without fill.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see GeometricShape
 */
public class CircleShape extends GeometricShape {

    /** Serial version UID. */
    private static final long serialVersionUID = 9110752092907627194L;

    /** Name of the geometric shape. */
    public static final String SHAPE_NAME = "CIRCLE";

    /** The center point. */
    Point center;
    /** The radius. */
    private int radius;

    /**
     * Constructs a new {@code CircleShape} as a deep copy of parameter
     * {@code other}.
     * 
     * @param other
     *            the {@code CircleShape} that is going to be copied
     */
    public CircleShape(CircleShape other) {
        this(other.center, other.radius, other.foreground);
    }

    /**
     * Constructs a new {@code CircleShape} with specified {@code center} point,
     * {@code radius} of the circle and {@code color}.
     * 
     * @param center
     *            the center point
     * @param radius
     *            the radius
     * @param color
     *            the foreground color(outline color)
     */
    public CircleShape(Point center, int radius, Color color) {
        this(center.x, center.y, radius, color);
    }

    /**
     * Constructs a new {@code FilledCircleShape} with specified center point's
     * {@code x} and {@code y} coordinates, the {@code radius} and {@code color}
     * .
     * 
     * @param x
     *            the center point's x coordinate
     * @param y
     *            the center point's y coordinate
     * @param radius
     *            the radius
     * @param color
     *            the foreground color(outline color)
     */
    public CircleShape(int x, int y, int radius, Color color) {
        set(new Point(x, y), radius, color);
    }

    /**
     * Constructs a new {@code CircleShape} with specified {@code start} and
     * {@code end} point and {@code color} of the circle.
     * <p>
     * Points {@code start} and {@code end} are two points where user clicked.
     * 
     * @param start
     *            the first point
     * @param end
     *            the second point
     * @param color
     *            the color of the circle
     */
    public CircleShape(Point start, Point end, Color color) {
        this(start, Double.valueOf(start.distance(end)).intValue(), color);
    }

    @Override
    public void paint(Graphics g) {
        Rectangle bounds = getBounds();

        g.setColor(background);
        g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public String getString() {
        return SHAPE_NAME + " " + center.x + " " + center.y + " " + radius + " " + colorToString(foreground);
    }

    @Override
    public String getName() {
        return SHAPE_NAME;
    }

    @Override
    public void showChangeDialog(Component parent) {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));

        JTextField xTextField = new JTextField(Integer.toString(center.x));
        JTextField yTextField = new JTextField(Integer.toString(center.y));
        JTextField radiusTextField = new JTextField(Integer.toString(radius));
        JColorArea color = new JColorArea(parent, "Choose color", foreground);

        panel.add(new JLabel("Center:"));
        panel.add(xTextField);
        panel.add(yTextField);
        panel.add(new JLabel("Radius:"));
        panel.add(radiusTextField);
        panel.add(new JPanel());
        panel.add(new JLabel("Color:"));
        panel.add(color);

        if (JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Change circle",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Point center = new Point();
                int radius = 0;

                try {
                    center.x = Integer.parseInt(xTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("X coordinate must be of integer type.");
                }
                try {
                    center.y = Integer.parseInt(yTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Y coordinate must be of integer type.");
                }
                try {
                    radius = Integer.parseInt(radiusTextField.getText());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Radius must be of integer type.");
                }

                set(center, radius, color.getCurrentColor());
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
     * Changes {@code center} point, {@code radius} and {@code color}.
     * 
     * @param center
     *            the center point
     * @param radius
     *            the radius
     * @param color
     *            the foreground color(outline color)
     * @see GeometricShape#set(int, int, int, int, Color, Color)
     */
    protected void set(Point center, int radius, Color color) {
        Objects.requireNonNull(center, "Null parameter: center");
        Objects.requireNonNull(color, "Null parameter: color");

        this.center = center;
        this.radius = radius;

        super.set(center.x - radius, center.y - radius, 2 * radius, 2 * radius, color, color);
    }

}