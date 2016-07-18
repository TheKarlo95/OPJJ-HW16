package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * {@code ExportAction} is a action class that performs operation of exporting
 * drawings to image files.
 * <p>
 * Currently this action is not implemented.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractAction
 */
public class ExportAction extends AbstractAction {

    /** Serial version UID. */
    private static final long serialVersionUID = 2416211982488481743L;

    /** Parent component of this component. */
    private Component parent;
    /** Panel to be displayed in message dialog. */
    private JPanel panel;

    /**
     * Constructs a new {@code ExportAction} with specified {@code parent}
     * window and "Export" as a name of this action.
     * 
     * @param parent
     *            the parent component of this component
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public ExportAction(Component parent) {
        this(parent, "Export");
    }

    /**
     * Constructs a new {@code ExportAction} with specified {@code parent}
     * window and action {@code name}.
     * 
     * @param parent
     *            the parent component of this component
     * @param name
     *            the name of this action
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public ExportAction(Component parent, String name) {
        super(name);
        this.parent = Objects.requireNonNull(parent, "Null parameter: parent");

        panel = new JPanel(new BorderLayout());

        JTextPane area = new JTextPane();
        area.setContentType("text/html");
        area.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        area.setBackground(new Color(255, 255, 255, 0));

        area.setText("<html><body>"
                + "<p>Sorry, this feature is currently unavailable.<br>"
                + "You can only save in JVD format using 'Save' or 'Save as...' option.<br>"
                + "Thank You for your understanding.</p>"
                + "</body></html>");

        panel.add(area, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
                parent,
                panel,
                "Unavailable",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
