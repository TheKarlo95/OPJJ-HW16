package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;

/**
 * {@code JColorArea} is class that provides color and gives user the option to
 * choose color which they want this {@code JColorArea} to provide.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JComponent
 * @see IColorProvider
 */
public class JColorArea extends JComponent implements IColorProvider {

    /** Serial version UID. */
    private static final long serialVersionUID = 3895183069324382728L;

    /** Preferred size of {@code JColorArea}. */
    private static final Dimension PREFERRED_SIZE = new Dimension(15, 15);
    /** Maximum size of {@code JColorArea}. */
    private static final Dimension MAXIMUM_SIZE = new Dimension(15, 15);
    /** Minimum size of {@code JColorArea}. */
    private static final Dimension MINIMUM_SIZE = new Dimension(15, 15);

    /** Current color of this color area. */
    private Color color;
    /** Collection of listeners that currently observe this color area. */
    private Collection<ColorChangeListener> colorChangeListeners;

    /**
     * Constructs a new {@code JColorArea} with specified {@code parent}
     * component, {@code title} and {@code initialColor}.
     * 
     * @param parent
     *            parent component of this component
     * @param name
     *            the title/name of this color area
     * @param initialColor
     *            the initial color of this color area
     * @throws NullPointerException
     *             if parameters {@code title} or {@code initialColor} are a
     *             {@code null} reference
     */
    public JColorArea(Component parent, String name, Color initialColor) {
        setName(name);
        this.color = Objects.requireNonNull(initialColor, "Null parameter: color");

        this.colorChangeListeners = new HashSet<ColorChangeListener>();

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(parent, name, color);

                if (newColor != null && !newColor.equals(color)) {
                    fire(JColorArea.this.color, newColor);
                    setColor(newColor);
                    repaint();
                }
            }

        });
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    @Override
    public Dimension getMaximumSize() {
        return MAXIMUM_SIZE;
    }

    @Override
    public Dimension getMinimumSize() {
        return MINIMUM_SIZE;
    }

    @Override
    public Color getCurrentColor() {
        return color;
    }

    /**
     * Returns the string representation of {@code color} which is used in save
     * files.
     * 
     * @return the string representation of {@code color}
     */
    public String getCurrentColorString() {
        return color.getRed() + " " + color.getBlue() + " " + color.getGreen();
    }

    /**
     * Adds a color change listener to the collection of listeners that
     * currently observe this color area.
     * 
     * @param l
     *            the color change listener which is going to observe this color
     *            area
     * @throws NullPointerException
     *             if parameters {@code l} is a {@code null} reference
     */
    public void addColorChangeListener(ColorChangeListener l) {
        Objects.requireNonNull(l, "Null parameter: l");
        colorChangeListeners.add(l);
    }

    /**
     * Removes a color change listener from the collection of listeners that
     * currently observe this color area.
     * 
     * @param l
     *            the color change listener which is going to be removed from
     *            collection of listeners
     * @throws NullPointerException
     *             if parameters {@code l} is a {@code null} reference
     */
    public void removeColorChangeListener(ColorChangeListener l) {
        Objects.requireNonNull(l, "Null parameter: l");
        colorChangeListeners.remove(l);
    }

    /**
     * Notifies all listeners about a change in the color of the color area.
     * 
     * @param oldColor
     *            the old color
     * @param newColor
     *            the new color
     * @throws NullPointerException
     *             if parameters {@code oldColor} or {@code newColor} are a
     *             {@code null} reference
     */
    private void fire(Color oldColor, Color newColor) {
        Objects.requireNonNull(oldColor, "Null parameter: oldColor");
        Objects.requireNonNull(newColor, "Null parameter: newColor");
        colorChangeListeners.forEach(l -> l.newColorSelected(this, oldColor, newColor));
    }

    /**
     * Sets the color of this color area.
     * 
     * @param color
     *            the new color of this color area
     * @throws NullPointerException
     *             if parameters {@code color} is a {@code null} reference
     */
    private void setColor(Color color) {
        Objects.requireNonNull(color, "Null parameter: color");
        colorChangeListeners.forEach(l -> l.newColorSelected(JColorArea.this, this.color, color));
        this.color = color;
    }

}
