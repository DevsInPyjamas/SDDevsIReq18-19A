import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class GrantManagement {
    private JPanel gestionBecasPanel;
    private JButton atrasButton;
    private JLabel gestionBecasLabel;
    private JButton añadirNuevoNiñoButton;
    private JButton buscarNiñoButton;
    private JButton buscarSocioButton;
    private JButton añadirNuevoSocioButton;

    private Usuario loggedUser;

    GrantManagement(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        gestionBecasPanel.setSize(700, 250);
        JFrame frame = new JFrame("Gestion Becas");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(gestionBecasPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
