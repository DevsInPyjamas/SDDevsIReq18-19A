import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class SupportChild {
    private Usuario loggedUser;
    private JButton atrasButton;
    private JButton apadrinarButton;
    private JButton listaApadrinadosButton;
    private JPanel apadrinamientoPanel;

    SupportChild(Usuario loggedUser){
        this.loggedUser = loggedUser;
        apadrinamientoPanel.setSize(700, 250);
        JFrame frame = new JFrame("Gestión de Apadrinamiento");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(apadrinamientoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")){
                new GrantManagement(loggedUser);
                frame.dispose();
            }
        });
        apadrinarButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Apadrinar")) {
                new SearchChildToSupport(loggedUser);
                frame.dispose();
            }
        });
        listaApadrinadosButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Lista de niños apadrinados")) {
                new SupportedChildList(loggedUser);
                frame.dispose();
            }
        });
    }
}
