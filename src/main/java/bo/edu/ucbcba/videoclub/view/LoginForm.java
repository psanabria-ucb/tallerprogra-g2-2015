package bo.edu.ucbcba.videoclub.view;

import bo.edu.ucbcba.videoclub.controller.UserController;
import bo.edu.ucbcba.videoclub.exceptions.ValidationException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by privado on 29/05/2016.
 */
public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel rootPane;
    private JButton loginButton;
    private UserController userController;

    public LoginForm() {
        super("Login | Sakila 2.0");
        setContentPane(rootPane);
        setSize(550, 300);
        setResizable(false);
        userController = new UserController();

        switch (userController.verifyExistenceofAdminAndStaff()) {
            case 1:
                CreateUserAdmin();
                CreateUserStaff();
                break;
            case 2:
                CreateUserStaff();
                break;
            case 3:
                CreateUserAdmin();
                break;
            default:
                break;
        }


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidateUser()) {
                    goInitialForm();
                } else
                    invalidUser();
            }
        });
    }

    public void goInitialForm() {
        this.setVisible(false);
        InitialForm form = new InitialForm();
        form.setVisible(true);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void CreateUserStaff() {
        userController.create("staff", "staff123");
        JOptionPane.showMessageDialog(this, "User created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void CreateUserAdmin() {
        userController.create("admin", "admin123");
        JOptionPane.showMessageDialog(this, "User created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void invalidUser() {
        JOptionPane.showMessageDialog(this, "Username or Password are not correct", "Authentication Error", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean ValidateUser() {
        String usr = usernameField.getText();
        String pswd = passwordField.getText();
        if (!usr.isEmpty()) {
            if (!pswd.isEmpty()) {
                if (userController.validateUser(usr, pswd))
                    return true;
            } else {
                JOptionPane.showMessageDialog(this, "Password can't be blank", "Authentication Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username can't be blank", "Authentication Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
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
        rootPane = new JPanel();
        rootPane.setLayout(new GridLayoutManager(4, 4, new Insets(20, 40, 20, 40), -1, -1));
        rootPane.setBackground(new Color(-3090213));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12828863));
        label1.setFont(new Font("Courier New", Font.BOLD, 28));
        label1.setForeground(new Color(-4486332));
        label1.setText("Login");
        rootPane.add(label1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-12828863));
        label2.setFont(new Font("Courier New", Font.BOLD, 18));
        label2.setForeground(new Color(-4486332));
        label2.setText("Username:");
        rootPane.add(label2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-12828863));
        label3.setFont(new Font("Courier New", Font.BOLD, 18));
        label3.setForeground(new Color(-4486332));
        label3.setText("Password:");
        rootPane.add(label3, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernameField = new JTextField();
        usernameField.setBackground(new Color(-4477584));
        usernameField.setFont(new Font("Courier New", usernameField.getFont().getStyle(), 16));
        usernameField.setForeground(new Color(-12504532));
        rootPane.add(usernameField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(350, 30), null, 0, false));
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(-4477584));
        passwordField.setFont(new Font("Courier New", passwordField.getFont().getStyle(), 16));
        passwordField.setForeground(new Color(-12504532));
        rootPane.add(passwordField, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(350, 30), null, 0, false));
        loginButton = new JButton();
        loginButton.setBackground(new Color(-12828863));
        loginButton.setFont(new Font("Courier New", loginButton.getFont().getStyle(), 18));
        loginButton.setForeground(new Color(-4486332));
        loginButton.setText("Login");
        rootPane.add(loginButton, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, 30), null, new Dimension(120, 30), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }
}
