import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class SearchChildToSupport {
    private JPanel searchChildToSupport;
    private JButton atrasButton;
    private JTable childrenList;
    private Usuario loggedUser;

    SearchChildToSupport(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        searchChildToSupport.setSize(700,250);
        JFrame frame = new JFrame("Lista de niños para apadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(searchChildToSupport);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        atrasButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Atras")){
                new SupportChild(loggedUser);
                frame.dispose();
            }
        });
    }
}
