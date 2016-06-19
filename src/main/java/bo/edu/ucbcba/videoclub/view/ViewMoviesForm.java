package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.MovieController;
import bo.edu.ucbcba.videoclub.model.Movie;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class ViewMoviesForm extends JDialog {
    private JPanel rootPanel;
    private JTextField searchText;
    private JButton searchButton;
    private JTable moviesTable;
    private JButton viewButton;
    private JRadioButton Titleradiobutton;
    private JRadioButton Directorradiobutton;
    private JButton Deletebutton;
    private JButton Export;
    private JButton Print;
    private MovieController movieController;
    ButtonGroup group = new ButtonGroup();

    public ViewMoviesForm(HomeMoviesForm parent) {
        super(parent, "Movies", true);
        setContentPane(rootPanel);
        setSize(900, 400);
        setResizable(false);
        movieController = new MovieController();
        populateTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view();
            }
        });
        Deletebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        Titleradiobutton.setSelected(true);
        group.add(Titleradiobutton);
        group.add(Directorradiobutton);
        Export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excel(moviesTable);
            }
        });

        Print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utilJTablePrint(moviesTable, "Movies", "End",
                        true);
            }
        });


    }

    private void view() {
        DefaultTableModel tm = (DefaultTableModel) moviesTable.getModel();
        if (moviesTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must select a row to View Details");

        } else {
            String title = (String) tm.getValueAt(moviesTable.getSelectedRow(), 0);
            List<Movie> movies = movieController.searchMovies2(title);
            for (Movie m : movies) {
                ViewDetailsMovies form = new ViewDetailsMovies(this, m.getTitle(), m.getNameImage(), m.getDescription());
                form.setVisible(true);
            }
        }
    }

    private void delete() {
        DefaultTableModel tm = (DefaultTableModel) moviesTable.getModel();
        if (moviesTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must select a row to delete");

        } else {
            String title = (String) tm.getValueAt(moviesTable.getSelectedRow(), 0);
            movieController.delete(title);
        }
        populateTable();
    }

    private void populateTable() {
        List<Movie> movies;
        if (Titleradiobutton.isSelected() == true) {
            movies = movieController.searchMovies(searchText.getText());
        } else {
            movies = movieController.searchDirectors(searchText.getText());
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Director");
        model.addColumn("Rating");
        model.addColumn("Release Year");
        model.addColumn("Length");
        model.addColumn("Price");
        moviesTable.setModel(model);

        for (Movie m : movies) {
            Object[] row = new Object[6];

            row[0] = m.getTitle();
            row[1] = m.getDirector();
            row[2] = m.getRating();
            row[3] = m.getReleaseYear();
            row[4] = String.format("%s:%s", m.getLength() / 60, m.getLength() % 60);
            row[5] = m.getPrice();


            model.addRow(row);
        }
    }


    public void excel(JTable table) {
        {
            JFileChooser fc = new JFileChooser();
            int option = fc.showSaveDialog(table);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fc.getSelectedFile().getName();
                String path = fc.getSelectedFile().getParentFile().getPath();
                int len = filename.length();
                String ext = "";
                String file = "";
                if (len > 4) {
                    ext = filename.substring(len - 4, len);
                }
                if (ext.equals(".xls")) {
                    file = path + "\\" + filename;
                } else {
                    file = path + "\\" + filename + ".xls";
                }
                try {
                    toExcel(table, new File(file));
                    JOptionPane.showMessageDialog(this, "The table was exported successfully");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "\n" +
                            "An error has occurred when exporting", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }


    public void toExcel(JTable tabla, File ficheroXLS) throws IOException {
        TableModel modelo = tabla.getModel();
        FileWriter fichero = new FileWriter(ficheroXLS);

        for (int i = 0; i < modelo.getColumnCount(); i++) {
            fichero.write(modelo.getColumnName(i) + "\t");
        }
        fichero.write("\n");
        for (int i = 0; i < modelo.getRowCount(); i++) {
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fichero.write(modelo.getValueAt(i, j).toString() + "\t");
            }
            fichero.write("\n");
        }
        fichero.close();
    }

    public void utilJTablePrint(JTable jTable, String header, String footer, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        // We define the print mode (Definimos el modo de impresión)
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // Print the table (Imprimo la <span id="IL_AD1" class="IL_AD">tabla</span>)
            boolean complete = jTable.print(mode,
                    new MessageFormat(header),
                    new MessageFormat(footer),
                    showPrintDialog,
                    null,
                    interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
                JOptionPane.showMessageDialog(jTable,
                        "Print complete",
                        "Print result",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostramos un mensaje indicando que la impresión fue cancelada
                JOptionPane.showMessageDialog(jTable,
                        "Print canceled",
                        "Print result",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable,
                    "Print fail " + pe.getMessage(),
                    "Print result ",
                    JOptionPane.ERROR_MESSAGE);
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
        rootPanel.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setBackground(new Color(-12828863));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-12828863));
        rootPanel.add(panel1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Titleradiobutton = new JRadioButton();
        Titleradiobutton.setBackground(new Color(-12828863));
        Titleradiobutton.setForeground(new Color(-4486332));
        Titleradiobutton.setText("Title");
        panel1.add(Titleradiobutton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Directorradiobutton = new JRadioButton();
        Directorradiobutton.setBackground(new Color(-12828863));
        Directorradiobutton.setForeground(new Color(-4486332));
        Directorradiobutton.setText("Director");
        panel1.add(Directorradiobutton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12828863));
        label1.setForeground(new Color(-4486332));
        label1.setText("Search By:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchText = new JTextField();
        searchText.setToolTipText("Search by name");
        rootPanel.add(searchText, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setBackground(new Color(-12828863));
        searchButton.setForeground(new Color(-4486332));
        searchButton.setIcon(new ImageIcon(getClass().getResource("/icons/magnifier.png")));
        searchButton.setText("Search");
        rootPanel.add(searchButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(0);
        label2.setHorizontalTextPosition(0);
        label2.setText("Title");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Year");
        panel2.add(label3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Rating");
        panel2.add(label4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Director");
        panel2.add(label5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Price");
        panel2.add(label6, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Length");
        panel2.add(label7, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-12828863));
        rootPanel.add(panel3, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Deletebutton = new JButton();
        Deletebutton.setBackground(new Color(-12828863));
        Deletebutton.setForeground(new Color(-4486332));
        Deletebutton.setIcon(new ImageIcon(getClass().getResource("/icons/minus-sign.png")));
        Deletebutton.setText("Delete");
        panel3.add(Deletebutton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        viewButton = new JButton();
        viewButton.setBackground(new Color(-12828863));
        viewButton.setForeground(new Color(-4486332));
        viewButton.setIcon(new ImageIcon(getClass().getResource("/icons/correct-symbol.png")));
        viewButton.setText("View");
        panel3.add(viewButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Export = new JButton();
        Export.setBackground(new Color(-12828863));
        Export.setForeground(new Color(-4486332));
        Export.setIcon(new ImageIcon(getClass().getResource("/icons/spreadsheet-cell-row.png")));
        Export.setText(" Export to excel");
        panel3.add(Export, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Print = new JButton();
        Print.setBackground(new Color(-12828863));
        Print.setForeground(new Color(-4486332));
        Print.setIcon(new ImageIcon(getClass().getResource("/icons/printer.png")));
        Print.setText("Print");
        panel3.add(Print, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moviesTable = new JTable();
        moviesTable.setToolTipText("");
        rootPanel.add(moviesTable, new GridConstraints(3, 0, 3, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
