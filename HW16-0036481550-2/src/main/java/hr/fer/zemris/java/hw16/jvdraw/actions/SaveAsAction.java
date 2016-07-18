package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

/**
 * {@code SaveAsAction} is a action class that performs operation of saving
 * drawing model to a user specified path.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractAction
 */
public class SaveAsAction extends AbstractAction {

    /** Serial version UID. */
    private static final long serialVersionUID = -8166437167222569833L;

    /** Drawing model. */
    private DrawingModel model;
    /** Parent component of this component. */
    private Component parent;
    /** Indicates if model has been modified since last save. */
    private boolean modified;

    /**
     * Constructs a new {@code SaveAsAction} with specified {@code parent}
     * component and drawing {@code model} and "Save as..." as a name of this
     * action.
     * 
     * @param parent
     *            the parent component of this component
     * @param model
     *            the drawing model
     * @throws NullPointerException
     *             if parameter {@code model} is a {@code null} reference
     */
    public SaveAsAction(Component parent, DrawingModel model) {
        this(parent, model, "Save as...");
    }

    /**
     * Constructs a new {@code SaveAsAction} with specified {@code parent}
     * component, drawing {@code model} and action {@code name}.
     * 
     * @param parent
     *            the parent component of this component
     * @param model
     *            the drawing model
     * @param name
     *            the name of this action
     * @throws NullPointerException
     *             if parameter {@code model} is a {@code null} reference
     */
    public SaveAsAction(Component parent, DrawingModel model, String name) {
        super(name);
        this.model = Objects.requireNonNull(model, "Null parameter: model");
        this.parent = parent;
        setModified(false);

        model.addDrawingModelListener(new DrawingModelListener() {

            @Override
            public void objectsRemoved(DrawingModel source, int index0, int index1) {
                modified();
            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                modified();
            }

            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                modified();
            }

            private void modified() {
                SaveAsAction.this.setModified(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!modified)
            return;

        Path path = choosePath();

        if (path == null)
            return;

        try {
            if (Files.exists(path)) {
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to overwrite ",
                        "Warning",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            Files.write(path, model.getBytes());
            setModified(false);

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error while saving file to " + path.toString() + "!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Set the drawing model.
     * 
     * @param model
     *            new drawing model
     * @throws NullPointerException
     *             if parameters {@code model} is a {@code null} reference
     */
    public void setModel(DrawingModel model) {
        this.model = Objects.requireNonNull(model, "Null parameter: model");
    }

    /**
     * Returns {@code true} if model has been modified since last save.
     * 
     * @return {@code true} if model has been modified since last save;
     *         {@code false} otherwise
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets the modified flag and enables/disables this action if new
     * {@code modified} value is {@code true}/{@code false}.
     * 
     * @param modified
     *            new modified value
     */
    private void setModified(boolean modified) {
        if (this.modified == modified)
            return;

        this.modified = modified;
        SaveAsAction.this.setEnabled(modified);
    }

    /**
     * Asks the user for save path and returns it.
     * 
     * @return the path user chose
     * @see JFileChooser
     */
    private Path choosePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JVD (*.jvd)", ".jvd"));

        Path path = null;
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().toPath();
            String extension = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];

            if (!path.getFileName().endsWith(extension)) {
                path = Paths.get(path.toString() + extension);
            }
        }

        return path;
    }
}