import db_management.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminArea {
    private JButton backButton;
    private JButton newProjectButton;
    private JButton projectHistoricButton;
    private JButton listaDeUsuariosButton;
    private JButton anadirNuevoUsuaroButton;
    private JPanel adminArea;
    private JPanel adminWindow;
    private Usuario loggedUser;

    AdminArea(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        displayButtons();
        adminWindow.setSize(700, 250);
        JFrame frame = new JFrame("Admin Section");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(adminWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        backButton.addActionListener((e) -> {
            if(e.getActionCommand().equals("Atrás")) {
                new WelcomeForm(loggedUser);
                frame.dispose();
            }
        });
        newProjectButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Añadir nuevo proyecto")){
                new NewProject(loggedUser);
                frame.dispose();
            }
        });

        projectHistoricButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Historial de Proyectos")){
                new SearchProject(loggedUser);
                frame.dispose();
            }
        });
    }

    private void displayButtons() {
        if(loggedUser.getRol().isSuperAdmin()) {
            newProjectButton.setVisible(true);
            projectHistoricButton.setVisible(true);
        } else {
            newProjectButton.setVisible(false);
            projectHistoricButton.setVisible(false);
        }
    }
}
