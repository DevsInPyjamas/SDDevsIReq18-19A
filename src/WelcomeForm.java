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
        displayButtons();
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
                frame.dispose();
            }
        });
        adminButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Zona Admin")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
        cerrarSesionButton.addActionListener((e -> {
            if(e.getActionCommand().equals("Cerrar Sesión")) {
                new ACOESApp();
                frame.dispose();
            }
        }));
    }

    private void displayButtons(){
        if(loggedUser.getRol().isAdmin()) {
            adminButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
        }
        if(loggedUser.getRol().hasAccessGrants()) {
            becasButton.setVisible(true);
        } else {
            becasButton.setVisible(false);
        }
        if(loggedUser.getRol().hasAccessEconomic()) {
            economicoButton.setVisible(true);
        } else {
            economicoButton.setVisible(false);
        }
        modificarPerfilPropioButton.setVisible(true);
        cerrarSesionButton.setVisible(true);
    }
}
