package bo.edu.ucbcba.videoclub;

import bo.edu.ucbcba.videoclub.view.InitialForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        InitialForm form = new InitialForm();
        form.setVisible(true);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
