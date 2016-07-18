package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * {@code ColorChangeListener} is a listener interface for receiving color
 * selection events. The class that is interested in getting selected color must
 * implement this interface.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DrawingModel
 */
public interface ColorChangeListener {

    /**
     * Indicates that the color provider {@code source} changed color from
     * {@code oldColor} to {@code newColor}.
     * 
     * @param source
     *            the color provider which this listener observes
     * @param oldColor
     *            the old color
     * @param newColor
     *            the new color)
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
