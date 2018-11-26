import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class WelcomeForm {
    private JButton cerrarSesionButton;
    private JButton economicoButton;
    private JButton becasButton;
    private JButton adminButton;
    private JLabel acoesappLabel;
    private JButton modificarPerfilPropioButton;
    private JPanel welcomePanel;
    private JLabel bienvenidoLabel;
    private Usuario loggedUser;

    WelcomeForm(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        buttonVisibility();
        welcomePanel.setSize(700, 250);
        JFrame frame = new JFrame("ACOES App Welcome Section!");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(welcomePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        becasButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Becas")) {
                new GrantManagement(loggedUser);
            }
        });
    }

    private void buttonVisibility(){
        if(loggedUser.getRol().isAdmin()) {
            adminButton.setVisible(true);
            becasButton.setVisible(true);
            economicoButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
        }
        modificarPerfilPropioButton.setVisible(true);
        cerrarSesionButton.setVisible(true);
    }

}
