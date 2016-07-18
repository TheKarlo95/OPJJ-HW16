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
 * {@code ExitAction} is a action class that displays the information about the
 * application and its author.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see AbstractAction
 */
public class AboutAction extends AbstractAction {

    /** Serial version UID. */
    private static final long serialVersionUID = 7530880444603806806L;

    /** Parent component of this component. */
    private Component parent;
    /** Panel to be displayed in message dialog. */
    private JPanel panel;

    /**
     * Constructs a new {@code AboutAction} with specified {@code parent} window
     * and "About" as a name of this action.
     * 
     * @param parent
     *            the parent component of this component
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public AboutAction(Component parent) {
        this(parent, "About");
    }

    /**
     * Constructs a new {@code AboutAction} with specified {@code parent} window
     * and action {@code name}.
     * 
     * @param parent
     *            the parent component of this component
     * @param name
     *            the name of this action
     * @throws NullPointerException
     *             if parameters {@code parent} is a {@code null} reference
     */
    public AboutAction(Component parent, String name) {
        super(name);
        this.parent = Objects.requireNonNull(parent, "Null parameter: parent");

        panel = new JPanel(new BorderLayout());

        JTextPane area = new JTextPane();
        area.setContentType("text/html");
        area.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        area.setBackground(new Color(255, 255, 255, 0));

        area.setText("<html><body>"
                + "<p>The 16th homework from the \"Basics of Programming Language Java\" course.</p>"
                + "<p>Features that doesn't work:"
                + "<ul>"
                + "<li>image export</li>"
                + "<li>continous repainting of geometric shape that is currently drawn</li>"
                + "</ul></p>"
                + "<p>This application is made by Karlo Vrbić (karlo.vrbic@fer.hr).</p>"
                + "</body></html>");

        panel.add(area, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
                parent,
                panel,
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
