package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.ClientController;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import bo.edu.ucbcba.videoclub.model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by privado on 16/05/2016.
 */
public class ClientsForm extends JFrame {
    private JButton viewClientsButton;
    private JButton addClientsButton;
    private JPanel rootPanel;

    public ClientsForm(JFrame parent) {
        super("Clients");
        setContentPane(rootPanel);
        setSize(600, 400);
        pack();
        setResizable(true);
        addClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchClientRegister();
            }
        });

        viewClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewClients();
            }
        });

    }

    private void launchClientRegister() {
        ClientRegister form = new ClientRegister(this);
        form.setVisible(true);
    }

    private void viewClients(){
        ViewClientsForm form = new ViewClientsForm(this);
        form.setVisible(true);
    }

}
