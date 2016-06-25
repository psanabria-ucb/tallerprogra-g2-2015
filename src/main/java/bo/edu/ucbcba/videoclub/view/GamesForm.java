package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.GameController;
import bo.edu.ucbcba.videoclub.controller.Session;
import bo.edu.ucbcba.videoclub.exceptions.ControllerException;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Game;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class GamesForm extends JDialog {
    private JTextField searchText;
    private JButton searchButton;
    private JPanel rootPanel;
    private JTable gamesTable;
    private JButton createButton;
    private JRadioButton nameRadioButton;
    private JRadioButton companyRadioButton;
    private JButton deleteButton;
    private JComboBox OrderComboBox;
    private JComboBox SenseComboBox;
    private GameController gameController;
    ButtonGroup group = new ButtonGroup();

    public GamesForm(HomeGamesForm parent) {
        super(parent, "Games", true);
        setContentPane(rootPanel);
        setSize(1000, 600);
        setResizable(false);
        launchImage();
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchRegister();
            }
        });
        gameController = new GameController();
        populateTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });

        if (Session.getSession().getUtype() == 1) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
        if (Session.getSession().getUtype() == 1) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });

        OrderComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order();
            }
        });
        SenseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order();
            }
        });
        group.add(nameRadioButton);
        group.add(companyRadioButton);
    }

    public GamesForm(InitialForm parent) {
        super(parent, "Games", true);
        setContentPane(rootPanel);
        setSize(600, 400);
        setResizable(false);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchRegister();
            }
        });
        gameController = new GameController();
        populateTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });
        /*homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHome();
            }
        });*/
    }


    private void launchRegister() {
        RegisterGamesForm form = new RegisterGamesForm(this);
        form.setVisible(true);
        populateTable();
    }

    public void delete() {
        DefaultTableModel tm = (DefaultTableModel) gamesTable.getModel();
        if (gamesTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must select a row to delete");

        } else {
            int ci = (Integer) tm.getValueAt(gamesTable.getSelectedRow(), 0);
            gameController.delete(ci);
        }
        populateTable();
    }

    public void order() {
        populateTable();
    }

    /* public void showHome() {
         InitialForm form = new InitialForm(this);
         form.setVisible(true);
         form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     }*/
    private void populateTable() {
        String option = (String) OrderComboBox.getSelectedItem();
        String sense = (String) SenseComboBox.getSelectedItem();
        List<Game> games;
        if (nameRadioButton.isSelected()) {
            games = gameController.searchGames(searchText.getText(), option, sense);
        } else {
            games = gameController.searchCompany(searchText.getText(), option, sense);
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Title");
        model.addColumn("Description");
        model.addColumn("Year");
        model.addColumn("Company");
        model.addColumn("Rating");
        model.addColumn("Price");
        gamesTable.setModel(model);
        for (Game g : games) {
            Object[] row = new Object[7];
            row[0] = g.getId();
            row[1] = g.getTitle();
            row[2] = g.getDescription();
            row[3] = g.getReleaseYear();
            row[4] = g.getCompany().getName();
            row[5] = g.getRating();
            row[6] = g.getPrice();
            model.addRow(row);
        }
    }

    private void launchImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("fondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel(new ImageIcon(myPicture));
        ((JPanel) getContentPane()).setOpaque(false);
        getLayeredPane().add(label, JLayeredPane.FRAME_CONTENT_LAYER);
        label.setBounds(0, 0, myPicture.getWidth(), myPicture.getHeight());

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(8, 17, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setBackground(new Color(-3090213));
        rootPanel.setForeground(new Color(-3090213));
        searchText = new JTextField();
        searchText.setText("");
        searchText.setToolTipText("Search Game");
        rootPanel.add(searchText, new GridConstraints(2, 1, 1, 10, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setBackground(new Color(-12828863));
        searchButton.setFont(new Font("Courier New", searchButton.getFont().getStyle(), 18));
        searchButton.setForeground(new Color(-4486332));
        searchButton.setText("Search");
        rootPanel.add(searchButton, new GridConstraints(2, 13, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gamesTable = new JTable();
        gamesTable.setEnabled(true);
        gamesTable.setFont(new Font("Courier New", gamesTable.getFont().getStyle(), 16));
        gamesTable.setForeground(new Color(-11948357));
        rootPanel.add(gamesTable, new GridConstraints(5, 1, 1, 15, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(800, 300), null, 0, false));
        createButton = new JButton();
        createButton.setBackground(new Color(-12828863));
        createButton.setFont(new Font("Courier New", createButton.getFont().getStyle(), 18));
        createButton.setForeground(new Color(-4486332));
        createButton.setText("Add Game");
        rootPanel.add(createButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(6, 4, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12828863));
        label1.setFont(new Font("Courier New", Font.BOLD, 20));
        label1.setForeground(new Color(-4486332));
        label1.setText("Games");
        rootPanel.add(label1, new GridConstraints(1, 4, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        rootPanel.add(spacer2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(50, 50), null, new Dimension(50, 50), 0, false));
        final Spacer spacer3 = new Spacer();
        rootPanel.add(spacer3, new GridConstraints(5, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(50, 50), null, new Dimension(50, 50), 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(4, 1, 1, 15, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font(label2.getFont().getName(), Font.BOLD, label2.getFont().getSize()));
        label2.setText("Price");
        panel1.add(label2, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setFont(new Font(label3.getFont().getName(), Font.BOLD, label3.getFont().getSize()));
        label3.setText("Rating");
        panel1.add(label3, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font(label4.getFont().getName(), Font.BOLD, label4.getFont().getSize()));
        label4.setText("Company");
        panel1.add(label4, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setFont(new Font(label5.getFont().getName(), Font.BOLD, label5.getFont().getSize()));
        label5.setText(" Release Year");
        panel1.add(label5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setFont(new Font(label6.getFont().getName(), Font.BOLD, label6.getFont().getSize()));
        label6.setText("  Description");
        panel1.add(label6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setFont(new Font(label7.getFont().getName(), Font.BOLD, label7.getFont().getSize()));
        label7.setText("Title");
        panel1.add(label7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setFont(new Font(label8.getFont().getName(), Font.BOLD, label8.getFont().getSize()));
        label8.setText("Identifier");
        panel1.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setOpaque(false);
        panel2.setVisible(true);
        rootPanel.add(panel2, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Search by:");
        panel2.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameRadioButton = new JRadioButton();
        nameRadioButton.setOpaque(false);
        nameRadioButton.setSelected(true);
        nameRadioButton.setText("Name");
        panel2.add(nameRadioButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        companyRadioButton = new JRadioButton();
        companyRadioButton.setOpaque(false);
        companyRadioButton.setText("Company");
        panel2.add(companyRadioButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel2.add(spacer4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel2.add(spacer5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setBackground(new Color(-12828863));
        deleteButton.setFont(new Font("Courier New", deleteButton.getFont().getStyle(), 18));
        deleteButton.setForeground(new Color(-4486332));
        deleteButton.setText("Delete");
        rootPanel.add(deleteButton, new GridConstraints(3, 13, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Order by:");
        rootPanel.add(label10, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        OrderComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Title");
        defaultComboBoxModel1.addElement("Year");
        defaultComboBoxModel1.addElement("Company");
        defaultComboBoxModel1.addElement("Rating");
        defaultComboBoxModel1.addElement("Price");
        OrderComboBox.setModel(defaultComboBoxModel1);
        OrderComboBox.setOpaque(true);
        rootPanel.add(OrderComboBox, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        rootPanel.add(spacer6, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        rootPanel.add(spacer7, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Sense");
        rootPanel.add(label11, new GridConstraints(3, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SenseComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Ascendant");
        defaultComboBoxModel2.addElement("Descendant");
        SenseComboBox.setModel(defaultComboBoxModel2);
        rootPanel.add(SenseComboBox, new GridConstraints(3, 9, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
