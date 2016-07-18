package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Component;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * {@code GeometricShape} is a Swing component class that represents a geometric
 * shape.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JComponent
 */
public abstract class GeometricShape extends JComponent {

    /** Serial version UID. */
    private static final long serialVersionUID = 1960052435737918409L;

    /** Foreground color. */
    protected Color foreground;
    /** Background color. */
    protected Color background;

    /**
     * Moves,resizes and changes foreground and background color of this
     * {@code GeometricShape}. The new location of the top-left corner is
     * specified by {@code x} and {@code y}, and the new size is specified by
     * {@code width} and {@code height}.
     * 
     * @param x
     *            the new x-coordinate of this {@code GeometricShape}
     * @param y
     *            the new y-coordinate of this {@code GeometricShape}
     * @param width
     *            the new {@code width} of this {@code GeometricShape}
     * @param height
     *            the new {@code height} of this {@code GeometricShape}
     * @param foreground
     *            the foreground color of this {@code GeometricShape}
     * @param background
     *            the background color of this {@code GeometricShape}
     */
    protected void set(int x, int y, int width, int height, Color foreground, Color background) {
        this.foreground = Objects.requireNonNull(foreground, "Null parameter: foregroundColor");
        this.background = Objects.requireNonNull(background, "Null parameter: backgroundColor");

        setBounds(x, y, width, height);
    }

    /**
     * Shows change {@link JOptionPane dialog} which user can use to change
     * properties of the {@code GeometricShape}.
     * 
     * @param parent
     *            determines the {@code Frame} in which the dialog is displayed;
     *            if {@code null}, or if the {@code parentComponent} has no
     *            {@code Frame}, a default {@code Frame} is used
     */
    public abstract void showChangeDialog(Component parent);

    /**
     * Returns the string representation of this {@code GeometricShape} which is
     * used in save files.
     * 
     * @return the string representation of this {@code GeometricShape}
     */
    public abstract String getString();

    /**
     * Returns a {@code GeometricShape} represented by a parameter {@code str}.
     * 
     * @param str
     *            string representation of the {@code GeometricShape}
     * @return {@code GeometricShape} represented by a parameter {@code str}
     */
    public static GeometricShape getShape(String str) {
        GeometricShape shape = null;

        if (str.startsWith(LineShape.SHAPE_NAME)) {
            shape = getLine(str);
        } else if (str.startsWith(CircleShape.SHAPE_NAME)) {
            shape = getCircle(str);
        } else if (str.startsWith(FilledCircleShape.SHAPE_NAME)) {
            shape = getFilledCircle(str);
        }

        return shape;
    }

    /**
     * Parses the parameter {@code str} and returns the {@link LineShape}.
     * <p>
     * Only valid string format is
     * "LINE [0-255] [0-255] [0-255] [0-255] [0-255] [0-255] [0-255]"
     * 
     * @param str
     *            string representation of the {@code LineShape}
     * @return {@code LineShape} object
     */
    private static GeometricShape getLine(String str) {
        Pattern pattern = Pattern
                .compile(LineShape.SHAPE_NAME + " ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3})"
                        + " ([0-9]{1,3}) ([0-9]{1,3})");
        Matcher matcher = pattern.matcher(str.trim());
        GeometricShape shape = null;

        if (matcher.find()) {
            int x1 = Integer.valueOf(matcher.group(1));
            int y1 = Integer.valueOf(matcher.group(2));
            int x2 = Integer.valueOf(matcher.group(3));
            int y2 = Integer.valueOf(matcher.group(4));
            int r = Integer.valueOf(matcher.group(5));
            int g = Integer.valueOf(matcher.group(6));
            int b = Integer.valueOf(matcher.group(7));

            shape = new LineShape(x1, y1, x2, y2, new Color(r, g, b));
        }

        return shape;
    }

    /**
     * Parses the parameter {@code str} and returns the {@link CircleShape}.
     * <p>
     * Only valid string format is
     * "CIRCLE [0-255] [0-255] [0-255] [0-255] [0-255] [0-255]"
     * 
     * @param str
     *            string representation of the {@code CircleShape}
     * @return {@code CircleShape} object
     */
    private static GeometricShape getCircle(String str) {
        Pattern pattern = Pattern
                .compile(CircleShape.SHAPE_NAME + " ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3})"
                        + " ([0-9]{1,3})");
        Matcher matcher = pattern.matcher(str.trim());
        GeometricShape shape = null;

        if (matcher.find()) {
            int x = Integer.valueOf(matcher.group(1));
            int y = Integer.valueOf(matcher.group(2));
            int radius = Integer.valueOf(matcher.group(3));
            int r = Integer.valueOf(matcher.group(4));
            int g = Integer.valueOf(matcher.group(5));
            int b = Integer.valueOf(matcher.group(6));

            shape = new CircleShape(x, y, radius, new Color(r, g, b));
        }

        return shape;
    }

    /**
     * Parses the parameter {@code str} and returns the
     * {@link FilledCircleShape}.
     * <p>
     * Only valid string format is
     * "FCIRCLE [0-255] [0-255] [0-255] [0-255] [0-255] [0-255] [0-255] [0-255] [0-255]"
     * 
     * @param str
     *            string representation of the {@code FilledCircleShape}
     * @return {@code FilledCircleShape} object
     */
    private static GeometricShape getFilledCircle(String str) {
        Pattern pattern = Pattern
                .compile(FilledCircleShape.SHAPE_NAME + " ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3})"
                        + " ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3}) ([0-9]{1,3})");
        Matcher matcher = pattern.matcher(str.trim());
        GeometricShape shape = null;

        if (matcher.find()) {
            int x = Integer.valueOf(matcher.group(1));
            int y = Integer.valueOf(matcher.group(2));
            int radius = Integer.valueOf(matcher.group(3));
            int r1 = Integer.valueOf(matcher.group(4));
            int g1 = Integer.valueOf(matcher.group(5));
            int b1 = Integer.valueOf(matcher.group(6));
            int r2 = Integer.valueOf(matcher.group(7));
            int g2 = Integer.valueOf(matcher.group(8));
            int b2 = Integer.valueOf(matcher.group(9));

            shape = new FilledCircleShape(x, y, radius, new Color(r1, g1, b1), new Color(r2, g2, b2));
        }

        return shape;
    }

    /**
     * Returns the string representation of {@code color} which is used in save
     * files.
     * 
     * @param color
     *            the color
     * @return the string representation of {@code color}
     */
    protected static String colorToString(Color color) {
        return color.getRed() + " " + color.getBlue() + " " + color.getGreen();
    }

}