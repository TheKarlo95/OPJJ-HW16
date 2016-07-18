package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Component;
import java.util.Objects;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code DrawingObjectListModel} is a decorator class that constructs a list
 * model out of drawing model.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractListModel
 * @see DrawingModel
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricShape> {

    /** Serial version UID. */
    private static final long serialVersionUID = -3638989311447730674L;

    /** Model of the drawing. */
    private DrawingModel model;

    /**
     * Constructs a new {@code DrawingObjectListModel} with specified drawing
     * {@code model}.
     * 
     * @param model
     *            the model of the drawing
     * @throws NullPointerException
     *             if parameter {@code model} is a {@code null} reference
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = Objects.requireNonNull(model, "Null parameter: model");

        model.addDrawingModelListener(new DrawingModelListener() {

            @Override
            public void objectsRemoved(DrawingModel source, int index0, int index1) {
                fireIntervalRemoved(source, index0, index1);
            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                fireContentsChanged(source, index0, index1);
            }

            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                fireIntervalAdded(source, index0, index1);
            }
        });
    }

    @Override
    public int getSize() {
        return model.getSize();
    }

    @Override
    public GeometricShape getElementAt(int index) {
        return model.getObject(index);
    }

    /**
     * Changes the element at the specified {@code index} in this drawing model
     * and notifies the listeners.
     * 
     * @param index
     *            index the index of the element to be changed
     * @param parent
     *            parent component used to center change dialog
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             {@code index < 0 || index >= size()})
     * @see DrawingModel#change(int, Component)
     */
    public void change(int index, Component parent) {
        model.change(index, parent);
    }

}
