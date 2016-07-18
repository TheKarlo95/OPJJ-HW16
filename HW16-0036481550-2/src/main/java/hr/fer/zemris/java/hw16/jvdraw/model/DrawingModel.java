package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Component;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;

/**
 * {@code DrawingModel} is class that represent data model used in drawing
 * geometric shapes.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface DrawingModel extends Iterable<GeometricShape> {

    /**
     * Returns the number of elements in this drawing model. If this list
     * contains more than {@value Integer#MAX_VALUE} elements, returns
     * {@value Integer#MAX_VALUE}.
     *
     * @return the number of elements in this list
     */
    public int getSize();

    /**
     * Returns the element at the specified position in this drawing model.
     *
     * @param index
     *            index of the object to return
     * @return the object at the specified position in this drawing model
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             {@code index < 0 || index >= size()})
     */
    public GeometricShape getObject(int index);

    /**
     * Appends the specified geometric {@code shape} to the end of this drawing
     * model and notifies the listeners.
     * 
     * @param shape
     *            the shape to be appended to this drawing model
     * @throws NullPointerException
     *             if parameter {@code shape} is a {@code null} reference
     */
    public void add(GeometricShape shape);

    /**
     * Removes the element at the specified {@code index} in this drawing model
     * and notifies the listeners. Shifts any subsequent elements to the left
     * (subtracts one from their indices).
     * 
     * @param index
     *            index the index of the element to be removed
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (
     *             {@code index < 0 || index >= size()})
     */
    public void remove(int index);

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
     */
    public void change(int index, Component parent);

    /**
     * Adds the specified drawing model listener to receive drawing model events
     * from this drawing model.
     * 
     * @param l
     *            the drawing model listener which is going to observe this
     *            drawing model
     */
    public void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the specified drawing model listener from drawing model
     * listeners.
     * 
     * @param l
     *            the drawing model listener which is going to be removed
     */
    public void removeDrawingModelListener(DrawingModelListener l);

    /**
     * Returns a string representation of the drawing model, usually used to
     * save a drawing model as {@code JVD} file.
     * <p>
     * Every geometric shape in this drawing model is represented by method
     * {@linkplain GeometricShape#getString()}.
     * 
     * @return the string representation of the drawing model
     * @see GeometricShape#getString()
     */
    public default String getString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0, size = getSize(); i < size; i++) {
            sb.append(getObject(i).getString() + "\n");
        }

        return sb.toString();
    }

    /**
     * Returns bytes representation of the drawing model. Bytes represent string
     * from method {@linkplain DrawingModel#getString()} encoded with
     * {@linkplain StandardCharsets#UTF_8}.
     * 
     * @return bytes the byte representation of the drawing model encoded with
     *         {@linkplain StandardCharsets#UTF_8}
     */
    public default byte[] getBytes() {
        return getString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Returns bytes representation of the drawing model. Bytes represent string
     * from method {@linkplain DrawingModel#getString()} encoded with specified
     * {@code charset}.
     * 
     * @param charset
     *            The {@linkplain java.nio.charset.Charset} to be used to encode
     *            the {@code String}
     * 
     * @return bytes the byte representation of the drawing model encoded with
     *         specified {@code charset}
     */
    public default byte[] getBytes(Charset charset) {
        return getString().getBytes(charset);
    }

    @Override
    public Iterator<GeometricShape> iterator();

}
