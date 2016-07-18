package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.AbstractAction;

/**
 * {@code ExitAction} is a action class that dispatches
 * {@linkplain WindowEvent#WINDOW_CLOSING} event and therefore closes the
 * window.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractAction
 */
public class ExitAction extends AbstractAction {

    /** Serial version UID. */
    private static final long serialVersionUID = -7828827911357570620L;

    /** Parent window of this component. */
    private Window parent;

    /**
     * Constructs a new {@code ExitAction} with specified {@code parent} window
     * and "Export" as a name of this action.
     * 
     * @param parent
     *            the parent component of this component
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public ExitAction(Window parent) {
        this(parent, "Exit");
    }

    /**
     * Constructs a new {@code ExitAction} with specified {@code parent} window
     * and action {@code name}.
     * 
     * @param parent
     *            the parent component of this component
     * @param name
     *            the name of this action
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public ExitAction(Window parent, String name) {
        super(name);
        this.parent = Objects.requireNonNull(parent, "Null parameter: parent");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
    }

}
