package bo.edu.ucbcba.videoclub;

import bo.edu.ucbcba.videoclub.view.MoviesForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MoviesForm form = new MoviesForm();
        form.setVisible(true);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
