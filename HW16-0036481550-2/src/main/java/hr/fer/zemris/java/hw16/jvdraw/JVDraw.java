package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import hr.fer.zemris.java.hw16.jvdraw.actions.AboutAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorLabel;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.components.JShapeButton;
import hr.fer.zemris.java.hw16.jvdraw.components.JShapeButtonGroup;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.CircleShape;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircleShape;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricShape;
import hr.fer.zemris.java.hw16.jvdraw.shapes.LineShape;

/**
 * {@code JVDraw} is a frame for the application JVDraw.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see JFrame
 */
public class JVDraw extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 4663686425757832346L;

    /** Window width in pixels. */
    private static final int WINDOW_WIDTH = 800;
    /** Window height in pixels. */
    private static final int WINDOW_HEIGHT = 600;

    /** Drawing canvas. */
    private JDrawingCanvas canvas;
    /** Drawing model. */
    private DrawingModel model;
    /** Background color area. */
    private JColorArea background;
    /** Foreground color area. */
    private JColorArea foreground;
    /** Buttons used to generate geometric shapes. */
    private JShapeButtonGroup shapeButtons;
    /** Toolbar with commonly used Actions. */
    private JToolBar toolbar;
    /** Status bar that display RGB of currently used colors. */
    private JPanel colorPanel;
    /** Scroll pane that displays all drawn geometric shapes. */
    private JScrollPane shapeScrollPane;
    /** Save action. */
    private SaveAction saveAction;

    /**
     * Constructs a new {@code JVDraw} objects and initializes GUI.
     */
    public JVDraw() {
        model = new DrawingModelImpl();
        saveAction = new SaveAction(this, model);

        setTitle("JVDraw");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int option = 0;

                if (saveAction.isModified()) {
                    option = JOptionPane.showConfirmDialog(
                            JVDraw.this,
                            "Do you want to save the changes?",
                            "Save",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                } else {
                    option = JOptionPane.NO_OPTION;
                }

                switch (option) {
                    case JOptionPane.YES_OPTION:
                        saveAction.actionPerformed(null);
                    case JOptionPane.NO_OPTION:
                        dispose();
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        return;
                }
            };

        });

        initGUI();
    }

    /**
     * Initializes GUI(Graphical User Interface)
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        initCanvas();
        initShapesList();
        initMenu();
        initToolbarAndColorPanel();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(colorPanel, BorderLayout.SOUTH);
        panel.add(shapeScrollPane, BorderLayout.EAST);

        cp.add(panel, BorderLayout.CENTER);
        cp.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Initializes drawing canvas.
     */
    private void initCanvas() {
        canvas = new JDrawingCanvas((DrawingModelImpl) model);

        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                
                Point start = canvas.getStartPoint();
                Point end = e.getPoint();

                if (start != null) {
                    int width = start.x - end.x;
                    int height = start.y - end.y;

                    if (width == 0 && height == 0)
                        return;

                    model.add(shapeButtons.getShape(
                            start,
                            end,
                            foreground.getCurrentColor(),
                            background.getCurrentColor()));
                    canvas.setStartPoint(null);
                } else {
                    canvas.setStartPoint(end);
                }
            }

        });

        canvas.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                paintToCurrentPosition(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                paintToCurrentPosition(e);
            }

            public void paintToCurrentPosition(MouseEvent e) {
                Point start = canvas.getStartPoint();

                if (start != null) {
                    Point end = e.getPoint();

                    canvas.paintShape(shapeButtons.getShape(
                            start,
                            end,
                            foreground.getCurrentColor(),
                            background.getCurrentColor()));
                }
            }
        });
    }

    /**
     * Initializes shapes list.
     */
    private void initShapesList() {
        ListModel<GeometricShape> list = new DrawingObjectListModel(model);
        JList<GeometricShape> shapeList = new JList<GeometricShape>(list);

        shapeScrollPane = new JScrollPane(shapeList);
        shapeScrollPane.setPreferredSize(new Dimension(150, 0));
        shapeScrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        shapeList.setBackground(Color.GRAY);
        shapeList.setForeground(Color.WHITE);

        shapeList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    int index = shapeList.locationToIndex(e.getPoint());
                    ((DrawingObjectListModel) shapeList.getModel()).change(index, JVDraw.this);
                }
            }

        });

        shapeList.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int index = shapeList.getSelectedIndex();

                    if (index == -1)
                        return;

                    if (model.getObject(index) != null) {
                        model.remove(index);
                        shapeList.revalidate();
                        shapeList.repaint();
                    }
                }
            }
        });

        shapeList.setCellRenderer(new ListCellRenderer<GeometricShape>() {

            @Override
            public Component getListCellRendererComponent(
                    JList<? extends GeometricShape> list,
                    GeometricShape value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
                JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(
                        shapeList,
                        value,
                        index,
                        isSelected,
                        cellHasFocus);

                DrawingObjectListModel model = (DrawingObjectListModel) list.getModel();
                int id = 0;

                for (int i = 0, count = model.getSize(); i < count; i++) {
                    GeometricShape current = model.getElementAt(i);
                    if (current.getClass() == value.getClass()) {
                        id++;
                    }
                    if (current == value) {
                        break;
                    }
                }

                label.setText(value.getName() + " " + id);
                return label;
            }
        });
    }

    /**
     * Initializes menu.
     */
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveButton = new JMenuItem(saveAction);
        JMenuItem saveAsButton = new JMenuItem(new SaveAsAction(this, model));
        JMenuItem loadButton = new JMenuItem(new OpenAction(this, model, canvas, saveAction));
        JMenuItem exportButton = new JMenuItem(new ExportAction(this));
        JMenuItem exit = new JMenuItem(new ExitAction(this));

        fileMenu.add(saveButton);
        fileMenu.add(saveAsButton);
        fileMenu.add(loadButton);
        fileMenu.addSeparator();
        fileMenu.add(exportButton);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        JMenu aboutMenu = new JMenu("?");

        JMenuItem aboutItem = new JMenuItem(new AboutAction(this));

        aboutMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Initializes toolbar and color panel.
     */
    private void initToolbarAndColorPanel() {
        toolbar = new JToolBar();
        colorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        foreground = new JColorArea(this, "Foreground", Color.BLACK);
        background = new JColorArea(this, "Background", Color.WHITE);

        JLabel foregroundLabel = new JColorLabel(foreground);
        JLabel backgroundLabel = new JColorLabel(background);

        toolbar.add(foreground);
        toolbar.add(background);
        toolbar.addSeparator();
        for (JShapeButton creator : initShapeButtons()) {
            toolbar.add(creator);
        }
        toolbar.addSeparator();

        colorPanel.add(foregroundLabel);
        colorPanel.add(backgroundLabel);
        colorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    /**
     * Initializes shape buttons.
     * 
     * @return array of shape buttons
     */
    private JShapeButton[] initShapeButtons() {
        JShapeButton[] buttons = new JShapeButton[3];

        buttons[0] = new JShapeButton() {

            /** Serial version UID. */
            private static final long serialVersionUID = 7202684190877010423L;

            @Override
            public GeometricShape getShape(Point start, Point end, Color foreground, Color background) {
                return new LineShape(start, end, foreground);
            }

        };
        buttons[1] = new JShapeButton() {

            /** Serial version UID. */
            private static final long serialVersionUID = 8407288760847414299L;

            @Override
            public GeometricShape getShape(Point start, Point end, Color foreground, Color background) {
                return new CircleShape(start, end, foreground);
            }

        };
        buttons[2] = new JShapeButton() {

            /** Serial version UID. */
            private static final long serialVersionUID = 9115942957136763314L;

            @Override
            public GeometricShape getShape(Point start, Point end, Color foreground, Color background) {
                return new FilledCircleShape(start, end, foreground, background);
            }

        };

        buttons[0].setText("Line");
        buttons[1].setText("Circle");
        buttons[2].setText("Filled circle");

        shapeButtons = new JShapeButtonGroup(buttons[0], buttons[1], buttons[2]);
        return buttons;
    }

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            try {
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignorable) {
            }

            JFrame frame = new JVDraw();
            frame.setVisible(true);
        });
    }

}
