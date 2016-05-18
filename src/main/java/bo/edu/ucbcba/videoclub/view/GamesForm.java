package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.GameController;
import bo.edu.ucbcba.videoclub.model.Game;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class GamesForm extends JDialog {
    private JTextField searchText;
    private JButton searchButton;
    private JPanel rootPanel;
    private JTable gamesTable;
    private JButton createButton;
    private GameController gameController;

    public GamesForm(HomeGamesForm parent) {
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
   /* public void showHome() {
        InitialForm form = new InitialForm(this);
        form.setVisible(true);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }*/
    private void populateTable() {
        List<Game> games = gameController.searchGames(searchText.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Description");
        model.addColumn("Year");
        model.addColumn("Company");
        model.addColumn("Rating");
        gamesTable.setModel(model);
        for (Game g : games) {
            Object[] row = new Object[5];

            row[0] = g.getTitle();
            row[1] = g.getDescription();
            row[2] = g.getReleaseYear();
            row[3] = g.getCompany();
            row[4] = g.getRating();
            model.addRow(row);
        }
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
        rootPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        searchText = new JTextField();
        rootPanel.add(searchText, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        rootPanel.add(searchButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gamesTable = new JTable();
        rootPanel.add(gamesTable, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        createButton = new JButton();
        createButton.setText("Add Game");
        rootPanel.add(createButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
