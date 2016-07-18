package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;

/**
 * {@code OpenAction} is a action class that performs operation of opening JVD
 * files and creating drawing model.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractAction
 */
public class OpenAction extends AbstractAction {

    /** Serial version UID. */
    private static final long serialVersionUID = -2797157404470873154L;

    /** Drawing model. */
    private DrawingModel model;
    /** Parent component of this component. */
    private Component parent;
    /** Drawing canvas. */
    private JDrawingCanvas canvas;
    /** Save action. */
    private SaveAction saveAction;

    /**
     * Constructs a new {@code OpenAction} with specified parameters and "Open"
     * as an action name.
     * 
     * @param parent
     *            the parent component of this component
     * @param model
     *            the drawing model
     * @param canvas
     *            the drawing canvas
     * @param saveAction
     *            the save action
     * @throws NullPointerException
     *             if parameters {@code model}, {@code canvas} and
     *             {@code saveAction} are a {@code null} reference
     */
    public OpenAction(Component parent, DrawingModel model, JDrawingCanvas canvas, SaveAction saveAction) {
        this(parent, model, canvas, saveAction, "Open");
    }

    /**
     * Constructs a new {@code OpenAction} with specified parameters.
     * 
     * @param parent
     *            the parent component of this component
     * @param model
     *            the drawing model
     * @param canvas
     *            the drawing canvas
     * @param saveAction
     *            the save action
     * @param name
     *            the name of this action
     * @throws NullPointerException
     *             if parameters {@code model}, {@code canvas} and
     *             {@code saveAction} are a {@code null} reference
     */
    public OpenAction(Component parent, DrawingModel model, JDrawingCanvas canvas, SaveAction saveAction, String name) {
        super(name);
        this.parent = parent;
        this.model = Objects.requireNonNull(model, "Null parameter: model");
        this.canvas = Objects.requireNonNull(canvas, "Null parameter: canvas");
        this.saveAction = Objects.requireNonNull(saveAction, "Null parameter: saveAction");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JVD", "jvd"));

        int r = fileChooser.showOpenDialog(parent);
        if (r == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath();

            List<String> lines = null;
            try {
                lines = Files.readAllLines(path);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(
                        parent,
                        "Error while opening file: " + path,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ((DrawingModelImpl) model).setModel(DrawingModelImpl.fromString(lines));
            canvas.setModel((DrawingModelImpl) model);
            canvas.repaint();
            saveAction.setModel(model);
            saveAction.setPath(path);
        }
    }

}
