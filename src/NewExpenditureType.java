import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class NewExpenditureType {
    private JPanel newExpenditureTypePanel;
    private JButton atrasButton;
    private JTextField nombreTextField;
    private JButton anadirButton;
    private Usuario loggedUser;

    NewExpenditureType(Usuario loggedUser){
        this.loggedUser = loggedUser;
        newExpenditureTypePanel.setSize(700, 250);
        JFrame frame = new JFrame("Nuevo tipo de gasto");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(newExpenditureTypePanel);
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
