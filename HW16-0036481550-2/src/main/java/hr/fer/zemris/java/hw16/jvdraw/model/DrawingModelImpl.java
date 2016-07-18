package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code DrawingModelImpl} is class that implements {@linkplain DrawingModel}
 * and is used in drawing geometric shapes.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DrawingModel
 */
public class DrawingModelImpl implements DrawingModel {

    /** List of all shapes in this drawing model. */
    private List<GeometricShape> shapes;
    /** Collection of listeners that currently observe this drawing model. */
    private Collection<DrawingModelListener> listeners;

    /**
     * Constructs a new empty {@code DrawingModelImpl}.
     */
    public DrawingModelImpl() {
        shapes = new ArrayList<GeometricShape>();
        listeners = new HashSet<DrawingModelListener>();
    }

    @Override
    public int getSize() {
        return shapes.size();
    }

    @Override
    public GeometricShape getObject(int index) {
        return shapes.get(index);
    }

    @Override
    public void add(GeometricShape shape) {
        shapes.add(Objects.requireNonNull(shape, "Null parameter: shape"));
        int index = getSize() - 1;
        fireAdded(index, index);
    }

    @Override
    public void remove(int index) {
        shapes.remove(index);
        fireRemoved(index, index);
    }

    @Override
    public void change(int index, Component parent) {
        getObject(index).showChangeDialog(parent);
        fireChanged(index, index);
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener dml) {
        listeners.add(dml);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener dml) {
        listeners.remove(dml);
    } 

    @Override
    public Iterator<GeometricShape> iterator() {
        return shapes.iterator();
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
    public void setModel(DrawingModelImpl model) {
        Objects.requireNonNull(model, "Null parameter: model");
        this.shapes = new LinkedList<GeometricShape>(model.shapes);
        fireChanged(0, shapes.size() - 1);
    }

    /**
     * Notifies the listeners about adding the objects to this drawing model.
     * 
     * @param index0
     *            the index of first object to be added to drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be added to drawing model
     *            (inclusive)
     */
    private void fireAdded(int index0, int index1) {
        listeners.forEach(l -> l.objectsAdded(this, index0, index1));
    }

    /**
     * Notifies the listeners about removing the objects from this drawing
     * model.
     * 
     * @param index0
     *            the index of first object to be removed from drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be removed from drawing model
     *            (inclusive)
     */
    private void fireRemoved(int index0, int index1) {
        listeners.forEach(l -> l.objectsRemoved(this, index0, index1));
    }

    /**
     * Notifies the listeners about changing the objects in this drawing model.
     * 
     * @param index0
     *            the index of first object to be changed in drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be changed in drawing model
     *            (inclusive)
     */
    private void fireChanged(int index0, int index1) {
        listeners.forEach(l -> l.objectsChanged(this, index0, index1));
    }

    /**
     * Returns the drawing model with geometric shapes represented by lines in
     * list {@code lines}.
     * 
     * @param lines
     *            list of lines
     * @return the drawing model with geometric shapes represented by lines list
     */
    public static DrawingModelImpl fromString(List<String> lines) {
        DrawingModelImpl model = new DrawingModelImpl();
        for (String line : lines) {
            model.add(GeometricShape.getShape(line));
        }
        return model;
    }

}
