import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class NewUser {
    private JPanel nuevoUserPanel;
    private JButton backButton;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField surnameField;
    private JButton añadirButton;
    private JRadioButton coordinadorGeneralRadioButton;
    private JRadioButton responsableEconómicoGeneralRadioButton;
    private JRadioButton coordinadorGeneralDeProyectosRadioButton;
    private JRadioButton responsableEconómicoGeneralDeRadioButton;
    private JRadioButton coordinadorDeProyectoRadioButton;
    private JRadioButton responsableEconómicoDeProyectoRadioButton;
    private JRadioButton usuarioNormalRadioButton;
    private Usuario loggedUser;

    NewUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevoUserPanel.setSize(700, 500);
        JFrame frame = new JFrame("Nuevo Usuario");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setContentPane(nuevoUserPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atrás")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
    }
}
