package bo.edu.ucbcba.videoclub.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InitialForm extends JFrame{
    private JPanel rootPanel;
    private JButton MoviesButton;
    private JButton CustomersButton;
   private JButton GamesButton;

    public InitialForm(){
        super("Welcome");
        setContentPane(rootPanel);
        setSize(600, 400);
        MoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoviesZone();
            }
        });

        CustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchClientsForm();
            }
        });

    }

    private void launchClientsForm() {
        ClientsForm form = new ClientsForm(this);
        form.setVisible(true);
    }

    private void MoviesZone() {
        MoviesForm form = new MoviesForm(this);
        form.setVisible(true);
    }
}
