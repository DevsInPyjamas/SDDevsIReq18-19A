import db_management.DBManager;
import db_management.Usuario;

import javax.swing.*;
import java.awt.*;

public class NewProjectType {
    private JPanel newProjectTypePanel;
    private JButton atrasButton;
    private JTextField nombreTextField;
    private JButton anadirButton;
    private Usuario loggedUser;

    NewProjectType(Usuario loggedUser) {
        this.loggedUser = loggedUser;
        newProjectTypePanel.setSize(700, 250);
        JFrame frame = new JFrame("Nuevo tipo de proyecto");
        frame.setBounds(400, 400, 300, 200);
        frame.setMinimumSize(new Dimension(700, 250));
        frame.setContentPane(newProjectTypePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        atrasButton.addActionListener(e -> {
            if (e.getActionCommand().equals("Atras")) {
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
        anadirButton.addActionListener(e -> {
            if(e.getActionCommand().equals("Añadir")) {
                DBManager dbManager = new DBManager();
                dbManager.execute("insert into TipoProyecto(nombre) values ('" + this.nombreTextField.getText()
                        + "');");
                JOptionPane.showMessageDialog(new JFrame(), "Se ha añadido el tipo de proyecto correctamente");
                new AdminArea(loggedUser);
                frame.dispose();
            }
        });
    }
}
