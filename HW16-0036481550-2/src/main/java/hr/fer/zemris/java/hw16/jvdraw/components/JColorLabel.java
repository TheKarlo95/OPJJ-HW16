package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.util.Objects;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;

/**
 * {@code JColorLabel} is a class that extends {@linkplain JLabel} and is used
 * to visually represent {@linkplain JColorArea} as a label. This label
 * automatically changes according to changes in the {@code JColorArea}.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JLabel
 */
public class JColorLabel extends JLabel {

    /** Serial version UID. */
    private static final long serialVersionUID = 4913069547664152137L;

    /** Color area that this label is going to represent. */
    private JColorArea colorArea;

    /**
     * Constructs a new {@code JColorLabel} with specified {@code colorArea}
     * 
     * @param colorArea
     *            the color area that this label is going to represent
     * @throws NullPointerException
     *             if parameter {@code colorArea} is a {@code null} reference
     */
    public JColorLabel(JColorArea colorArea) {
        this.colorArea = Objects.requireNonNull(colorArea, "Null parameter: colorArea");

        setText(colorArea.getCurrentColor());
        this.colorArea.addColorChangeListener(new ColorChangeListener() {

            @Override
            public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
                if (newColor != null && !newColor.equals(oldColor)) {
                    setText(newColor);
                }
            }

        });
    }

    /**
     * Sets the text of this label using specified color.
     * 
     * @param c
     *            the colors
     */
    private void setText(Color c) {
        setText(String.format("%s color: (%d, %d, %d)", colorArea.getName(), c.getRed(), c.getGreen(), c.getBlue()));
    }
}
