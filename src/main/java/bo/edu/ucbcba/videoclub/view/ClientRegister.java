package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.ClientController;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import bo.edu.ucbcba.videoclub.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientRegister extends JDialog{
    private JTextField ci;
    private JTextField firstname;
    private JTextField lastname;
    private JButton registerButton;
    private JPanel rootPanel;
    private JTextField address;
    private JButton cancelButton;
    private ClientController clientController;

    public ClientRegister(JFrame parent) {
        super(parent, "Register Client", true);
        setContentPane(rootPanel);
        pack();
        setResizable(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClient();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        clientController = new ClientController();
    }

    private void saveClient() {
        try {
            clientController.create(ci.getText(),
                    firstname.getText(),
                    lastname.getText(),
                    address.getText());

        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Format error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, "Client created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        cancel();
    }

    private void cancel() {
        setVisible(false);
        dispose();
    }


}
