import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class NewCompany {
    private JPanel newCompanypanel;
    private JButton atrasButton;
    private JTextField nombreTextField;
    private JTextField nifTextField;
    private JTextField direccionTextField;
    private JButton anadirButton;
    private Usuario loggedUser;

    NewCompany(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        newCompanypanel.setSize(700, 250);
        JFrame frame = new JFrame("Nueva empresa");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(newCompanypanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
    }
}
