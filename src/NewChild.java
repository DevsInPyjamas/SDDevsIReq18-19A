import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class NewChild {
    private JPanel nuevoNinoPanel;
    private JButton backButton;
    private JButton subirFotoButton;
    private JButton addKidButton;
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField nombreMadreField;
    private JTextField nombrePadreField;
    private JTextField fechaNacimientoFIeld;
    private JEditorPane historialPane;
    private JLabel newKid;
    private JLabel nombreLabel;
    private JLabel apellidosLabel;
    private JLabel fechaNacimientoLabel;
    private JLabel nombreMadreLabel;
    private JLabel nombrePadreLabel;
    private JLabel historialLabel;
    Usuario loggedUser;

    NewChild(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        nuevoNinoPanel.setSize(700, 250);
        JFrame frame = new JFrame("Información Niño");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(nuevoNinoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });

    }

}
