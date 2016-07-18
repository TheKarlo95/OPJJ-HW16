package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code JShapeButtonGroup} is a implmentation of a {@linkplain ButtonGroup}
 * which creates mutually exclusive button group from {@linkplain JShapeButton}
 * objects specified in constructor.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see ButtonGroup
 */
public class JShapeButtonGroup extends ButtonGroup {

    /** Serial version UID. */
    private static final long serialVersionUID = -4600092135726392098L;

    /** Currently selected button. */
    private JShapeButton current;

    /**
     * Constructs a {@code JShapeButtonGroup} with specified
     * {@linkplain JShapeButton} array {@code buttons}.
     * 
     * @param buttons
     *            the buttons to be used in this group
     */
    public JShapeButtonGroup(JShapeButton... buttons) {
        this(Arrays.asList(buttons));
    }

    /**
     * Constructs a {@code JShapeButtonGroup} with specified
     * {@linkplain JShapeButton} collection {@code buttons}.
     * 
     * @param buttons
     *            the buttons to be used in this group
     */
    public JShapeButtonGroup(Collection<JShapeButton> buttons) {
        this.current = buttons.toArray(new JShapeButton[0])[0];
        this.current.setSelected(true);

        for (JShapeButton button : buttons) {
            super.add(button);
            button.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    JShapeButtonGroup.this.current = button;
                }

            });
        }
    }

    /**
     * Returns the {@linkplain GeometricShape} using {@code current} button's
     * method {@linkplain JShapeButton#getShape(Point, Point, Color, Color)}.
     * 
     * @param start
     *            the starting point; 1st point user pressed
     * @param end
     *            the end point; 2nd point user pressed
     * @param foreground
     *            the foreground color
     * @param background
     *            the background color
     * @return the geometric shape with specified parameters
     * @see JShapeButton#getShape(Point, Point, Color, Color)
     */
    public GeometricShape getShape(Point start, Point end, Color foreground, Color background) {
        return current.getShape(start, end, foreground, background);
    }

    @Override
    public void add(AbstractButton b) {
    }

}
