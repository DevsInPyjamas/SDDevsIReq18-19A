import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class GrantManagement {
    private JPanel gestionBecasPanel;
    private JButton backButton;
    private JLabel gestionBecasLabel;
    private JButton addNewKidButton;
    private JButton searchKidButton;
    private JButton buscarSocioButton;
    private JButton addNewSocioButton;
    private JButton apadrinamientoButton;
    private Usuario loggedUser;


    GrantManagement(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        gestionBecasPanel.setSize(700, 250);
        JFrame frame = new JFrame("Gestión de Becas");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(gestionBecasPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new WelcomeForm(loggedUser);
                frame.dispose();
            }
        });
        addNewKidButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Añadir nuevo niño")) {
                new NewChild(loggedUser);
                frame.dispose();
            }
        });
        searchKidButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Buscar niño")) {
                new SearchChild(loggedUser);
                frame.dispose();
            }
        });
        apadrinamientoButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Apadrinamiento")) {
                new SupportChild(loggedUser);
                frame.dispose();
            }
        });
        addNewSocioButton.addActionListener(e -> {
            new NewMember(loggedUser);
            frame.dispose();
        });
    }
}
