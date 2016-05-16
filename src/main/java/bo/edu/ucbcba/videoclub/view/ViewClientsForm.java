package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.ClientController;
import bo.edu.ucbcba.videoclub.model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by privado on 16/05/2016.
 */
public class ViewClientsForm extends JFrame{
    private JTable clientsTable;
    private JTextField searchText;
    private JButton searchButton;
    private JPanel rootPanel;
    private ClientController clientController;

    public ViewClientsForm(JFrame parent){
        super("View Clients");
        setContentPane(rootPanel);
        setSize(600, 400);
        pack();
        setResizable(true);
        clientController = new ClientController();
        populateTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable();
            }
        });
    }

    private void populateTable(){
        List<Client> clients = clientController.searchClient(searchText.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CI");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Address");

        clientsTable.setModel(model);

        for (Client c : clients) {
            Object[] row = new Object[4];

            row[0] = c.getCi();
            row[1] = c.getFirstname();
            row[2] = c.getLastname();
            row[3] = c.getAddress();
            model.addRow(row);
        }
    }

}
