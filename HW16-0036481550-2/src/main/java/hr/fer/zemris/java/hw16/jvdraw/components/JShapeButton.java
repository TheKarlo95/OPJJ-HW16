package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code JShapeButton} is a button factory of geometric shape.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JToggleButton
 */
public abstract class JShapeButton extends JToggleButton {

    /** Serial version UID. */
    private static final long serialVersionUID = 1533680399378051276L;

    /**
     * Returns the {@linkplain GeometricShape} with specified {@code start} and
     * {@code end} point and {@code foreground} and {@code background} color.
     * 
     * @param start
     *            the starting point; 1st point user pressed
     * @param end
     *            the end point; 2nd point user pressed
     * @param foreground
     *            the foreground color
     * @param background
     *            the background color
     * @return the geometric shape
     */
    public abstract GeometricShape getShape(Point start, Point end, Color foreground, Color background);

}
