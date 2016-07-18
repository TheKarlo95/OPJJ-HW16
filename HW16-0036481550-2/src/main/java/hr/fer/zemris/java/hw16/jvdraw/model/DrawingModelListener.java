package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * {@code DrawingObjectListModel} is a listener interface for receiving drawing
 * model events. The class that is interested in processing a drawing model
 * event must implement this interface.
 * <p>
 * The listener object created from that class is then registered with a
 * component using the component's
 * {@link DrawingModel#addDrawingModelListener(DrawingModelListener)} method.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DrawingModel
 */
public interface DrawingModelListener {

    /**
     * Indicates that the objects from index {@code index0}(inclusive) to index
     * {@code index1}(inclusive) were added to {@code source} drawing model.
     * 
     * @param source
     *            the drawing model which this listener observes
     * @param index0
     *            the index of first object to be added to drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be added to drawing model
     *            (inclusive)
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Indicates that the objects from index {@code index0}(inclusive) to index
     * {@code index1}(inclusive) were removed from {@code source} drawing model.
     * 
     * @param source
     *            the drawing model which this listener observes
     * @param index0
     *            the index of first object to be removed from drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be removed from drawing model
     *            (inclusive)
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Indicates that the objects from index {@code index0}(inclusive) to index
     * {@code index1}(inclusive) were changed in {@code source} drawing model.
     * 
     * @param source
     *            the drawing model which this listener observes
     * @param index0
     *            the index of first object to be changed in drawing model
     *            (inclusive)
     * @param index1
     *            the index of last object to be changed in drawing model
     *            (inclusive)
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);

}
