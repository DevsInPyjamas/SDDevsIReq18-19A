import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class EconomicSection {

    private JPanel gestionEconomicaPanel;
    private JButton newTransactionButton;
    private JButton historialButton;
    private JButton atrasButton;
    private Usuario loggedUser;

    public EconomicSection(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        JFrame frame= new JFrame("Menú Gestión Económica");
        gestionEconomicaPanel.setSize(new Dimension(700, 500));
        frame.setBounds(400,400,300,200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(gestionEconomicaPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        if(loggedUser.getRol().spanishBoy())
            historialButton.setVisible(false);
        newTransactionButton.addActionListener(e -> {
            new NewEconomicMovement(loggedUser);
            frame.dispose();
        });
        atrasButton.addActionListener(e -> {
            new WelcomeForm(loggedUser);
            frame.dispose();
        });
        historialButton.addActionListener(e -> {
            new SearchEconomicMovement(loggedUser);
            frame.dispose();
        });
    }
}
