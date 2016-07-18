package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * {@code IColorProvider} is a functional interface that returns the current
 * color.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface IColorProvider {

    /**
     * Returns the current color.
     * 
     * @return the current color
     */
    public Color getCurrentColor();

}
