import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class UncheckChildAsSupported {
    private JPanel ucheckChildAsSupported;
    private JButton atrasButton;
    private JTable childrenList;
    private Usuario loggedUser;

    UncheckChildAsSupported(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        ucheckChildAsSupported.setSize(700,250);
        JFrame frame = new JFrame("Lista de niños para desapadrinar");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(ucheckChildAsSupported);
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
